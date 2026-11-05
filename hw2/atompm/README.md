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
| `samples/*.mindmap` | The sample inputs as text |

## Run

```bash
cd hw2/atompm && make run            # AToMPM on http://localhost:8124/atompm
```

## Engine bug worked around

In `mt/ptcal/pytcore/tcore/matcher.py`, the loop over bound NACs assigns each
NAC's matches to the same variable as the LHS match. When a NAC matches its
structure but fails its condition, the NACs checked after it are bound to the
wrong elements. No rule here combines a NAC that has a condition with another
NAC, which is why "a topic is still unmarked" is the query `HasUnmarkedTopic`
rather than a second NAC of `CreateMarker`.
