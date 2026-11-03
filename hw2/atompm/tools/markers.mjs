// The requirements of T_AssignMarkers, checked on an input map and the output
// map, both in the { title, markers, topic } shape of readModel. A topic is
// identified by its path of names from the central topic.

// Path -> topic.
export function byPath(map) {
	const out = new Map();
	const walk = (t, path) => { out.set(path, t); t.children.forEach(c => walk(c, `${path}/${c.name}`)); };
	walk(map.topic, map.topic.name);
	return out;
}

// The groups of siblings, as lists of paths. The central topic is alone.
function siblingGroups(map) {
	const groups = [[map.topic.name]];
	for (const [path, t] of byPath(map))
		if (t.children.length) groups.push(t.children.map(c => `${path}/${c.name}`));
	return groups;
}

// Problems found, as messages; empty if the output meets every requirement.
export function checkAssignment(input, output) {
	const problems = [];
	const before = byPath(input), after = byPath(output);
	const kinds = m => [...byPath(m)].map(([p, t]) => `${p}:${t.kind}`).sort().join();
	if (kinds(input) !== kinds(output)) problems.push('the topics or their parents changed');
	if (new Set(output.markers).size !== output.markers.length) problems.push(`duplicate marker symbols: ${output.markers}`);

	// Requirement 1: every topic has exactly one marker.
	for (const [path, t] of after)
		if (t.markers.length !== 1) problems.push(`${path} has ${t.markers.length} markers`);

	for (const group of siblingGroups(input)) {
		// Requirements 2 and 3: siblings end up with different markers.
		const symbols = group.map(p => after.get(p)?.marker);
		if (new Set(symbols).size !== symbols.length)
			problems.push(`siblings ${group.map((p, i) => `${p}:${symbols[i]}`).join(', ')} share a marker`);

		// Bonus 1: of the siblings holding a marker in the input, exactly one
		// still holds it, whether it was shared or not.
		const holders = new Map();
		for (const p of group) {
			const m = before.get(p).marker;
			if (m) holders.set(m, [...(holders.get(m) ?? []), p]);
		}
		for (const [symbol, paths] of holders) {
			const kept = paths.filter(p => after.get(p)?.marker === symbol);
			if (kept.length !== 1) problems.push(`${kept.length} of ${paths.join(', ')} kept marker ${symbol}`);
		}
	}
	return problems;
}

// Path -> marker symbol.
export const markersOf = map => Object.fromEntries([...byPath(map)].map(([p, t]) => [p, t.marker]));
