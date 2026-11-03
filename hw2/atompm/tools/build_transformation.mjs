// Builds the MoTif transformation T_AssignMarkers and its rules R_*.model in
// AToMPM's own rule and schedule editors, saves them, and takes a picture of
// each. Rerunning replaces them. Needs AToMPM running (make run) and the
// pattern metamodels from build_formalism.mjs.
//
// Markers are Marker nodes owned by the map, and a topic's marker is its marker
// link, as in the assignment's metamodel. The transformation:
//   1. CreateFirstMarker: a map without markers gets marker 1.
//   2. Dedup*: of two siblings linked to the same marker, the one whose name
//      comes later loses its link. This is the only rule that removes a
//      marker, so a marker that conflicts with no sibling is kept (bonus 1).
//   3. Assign*: an unmarked topic gets a marker none of its siblings has.
//   4. HasUnmarkedTopic (a query), then CreateMarker: when no Assign applies
//      but a topic is still unmarked, every marker is taken among its
//      siblings, so add max + 1 and go back to 3. Otherwise, done.
//
// AToMPM's matcher (pytcore/tcore/matcher.py) reuses one variable for the
// LHS match and for each bound NAC's matches, so after a NAC that matched
// structurally but failed its condition, the next NAC is bound wrongly. No
// rule here has a conditional NAC next to another NAC: that is why the
// unmarked-topic check is a query of its own instead of a NAC of CreateMarker.
import { mkdirSync } from 'node:fs';
import { dirname, join } from 'node:path';
import { fileURLToPath } from 'node:url';
import { Atompm } from './client.mjs';
import { CANVAS_TOP, connect, jitter } from './geometry.mjs';

const HERE = dirname(fileURLToPath(import.meta.url));
const PCS = '/Formalisms/MindMap/MindMap.defaultIcons.pattern';
const RCS = '/Formalisms/__Transformations__/TransformationRule/TransformationRule.defaultIcons';
const MOTIF = '/Formalisms/__Transformations__/Transformation/MoTif.defaultIcons';
const DIR = '/Models/MindMap';

// ---------------------------------------------------------------- rules
// A rule lists its elements once, as label: [type, position in the pattern
// box], and each pattern says which of them it holds. Links are
// [label, association, source label, target label]. An LHS element missing
// from the RHS is deleted; an RHS element missing from the LHS is created.
const ATTRS = { MindMap: ['title'], Marker: ['symbol'], Topic: ['name'], CentralTopic: ['name'], MainTopic: ['name'], SubTopic: ['name'] };

// Setting a topic's name to itself reruns its icon's mappers, which is how
// its marker label gets redrawn after its marker link changes.
const redraw = label => `setAttr('name', getAttr('name', '${label}'), '${label}')`;

// The four parent-child links. A topic's siblings are the other targets of
// its parent's link of the same association.
const PARENTS = [
	{ suffix: 'Central', link: 'topic', parent: 'MindMap', child: 'CentralTopic' },
	{ suffix: 'Main', link: 'maintopics', parent: 'CentralTopic', child: 'MainTopic' },
	{ suffix: 'Sub', link: 'subtopics', parent: 'MainTopic', child: 'SubTopic' },
	{ suffix: 'SubSub', link: 'subsubtopics', parent: 'SubTopic', child: 'SubTopic' },
];

const RULES = {
	CreateFirstMarker: {
		elements: { 0: ['MindMap', [20, 60]], 1: ['Marker', [280, 260]] },
		nacs: [{ name: 'NAC_hasMarker', nodes: [0, 1], links: [[2, 'markers', 0, 1]] }],
		lhs: { nodes: [0] },
		rhs: { nodes: [0, 1], links: [[2, 'markers', 0, 1]], set: { 1: { symbol: "result = '1'" } } },
	},
};

for (const { suffix, link, parent, child } of PARENTS.slice(1)) {
	RULES[`Dedup${suffix}`] = {
		elements: { 0: [parent, [80, 40]], 1: [child, [20, 220]], 2: [child, [210, 220]], 3: ['Marker', [170, 400]] },
		lhs: {
			nodes: [0, 1, 2, 3],
			links: [[4, link, 0, 1], [5, link, 0, 2], [6, 'marker', 1, 3], [7, 'marker', 2, 3]],
			// Also tells (1) and (2) apart, so each pair matches only one way.
			condition: "result = getAttr('name', '1') <= getAttr('name', '2')",
		},
		rhs: { nodes: [0, 1, 2, 3], links: [[4, link, 0, 1], [5, link, 0, 2], [6, 'marker', 1, 3]], action: redraw(2) },
	};
}

for (const { suffix, link, parent, child } of PARENTS) {
	const nacs = [{ name: 'NAC_marked', nodes: [1, 4], links: [[5, 'marker', 1, 4]] }];
	// The central topic is its map's only topic, so it has no siblings.
	if (suffix !== 'Central')
		nacs.push({ name: 'NAC_taken', nodes: [0, 2, 6], links: [[7, link, 0, 6], [8, 'marker', 6, 2]] });
	RULES[`Assign${suffix}`] = {
		elements: { 0: [parent, [20, 40]], 1: [child, [20, 200]], 2: ['Marker', [300, 200]], 4: ['Marker', [300, 360]], 6: [child, [20, 360]] },
		nacs,
		lhs: { nodes: [0, 1, 2], links: [[3, link, 0, 1]] },
		rhs: { nodes: [0, 1, 2], links: [[3, link, 0, 1], [9, 'marker', 1, 2]], action: redraw(1) },
	};
}

// A query: matches without changing anything, so it has no RHS.
RULES.HasUnmarkedTopic = {
	elements: { 0: ['Topic', [20, 200]], 1: ['Marker', [300, 200]] },
	nacs: [{ name: 'NAC_marked', nodes: [0, 1], links: [[2, 'marker', 0, 1]] }],
	lhs: { nodes: [0] },
};

// Marker (1) is the largest: no marker (3) has a greater symbol.
RULES.CreateMarker = {
	elements: { 0: ['MindMap', [20, 40]], 1: ['Marker', [300, 160]], 3: ['Marker', [300, 320]], 5: ['Marker', [300, 320]] },
	nacs: [{
		name: 'NAC_larger', nodes: [0, 1, 3], links: [[2, 'markers', 0, 1], [4, 'markers', 0, 3]],
		condition: "result = int(getAttr('symbol', '3')) > int(getAttr('symbol', '1'))",
	}],
	lhs: { nodes: [0, 1], links: [[2, 'markers', 0, 1]] },
	rhs: {
		nodes: [0, 1, 5], links: [[2, 'markers', 0, 1], [6, 'markers', 0, 5]],
		set: { 5: { symbol: "result = ''" } },
		action: "setAttr('symbol', str(int(getAttr('symbol', '1')) + 1), '5')",
	},
};

const BOX = { NAC: 420, LHS: 470, RHS: 470 }; // drawn width plus a gap
const INSET = { NAC: 0, LHS: 0, RHS: 100 }; // the RHS shape has a notch on its left

async function buildPattern(a, kind, x, rule, p, isRhs) {
	const box = await a.create(`${RCS}/${kind}Icon.type`, jitter([x, CANVAS_TOP], 3));
	if (kind === 'NAC') await a.update(box, { name: p.name });
	if (p.condition) await a.update(box, { Condition: p.condition });
	if (p.action) await a.update(box, { Action: p.action });

	const uris = {};
	for (const label of p.nodes) {
		const [type, [dx, dy]] = rule.elements[label];
		const uri = uris[label] = await a.create(`${PCS}/__p${type}Icon.type`, jitter([x + INSET[kind] + dx, CANVAS_TOP + dy]));
		const code = isRhs ? 'result = getAttr()' : 'result = True';
		await a.update(uri, {
			__pLabel: String(label),
			...Object.fromEntries(ATTRS[type].map(attr => [attr, code])),
			...p.set?.[label],
		});
		await a.create(`${RCS}/PatternContentsLink.type`, { src: box, dest: uri, ...connect(await a.bbox(box), await a.bbox(uri)) });
	}
	for (const [label, type, src, dest] of p.links ?? []) {
		const uri = await a.create(`${PCS}/__p${type}Link.type`, {
			src: uris[src], dest: uris[dest], ...connect(await a.bbox(uris[src]), await a.bbox(uris[dest])),
		});
		await a.update(uri, { __pLabel: String(label) });
	}
}

async function buildRule(a, name, rule) {
	await a.newPage();
	await a.loadmm(`${RCS}.metamodel`);
	await a.loadmm(`${PCS}.metamodel`);
	let x = 40;
	for (const nac of rule.nacs ?? []) {
		await buildPattern(a, 'NAC', x, rule, nac, false);
		x += BOX.NAC;
	}
	await buildPattern(a, 'LHS', x, rule, rule.lhs, false);
	if (rule.rhs) await buildPattern(a, 'RHS', x + BOX.LHS, rule, rule.rhs, true);
	await a.writeFile(`${DIR}/R_${name}.model`);
	await a.settle(500);
	await a.canvasShot(join(HERE, 'out', `R_${name}.png`));
}

// ---------------------------------------------------------------- schedule
// One column, read top to bottom: each block's fail link goes to the next
// block, and its success link loops back to itself so that it applies as long
// as it matches. CreateFirstMarker goes on either way, HasUnmarkedTopic and
// CreateMarker branch, and CreateMarker returns to the Assign rules.
const COLUMN = [
	['CreateFirstMarker'], ['DedupMain'], ['DedupSub'], ['DedupSubSub'],
	['AssignCentral'], ['AssignMain'], ['AssignSub'], ['AssignSubSub'],
	['HasUnmarkedTopic', 'QRule'], ['CreateMarker'],
];
const FLOW = [
	// [from, link, to]; besides these, every ARule loops on success.
	['CreateFirstMarker', 'success', 'DedupMain'],
	['HasUnmarkedTopic', 'success', 'CreateMarker'], ['HasUnmarkedTopic', 'fail', 'EndSuccess'],
	['CreateMarker', 'success', 'AssignCentral'], ['CreateMarker', 'fail', 'EndFail'],
];
const X = 300, ROW = 130;

// A link path through the given points; its label sits at points[mid].
function through(points, mid) {
	const d = pts => `M${pts.map(p => p.join(',')).join('L')}`;
	return { pos: points[mid], segments: [d(points.slice(0, mid + 1)), d(points.slice(mid))] };
}

async function buildSchedule(a) {
	await a.newPage();
	await a.loadmm(`${MOTIF}.metamodel`);
	const uris = {};
	uris.Start = await a.create(`${MOTIF}/StartIcon.type`, jitter([X + 85, CANVAS_TOP]));
	for (const [row, [name, type = 'ARule']] of COLUMN.entries()) {
		uris[name] = await a.create(`${MOTIF}/${type}Icon.type`, jitter([X, CANVAS_TOP + 80 + ROW * row]));
		await a.update(uris[name], { name, [type === 'QRule' ? 'query' : 'rule']: `${DIR}/R_${name}.model` });
	}
	const rowOf = name => COLUMN.findIndex(([n]) => n === name);
	uris.EndSuccess = await a.create(`${MOTIF}/EndSuccessIcon.type`, jitter([X + 420, CANVAS_TOP + 100 + ROW * rowOf('HasUnmarkedTopic')]));
	uris.EndFail = await a.create(`${MOTIF}/EndFailIcon.type`, jitter([X + 420, CANVAS_TOP + 100 + ROW * rowOf('CreateMarker')]));
	await a.settle(500);
	const box = {};
	for (const [name, uri] of Object.entries(uris)) box[name] = await a.bbox(uri);

	// Where links attach: the block's top, its left side, and the success and
	// fail marks under it.
	const top = b => [b.x + b.w / 2, b.y];
	const left = b => [b.x, b.y + b.h / 2];
	const center = b => [b.x + b.w / 2, b.y + b.h / 2];
	const ok = b => [b.x + b.w * 0.27, b.y + b.h];
	const ko = b => [b.x + b.w * 0.73, b.y + b.h];

	const create = (type, from, to, path) =>
		a.create(`${MOTIF}/${type}Link.type`, { src: uris[from], dest: uris[to], ...path });
	const down = (from, at, to) => through([at, [at[0], (at[1] + box[to].y) / 2], top(box[to])], 1);

	await create('initial', 'Start', 'CreateFirstMarker', through([center(box.Start), top(box.CreateFirstMarker)], 0));
	for (const [row, [name]] of COLUMN.entries()) {
		const b = box[name], next = COLUMN[row + 1]?.[0];
		const flows = FLOW.filter(f => f[0] === name);
		const fail = flows.find(f => f[1] === 'fail')?.[2] ?? next;
		const success = flows.find(f => f[1] === 'success')?.[2] ?? name;
		// Failing moves down the column, or right to an end state.
		if (box[fail].y > b.y + b.h && box[fail].x < b.x + b.w) await create('fail', name, fail, down(name, ko(b), fail));
		else await create('fail', name, fail, through([ko(b), [ko(b)[0], b.y + b.h + 25], left(box[fail])], 1));
		if (success === name) {
			const lane = b.x - 40;
			await create('success', name, name, through([ok(b), [ok(b)[0], b.y + b.h + 20], [lane, b.y + b.h + 20], [lane, left(b)[1]], left(b)], 2));
		} else if (box[success].y > b.y) {
			await create('success', name, success, down(name, ok(b), success));
		} else {
			// Back up to an earlier block, along a lane left of the loops.
			const lane = b.x - 90, to = box[success];
			await create('success', name, success, through([ok(b), [ok(b)[0], b.y + b.h + 30], [lane, b.y + b.h + 30], [lane, to.y + to.h / 2 - 10], [to.x, to.y + to.h / 2 - 10]], 2));
		}
	}

	await a.writeFile(`${DIR}/T_AssignMarkers.model`);
	await a.settle(500);
	await a.canvasShot(join(HERE, 'out', 'T_AssignMarkers.png'));
}

mkdirSync(join(HERE, 'out'), { recursive: true });
const only = process.argv.slice(2);
const a = await Atompm.open({ width: 2400, height: 1600 });
try {
	for (const [name, rule] of Object.entries(RULES))
		if (!only.length || only.includes(name)) await buildRule(a, name, rule);
	if (!only.length || only.includes('T_AssignMarkers')) await buildSchedule(a);
	console.log(`built ${only.length ? only.join(", ") : "all rules and T_AssignMarkers"}`, a.errors);
} finally {
	await a.close();
}
