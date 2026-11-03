// Icon sizes, shared by the icon mappers (build_formalism.mjs) and the sample
// layout (mm.mjs) so that links meet the shapes AToMPM draws. Each size is an
// expression of n, the length of the label, usable both as JS and as mapper code.
const formula = expr => Object.assign(new Function('n', `return ${expr};`), { expr });

export const SIZES = {
	titleWidth: formula('9.5 * n + 100'),
	centralRx: formula('4.6 * n + 30'),
	mainRx: formula('4.2 * n + 27'),
	subWidth: formula('7.4 * n + 22'),
};

export const CENTRAL_RY = 34;
export const MAIN_RY = 24;
export const SUB_LINE_Y = 20;
export const MARKER_R = 15;
export const TITLE_HEIGHT = 40;

// Anything placed higher ends up under AToMPM's toolbars and cannot be selected,
// so every script lays its elements out from here down.
export const CANVAS_TOP = 400;

// Moves a point a few pixels off the grid, as placing it by hand would. The
// generator is seeded, so a rebuild gives the same layout.
let seed = 6253;
function random() {
	seed = (seed + 0x6d2b79f5) | 0;
	let t = Math.imul(seed ^ (seed >>> 15), 1 | seed);
	t = (t + Math.imul(t ^ (t >>> 7), 61 | t)) ^ t;
	return ((t ^ (t >>> 14)) >>> 0) / 4294967296;
}
export const jitter = ([x, y], by = 4) =>
	[x + Math.round((random() * 2 - 1) * by), y + Math.round((random() * 2 - 1) * by)];

// Link paths, between boxes { x, y, w, h } such as Atompm.bbox returns. AToMPM
// stores a link as two segments meeting at its position.
// Straight path between the facing sides of two boxes.
export function connect(s, d) {
	const c = b => [b.x + b.w / 2, b.y + b.h / 2];
	const [sx, sy] = c(s), [dx, dy] = c(d);
	const horizontal = Math.abs(dx - sx) > Math.abs(dy - sy);
	const from = horizontal ? [dx > sx ? s.x + s.w : s.x, sy] : [sx, dy > sy ? s.y + s.h : s.y];
	const to = horizontal ? [dx > sx ? d.x : d.x + d.w, dy] : [dx, dy > sy ? d.y : d.y + d.h];
	const mid = [(from[0] + to[0]) / 2, (from[1] + to[1]) / 2];
	return { pos: mid, segments: [`M${from}L${mid}`, `M${mid}L${to}`] };
}

// A self-loop out of the right side and back into the bottom.
export function loop(b) {
	const from = [b.x + b.w, b.y + b.h - 30], to = [b.x + b.w - 40, b.y + b.h];
	const mid = [b.x + b.w + 50, b.y + b.h + 50];
	return { pos: mid, segments: [`M${from}L${mid}`, `M${mid}L${to}`] };
}

// The mapper form: SIZES.mainRx.of("getAttr('name').length").
for (const f of Object.values(SIZES)) f.of = n => `(${f.expr.replaceAll('n', `(${n})`)})`;
