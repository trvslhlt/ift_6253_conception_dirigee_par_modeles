// Runs T_AssignMarkers on each <name>_in.model, saves <name>_out.model, checks
// the requirements on the result (markers.mjs), runs it again on the output to
// check that nothing changes, and takes pictures. Needs AToMPM running.
//
// Before saving, it tidies what the transformation leaves on the canvas. The
// engine puts a created marker between its neighbours, so it is moved to the
// end of the marker row with its links. And a topic's marker label is drawn by
// a mapper, which AToMPM reruns only when one of the topic's attributes
// changes, which the transformation never does; so each name is changed and
// changed back. Neither touches the abstract syntax the rules produced.
import { readFileSync, rmSync } from 'node:fs';
import { dirname, join } from 'node:path';
import { fileURLToPath } from 'node:url';
import { Atompm } from './client.mjs';
import { MARKER_R, SIZES, TITLE_HEIGHT, jitter } from './geometry.mjs';
import { readModel } from './mapmodel.mjs';
import { checkAssignment, markersOf } from './markers.mjs';
import { route } from './mm.mjs';

const HERE = dirname(fileURLToPath(import.meta.url));
const MODELS = join(HERE, '..', 'users', 'travis', 'Models', 'MindMap');
const DIR = '/Models/MindMap';
const T = `${DIR}/T_AssignMarkers.model`;
const CSMM = '/Formalisms/MindMap/MindMap.defaultIcons';
const TARGETS = ['research', 'courseProject'];

const point = s => s.split(',').map(Number);

async function tidy(a, inputFile, savedFile) {
	const before = JSON.parse(readFileSync(inputFile, 'utf8')).asm.nodes;
	const { asm, csm } = JSON.parse(readFileSync(savedFile, 'utf8'));
	const icon = (k, n) => `${CSMM}/${n.$type.split('/').at(-1)}/${k}.instance`;
	const asId = n => n.$asuri.value.match(/\/(\d+)\.instance$/)[1];
	const icons = Object.entries(csm.nodes).map(([k, n]) => ({ uri: icon(k, n), n, as: asm.nodes[asId(n)] }));
	const ofType = t => icons.filter(i => i.n.$type.endsWith(`/${t}`));

	// The marker row: after the last marker that was there, or after the title.
	const old = ofType('MarkerIcon').filter(i => before[asId(i.n)]);
	const created = ofType('MarkerIcon').filter(i => !before[asId(i.n)])
		.sort((x, y) => Number(x.as.symbol.value) - Number(y.as.symbol.value));
	const map = ofType('MindMapIcon')[0];
	let [x, y] = old.length
		? [Math.max(...old.map(i => i.n.position.value[0])), old[0].n.position.value[1]]
		: [map.n.position.value[0] + SIZES.titleWidth(map.as.title.value.length) - 10, map.n.position.value[1] + TITLE_HEIGHT / 2 - MARKER_R];
	for (const m of created) {
		x += 2 * MARKER_R + 20;
		const [mx, my] = jitter([x, y], 3);
		await a.updatecs(m.uri, { position: [mx, my] });
		// Links keep their source end and get a new target end.
		for (const l of [...ofType('markersLink'), ...ofType('markerLink')]) {
			const keys = Object.keys(l.n.$segments.value);
			if (!keys.some(k => k.endsWith(`--${m.uri}`))) continue;
			const from = point(l.n.$segments.value[keys.find(k => !k.endsWith(`--${m.uri}`))].slice(1).split('L')[0]);
			const to = l.n.$type.endsWith('/markersLink') ? [mx, my + MARKER_R] : [mx + MARKER_R, my + 2 * MARKER_R];
			const { pos, segments } = route(from, to);
			const [toLink, fromLink] = [keys.find(k => !k.endsWith(`--${m.uri}`)), keys.find(k => k.endsWith(`--${m.uri}`))];
			await a.updatecs(l.uri, { position: pos, $segments: { [toLink]: segments[0], [fromLink]: segments[1] } });
		}
	}

	for (const t of [...ofType('CentralTopicIcon'), ...ofType('MainTopicIcon'), ...ofType('SubTopicIcon')]) {
		await a.update(t.uri, { name: `${t.as.name.value} ` });
		await a.update(t.uri, { name: t.as.name.value });
	}
}

const only = process.argv.slice(2);
let failed = false;
const a = await Atompm.open({ width: 2200, height: 3600 });
try {
	for (const name of TARGETS) {
		if (only.length && !only.includes(name)) continue;
		const inFile = join(MODELS, `${name}_in.model`), outFile = join(MODELS, `${name}_out.model`);
		await a.newPage();
		await a.load(`${DIR}/${name}_in.model`);
		await a.transform(T);
		await a.writeFile(`${DIR}/${name}_out.model`);
		await a.settle(800);
		await tidy(a, inFile, outFile);
		await a.validate();
		await a.writeFile(`${DIR}/${name}_out.model`);
		await a.settle(800);
		await a.canvasShot(join(HERE, 'out', `${name}_out.png`));

		const input = readModel(inFile), output = readModel(outFile);
		const problems = checkAssignment(input, output);

		// A second run finds nothing to do.
		await a.newPage();
		await a.load(`${DIR}/${name}_out.model`);
		await a.transform(T);
		await a.writeFile(`${DIR}/_rerun.model`);
		await a.settle(800);
		const rerun = readModel(join(MODELS, '_rerun.model'));
		if (JSON.stringify(markersOf(rerun)) !== JSON.stringify(markersOf(output)) || rerun.markers.length !== output.markers.length)
			problems.push('a second run changed the markers');

		const changed = Object.entries(markersOf(output)).filter(([p, m]) => markersOf(input)[p] !== m);
		console.log(`${name}: ${problems.length ? 'FAIL' : 'ok'}, ${changed.length} topics got a new marker, markers ${input.markers} -> ${output.markers}`);
		for (const [p, m] of changed) console.log(`  ${p}: ${markersOf(input)[p] ?? '-'} -> ${m}`);
		for (const p of problems) console.log(`  problem: ${p}`);
		failed ||= problems.length > 0;
	}
	rmSync(join(MODELS, '_rerun.model'), { force: true });
	if (a.errors.length) console.log('client errors:', a.errors);
} finally {
	await a.close();
}
process.exit(failed ? 1 : 0);
