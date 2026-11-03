// Builds the MindMap formalism from scratch: draws the class diagram (abstract
// syntax) and the icon model (concrete syntax) in AToMPM's own editors, saves
// both, and compiles them with AToMPM's compiler. Rerunning replaces them.
// Needs AToMPM running (make run, port 8125).
import { mkdirSync } from 'node:fs';
import { dirname, join } from 'node:path';
import { fileURLToPath } from 'node:url';
import { Atompm } from './client.mjs';
import { CANVAS_TOP, CENTRAL_RY, MAIN_RY, MARKER_R, SIZES, SUB_LINE_Y, TITLE_HEIGHT, connect, jitter, loop } from './geometry.mjs';

const USER_DIR = join(dirname(fileURLToPath(import.meta.url)), '..', 'users', 'travis');
const SCD = '/Formalisms/__LanguageSyntax__/SimpleClassDiagram/SimpleClassDiagram.umlIcons';
const CS = '/Formalisms/__LanguageSyntax__/ConcreteSyntax/ConcreteSyntax.defaultIcons';
const F = '/Formalisms/MindMap/MindMap/';
const AS_MODEL = '/Models/MindMap/MindMap.model';
const CS_MODEL = '/Models/MindMap/MindMap.defaultIcons.model';

// ---------------------------------------------------------------- abstract syntax
// The metamodel of the assignment, class for class. AToMPM has no composition
// arrows that draw as lines, so the containments are plain associations whose
// cardinalities (and the constraints below) give them containment semantics:
// every Marker, CentralTopic, MainTopic and SubTopic has exactly one owner.
const card = (dir, type, min, max) => ({ dir, type, min: String(min), max: String(max) });
const attr = (name, type, def) => ({ name, type, default: def });

// Positions as laid out by hand in the class diagram editor.
const CLASSES = {
	MindMap: {
		pos: [75, 175], attributes: [attr('title', 'string', 'MindMap')],
		cardinalities: [card('out', 'markers', 0, 'Infinity'), card('out', 'topic', 1, 1)],
	},
	Marker: {
		pos: [525, 179], attributes: [attr('symbol', 'string', '1')],
		cardinalities: [card('in', 'markers', 1, 1), card('in', 'marker', 0, 'Infinity')],
	},
	Topic: {
		pos: [524, 454], abstract: true, attributes: [attr('name', 'string', 'Topic')],
		cardinalities: [card('out', 'marker', 0, 1)],
	},
	CentralTopic: {
		pos: [76, 457], attributes: [],
		cardinalities: [card('in', 'topic', 1, 1), card('out', 'maintopics', 0, 'Infinity')],
	},
	MainTopic: {
		pos: [528, 680], attributes: [],
		cardinalities: [card('in', 'maintopics', 1, 1), card('out', 'subtopics', 0, 'Infinity')],
	},
	SubTopic: {
		pos: [927, 680], attributes: [],
		// One parent, either a MainTopic or a SubTopic: see one-parent-per-subtopic.
		cardinalities: [card('in', 'subtopics', 0, 1), card('in', 'subsubtopics', 0, 1),
			card('out', 'subsubtopics', 0, 'Infinity')],
	},
};

const ASSOCIATIONS = [
	['markers', 'MindMap', 'Marker'],
	['topic', 'MindMap', 'CentralTopic'],
	['marker', 'Topic', 'Marker'],
	['maintopics', 'CentralTopic', 'MainTopic'],
	['subtopics', 'MainTopic', 'SubTopic'],
	['subsubtopics', 'SubTopic', 'SubTopic'],
];

const INHERITANCE = ['CentralTopic', 'MainTopic', 'SubTopic'];

// What cardinalities cannot say. They run on the "validate" button.
// Laid out by hand, in this order, right of the classes.
const CONSTRAINT_AT = [[827, 179], [827, 227], [826, 279], [827, 329], [827, 378]];
const CONSTRAINTS = {
	'exactly-one-mindmap': `getAllNodes(['${F}MindMap']).length == 1`,
	'one-parent-per-subtopic': `getAllNodes(['${F}SubTopic']).every(function(t) {
	return getNeighbors('<', '${F}subtopics', t).length + getNeighbors('<', '${F}subsubtopics', t).length == 1;
})`,
	'subtopics-descend-from-a-maintopic': `getAllNodes(['${F}SubTopic']).every(function(t) {
	var seen = {};
	while (getAttr('$type', t) == '${F}SubTopic') {
		if (seen[t]) return false;
		seen[t] = true;
		var links = getNeighbors('<', '${F}subtopics', t).concat(getNeighbors('<', '${F}subsubtopics', t));
		if (links.length != 1) return false;
		t = getNeighbors('<', '*', links[0])[0];
	}
	return true;
})`,
	'unique-marker-symbols': `var symbols = getAllNodes(['${F}Marker']).map(function(m) { return getAttr('symbol', m); });
symbols.length == new Set(symbols).size`,
	'non-empty-names': `getAllNodes(['${F}CentralTopic', '${F}MainTopic', '${F}SubTopic']).every(function(t) {
	return getAttr('name', t).trim() != '';
})`,
};

async function buildAbstractSyntax(a) {
	await a.loadmm(`${SCD}.metamodel`);
	const uris = {};
	for (const [name, c] of Object.entries(CLASSES)) {
		uris[name] = await a.create(`${SCD}/ClassIcon.type`, c.pos);
		await a.update(uris[name], {
			name, attributes: c.attributes, cardinalities: c.cardinalities, abstract: !!c.abstract,
		});
	}
	await a.settle(800);
	const boxes = {};
	for (const name of Object.keys(CLASSES)) boxes[name] = await a.bbox(uris[name]);

	for (const [name, src, dest] of ASSOCIATIONS) {
		const path = src === dest ? loop(boxes[src]) : connect(boxes[src], boxes[dest]);
		const uri = await a.create(`${SCD}/AssociationLink.type`, { src: uris[src], dest: uris[dest], ...path });
		await a.update(uri, { name, linktype: 'visual' });
	}
	for (const sub of INHERITANCE)
		await a.create(`${SCD}/InheritanceLink.type`, { src: uris[sub], dest: uris.Topic, ...connect(boxes[sub], boxes.Topic) });

	for (const [n, [name, code]] of Object.entries(CONSTRAINTS).entries()) {
		const uri = await a.create(`${SCD}/GlobalConstraintIcon.type`, CONSTRAINT_AT[n]);
		await a.update(uri, { name, event: '', code });
	}
	await a.writeFile(AS_MODEL);
	await a.writeFile('/Formalisms/MindMap/MindMap.metamodel');
}

// ---------------------------------------------------------------- concrete syntax
// Close to the assignment's figure: a dark central ellipse, light red main
// topics, underlined sub-topics, and markers as green numbers in front of a
// topic's name. Markers and the map's title sit in a small legend.
const MARKER_GREEN = '#2e7d32';

// Label of a topic's marker, read through its marker link. Mappers only rerun
// when the node's own attributes change, so set the name after linking.
const markerSymbol = `(function() {
	var links = getNeighbors('>', '${F}marker');
	return links.length ? getAttr('symbol', getNeighbors('>', '*', links[0])[0]) : '';
})()`;

// Text is placed by its top-left corner, so labels are left-aligned.
const textStyle = (fill, size, extra = {}) => ({
	stroke: 'none', 'stroke-dasharray': '', fill, 'fill-opacity': 1, 'font-size': size, 'stroke-width': 1,
	'arrow-start': 'none', 'arrow-end': 'none', 'text-anchor': 'start', ...extra,
});
const shapeStyle = (fill, stroke, width = 1) => ({
	stroke, 'stroke-dasharray': '', fill, 'fill-opacity': 1, 'font-size': '14px', 'stroke-width': width,
	'arrow-start': 'none', 'arrow-end': 'none',
});

// The central topic shows its marker, if any, in front of its name.
const centralLabel = `(function() { var m = ${markerSymbol}; return (m ? m + '  ' : '') + getAttr('name'); })()`;

const ICONS = {
	MindMapIcon: [
		{ type: 'Rectangle', at: [0, 0], attrs: { width: 200, height: TITLE_HEIGHT, cornerRadius: 6, style: shapeStyle('#f5f5f5', '#9e9e9e') },
			mapper: `({'width': ${SIZES.titleWidth.of("getAttr('title').length")}})` },
		{ type: 'Text', at: [10, 12], attrs: { textContent: 'mind map', style: textStyle('#616161', '12px') } },
		{ type: 'Text', at: [80, 10], attrs: { textContent: 'title', style: textStyle('#000000', '16px', { 'font-weight': 'bold' }) },
			mapper: `({'textContent': getAttr('title')})` },
	],
	MarkerIcon: [
		{ type: 'Circle', at: [0, 0], attrs: { r: MARKER_R, style: shapeStyle('#ffffff', MARKER_GREEN, 2) } },
		{ type: 'Text', at: [10, 6], attrs: { textContent: '1', style: textStyle(MARKER_GREEN, '15px', { 'font-weight': 'bold' }) },
			mapper: `({'textContent': getAttr('symbol')})` },
	],
	CentralTopicIcon: [
		{ type: 'Ellipse', at: [0, 0], attrs: { rx: 90, ry: CENTRAL_RY, style: shapeStyle('#333333', '#000000', 2) },
			mapper: `({'rx': ${SIZES.centralRx.of(centralLabel + '.length')}})` },
		{ type: 'Text', at: [30, 25], attrs: { textContent: 'central', style: textStyle('#ffffff', '16px') },
			mapper: `({'textContent': ${centralLabel}})` },
	],
	MainTopicIcon: [
		{ type: 'Ellipse', at: [0, 0], attrs: { rx: 80, ry: MAIN_RY, style: shapeStyle('#f8a19a', '#f8a19a') },
			mapper: `({'rx': ${SIZES.mainRx.of("getAttr('name').length")}})` },
		{ type: 'Text', at: [14, 14], attrs: { textContent: '', style: textStyle(MARKER_GREEN, '15px', { 'font-weight': 'bold' }) },
			mapper: `({'textContent': ${markerSymbol}})` },
		{ type: 'Text', at: [32, 14], attrs: { textContent: 'main', style: textStyle('#b71c1c', '15px') },
			mapper: `({'textContent': getAttr('name')})` },
	],
	SubTopicIcon: [
		// A path is drawn from the icon's origin whatever its position, so the
		// underline's offset is in its segments.
		{ type: 'Path', at: [0, 0], attrs: { segments: `m0,${SUB_LINE_Y}l120,0`, style: shapeStyle('none', '#000000', 1.5) },
			mapper: `({'segments': 'm0,${SUB_LINE_Y}l' + ${SIZES.subWidth.of("getAttr('name').length")} + ',0'})` },
		{ type: 'Text', at: [0, 1], attrs: { textContent: '', style: textStyle(MARKER_GREEN, '13px', { 'font-weight': 'bold' }) },
			mapper: `({'textContent': ${markerSymbol}})` },
		{ type: 'Text', at: [14, 1], attrs: { textContent: 'sub', style: textStyle('#212121', '13px') },
			mapper: `({'textContent': getAttr('name')})` },
	],
};

const linkStyle = (stroke, width, dash = '', opacity = 1) =>
	({ stroke, 'stroke-dasharray': dash, 'stroke-opacity': opacity, 'stroke-width': width });

// arrowHead/arrowTail 'custom' with no contents draws a bare line.
const LINKS = {
	markersLink: { style: linkStyle('#9e9e9e', 1, '. '), arrowTail: 'custom' },
	topicLink: { style: linkStyle('#9e9e9e', 1, '- '), arrowTail: 'custom' },
	markerLink: { style: linkStyle(MARKER_GREEN, 1, '. ', 0.3), arrowTail: 'custom' },
	maintopicsLink: { style: linkStyle('#555555', 2), arrowTail: 'custom' },
	subtopicsLink: { style: linkStyle('#555555', 1.2), arrowTail: 'custom' },
	subsubtopicsLink: { style: linkStyle('#555555', 1.2), arrowTail: 'custom' },
};

async function buildConcreteSyntax(a) {
	await a.loadmm(`${CS}.metamodel`);
	let x = 80;
	for (const [typename, parts] of Object.entries(ICONS)) {
		// The icon and its shapes move together, so only the icon is off the grid.
		const origin = jitter([x, CANVAS_TOP + 40]);
		const icon = await a.create(`${CS}/IconIcon.type`, origin);
		await a.update(icon, { typename });
		for (const part of parts) {
			const shape = await a.create(`${CS}/${part.type}Icon.type`, [origin[0] + 40, origin[1] + 40]);
			await a.update(shape, { ...part.attrs, ...(part.mapper ? { mapper: part.mapper } : {}) });
			await a.create(`${CS}/IconContentsLink.type`, {
				src: icon, dest: shape, pos: [origin[0] + 20, origin[1] + 20],
				segments: [`M${origin}L${origin[0] + 20},${origin[1] + 20}`, `M${origin[0] + 20},${origin[1] + 20}L${origin[0] + 40},${origin[1] + 40}`],
			});
			// Contents are positioned relative to their Icon.
			await a.updatecs(shape, { position: [origin[0] + part.at[0], origin[1] + part.at[1]] });
		}
		x += 300;
	}
	x = 80;
	for (const [typename, l] of Object.entries(LINKS)) {
		const link = await a.create(`${CS}/LinkIcon.type`, jitter([x, CANVAS_TOP + 360]));
		await a.update(link, { typename, 'link-style': l.style, arrowHead: 'custom', arrowTail: l.arrowTail });
		x += 260;
	}
	await a.writeFile(CS_MODEL);
	await a.writeFile('/Formalisms/MindMap/MindMap.defaultIcons.metamodel');
	// The pattern metamodels the MoTif rules are drawn with. The server derives
	// MindMap.defaultIcons.pattern.metamodel from the icon metamodel above.
	await a.writeFile('/Formalisms/MindMap/MindMap.pattern.metamodel');
}

mkdirSync(join(USER_DIR, 'Formalisms', 'MindMap'), { recursive: true });
mkdirSync(join(USER_DIR, 'Models', 'MindMap'), { recursive: true });
const a = await Atompm.open();
try {
	// `node build_formalism.mjs icons` rebuilds only the concrete syntax.
	if (!process.argv.includes('icons')) {
		await buildAbstractSyntax(a);
		await a.newPage();
	}
	await buildConcreteSyntax(a);
	console.log('formalism built', a.errors);
} finally {
	await a.close();
}
