// Checks the built formalism: each constraint must reject its violation and the
// saved models must load in a fresh client. Needs AToMPM on :8124.
import { dirname, join } from 'node:path';
import { fileURLToPath } from 'node:url';
import { Atompm } from './client.mjs';

const HERE = dirname(fileURLToPath(import.meta.url));
const CSMM = '/Formalisms/MindMap/MindMap.defaultIcons';
const type = name => `${CSMM}/${name}.type`;

// A valid map with one root, one child and one tag.
async function base(a) {
	await a.newPage();
	await a.loadmm(`${CSMM}.metamodel`);
	const map = await a.create(type('MindMapIcon'), [40, 40]);
	const root = await a.create(type('TopicIcon'), [300, 40]);
	const child = await a.create(type('TopicIcon'), [600, 40]);
	const tag = await a.create(type('TagIcon'), [40, 300]);
	await a.update(root, { name: 'root' });
	await a.update(child, { name: 'child' });
	await a.create(type('rootsLink'), { src: map, dest: root });
	await a.create(type('childrenLink'), { src: root, dest: child });
	await a.create(type('hasTagLink'), { src: map, dest: tag });
	return { map, root, child, tag };
}

const VIOLATIONS = {
	'exactly-one-mindmap': a => a.create(type('MindMapIcon'), [40, 500]),
	'unique-topic-names': (a, m) => a.update(m.child, { name: 'root' }),
	'unique-tag-names': async (a, m) => {
		const tag = await a.create(type('TagIcon'), [200, 300]);
		await a.create(type('hasTagLink'), { src: m.map, dest: tag });
	},
	'valid-topic-colors': (a, m) => a.update(m.child, { color: 'blue' }),
	'no-deeper-at-root': (a, m) => a.update(m.root, { isDeeper: true }),
	'no-self-relation': (a, m) => a.create(type('relatedLink'), { src: m.child, dest: m.child }),
	'topics-form-a-tree': (a, m) => a.create(type('childrenLink'), { src: m.child, dest: m.root }),
	'tags-belong-to-the-map': async a => {
		await a.update(await a.create(type('TagIcon'), [200, 300]), { name: 'orphan' });
	},
};

const MODELS = ['MindMap', 'MindMap.defaultIcons', 'model00', 'courseProject00', 'sample0',
	'R_Reset', 'R_Roots', 'R_Propagate', 'T_ComputeLevels'];

const a = await Atompm.open({ width: 2600, height: 1800 });
let failed = 0;
const report = (ok, what, detail = '') => {
	if (!ok) failed++;
	console.log(`${ok ? 'ok  ' : 'FAIL'} ${what}${detail && ` (${detail})`}`);
};
try {
	await base(a);
	report((await a.req('GET', '/validatem')).status === 200, 'valid base map is accepted');

	for (const [name, violate] of Object.entries(VIOLATIONS)) {
		const m = await base(a);
		await violate(a, m);
		const r = await a.req('GET', '/validatem');
		report(r.status !== 200 && String(r.text).includes(name), `rejects ${name}`, String(r.text).slice(0, 80));
	}

	for (const name of MODELS) {
		await a.newPage();
		await a.load(`/Models/MindMap/${name}.model`);
		const icons = (await a.iconUris()).length;
		report(icons > 0, `loads ${name}`, `${icons} elements`);
		await a.canvasShot(join(HERE, 'out', `loaded_${name}.png`));
	}
	// The way a person reruns it: open a saved map, then press play.
	await a.newPage();
	await a.load('/Models/MindMap/model00.model');
	await a.transform('/Models/MindMap/T_ComputeLevels.model');
	report((await a.req('GET', '/validatem')).status === 200, 'T_ComputeLevels reruns on a saved model');

	report(a.errors.length === 0, 'no client errors', a.errors.slice(0, 3).join(' | '));
} finally {
	await a.close();
}
process.exit(failed ? 1 : 0);
