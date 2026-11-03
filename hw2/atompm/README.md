# HW2 task 1: marker assignment in MoTif

Built on the HW1 redux formalism (the assignment's metamodel: `Marker` nodes
owned by the map, a topic's marker is its `marker` link). The report is
[../rapport.htm](../rapport.htm).

| Path | What |
|---|---|
| `users/travis/Formalisms/MindMap/` | The formalism, plus the pattern metamodels the rules are drawn with |
| `users/travis/Models/MindMap/R_*.model` | The 10 rules |
| `users/travis/Models/MindMap/T_AssignMarkers.model` | The schedule |
| `users/travis/Models/MindMap/*_in.model`, `*_out.model` | Sample input and output: `research` (the assignment's example) and `courseProject` |
| `samples/*.mindmap` | The sample inputs as text, which `build_samples.mjs` draws |
| `tools/` | Scripts that build and run everything through AToMPM's own client |

## Run

```bash
cd hw2/atompm && make run            # AToMPM on http://localhost:8124/atompm
cd tools && npm install
node build_formalism.mjs             # metamodel, icons, pattern metamodels
node build_samples.mjs               # research_in, courseProject_in
node build_transformation.mjs        # R_*.model, T_AssignMarkers.model
node run_transformation.mjs          # *_out.model, checked, then rerun to check nothing changes
node test_transformation.mjs         # six small cases
node test_formalism.mjs              # the formalism's constraints
```

Every script places elements at y >= `CANVAS_TOP` (400, in `geometry.mjs`) so
that they stay clear of the toolbars and can be selected in the UI.

In the UI: open a `*_in.model`, load `T_AssignMarkers.model` in the
transformation controller and press play. The rules only add and remove links,
and AToMPM reruns a topic's icon mappers only when one of its attributes
changes, so the green numbers in front of topic names do not update during the
run; the marker links do. `run_transformation.mjs` redraws the labels and moves
the created markers into the marker row before saving `*_out.model`.

## Engine bug worked around

In `mt/ptcal/pytcore/tcore/matcher.py`, the loop over bound NACs assigns each
NAC's matches to the same variable as the LHS match. When a NAC matches its
structure but fails its condition, the NACs checked after it are bound to the
wrong elements. No rule here combines a NAC that has a condition with another
NAC, which is why "a topic is still unmarked" is the query `HasUnmarkedTopic`
rather than a second NAC of `CreateMarker`.
