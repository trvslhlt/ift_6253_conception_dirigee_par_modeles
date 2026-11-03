// Builds the input models of T_AssignMarkers from the textual samples in
// ../samples/*.mindmap (the syntax of the Xtext editor): creates each one in the
// generated editor, validates it, checks the saved model against the text, saves
// it as <name>_in.model and takes a screenshot. Needs AToMPM running (make run).
import { mkdirSync } from 'node:fs';
import { dirname, join } from 'node:path';
import { fileURLToPath } from 'node:url';
import { Atompm } from './client.mjs';
import { CSMM, createMap, readModel, sameTree } from './mapmodel.mjs';
import { allTopics, check, layout, readMindMap } from './mm.mjs';

const HERE = dirname(fileURLToPath(import.meta.url));
const SAMPLES = join(HERE, '..', 'samples');
const MODELS = join(HERE, '..', 'users', 'travis', 'Models', 'MindMap');

const TARGETS = ['research', 'courseProject'];

const only = process.argv.slice(2);
const a = await Atompm.open({ width: 2200, height: 3600 });
try {
	mkdirSync(join(HERE, 'out'), { recursive: true });
	for (const name of TARGETS) {
		if (only.length && !only.includes(name)) continue;
		const map = layout(readMindMap(join(SAMPLES, `${name}.mindmap`)));
		const errors = check(map);
		if (errors.length) throw new Error(`${name}: ${errors.join('; ')}`);

		await a.newPage();
		await a.loadmm(`${CSMM}.metamodel`);
		await createMap(a, map);
		await a.validate();
		await a.writeFile(`/Models/MindMap/${name}_in.model`);
		await a.settle(800);
		const diff = sameTree(map, readModel(join(MODELS, `${name}_in.model`)));
		if (diff) throw new Error(`${name}: ${diff}`);
		await a.canvasShot(join(HERE, 'out', `${name}_in.png`));
		console.log(`${name}: ${allTopics(map).length} topics, ${map.markers.length} markers, valid and saved as written`);
	}
	if (a.errors.length) console.log('client errors:', a.errors);
} finally {
	await a.close();
}
