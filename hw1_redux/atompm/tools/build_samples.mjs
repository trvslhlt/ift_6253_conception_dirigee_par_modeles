// Builds the AToMPM sample models from the textual samples the Xtext editor
// uses (../../xtext/samples/*.mindmap): creates each one in the generated
// editor, validates it, checks the saved model against the text, saves it and
// takes a screenshot. Needs AToMPM running (make run, port 8125).
import { mkdirSync, readFileSync } from 'node:fs';
import { dirname, join } from 'node:path';
import { fileURLToPath } from 'node:url';
import { Atompm } from './client.mjs';
import { MARKER_R, SIZES, TITLE_HEIGHT } from './geometry.mjs';
import { check, layout, readMindMap, route, shape } from './mm.mjs';

const HERE = dirname(fileURLToPath(import.meta.url));
const SAMPLES = join(HERE, '..', '..', 'xtext', 'samples');
const USER_DIR = join(HERE, '..', 'users', 'travis');
const CSMM = '/Formalisms/MindMap/MindMap.defaultIcons';
const type = name => `${CSMM}/${name}.type`;

const TARGETS = ['research', 'courseProject'];

const ICON = { central: 'CentralTopicIcon', main: 'MainTopicIcon', sub: 'SubTopicIcon' };
const CHILD_LINK = { central: 'maintopicsLink', main: 'subtopicsLink', sub: 'subsubtopicsLink' };

async function create(a, map) {
	const link = (name, src, dest, from, to) => a.create(type(name), { src, dest, ...route(from, to) });
	const left = t => [t.pos[0], t.anchorY];
	const right = t => [t.pos[0] + shape(t).width, t.anchorY];

	const mapUri = await a.create(type('MindMapIcon'), map.pos);
	await a.update(mapUri, { title: map.title });
	const titleRight = [map.pos[0] + SIZES.titleWidth(map.title.length), map.pos[1] + TITLE_HEIGHT / 2];

	const markers = {};
	for (const m of map.markers) {
		const at = map.markerPos[m];
		markers[m] = await a.create(type('MarkerIcon'), at);
		await a.update(markers[m], { symbol: m });
		await link('markersLink', mapUri, markers[m], titleRight, [at[0], at[1] + MARKER_R]);
	}

	// The marker link comes before the name, which reruns the label mappers.
	const addTopic = async (t, parentUri, parentLink, from) => {
		const uri = await a.create(type(ICON[t.kind]), t.pos);
		await link(parentLink, parentUri, uri, from, left(t));
		if (t.marker) {
			const at = map.markerPos[t.marker];
			await link('markerLink', uri, markers[t.marker], [t.pos[0] + 14, t.pos[1]], [at[0] + MARKER_R, at[1] + 2 * MARKER_R]);
		}
		await a.update(uri, { name: t.name });
		for (const c of t.children) await addTopic(c, uri, CHILD_LINK[t.kind], right(t));
	};
	await addTopic(map.topic, mapUri, 'topicLink', [map.pos[0] + 30, map.pos[1] + TITLE_HEIGHT]);
}

// The saved model has the text's topics, each with its type, parent and marker.
function verifySaved(map, name) {
	const { asm } = JSON.parse(readFileSync(join(USER_DIR, 'Models', 'MindMap', `${name}.model`), 'utf8'));
	const typeOf = id => asm.nodes[id].$type.split('/').at(-1);
	const out = (id, t) => asm.edges.filter(e => e.src === id && typeOf(e.dest) === t).map(e => asm.edges.find(f => f.src === e.dest).dest);
	const KIND = { CentralTopic: 'central', MainTopic: 'main', SubTopic: 'sub' };
	const seen = [];
	const walk = (id, parent) => {
		const n = asm.nodes[id];
		const marker = out(id, 'marker').map(m => asm.nodes[m].symbol.value)[0] ?? null;
		const children = out(id, { CentralTopic: 'maintopics', MainTopic: 'subtopics', SubTopic: 'subsubtopics' }[typeOf(id)]);
		seen.push(`${parent}>${KIND[typeOf(id)]}:${n.name.value}:${marker}`);
		children.forEach(c => walk(c, n.name.value));
	};
	const mapId = Object.keys(asm.nodes).find(id => typeOf(id) === 'MindMap');
	walk(out(mapId, 'topic')[0], map.title);

	const want = [];
	const expect = (t, parent) => {
		want.push(`${parent}>${t.kind}:${t.name}:${t.marker}`);
		t.children.forEach(c => expect(c, t.name));
	};
	expect(map.topic, map.title);
	const missing = want.filter(w => !seen.includes(w)), extra = seen.filter(s => !want.includes(s));
	if (missing.length || extra.length) throw new Error(`${name}: missing ${missing}, unexpected ${extra}`);
	return want.length;
}

const only = process.argv.slice(2);
const a = await Atompm.open({ width: 2200, height: 3600 });
try {
	mkdirSync(join(HERE, 'out'), { recursive: true });
	for (const name of TARGETS) {
		if (only.length && !only.includes(name)) continue;
		const map = layout(readMindMap(join(SAMPLES, `${name}.mindmap`)));
		const errors = check(map);
		if (errors.length) throw new Error(`${name}: ${errors.join('; ')}`);

		await a.newPage();
		await a.loadmm(`${CSMM}.metamodel`);
		await create(a, map);
		await a.validate();
		await a.writeFile(`/Models/MindMap/${name}.model`);
		await a.settle(800);
		const n = verifySaved(map, name);
		await a.canvasShot(join(HERE, 'out', `${name}.png`));
		console.log(`${name}: ${n} topics, ${map.markers.length} markers, valid and saved as written`);
	}
	if (a.errors.length) console.log('client errors:', a.errors);
} finally {
	await a.close();
}
