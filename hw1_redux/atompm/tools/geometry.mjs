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

// The mapper form: SIZES.mainRx.of("getAttr('name').length").
for (const f of Object.values(SIZES)) f.of = n => `(${f.expr.replaceAll('n', `(${n})`)})`;
