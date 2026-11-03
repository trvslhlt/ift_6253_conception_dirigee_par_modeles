// Loads saved models and screenshots them: node shot.mjs <model path>...
import { basename, dirname, join } from 'node:path';
import { fileURLToPath } from 'node:url';
import { Atompm } from './client.mjs';

const OUT = join(dirname(fileURLToPath(import.meta.url)), 'out');
const a = await Atompm.open({ width: 2400, height: 1600 });
try {
	for (const model of process.argv.slice(2)) {
		await a.newPage();
		await a.load(model);
		await a.canvasShot(join(OUT, `${basename(model, '.model')}.png`));
		console.log('saved', basename(model, '.model'));
	}
} finally {
	await a.close();
}
