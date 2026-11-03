// Reads a textual mind map (.mindmap, see MindMap.xtext) into a plain object
// and lays it out as a left-to-right tree for the AToMPM canvas.
import { readFileSync } from 'node:fs';
import { CENTRAL_RY, MAIN_RY, MARKER_R, SIZES, SUB_LINE_Y, TITLE_HEIGHT } from './geometry.mjs';

const TOKEN = /\s+|\/\/[^\n]*|\/\*[\s\S]*?\*\/|[A-Za-z_][A-Za-z0-9_]*|\d+|[(){},*-]/y;

function tokenize(src) {
	const tokens = [];
	TOKEN.lastIndex = 0;
	while (TOKEN.lastIndex < src.length) {
		const at = TOKEN.lastIndex;
		const m = TOKEN.exec(src);
		if (!m) throw new Error(`unexpected character at offset ${at}: ${src.slice(at, at + 20)}`);
		if (!/^(\s|\/\/|\/\*)/.test(m[0])) tokens.push(m[0]);
	}
	return tokens;
}

export function parseMindMap(src) {
	const tokens = tokenize(src);
	let i = 0;
	const peek = () => tokens[i];
	const next = () => tokens[i++];
	const accept = t => (peek() === t ? (i++, true) : false);
	const expect = t => {
		if (next() !== t) throw new Error(`expected '${t}' near: ${tokens.slice(i - 3, i + 3).join(' ')}`);
	};

	// kind: 'central' | 'main' | 'sub'; the bullet of each level's children.
	const CHILD = { central: ['*', 'main'], main: ['-', 'sub'], sub: ['-', 'sub'] };
	function topic(kind) {
		const t = { kind, marker: /^\d+$/.test(peek()) ? next() : null, name: next(), children: [] };
		if (accept('includes')) {
			expect('{');
			const [bullet, childKind] = CHILD[kind];
			while (accept(bullet)) t.children.push(topic(childKind));
			expect('}');
		}
		return t;
	}

	expect('mindmap');
	const map = { title: next(), markers: [] };
	if (accept('(')) {
		do { expect('marker'); map.markers.push(next()); } while (accept(','));
		expect(')');
	}
	expect('topic');
	map.topic = topic('central');
	if (i !== tokens.length) throw new Error(`unexpected '${peek()}' after the central topic`);
	return map;
}

export const readMindMap = path => parseMindMap(readFileSync(path, 'utf8'));

export function allTopics(map) {
	const out = [];
	const walk = t => { out.push(t); t.children.forEach(walk); };
	walk(map.topic);
	return out;
}

// Width and the y of the line links attach to, relative to the icon's top-left.
export function shape(t) {
	if (t.kind === 'central') {
		const label = (t.marker ? `${t.marker}  ` : '') + t.name;
		return { width: 2 * SIZES.centralRx(label.length), anchor: CENTRAL_RY };
	}
	if (t.kind === 'main') return { width: 2 * SIZES.mainRx(t.name.length), anchor: MAIN_RY };
	return { width: SIZES.subWidth(t.name.length), anchor: SUB_LINE_Y };
}

// Sets pos (top-left) on every topic, the map and its markers: one column per
// level as wide as its widest topic, leaves stacked, parents centered on their
// children. The title and the marker legend sit in a row above the tree.
export function layout(map, { gap = 70, yStep = 52 } = {}) {
	const origin = { x: 40, y: 140 }; // y clears AToMPM's toolbars
	const widths = [];
	const measure = (t, depth) => {
		widths[depth] = Math.max(widths[depth] ?? 0, shape(t).width);
		t.children.forEach(c => measure(c, depth + 1));
	};
	measure(map.topic, 0);
	const columns = [origin.x];
	for (const w of widths) columns.push(columns.at(-1) + w + gap);

	const top = origin.y + TITLE_HEIGHT + 60;
	let leaf = 0;
	const place = (t, depth) => {
		t.children.forEach(c => place(c, depth + 1));
		const anchorY = t.children.length
			? (t.children[0].anchorY + t.children.at(-1).anchorY) / 2
			: top + MAIN_RY + yStep * leaf++;
		t.anchorY = anchorY;
		t.pos = [columns[depth], anchorY - shape(t).anchor];
	};
	place(map.topic, 0);

	map.pos = [origin.x, origin.y];
	const legendX = origin.x + SIZES.titleWidth(map.title.length) + 40;
	map.markerPos = Object.fromEntries(map.markers.map(
		(m, n) => [m, [legendX + n * (2 * MARKER_R + 20), origin.y + TITLE_HEIGHT / 2 - MARKER_R]]));
	return map;
}

// Two-segment link path between points, as AToMPM stores them.
export function route([x1, y1], [x2, y2], mid = [(x1 + x2) / 2, (y1 + y2) / 2]) {
	return { pos: mid, segments: [`M${x1},${y1}L${mid}`, `M${mid}L${x2},${y2}`] };
}

// The rules the formalism enforces, checked first so that a bad .mindmap fails
// with a readable message instead of a failed validation after the build.
export function check(map) {
	const errors = [];
	if (new Set(map.markers).size !== map.markers.length) errors.push('duplicate marker symbols');
	for (const t of allTopics(map))
		if (t.marker && !map.markers.includes(t.marker)) errors.push(`${t.name} uses undefined marker ${t.marker}`);
	return errors;
}
