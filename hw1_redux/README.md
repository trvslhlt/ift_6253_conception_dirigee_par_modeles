# HW1 redux: mind map DSL on the assignment's metamodel

HW1 redone on the example metamodel of the assignment (MindMap, Marker,
abstract Topic, CentralTopic, MainTopic, SubTopic), as a base for HW2. The
report is [rapport.htm](rapport.htm).

| Folder | What |
|---|---|
| `xtext/ift6253.mindmap*` | Xtext projects. `model/MindMap.ecore` is the metamodel, `MindMap.xtext` the grammar mapped onto it. |
| `xtext/samples/` | `research.mindmap` (the assignment's example) and `courseProject.mindmap`. The AToMPM samples are built from these same files. |
| `atompm/users/travis/` | The AToMPM formalism (`Formalisms/MindMap`) and models (`Models/MindMap`). |
| `atompm/tools/` | Scripts that build the formalism and samples through AToMPM's own client. |

## Xtext

`emf-gen/` and `src-gen/` are not committed. In Eclipse, import the four
projects and `samples/`, then run `GenerateMindMap.mwe2` (Run As > MWE2
Workflow), then `.launch/Launch Runtime Eclipse.launch`.

Tests: `ift6253.mindmap.tests/src/.../MindMapParsingTest.java` (Run As > JUnit
Plug-in Test). They cover the example, the hierarchy rules, marker references,
the validator, and the course project's 3 levels below each MainTopic.

## AToMPM

```bash
cd hw1_redux/atompm && make run       # AToMPM on http://localhost:8125/atompm
cd tools && npm install
node build_formalism.mjs              # class diagram + icons, compiled
node build_samples.mjs                # research + courseProject models
node test_formalism.mjs               # one rejected violation per rule
```

The account is `travis` (password from the hw1 account).
