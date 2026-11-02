// Builds the AToMPM sample models from the textual .mm samples: creates them in
// the generated editor, validates them, runs T_ComputeLevels, checks its results
// against the .mm tree, saves, and takes a screenshot. Needs AToMPM on :8124.
import { mkdirSync, readFileSync } from 'node:fs';
import { dirname, join } from 'node:path';
import { fileURLToPath } from 'node:url';
import { Atompm } from './client.mjs';
import { allTopics, check, layout, MAP_DIAMETER, readMindMap, route, TAG_DIAMETER, TOPIC_HEIGHT, topicWidth } from './mm.mjs';

const HERE = dirname(fileURLToPath(import.meta.url));
const SAMPLES = join(HERE, '..', '..', 'samples');
const USER_DIR = join(HERE, '..', 'users', 'travis');
const CSMM = '/Formalisms/MindMap/MindMap.defaultIcons';
const TRANSFORMATION = '/Models/MindMap/T_ComputeLevels.model';
const type = name => `${CSMM}/${name}.type`;

const TARGETS = [
	{ mm: join(SAMPLES, 'model00.mm'), name: 'model00' },
	{ mm: join(SAMPLES, 'courseProject00.mm'), name: 'courseProject00' },
	{ mm: join(HERE, 'sample0.mm'), name: 'sample0' },
];

async function create(a, map) {
	const uris = {};
	const topicLeft = t => [t.pos[0], t.pos[1] + TOPIC_HEIGHT / 2];
	const topicRight = t => [t.pos[0] + topicWidth(t), t.pos[1] + TOPIC_HEIGHT / 2];
	const topicBottom = t => [t.pos[0] + topicWidth(t) / 2, t.pos[1] + TOPIC_HEIGHT];
	const tagAt = (tag, dx, dy) => [map.tagPos[tag][0] + dx, map.tagPos[tag][1] + dy];
	const link = (name, src, dest, from, to, mid) => a.create(type(name), { src, dest, ...route(from, to, mid) });

	const mapUri = await a.create(type('MindMapIcon'), map.pos);
	await a.update(mapUri, { name: map.name });
	const mapRight = [map.pos[0] + MAP_DIAMETER, map.pos[1] + MAP_DIAMETER / 2];
	const mapBottom = [map.pos[0] + MAP_DIAMETER / 2, map.pos[1] + MAP_DIAMETER];

	for (const tag of map.tags) {
		uris[`tag:${tag}`] = await a.create(type('TagIcon'), map.tagPos[tag]);
		await a.update(uris[`tag:${tag}`], { name: tag });
		await link('hasTagLink', mapUri, uris[`tag:${tag}`], mapBottom, tagAt(tag, TAG_DIAMETER / 2, 0));
	}

	const addTopic = async (t, parentUri, name, from) => {
		const uri = uris[t.name] = await a.create(type('TopicIcon'), t.pos);
		await a.update(uri, {
			name: t.name, isDeeper: t.deeper, isDone: t.done, priority: t.priority,
			description: t.description, color: t.color,
		});
		await link(name, parentUri, uri, from, topicLeft(t));
		for (const c of t.children) await addTopic(c, uri, 'childrenLink', topicRight(t));
	};
	for (const t of map.roots) await addTopic(t, mapUri, 'rootsLink', mapRight);

	const byName = Object.fromEntries(allTopics(map).map(t => [t.name, t]));
	for (const t of allTopics(map)) {
		for (const tag of t.tags)
			await link('taggedLink', uris[t.name], uris[`tag:${tag}`], topicBottom(t), tagAt(tag, TAG_DIAMETER, TAG_DIAMETER / 2));
		for (const r of t.related) {
			// Bow out to the right so the link clears the labels between the two topics.
			const [from, to] = [topicRight(t), topicRight(byName[r])];
			await link('relatedLink', uris[t.name], uris[r], from, to, [Math.max(from[0], to[0]) + 70, (from[1] + to[1]) / 2]);
		}
	}
}

// What the transformation must compute, derived from the .mm tree alone.
function expected(map) {
	const out = {};
	const walk = (t, level, done) => {
		const l = level + (t.deeper ? 1 : 0), d = done || t.done;
		out[t.name] = { level: l, effectivelyDone: d };
		t.children.forEach(c => walk(c, l, d));
	};
	map.roots.forEach(t => walk(t, 0, false));
	return out;
}

function verifySaved(map, name) {
	const { asm } = JSON.parse(readFileSync(join(USER_DIR, 'Models', 'MindMap', `${name}.model`), 'utf8'));
	const want = expected(map);
	const topics = Object.values(asm.nodes).filter(n => n.$type.endsWith('/Topic'));
	const wrong = topics.filter(n => {
		const w = want[n.name.value];
		return !w || n.level.value !== w.level || n.effectivelyDone.value !== w.effectivelyDone;
	}).map(n => n.name.value);
	if (topics.length !== Object.keys(want).length || wrong.length)
		throw new Error(`${name}: ${topics.length}/${Object.keys(want).length} topics saved, wrong values on ${wrong}`);
	return topics.length;
}

const only = process.argv.slice(2);
const a = await Atompm.open({ width: 2600, height: 4400 });
try {
	mkdirSync(join(HERE, 'out'), { recursive: true });
	for (const { mm, name } of TARGETS) {
		if (only.length && !only.includes(name)) continue;
		const map = layout(readMindMap(mm));
		const errors = check(map);
		if (errors.length) throw new Error(`${name}: ${errors.join('; ')}`);

		await a.newPage();
		await a.loadmm(`${CSMM}.metamodel`);
		await create(a, map);
		await a.validate();
		await a.transform(TRANSFORMATION);
		await a.writeFile(`/Models/MindMap/${name}.model`);
		await a.settle(800);
		const n = verifySaved(map, name);
		await a.canvasShot(join(HERE, 'out', `${name}.png`));
		console.log(`${name}: ${n} topics valid, transformation results match the .mm tree`);
	}
	if (a.errors.length) console.log('client errors:', a.errors);
} finally {
	await a.close();
}
