# AToMPM MindMap tooling

Scripts that drive AToMPM through its own web client in headless Chrome, so the
language can be rebuilt without the UI. Everything is compiled, created and saved
by AToMPM itself, into `../users/travis/`.

```bash
cd hw1/atompm && make run    # in one terminal; needs the atompmvm image
cd hw1/atompm/tools
npm install                  # once; uses your installed Chrome (override with CHROME=...)
node build_formalism.mjs     # apply language fixes, recompile AS, CS and pattern metamodels
node build_samples.mjs       # rebuild the sample models from the .mm files (or name some)
node test_formalism.mjs      # constraint rejection, reload and rerun checks
```

| File | Role |
|---|---|
| `build_formalism.mjs` | Metamodel changes, constraints, icon mappers and geometry |
| `build_samples.mjs` | `.mm` to AToMPM model: create, validate, transform, verify, save |
| `test_formalism.mjs` | One violation per constraint, plus reload of every model |
| `mm.mjs` | `.mm` parser and tree layout |
| `client.mjs` | Promise wrapper over the AToMPM client |
| `sample0.mm` | Small smoke-test map |

Worth knowing:

- Compile the pattern metamodel to `MindMap.pattern.metamodel`. AToMPM also writes
  `MindMap.defaultIcons.pattern.metamodel` from it. A target with no matching icon
  metamodel throws uncaught and kills the node server.
- `topicWidth` in `mm.mjs` must agree with the Topic icon's mapper.
- `level` and `effectivelyDone` only change when `T_ComputeLevels` runs, so rerun it
  after editing a map's structure.
- The constraints run on the validate button, not on each edit.
