# HW2: M2M guide (task 2, ETL)

Task 2 transforms a mind map model (`.mindmap`) into a LaTeX model (`.xmi`) with ETL. Task 3 (Xtend, `.latex` text) and bonus 3 (the automated chain) start from this output, so a few choices below are made for them.

## What is graded

| Item | Weight | Step |
|---|---|---|
| LaTeX metamodel, `.etl`, input `.mindmap`, output `.xmi` | 24% | 1 to 6 |
| 2.1 Characterization | 5% | 8 |
| 2.2 Why it meets the requirements, how you would verify it | 5% | 7, 8 |
| Bonus 2: SubTopics at depth 3 or deeper all become paragraphs | 8% | 4 |

The input must be your course project map, with at least 3 levels.

## Requirements, in terms of our metamodel

| # | Requirement | In our metamodel |
|---|---|---|
| 1 | Document name = map title, Document title = central topic name | `MindMap.title`, `MindMap.topic.name` |
| 2 | Section = MainTopic | `CentralTopic.maintopics` |
| 3 | SubSection = SubTopic at depth 1 | `MainTopic.subtopics` |
| 4 | SubSubSection = SubTopic at depth 2 | `subsubtopics` of a depth-1 SubTopic |
| 5 | Paragraph = SubTopic at depth 3, its name as text | `subsubtopics` of a depth-2 SubTopic |
| 6 | Order of (sub)sections and paragraphs follows the markers | sort siblings by `marker.symbol` |
| 7 | Bonus 2: depth 3 or deeper all become paragraphs | flatten everything under a SubSubSection |

Assumptions the assignment lets you make: every topic has a marker unique among its siblings (the MoTif output), there is one central topic, and without bonus 2 SubTopics at depth 4 or more are ignored.

Two things to get right:

- `Marker.symbol` is a string. Sort with `symbol.asInteger()`, or marker 10 sorts before 2.
- The HW2 assignment says `-` means `isDeep=false` and `--` means `isDeep=true`, but `isDeep` is in neither HW1's metamodel nor the LaTeX one; it is probably left over from an older version of the course. Our grammar uses `-` at every SubTopic level, as HW1's example does, and depth comes from how SubTopics are nested. State this as an assumption in the report.

"Using the modified Ecore metamodel": ours is `MindMap.ecore` from the hw2 Xtext project, unchanged. It already has markers, which is all the transformation needs.

## Where things go

| Path | What |
|---|---|
| `hw2/xtext/` | The mind map language (`ift6253.hw2.mindmap*`). Already copied and renamed. |
| `hw2/m2m/ift6253.hw2.latex/` | New EMF project: `model/LaTeX.ecore`, `LaTeX.genmodel` and the generated code |
| `hw2/m2m/hw2.m2m/` | New plain project: `MindMap2LaTeX.etl`, `models/*.mindmap`, `models/*.xmi`, the shared launch configuration |

The LaTeX metamodel is a plug-in with generated Java code, not just an `.ecore` file. Task 3's Xtend templates need the Java classes, and as a plug-in its nsURI is registered in the runtime Eclipse, as the mind map one is.

For the ZIP, `m2m/` needs every project used to run the transformation: the four Xtext projects, `ift6253.hw2.latex` and `hw2.m2m`.

## Step 0: Prerequisites

- [x] Epsilon 2.9 installed (ETL, EGL, the EMF driver)
- [x] `hw2/xtext` copied and renamed
- [x] Import `hw2/xtext` into the workspace: File > Import > Existing Projects into Workspace, all five projects
- [x] Close the hw1_redux projects (right-click > Close Project). Both languages use `.mindmap`, and the runtime Eclipse would load both editors.

## Step 1: The LaTeX metamodel

**Status: done.** Base Package `ift6253.hw2`, code in `emf-gen/` (ignored by git like the mind map's). EMF left the old package exports in the MANIFEST and `source.. = src/` in `build.properties` after the move; both were fixed by hand.

1. File > New > Other > Eclipse Modeling Framework > **Empty EMF Project**. Name it `ift6253.hw2.latex`, uncheck "Use default location", and set the location to `hw2/m2m/ift6253.hw2.latex`.
2. In `model/`, create `LaTeX.ecore` (New > Other > Eclipse Modeling Framework > Ecore Model, root object EPackage). The wizard does not ask for the package's details: in the tree editor, select the package, open the Properties view (right-click > Show Properties View) and set Name `latex`, Ns Prefix `latex`, Ns URI `http://www.iro.umontreal.ca/ift6253/hw2/latex`. Add classes and features with right-click > New Child, and set their name, type, bounds, Containment, Abstract and ESuper Types in the same view. Or right-click the `.ecore` > Initialize Ecore Diagram to draw it.
3. Add the classes from the assignment's figure:

| Class | Supertype | Features |
|---|---|---|
| *TextContainer* (abstract) | | `text : EString` |
| *NamedTextContainer* (abstract) | TextContainer | `name : EString` |
| Abstract | TextContainer | |
| Paragraph | TextContainer | |
| Bibliography | NamedTextContainer | `style : EString` |
| Section | NamedTextContainer | `subsections : SubSection [0..*]`, containment |
| SubSection | NamedTextContainer | `subsubsections : SubSubSection [0..*]`, containment |
| SubSubSection | NamedTextContainer | `paragraphs : Paragraph [0..*]`, containment |
| Document | | `name : EString` (ID = true), `title`, `author : EString`, `packages : EString [0..*]`; `abstract : Abstract [0..1]`, `sections : Section [0..*]`, `bibliography : Bibliography [0..1]`, all containment. The figure calls the first and last `abs` and `bib`: full names on purpose, note it in the report. |

4. Right-click `LaTeX.ecore` > Validate. Then New > EMF Generator Model from it, named `LaTeX.genmodel`. Set the package's **Base Package** to `ift6253.hw2`, then right-click the root > Generate Model Code.
5. Check that `plugin.xml` now registers the package (`org.eclipse.emf.ecore.generated_package`, with the URI above) and that the MANIFEST exports `ift6253.hw2.latex.*`.

## Step 2: The input models

Create the project `hw2.m2m` (File > New > Project > General > Project) at `hw2/m2m/hw2.m2m`, with a `models/` folder.

**Status:** both files written and parse with no issues. `courseProject.mindmap` was generated from `courseProject_out.model`: same topics, markers identical to the MoTif output, topics in the original text order (so some siblings are out of marker order, which the ETL's sorting must fix). `Entity` (Models > Metamodels > Domain > Entity, depth 3) has four children at depth 4 for bonus 2: Description, Traces, Attributes, Operations.

**`models/courseProject.mindmap`**: the course project map with every topic marked, as `courseProject_out` came out of MoTif (see `hw2/images/courseProject_out.png`). Start from `hw2/xtext/samples/courseProject.mindmap`:

- Declare `marker 1` to `marker 6` in the header.
- Mark every topic, the central topic too (`topic 1 DoModelsHelpAgentsMigrateCode`).
- Topics at depth 3 (for example Models > Metamodels > Domain > Entity) give paragraphs.
- A topic at depth 4 shows bonus 2 on your own map, such as children under `Entity`.

**`models/example.mindmap`**: the assignment's own example, so your output can be compared with its figure:

```
mindmap DocumentName (marker 1, marker 2, marker 3)
topic 1 TitleOfMyPaper includes {
	* 1 Section1 includes {
		- 1 Subsection11 includes {
			- 1 Subsubsection111 includes { - 1 Paragraph1 - 2 Paragraph2 }
			- 2 Subsubsection112 includes { - 1 Paragraph3 - 2 Paragraph4 }
		}
		- 2 Subsection12 includes {
			- 1 Subsubsection121
			- 2 Subsubsection122 includes { - 1 Paragraph6 }
		}
		- 3 Subsection13
	}
	* 2 Section2 includes {
		- 1 Subsection21 includes {
			- 1 Subsubsection211 includes {
				- 1 Paragraph7 includes { - 1 Paragraph8 - 2 Paragraph9 }
			}
		}
	}
	* 3 Section3
}
```

Open both in the runtime Eclipse (step 5) to check they parse with no errors.

## Step 3: The rules

`MindMap2LaTeX.etl` in `hw2.m2m`. One rule per row; the guards on SubTopic must not overlap.

| Rule | From | To | Guard | Body |
|---|---|---|---|---|
| `MindMap2Document` | `MindMap!MindMap` | `LaTeX!Document` | | `name`, `title`, `sections` from the main topics, sorted |
| `MainTopic2Section` | `MindMap!MainTopic` | `LaTeX!Section` | | `name`, `subsections` from `subtopics`, sorted |
| `SubTopic2SubSection` | `MindMap!SubTopic` | `LaTeX!SubSection` | depth 1 | `name`, `subsubsections` from `subsubtopics`, sorted |
| `SubTopic2SubSubSection` | `MindMap!SubTopic` | `LaTeX!SubSubSection` | depth 2 | `name`, `paragraphs` from `subsubtopics`, sorted |
| `SubTopic2Paragraph` | `MindMap!SubTopic` | `LaTeX!Paragraph` | depth 3 | `text` = name |

`MindMap` and `LaTeX` are the model names you give in the launch configuration (step 4). `::=` assigns the targets that other rules produced from a source element or collection; `=` would put the mind map objects themselves there.

Helper operations, in the same file:

```
// Siblings are ordered by their marker's number. Symbols are strings.
operation MindMap!Topic order() : Integer {
	return self.marker.symbol.asInteger();
}

// 1 directly under a main topic, then one more per SubTopic level.
operation MindMap!SubTopic depth() : Integer {
	var parent = self.eContainer();
	if (parent.isTypeOf(MindMap!MainTopic)) {
		return 1;
	}
	return parent.depth() + 1;
}
```

A rule with a guard, for the syntax:

```
rule SubTopic2SubSection
	transform t : MindMap!SubTopic
	to s : LaTeX!SubSection {
	guard : t.depth() = 1
	s.name = t.name;
	s.subsubsections ::= t.subsubtopics.sortBy(c | c.order());
}
```

## Step 4: Bonus 2

**Status (steps 3 and 4): done.** Checked by running the `.etl` headless on both inputs: one Document per output, no stray elements, example tree as in the figure plus Paragraph8 and Paragraph9.

Two changes:

- `SubTopic2Paragraph` guard becomes `t.depth() >= 3`.
- `SubTopic2SubSubSection` takes every SubTopic below it, depth first, each level in marker order:

```
operation MindMap!SubTopic below() : Sequence {
	var result : Sequence;
	for (c in self.subsubtopics.sortBy(c | c.order())) {
		result.add(c);
		result.addAll(c.below());
	}
	return result;
}
```

with `s.paragraphs ::= t.below();`. On `example.mindmap`, `Subsubsection211` then has Paragraph7, Paragraph8, Paragraph9. Without the bonus it has Paragraph7 only, as in the assignment's figure.

## Step 5: Run it

ETL loads the `.mindmap` through the Xtext language, which is only installed in the runtime Eclipse. So the transformation runs there:

1. Run **Launch Runtime Eclipse (hw2)**. It includes every open workspace plug-in, so both `ift6253.hw2.mindmap` and `ift6253.hw2.latex`.
2. In the runtime Eclipse: File > Import > Existing Projects into Workspace > `hw2/m2m/hw2.m2m`, **without** "Copy projects into workspace", so the files stay in the repository.
3. Run > Run Configurations > **ETL Transformation** > New:
   - Source: `/hw2.m2m/MindMap2LaTeX.etl`
   - Models > Add > **EMF Model**:
     - Name `MindMap`, model file `/hw2.m2m/models/courseProject.mindmap`, metamodel URI `http://www.iro.umontreal.ca/ift6253/hw2/mindmap`. Read on load: on. Store on disposal: off.
     - Name `LaTeX`, model file `/hw2.m2m/models/courseProject.xmi`, metamodel URI `http://www.iro.umontreal.ca/ift6253/hw2/latex`. Read on load: off. Store on disposal: on.
   - Common > **Shared file** in `/hw2.m2m`, so the configuration is saved with the project and goes in the ZIP.
4. Run it. Open `courseProject.xmi` with the Sample Reflective Ecore Model Editor and compare with the tree in the assignment.
5. Make a second configuration for `example.mindmap` → `example.xmi`.

## Step 6: Check the output

On `example.xmi`, the tree must match the assignment's figure exactly: Document `DocumentName`, title `TitleOfMyPaper`, Section1 > Subsection11 > Subsubsection111 > Paragraph1, Paragraph2, and so on, with Section3 empty. Without bonus 2, Paragraph8 and Paragraph9 are absent.

On `courseProject.xmi`:

- 4 sections in marker order: Experiment, Models, Migration, Evaluation.
- Subsection `Conditions` with its six children as subsubsections, ordered by their markers: Models 1, Plain 2, Notes 3, Static 4, StaticNotes 5, StaticModels 6. Likewise `Settings` (NoOracle, FullAccess) and `Analysis` (CodeQuality, Attribution, RootCauses).
- Subsubsection `Domain` with paragraphs Entity, Relationship, Invariant. With bonus 2: Entity, Description, Traces, Attributes, Operations, Relationship, Invariant.

## Step 7: Verification for 2.2

What to argue in the report:

- **1:** one MindMap, so one Document. Its name and title are copied.
- **2 to 5:** each SubTopic has exactly one depth, so exactly one guard holds and it becomes exactly one kind of element. The containment of the target follows the containment of the source, because each rule fills its children from its source's children.
- **6:** every collection is filled with `sortBy(order())`, and markers are unique among siblings (assumption, guaranteed by task 1).
- **Bonus 2:** `below()` visits every descendant once, depth first, in marker order.

How to check it, which the assignment does not require you to do: an EOL script (`check.eol`) run with both models loaded, asserting that

- the number of sections, subsections, subsubsections and paragraphs equals the number of topics at each depth (≥ 3 with bonus 2),
- each element's name or text equals its topic's name, and
- each list is in increasing marker order.

Also run it on `example.mindmap` and compare with the figure.

## Step 8: Report items

Draft positions for 2.1. Check the wording against the course slides.

| Concept | Position | Reason |
|---|---|---|
| Intent | Translation (synthesis of a document structure) | Turns a brainstorm into the outline of a paper |
| In-place or out-place | Out-place | Builds a new model; the mind map is read only |
| Endogenous or exogenous | Exogenous | MindMap metamodel in, LaTeX metamodel out |
| Horizontal or vertical | Your call | Horizontal if both are seen as models of the same content; vertical if the LaTeX model is seen as closer to the final artifact. Argue one. |
| Syntactic or semantic | Syntactic | Mapping by structure (depth, order), not by what the topics mean |

Report checklist:

- [ ] The LaTeX metamodel (a diagram picture), noting it follows the assignment's figure
- [ ] The `.etl` file, each rule explained
- [ ] Input `.mindmap` and output `.xmi` for the course project, with a screenshot of the output tree
- [ ] The example's output against the figure
- [ ] 2.1 and 2.2
- [ ] Bonus 2 shown on a depth-4 topic

## Troubleshooting

| Symptom | Likely cause |
|---|---|
| `Metamodel ... not found` in the ETL configuration | The plug-in that registers it is not in the runtime Eclipse: its project is closed in the host workspace, or the runtime Eclipse was started before it existed |
| The `.mindmap` loads as an empty or unknown resource | ETL was run in the host Eclipse, not the runtime one |
| Two editors offered for `.mindmap` | The hw1_redux projects are open in the host workspace |
| A SubTopic is transformed twice, or a warning about multiple rules | Two guards overlap |
| `::=` leaves a list empty | No rule transforms those source elements (wrong guard), or `=` was used where `::=` was needed |
| Sections out of order (10 before 2) | Sorted on the symbol string instead of `asInteger()` |
| `asInteger` called on null | A topic has no marker: the input is not a MoTif output |
| `courseProject.xmi` is not written | "Store on disposal" is off on the LaTeX model |
