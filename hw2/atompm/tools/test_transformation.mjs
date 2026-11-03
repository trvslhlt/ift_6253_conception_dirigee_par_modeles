// Runs T_AssignMarkers on small maps that each exercise one case, and checks
// the requirements (markers.mjs) plus what each case expects. Saves nothing
// but a scratch model, deleted at the end. Needs AToMPM running.
import { rmSync } from 'node:fs';
import { dirname, join } from 'node:path';
import { fileURLToPath } from 'node:url';
import { Atompm } from './client.mjs';
import { CSMM, createMap, readModel } from './mapmodel.mjs';
import { checkAssignment, markersOf } from './markers.mjs';
import { layout, parseMindMap } from './mm.mjs';

const HERE = dirname(fileURLToPath(import.meta.url));
const SCRATCH = '/Models/MindMap/_test.model';
const SCRATCH_FILE = join(HERE, '..', 'users', 'travis', 'Models', 'MindMap', '_test.model');

// [what, map, expected markers by path (beyond the requirements)]
const CASES = [
	['no markers at all: marker 1 is created, then more as needed',
		'mindmap M topic C includes { * A * B includes { - X - Y } }',
		{ C: '1', 'C/B/X': m => m !== undefined }],
	['the assignment\'s example: Implement gets 3',
		'mindmap M (marker 1, marker 2) topic C includes { * 1 Read * 2 Analyze * Implement }',
		{ 'C/Read': '1', 'C/Analyze': '2', 'C/Implement': '3' }],
	['three siblings share 2: the first by name keeps it',
		'mindmap M (marker 2) topic C includes { * 2 B * 2 A * 2 D }',
		{ 'C/A': '2', 'C/B': m => m !== '2', 'C/D': m => m !== '2' }],
	['a lone preassigned 5 is kept, its sibling gets another marker',
		'mindmap M (marker 5) topic C includes { * 5 A * B }',
		{ 'C/A': '5', 'C/B': '6' }],
	['the same marker under two parents is kept',
		'mindmap M (marker 1, marker 2) topic C includes { * 1 A includes { - 1 X } * 2 B includes { - 1 Y } }',
		{ 'C/A': '1', 'C/B': '2', 'C/A/X': '1', 'C/B/Y': '1' }],
	['conflicts at every level',
		'mindmap M (marker 1) topic C includes { * 1 A includes { - 1 X includes { - 1 P - 1 Q } - 1 Y } * 1 B }',
		{ 'C/A': '1', 'C/A/X': '1', 'C/A/X/P': '1' }],
];

let failed = 0;
const a = await Atompm.open({ width: 2000, height: 1600 });
try {
	for (const [what, text, expected] of CASES) {
		await a.newPage();
		await a.loadmm(`${CSMM}.metamodel`);
		await createMap(a, layout(parseMindMap(text)));
		await a.writeFile(SCRATCH);
		await a.settle(500);
		const input = readModel(SCRATCH_FILE);
		await a.transform('/Models/MindMap/T_AssignMarkers.model');
		await a.writeFile(SCRATCH);
		await a.settle(500);
		const output = readModel(SCRATCH_FILE);

		const problems = checkAssignment(input, output);
		const got = markersOf(output);
		for (const [path, want] of Object.entries(expected))
			if (typeof want === 'function' ? !want(got[path]) : got[path] !== want)
				problems.push(`${path} has ${got[path]}`);
		if (problems.length) failed++;
		console.log(`${problems.length ? 'FAIL' : 'ok  '} ${what}`);
		for (const p of problems) console.log(`     ${p}`);
	}
} finally {
	await a.close();
	rmSync(SCRATCH_FILE, { force: true });
}
process.exit(failed ? 1 : 0);
