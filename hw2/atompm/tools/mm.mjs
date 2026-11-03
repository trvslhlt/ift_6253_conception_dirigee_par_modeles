// Reads a textual MindMap (.mm, see MindMap.xtext) into a plain object and lays
// it out as a left-to-right tree for the AToMPM canvas.
import { readFileSync } from 'node:fs';

const TOKEN = /\s+|\/\/[^\n]*|\/\*[\s\S]*?\*\/|"(?:[^"\\]|\\.)*"|->|[A-Za-z_][A-Za-z0-9_]*|[-(){}<>,:*#]/y;
const PRIORITIES = { H: 'HIGH', M: 'MEDIUM', L: 'LOW' };

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
		if (next() !== t) throw new Error(`expected '${t}' near token ${i}: ${tokens.slice(i - 3, i + 3).join(' ')}`);
	};

	function topic() {
		const deeper = next() === '->';
		const t = {
			name: next(), deeper, tags: [], priority: 'NONE', done: false,
			description: '', color: '', related: [], children: [],
		};
		while (accept('<')) { t.tags.push(next()); expect('>'); }
		if (accept('(')) { t.priority = PRIORITIES[next()]; expect(')'); }
		t.done = accept('#');
		if (accept('{')) {
			if (accept('description')) { expect(':'); t.description = JSON.parse(next()); }
			while (accept('*')) t.related.push(next());
			if (accept('view')) {
				expect('{');
				if (accept('color')) { expect(':'); t.color = JSON.parse(next()); }
				expect('}');
			}
			while (peek() === '-' || peek() === '->') t.children.push(topic());
			expect('}');
		}
		return t;
	}

	expect('mindmap');
	const map = { name: next(), tags: [], roots: [] };
	if (accept('(')) {
		do { next(); map.tags.push(next()); } while (accept(','));
		expect(')');
	}
	expect('{');
	while (peek() === '-' || peek() === '->') map.roots.push(topic());
	expect('}');
	return map;
}

export const readMindMap = path => parseMindMap(readFileSync(path, 'utf8'));

export function allTopics(map) {
	const out = [];
	const walk = t => { out.push(t); t.children.forEach(walk); };
	map.roots.forEach(walk);
	return out;
}

// Must agree with the width in the Topic icon's mapper (build_formalism.mjs).
export const topicWidth = t =>
	40 + 10 * (t.name.length + (t.marker > 0 ? String(t.marker).length + 1 : 0) + (t.priority === 'NONE' ? 0 : 4) + (t.done ? 2 : 0));
export const TOPIC_HEIGHT = 50;
export const MAP_DIAMETER = 140;
export const TAG_DIAMETER = 120;
// Nothing is placed above this y, so no element ends up under AToMPM's toolbars.
export const CANVAS_TOP = 400;

// Sets t.pos (top-left) on every topic: columns as wide as their widest topic,
// y by leaf order, parents centered on their children. Tags sit in a row below.
export function layout(map, { xGap = 110, yStep = 74 } = {}) {
	// The map is centered on its roots, so start low enough that a single root keeps it below CANVAS_TOP.
	const origin = { x: 40, y: CANVAS_TOP + MAP_DIAMETER / 2 };
	const widths = [];
	const measure = (t, depth) => {
		widths[depth] = Math.max(widths[depth] ?? 0, topicWidth(t));
		t.children.forEach(c => measure(c, depth + 1));
	};
	map.roots.forEach(t => measure(t, 0));
	const columns = [origin.x + MAP_DIAMETER + xGap];
	for (const w of widths) columns.push(columns.at(-1) + w + xGap);

	let leaf = 0;
	const place = (t, depth) => {
		t.children.forEach(c => place(c, depth + 1));
		const y = t.children.length
			? (t.children[0].pos[1] + t.children.at(-1).pos[1]) / 2
			: origin.y + yStep * leaf++;
		t.pos = [columns[depth], y];
	};
	map.roots.forEach(t => place(t, 0));
	const ys = map.roots.map(t => t.pos[1] + TOPIC_HEIGHT / 2);
	const center = (Math.min(...ys) + Math.max(...ys)) / 2;
	map.pos = [origin.x, center - MAP_DIAMETER / 2];
	map.tagPos = Object.fromEntries(map.tags.map(
		(name, n) => [name, [origin.x + (TAG_DIAMETER + 20) * n, origin.y + yStep * leaf + 40]]));
	return map;
}

// Two-segment link path between points, as AToMPM stores them.
export function route([x1, y1], [x2, y2], mid = [(x1 + x2) / 2, (y1 + y2) / 2]) {
	return { pos: mid, segments: [`M${x1},${y1}L${mid}`, `M${mid}L${x2},${y2}`] };
}

// The rules the AToMPM formalism enforces, checked up front so a bad .mm fails
// with a readable message rather than a rejected edit halfway through a build.
export function check(map) {
	const errors = [];
	const topics = allTopics(map);
	const names = new Set();
	for (const t of topics) {
		if (names.has(t.name)) errors.push(`duplicate topic name: ${t.name}`);
		names.add(t.name);
		if (t.color && !/^#[0-9a-fA-F]{6}$/.test(t.color)) errors.push(`bad color on ${t.name}: ${t.color}`);
		for (const tag of t.tags) if (!map.tags.includes(tag)) errors.push(`unknown tag on ${t.name}: ${tag}`);
		for (const r of t.related) if (r === t.name) errors.push(`${t.name} relates to itself`);
	}
	for (const t of topics) for (const r of t.related)
		if (!names.has(r)) errors.push(`unknown related topic on ${t.name}: ${r}`);
	for (const t of map.roots) if (t.deeper) errors.push(`root topic uses ->: ${t.name}`);
	return errors;
}
