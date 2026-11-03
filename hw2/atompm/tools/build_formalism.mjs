// Applies the MindMap language fixes to the source models (class diagram and
// icon model), saves them, and recompiles every target with AToMPM's compiler.
// Safe to rerun. Needs AToMPM running on :8124.
import { readFileSync } from 'node:fs';
import { dirname, join } from 'node:path';
import { fileURLToPath } from 'node:url';
import { Atompm } from './client.mjs';

const USER_DIR = join(dirname(fileURLToPath(import.meta.url)), '..', 'users', 'travis');
const SCD = '/Formalisms/__LanguageSyntax__/SimpleClassDiagram/SimpleClassDiagram';
const F = '/Formalisms/MindMap/MindMap/';
const AS_MODEL = '/Models/MindMap/MindMap.model';
const CS_MODEL = '/Models/MindMap/MindMap.defaultIcons.model';

const readModel = fname => JSON.parse(readFileSync(join(USER_DIR, fname), 'utf8'));
const nodesOfType = (model, type) => Object.entries(model.asm.nodes).filter(([, n]) => n.$type.endsWith(`/${type}`));

// Same rules as MindMapValidator.java and MindMap.ocl, plus the tree shape that
// Xtext's containment gives for free. They run on the "validate" button.
const CONSTRAINTS = {
	'exactly-one-mindmap': `getAllNodes(['${F}MindMap']).length == 1`,
	'unique-topic-names': `var names = getAllNodes(['${F}Topic']).map(function(t) { return getAttr('name', t); });
names.length == new Set(names).size`,
	'unique-tag-names': `var names = getAllNodes(['${F}Tag']).map(function(t) { return getAttr('name', t); });
names.length == new Set(names).size`,
	'valid-topic-colors': `getAllNodes(['${F}Topic']).every(function(t) {
	var color = getAttr('color', t);
	return color == '' || /^#[0-9a-fA-F]{6}$/.test(color);
})`,
	'no-deeper-at-root': `getAllNodes(['${F}Topic']).every(function(t) {
	return getNeighbors('in', '${F}roots', t).length == 0 || !getAttr('isDeeper', t);
})`,
	'no-self-relation': `getAllNodes(['${F}related']).every(function(l) {
	return getNeighbors('in', '*', l)[0] != getNeighbors('out', '*', l)[0];
})`,
	'topics-form-a-tree': `getAllNodes(['${F}Topic']).every(function(t) {
	var seen = {};
	while (!seen[t]) {
		seen[t] = true;
		var rootLinks = getNeighbors('in', '${F}roots', t),
			 childLinks = getNeighbors('in', '${F}children', t);
		if (rootLinks.length + childLinks.length != 1) return false;
		if (rootLinks.length == 1) return true;
		t = getNeighbors('in', '*', childLinks[0])[0];
	}
	return false;
})`,
	'tags-belong-to-the-map': `getAllNodes(['${F}Tag']).every(function(t) {
	return getNeighbors('in', '${F}hasTag', t).length == 1;
})`,
};

const STROKE = "{'NONE': '#333333', 'HIGH': '#d32f2f', 'MEDIUM': '#f57c00', 'LOW': '#1976d2'}";
const TEXT_INSET = 14;

const LINK_STYLES = {
	rootsLink: { stroke: '#333333', 'stroke-width': 2 },
	childrenLink: { stroke: '#333333', 'stroke-width': 2 },
	relatedLink: { stroke: '#1976d2', 'stroke-width': 1.5 },
	taggedLink: { stroke: '#b8860b', 'stroke-width': 1.5 },
	hasTagLink: { stroke: '#b8860b', 'stroke-width': 1 },
};

const MAPPERS = {
	topicBox: `var fills = ['#ffffff', '#e0f0ff', '#b3d9ff', '#80bfff'];
var strokes = ${STROKE};
var priority = getAttr('priority');
var fill = getAttr('color') != '' ? getAttr('color') : fills[Math.min(getAttr('level'), 3)];
var marker = getAttr('marker');
({'width': 40 + 10 * (getAttr('name').length + (marker > 0 ? String(marker).length + 1 : 0) + (priority == 'NONE' ? 0 : 4) + (getAttr('isDone') ? 2 : 0)),
  'style': {'stroke': strokes[priority], 'stroke-width': priority == 'NONE' ? 1 : 3, 'fill': fill,
            'fill-opacity': getAttr('effectivelyDone') ? 0.35 : 1}})
`,
	topicLabel: `var marks = {'NONE': '', 'HIGH': ' (H)', 'MEDIUM': ' (M)', 'LOW': ' (L)'};
var color = getAttr('color');
var dark = color != '' && (parseInt(color.substr(1, 2), 16) * 0.3 + parseInt(color.substr(3, 2), 16) * 0.6 + parseInt(color.substr(5, 2), 16) * 0.1) < 110;
({'textContent': (getAttr('marker') > 0 ? getAttr('marker') + ' ' : '') + getAttr('name') + marks[getAttr('priority')] + (getAttr('isDone') ? ' #' : ''),
  'style': {'fill': getAttr('effectivelyDone') ? '#777777' : (dark ? '#ffffff' : '#000000'), 'stroke': 'none'}})
`,
	nameLabel: `({"textContent": getAttr("name")})
`,
};

async function updateClassDiagram(a) {
	await a.load(AS_MODEL);
	const model = readModel(AS_MODEL);
	const classes = nodesOfType(model, 'Class');
	const classNamed = name => classes.find(([, n]) => n.name.value === name);
	// HW2 marker: 0 means unassigned; nextMarker is the transformation's per-parent counter.
	const withMissing = (attrs, extra) => [...attrs, ...extra.filter(e => !attrs.some(x => x.name === e.name))];
	const nextMarker = { name: 'nextMarker', type: 'int', default: 1 };

	const [topicId, topic] = classNamed('Topic');
	const attributes = withMissing(topic.attributes.value.map(attr => attr.name === 'priority'
		? { name: 'priority', type: 'ENUM(NONE,HIGH,MEDIUM,LOW)', default: 'NONE' } : attr),
		[{ name: 'marker', type: 'int', default: 0 }, nextMarker]);
	await a.update(`${SCD}.umlIcons/ClassIcon/${topicId}.instance`, { attributes });

	const [mapId, map] = classNamed('MindMap');
	await a.update(`${SCD}.umlIcons/ClassIcon/${mapId}.instance`, { attributes: withMissing(map.attributes.value, [nextMarker]) });

	const existing = Object.fromEntries(nodesOfType(model, 'GlobalConstraint').map(([id, n]) => [n.name.value, id]));
	let y = 60;
	for (const [name, code] of Object.entries(CONSTRAINTS)) {
		const uri = existing[name]
			? `${SCD}.umlIcons/GlobalConstraintIcon/${existing[name]}.instance`
			: await a.create(`${SCD}.umlIcons/GlobalConstraintIcon.type`, [1000, y]);
		await a.update(uri, { name, event: '', code });
		y += 110;
	}
	await a.writeFile(AS_MODEL);
	await a.writeFile('/Formalisms/MindMap/MindMap.metamodel');
}

async function updateIcons(a) {
	await a.load(CS_MODEL);
	const model = readModel(CS_MODEL);
	const CS = '/Formalisms/__LanguageSyntax__/ConcreteSyntax/ConcreteSyntax.defaultIcons';
	const nodes = model.asm.nodes;
	const textOf = name => Object.keys(nodes).find(id => nodes[id].$type.endsWith('/Text') && nodes[id].textContent.value === name);
	const typed = t => nodesOfType(model, t)[0];

	// Icon contents are positioned relative to their Icon, so pin each shape to
	// its Icon's corner: a model's node position is then the shape's top-left.
	const iconOf = contentId => model.asm.edges.find(e => e.dest === model.asm.edges.find(l => l.dest === contentId).src).src;
	const place = (type, id, [dx, dy]) => {
		const [x, y] = model.csm.nodes[iconOf(id)].position.value;
		return a.updatecs(`${CS}/${type}/${id}.instance`, { position: [x + dx, y + dy] });
	};
	const label = (id, size, anchor) => ({ ...nodes[id].style.value, 'font-size': size, stroke: 'none', 'text-anchor': anchor });
	const iconOfText = id => iconOf(model.asm.edges.find(e => e.dest === model.asm.edges.find(l => l.dest === id).src).src);
	const placeText = (id, [dx, dy]) => {
		const [x, y] = model.csm.nodes[iconOfText(id)].position.value;
		return a.updatecs(`${CS}/TextIcon/${id}.instance`, { position: [x + dx, y + dy] });
	};

	const [boxId] = typed('Rectangle');
	await a.update(`${CS}/RectangleIcon/${boxId}.instance`, { width: 160, height: 50, cornerRadius: 12, mapper: MAPPERS.topicBox });
	await place('RectangleIcon', boxId, [0, 0]);
	// The icon's own mapper must stay empty: a failing mapper stops all of an icon's mappers.
	await a.update(`${CS}/IconIcon/${iconOf(boxId)}.instance`, { mapper: '' });

	const [circleId, circle] = typed('Circle');
	await a.update(`${CS}/CircleIcon/${circleId}.instance`, {
		r: 70, style: { ...circle.style.value, fill: '#fff4d6', 'stroke-width': 3 } });
	await place('CircleIcon', circleId, [0, 0]);

	const [starId, star] = typed('Star');
	await a.update(`${CS}/StarIcon/${starId}.instance`, {
		r: 60, style: { ...star.style.value, fill: '#fff9c4' } });
	await place('StarIcon', starId, [0, 0]);

	const topicLabel = textOf('topic'), mapLabel = textOf('mindmap'), tagLabel = textOf('tag');
	await a.update(`${CS}/TextIcon/${topicLabel}.instance`, { mapper: MAPPERS.topicLabel, style: label(topicLabel, '16px', 'start') });
	await placeText(topicLabel, [TEXT_INSET, 17]);
	await a.update(`${CS}/TextIcon/${mapLabel}.instance`, { mapper: MAPPERS.nameLabel, style: label(mapLabel, '16px', 'middle') });
	await placeText(mapLabel, [70, 62]);
	await a.update(`${CS}/TextIcon/${tagLabel}.instance`, { mapper: MAPPERS.nameLabel, style: label(tagLabel, '13px', 'middle') });
	await placeText(tagLabel, [60, 56]);

	for (const [id, link] of nodesOfType(model, 'Link'))
		await a.update(`${CS}/LinkIcon/${id}.instance`, {
			'link-style': { ...link['link-style'].value, ...LINK_STYLES[link.typename.value] } });

	await a.writeFile(CS_MODEL);
	await a.writeFile('/Formalisms/MindMap/MindMap.defaultIcons.metamodel');
	// Also writes MindMap.defaultIcons.pattern.metamodel, which rules are drawn with.
	await a.writeFile('/Formalisms/MindMap/MindMap.pattern.metamodel');
}

const a = await Atompm.open();
try {
	await updateClassDiagram(a);
	await a.newPage();
	await updateIcons(a);
	console.log('formalism rebuilt', a.errors);
} finally {
	await a.close();
}
