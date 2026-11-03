// Builds T_Test.model, a one-rule schedule for trying a rule on its own:
// Start -initial-> ARule -success-> EndSuccess, -fail-> EndFail.
// Usage: node build_t_test.mjs [/Models/MindMap/R_<Name>.model]
import { Atompm } from './client.mjs';
import { CANVAS_TOP, route } from './mm.mjs';

const MOTIF = '/Formalisms/__Transformations__/Transformation/MoTif.defaultIcons';
const type = name => `${MOTIF}/${name}.type`;
const rule = process.argv[2] ?? '/Models/MindMap/R_ResetMap.model';
const [x, y] = [200, CANVAS_TOP];

const a = await Atompm.open();
try {
	await a.loadmm(`${MOTIF}.metamodel`);
	const start = await a.create(type('StartIcon'), [x + 20, y]);
	const arule = await a.create(type('ARuleIcon'), [x, y + 160]);
	await a.update(arule, { name: 'test', rule });
	const ok = await a.create(type('EndSuccessIcon'), [x - 40, y + 400]);
	const ko = await a.create(type('EndFailIcon'), [x + 180, y + 400]);
	const link = (name, src, dest, from, to) => a.create(type(name), { src, dest, ...route(from, to) });
	await link('initialLink', start, arule, [x + 50, y + 60], [x + 50, y + 160]);
	await link('successLink', arule, ok, [x + 30, y + 260], [x - 10, y + 400]);
	await link('failLink', arule, ko, [x + 90, y + 260], [x + 210, y + 400]);
	await a.writeFile('/Models/MindMap/T_Test.model');
	console.log(`T_Test.model saved, running ${rule}`, a.errors);
} finally {
	await a.close();
}
