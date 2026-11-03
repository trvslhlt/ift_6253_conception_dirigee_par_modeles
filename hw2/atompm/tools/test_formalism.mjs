// Checks that the formalism accepts a valid map and that validation rejects
// one violation of each rule. Nothing is saved. Needs AToMPM running.
import { Atompm } from './client.mjs';
import { CANVAS_TOP } from './geometry.mjs';

const CSMM = '/Formalisms/MindMap/MindMap.defaultIcons';
const type = name => `${CSMM}/${name}.type`;
const path = { pos: [0, 0], segments: ['M0,0L0,0', 'M0,0L0,0'] };

let failed = 0;
const report = (ok, what, detail = '') => {
	if (!ok) failed++;
	console.log(`${ok ? 'ok  ' : 'FAIL'} ${what}${ok ? '' : ` ${detail}`}`);
};

// A valid map: title, marker 1, central C, main M (marker 1), sub S under M.
async function base(a) {
	await a.newPage();
	await a.loadmm(`${CSMM}.metamodel`);
	const node = async (icon, at, attrs) => {
		const uri = await a.create(type(icon), at);
		await a.update(uri, attrs);
		return uri;
	};
	const link = (name, src, dest) => a.create(type(name), { src, dest, ...path });
	const n = {};
	n.map = await node('MindMapIcon', [40, CANVAS_TOP], { title: 'T' });
	n.marker = await node('MarkerIcon', [300, CANVAS_TOP], { symbol: '1' });
	await link('markersLink', n.map, n.marker);
	n.central = await node('CentralTopicIcon', [40, CANVAS_TOP + 160], { name: 'C' });
	await link('topicLink', n.map, n.central);
	n.main = await node('MainTopicIcon', [300, CANVAS_TOP + 160], { name: 'M' });
	await link('maintopicsLink', n.central, n.main);
	await link('markerLink', n.main, n.marker);
	n.sub = await node('SubTopicIcon', [500, CANVAS_TOP + 160], { name: 'S' });
	await link('subtopicsLink', n.main, n.sub);
	return { n, node, link };
}

const validate = a => a.req('GET', '/validatem');

const VIOLATIONS = {
	'second mind map': ({ node }) => node('MindMapIcon', [40, CANVAS_TOP + 460], { title: 'U' }),
	'main topic without a central topic': ({ node }) => node('MainTopicIcon', [40, CANVAS_TOP + 460], { name: 'X' }),
	'central topic without a map': ({ node }) => node('CentralTopicIcon', [40, CANVAS_TOP + 460], { name: 'X' }),
	'sub-topic without a parent': ({ node }) => node('SubTopicIcon', [40, CANVAS_TOP + 460], { name: 'X' }),
	'sub-topic with two parents': async ({ n, node, link }) => {
		const x = await node('SubTopicIcon', [40, CANVAS_TOP + 460], { name: 'X' });
		await link('subtopicsLink', n.main, x);
		await link('subsubtopicsLink', n.sub, x);
	},
	'cycle of sub-topics': async ({ node, link }) => {
		const x = await node('SubTopicIcon', [40, CANVAS_TOP + 460], { name: 'X' });
		const y = await node('SubTopicIcon', [240, CANVAS_TOP + 460], { name: 'Y' });
		await link('subsubtopicsLink', x, y);
		await link('subsubtopicsLink', y, x);
	},
	'duplicate marker symbol': async ({ n, node, link }) => {
		const m = await node('MarkerIcon', [400, CANVAS_TOP], { symbol: '1' });
		await link('markersLink', n.map, m);
	},
	'marker outside the map': ({ node }) => node('MarkerIcon', [400, CANVAS_TOP], { symbol: '2' }),
	'empty topic name': ({ n, a }) => a.update(n.sub, { name: ' ' }),
};

const a = await Atompm.open();
try {
	await base(a);
	const r = await validate(a);
	report(r.status === 200, 'valid map is accepted', r.text);
	for (const [what, violate] of Object.entries(VIOLATIONS)) {
		const ctx = await base(a);
		let rejected;
		try {
			await violate({ ...ctx, a });
			rejected = (await validate(a)).status !== 200;
		} catch {
			rejected = true; // refused at edit time (a maximum cardinality)
		}
		report(rejected, `rejects ${what}`);
	}
} finally {
	await a.close();
}
process.exit(failed ? 1 : 0);
