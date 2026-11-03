// Drives AToMPM through its own web client in headless Chrome, so everything is
// compiled, created and saved by AToMPM itself rather than written by hand.
import puppeteer from 'puppeteer-core';

const CHROME = process.env.CHROME || '/Applications/Google Chrome.app/Contents/MacOS/Google Chrome';
export const URL = process.env.ATOMPM_URL || 'http://localhost:8124/atompm';

const sleep = ms => new Promise(r => setTimeout(r, ms));

export class Atompm {
	static async open({ user = 'travis', width = 1800, height = 1100 } = {}) {
		const browser = await puppeteer.launch({
			executablePath: CHROME, headless: true, defaultViewport: { width, height },
		});
		const a = new Atompm();
		a.browser = browser;
		a.errors = [];
		await a.newPage(user);
		return a;
	}

	async newPage(user = 'travis') {
		if (this.page) await this.page.close();
		this.page = await this.browser.newPage();
		this.console = [];
		this.page.on('pageerror', e => this.errors.push(String(e)));
		this.page.on('console', m => this.console.push(m.text()));
		await this.page.goto(URL, { waitUntil: 'networkidle2' });
		await this.page.evaluate(u => UserManagement.login(u), user);
		await this.page.waitForFunction(() => typeof __wid !== 'undefined' && __wid != undefined && __aswid != undefined, { timeout: 30000 });
		await sleep(1500);
		// Requests answer asynchronously, so promote the client's callback API.
		await this.page.evaluate(() => {
			window.req = (method, url, params) => new Promise(resolve =>
				HttpUtils.httpReq(method, url, params, (status, text) => resolve({ status, text })));
			window.ok = s => utils.isHttpSuccessCode(s);
		});
	}

	// One request to the client's worker; the url options mirror data_utils.js.
	async req(method, uri, params, opt = 'NO_USERNAME') {
		return this.page.evaluate(async (method, uri, params, opt) => {
			const o = { NO_USERNAME: __NO_USERNAME, FORCE_GET: __FORCE_GET, NO_WID: __NO_WID, NONE: 0 }[opt];
			return window.req(method, HttpUtils.url(uri, o), params ?? undefined);
		}, method, uri, params, opt);
	}

	async mustReq(...args) {
		const r = await this.req(...args);
		if (r.status < 200 || r.status >= 300) throw new Error(`${args[0]} ${args[1]} -> ${r.status} ${r.text}`);
		return r;
	}

	async settle(ms = 400) { await sleep(ms); }

	loadmm(csmm) {
		return this.page.evaluate(csmm => new Promise(resolve =>
			DataUtils.loadmm(csmm, (status, text) => resolve({ status, text }))), csmm);
	}

	// Loads a model, first loading any formalism it needs (as DataUtils.loadm does).
	async load(fname) {
		for (let tries = 0; tries < 8; tries++) {
			const r = await this.page.evaluate(fname => window.req('PUT',
				HttpUtils.url('/current.model', __NO_USERNAME),
				{ m: HttpUtils.url(fname, __NO_WID), insert: false }), fname);
			if (r.status >= 200 && r.status < 300) { await this.settle(1500); return r; }
			const missing = String(r.text).match(/metamodel not loaded :: (.*)/);
			if (!missing) throw new Error(`load ${fname}: ${r.status} ${r.text}`);
			const mm = await this.loadmm(`${missing[1]}.metamodel`);
			if (mm.status < 200 || mm.status >= 300) throw new Error(`loadmm ${missing[1]}: ${mm.text}`);
		}
		throw new Error(`load ${fname}: gave up`);
	}

	iconUris() { return this.page.evaluate(() => Object.keys(__icons)); }

	update(uri, changes) { return this.mustReq('PUT', uri, { changes }); }

	// Same, for concrete-syntax attributes such as position.
	updatecs(uri, changes) { return this.mustReq('PUT', `${uri}.cs`, { changes }); }

	// Creates a node or, given { src, dest }, a link; resolves to the new icon's uri.
	async create(typeUri, params) {
		const before = new Set(await this.iconUris());
		const body = Array.isArray(params) ? { pos: params } : params;
		await this.mustReq('POST', typeUri, body);
		for (let tries = 0; tries < 100; tries++) {
			const added = (await this.iconUris()).filter(u => !before.has(u));
			if (added.length) return added[0];
			await sleep(50);
		}
		throw new Error(`no icon appeared for ${typeUri}`);
	}

	// Runs the formalism's constraints, like the "validate" button.
	validate() { return this.mustReq('GET', '/validatem'); }

	// Runs a transformation to completion, like load + play on the controller.
	async transform(fname, { timeout = 120000 } = {}) {
		this.console.length = 0;
		await this.page.evaluate(async (fname, user) => {
			await window.req('PUT', `/__mt/current.transform?wid=${__wid}`, { transfs: [fname], username: user });
			await new Promise(r => setTimeout(r, 1500));
			await window.req('PUT', `/__mt/execmode?wid=${__wid}`, { mode: 'play' });
		}, fname, await this.page.evaluate(() => __user));
		const deadline = Date.now() + timeout;
		while (Date.now() < deadline) {
			const done = this.console.find(l => /transformation\(s\) terminated with status/.test(l));
			const error = this.console.find(l => /ERROR ::/.test(l));
			if (error) throw new Error(`transform ${fname}: ${error}`);
			if (done) {
				if (!/Success/.test(done)) throw new Error(`transform ${fname}: ${done}`);
				await sleep(800);
				return this.console.slice();
			}
			await sleep(100);
		}
		throw new Error(`transform ${fname}: timed out\n${this.console.slice(-10).join('\n')}`);
	}

	// Saves a picture of the canvas content, without the toolbars.
	async canvasShot(path, margin = 40) {
		const clip = await this.page.evaluate(margin => {
			const canvas = document.getElementById('div_canvas');
			document.querySelectorAll('body *').forEach(e => {
				if (!e.contains(canvas) && !canvas.contains(e)) e.style.visibility = 'hidden';
			});
			const box = document.querySelector('#div_canvas svg').getBBox();
			const at = document.getElementById('div_canvas').getBoundingClientRect();
			return { x: Math.max(0, at.x + box.x - margin), y: Math.max(0, at.y + box.y - margin),
				width: box.width + 2 * margin, height: box.height + 2 * margin };
		}, margin);
		await this.page.setViewport({
			width: Math.ceil(clip.x + clip.width) + 20, height: Math.ceil(clip.y + clip.height) + 20 });
		await sleep(300);
		await this.page.screenshot({ path, clip });
	}

	// Saving and all three compile targets are the same request: PUT /GET/<file>.
	writeFile(fname) { return this.mustReq('PUT', fname, null, 'FORCE_GET'); }

	async close() { await this.browser.close(); }
}
