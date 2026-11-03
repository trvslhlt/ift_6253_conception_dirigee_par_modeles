// Between the textual mind maps of mm.mjs and AToMPM models: draws a laid-out
// map in the MindMap editor, and reads a saved model back into the same shape.
import { readFileSync } from 'node:fs';
import { MARKER_R, SIZES, TITLE_HEIGHT } from './geometry.mjs';
import { route, shape } from './mm.mjs';

export const CSMM = '/Formalisms/MindMap/MindMap.defaultIcons';
const type = name => `${CSMM}/${name}.type`;

const ICON = { central: 'CentralTopicIcon', main: 'MainTopicIcon', sub: 'SubTopicIcon' };
const CHILD_LINK = { central: 'maintopicsLink', main: 'subtopicsLink', sub: 'subsubtopicsLink' };

// Creates the map in the current page, which must have CSMM loaded.
export async function createMap(a, map) {
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

// Null if both maps have the same topics, each with its kind, parent and marker;
// otherwise what differs.
export function sameTree(want, got) {
	const lines = m => {
		const out = [];
		const walk = (t, parent) => {
			out.push(`${parent}>${t.kind}:${t.name}:${t.marker}`);
			t.children.forEach(c => walk(c, t.name));
		};
		walk(m.topic, m.title);
		return out;
	};
	const w = lines(want), g = lines(got);
	const missing = w.filter(l => !g.includes(l)), extra = g.filter(l => !w.includes(l));
	return missing.length || extra.length ? `missing ${missing}, unexpected ${extra}` : null;
}

// A saved model as { title, markers, topic }, topics as { kind, name, marker,
// children } like parseMindMap. A topic with several marker links (which the
// formalism forbids) has them all in `markers`.
export function readModel(path) {
	const { asm } = JSON.parse(readFileSync(path, 'utf8'));
	const typeOf = id => asm.nodes[id].$type.split('/').at(-1);
	const out = (id, linkType) => asm.edges
		.filter(e => e.src === id && typeOf(e.dest) === linkType)
		.map(e => asm.edges.find(f => f.src === e.dest).dest);
	const KIND = { CentralTopic: 'central', MainTopic: 'main', SubTopic: 'sub' };
	const CHILDREN = { CentralTopic: 'maintopics', MainTopic: 'subtopics', SubTopic: 'subsubtopics' };
	const topic = id => {
		const markers = out(id, 'marker').map(m => asm.nodes[m].symbol.value);
		return {
			kind: KIND[typeOf(id)], name: asm.nodes[id].name.value, marker: markers[0] ?? null, markers,
			children: out(id, CHILDREN[typeOf(id)]).map(topic),
		};
	};
	const mapId = Object.keys(asm.nodes).find(id => typeOf(id) === 'MindMap');
	return {
		title: asm.nodes[mapId].title.value,
		markers: out(mapId, 'markers').map(m => asm.nodes[m].symbol.value),
		topic: topic(out(mapId, 'topic')[0]),
	};
}
