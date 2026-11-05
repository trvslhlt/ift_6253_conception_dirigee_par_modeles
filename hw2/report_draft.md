# Report Draft

## Raw Notes

### Header

- last 4 digits of metricule
- hours spent: 20h
- email: travis.holt@umontreal.ca

## Assumptions

- Every topic has a marker unique amongst its siblings.
- There is no notion of `author` or `packages` in MindMap.
- `Document.name` is the filename.

### Tasks

Assign markers in AToMPM
1. Using the same metamodel as in assignment 1, implement the graph transformation using the MoTif model transformation language in AToMPM. You must therefore design the rules and their execution logic. You must include an example input model and an example output model of your transformation.
    1. Characterize this transformation according to the concepts seen in class: intention, in/outplace, exo/endogenous, horizontal/vertical, syntactic/sematical.
    2. Describe why the transformation you defined satisfies the listed needs. How will you perform this verification? Note that you are not required to do so.

- I repeated HW1 from scratch. This time I used the metamodel from the assignment as the target, unchanged. The sample models in both Xtext and AToMPM now reflect the structure of my course research project. I also did not add optional features or explore additional capabilities of the modeling tooling.
- The MoTif graph transformation that updates a MindMap to have conformant topic markers is implemented as a set of rules orchestrated by a schedule (`/Models/MindMap/R_*` and `/Models/MindMap/T_AssignMarkers`).
- Sample models (in `Models/MindMap`):
    - `research_in.model` -> `research_out.model` (the assignment's example)
    - `courseProject_in.model` -> `courseProject_out.model` (my course project, with conflicts added)
- 1. Characterizing the transformation
    - intent: Model editing. Update the topic markers so the mindmap conforms to these criteria:
        1. All topics must be decorated with a marker that represents a number.
        2. All sibling markers must be unique.
        3. Preserve topic markers unless they conflict with other creiteria.
    - in-place: The transformation rules change the input model; they do not create a new model. (Note: the resulting model is saved as a new model to preserve the input model.)
    - endogenous: The input and output models (which are the same model, modified) conform to the same metamodel.
    - horizontal: The input and output are at the same level of abstraction.
    - syntatic: The semantics of a mindmap have no bearing on the transformation.
- 2. The transformation has 5 phases:
    1. Create first marker: If the model has no markers, create one for use in subsequent phases (`CreateFirstMarker`).
    2. Deduplicate markers: While two siblings share a marker, remove it from the one whose name comes later (`Dedup*`).
    3. Assign markers: Link each unmarked topic to a marker none of its siblings has (`Assign*`).
    4. Check: If no topic is unmarked, the transformation ends in success (`HasUnmarkedTopic`). (this could not be added as a NAC in `CreateMarker` because of a bug in AToMPM)
    5. Create new markers: Otherwise every marker is taken by a sibling, so create the largest + 1 and return to phase 3 (`CreateMarker`).
    - The schedule begins by ensuring there is a at least one marker. It then deduplicates topic markers. Next it assigns markers to topics. This continues until there are no unassigned numbers or un-marked topics. In the former case, one is created and the schedule returns to the assignment rules. In the later case the transformation is complete. We can verify the transformation by checking the output model with a specification program. This program can traverse the model to confirm all of our desired topic/marker criteria are met. 
    - Requirement 1: The schedule only reaches EndSuccess when HasUnmarkedTopic fails (when no topic lacks a marker).
    - Requirement 2, 3: After Dedup, no siblings share a marker. Afterwards markers are only added by Assign whose 'NAC_taken' forbids a marker a sibling already has.
    - Termination: each Dedup removes one shared link, each Assign marks one topic, and CreateMarker only runs when a topic is still unmarked, so the number of unmarked topics decreases on every loop.

M2M MindMap to LaTeX
2. Using the modified Ecore metamodel, implement the model-to-model transformation using the ATL or ETL model transformation language in EMF. You must therefore redefine the metamodel (file .ecore) and define the transformation (file .atlor .etl). You must include the example input model (file .mindmap) and the example output model (file .xmi) of your transformation. The example model must correspond to the minimum map in your project with at least 3 levels (see assignment 1).
    1. Characterize this transformation according to the concepts seen in class: intention, in/outplace, exo/endogenous, horizontal/vertical, syntactic/sematical.
    2. Describe why the transformation you defined satisfies the listed needs. How will you perform this verification? Note that you are not required to do so.
- The ETL transformation that converts a MindMap into a LaTeX document is `m2m/ift6253.hw2.mindmap2latex/MindMap2LaTeX.etl`. The source metamodel is `MindMap.ecore` from the hw2 Xtext project (the HW1 metamodel, which already has markers). The target metamodel, `LaTeX.ecore`, is based on the figure in the assignment and was defined in the Ecore graphical editor.
- Sample source and target models are:
    - `models/example.mindmap` -> `models/example.xmi` (the assignment's example)
    - `models/courseProject.mindmap` -> `models/courseProject.xmi` (my course project, more than 3 levels deep).
- 1. Characterizing the transformation
    - intent: Translation. Convert a MindMap into a LaTeX document model that meets these criteria:
        1. Document's name is the mindmap's title, and title is the CentralTopic's name.
        2. Each MainTopic becomes a Section with the same name.
        3. Each SubTopic at depth 1 becomes a SubSection, and at depth 2 a SubSubSection.
        4. Each SubTopic at depth 3 or greater becomes a Paragraph with its name as the text.
        5. Marker order is preserved for (sub)sections and paragraphs.
    - out-place: The transformation creates a new LaTeX model; the input model is not modified.
    - exogenous: Different metamodels. The input conforms to the MindMap metamodel and the output to the LaTeX metamodel.
    - horizontal: The input and output are at the same level of abstraction.
    - syntactic: The rules depend only on the type of a topic, its depth and its marker. What a topic means has no bearing on the transformation.
- 2. The transformation has one rule per target type:
    1. MindMap2Document: Create the Document, and fill its sections with the MainTopics sorted by marker.
    2. MainTopic2Section: Create a Section, and fill its subsections with the SubTopics sorted by marker.
    3. SubTopic2SubSection: For depth 1 SubTopics, create a SubSection, and fill its subsubsections with the children sorted by marker.
    4. SubTopic2SubSubSection: For depth 2 SubTopics, create a SubSubSection, and fill its paragraphs with all of its descendants, flattened depth first and sorted by marker at each level.
    5. SubTopic2Paragraph: For SubTopics at depth 3 or more, create a Paragraph with the topic's name as its text.
    The guards on depth are mutually exclusive, so every SubTopic is transformed by exactly one rule. The `::=` operator resolves each child topic to the element created from it by another rule, and `sortBy(order())` keeps the marker order. The flattening in `below()` is what makes topics deeper than level 3 become Paragraphs instead of being ignored. We can verify the transformation by running it on the assignment's example and comparing the output with the `.xmi` in the assignment. A specification program could also traverse the input and output models together, confirming that each topic has exactly one counterpart of the right type and name, in marker order.

M2T LaTeX to Text
3. Using the modified Ecore metamodel, implement a model-to-text transformation using the Xtend or EGL model transformation language in EMF. You must therefore define the templates (file .xtendor .egl) and the workflow (file .mwe2) for Xtend. You must include the output model (file .latex) which corresponds to the model produced by the transformation in task 2.
    1. Characterize this transformation according to the concepts seen in class: intention, in/outplace, exo/endogenous, horizontal/vertical, syntactic/sematical.
    2. Describe why the transformation you defined satisfies the listed needs. How will you perform this verification? Note that you are not required to do so.
- The Xtend M2T transformation is `m2t/ift6253.hw2.latex.generator/src/ift6253/hw2/latex/generator/LaTeXTemplate.xtend`. It is run by the MWE2 workflow `GenerateLaTeX.mwe2`, which reads the `.xmi` models from task 2 and calls `LaTeXGeneratorComponent` to write `<Document.name>.latex`.
- The outputs are `latex/DocumentName.latex` (from the assignment's example) and `latex/ModelGuidedMigration.latex` (from my course project), plus `ModelGuidedMigration.pdf` compiled with pdflatex.
- 1. Characterizing the transformation
    - intent: Synthesis (code generation). Produce a valid `.latex` file from a LaTeX model
    - out-place: The transformation produces a new text file; the input model is not modified.
    - exogenous: The input conforms to the LaTeX metamodel, and the output is text in LaTeX's concrete syntax, not a model of the same metamodel.
    - vertical: The output is closer to the final artifact. It is source code that can be compiled into a PDF.
    - syntactic: Each template prints its element the same way whatever the content. 
- 2. The transformation is one template per metaclass, dispatched by element type:
    1. Document: Print the preamble, then each Section.
    2. Section: Print `\section{name}`, then each SubSection.
    3. SubSection: Print `\subsection{name}`, then each SubSubSection.
    4. SubSubSection: Print `\subsubsection{name}`, then each Paragraph.
    5. Paragraph: Print the text and a blank line.
    The templates follow the containment of the LaTeX model, and the order of the lists was already fixed by the ETL transformation, so the text keeps the marker order. We can verify the transformation by generating `DocumentName.latex` from the assignment's example and comparing it with the assignment's listing; the two are identical. Compiling the output with pdflatex also confirms it is valid LaTeX, and the PDF can be compared with the assignment's figure.

E2E AToMPM Model and Metamodel to PDF
4. Bonus 3: Automate the transformation chain that starts with a mind map model, transforms it into a LaTeX model and then produces the text file. You can use AToMPM's model (and metamodel) exporter to Ecore under /Toolbars/Ecore to get a mind map model already assigned unique markers. The automation would start once the model is exported.
- The chain is the MWE2 workflow `m2t/ift6253.hw2.chain/src/ift6253/hw2/chain/AToMPM2LaTeX.mwe2`. It starts from `courseProject_out` (the output of task 1), exported with AToMPM's Ecore exporter to `atompm/exported_to_ecore/courseProject_outModel.xmi`, and ends with a PDF.
- The outputs are in `m2t/ift6253.hw2.chain/output/`:
    - `courseProject.xmi` (the repaired export)
    - `courseProject.latex.xmi` (the LaTeX model)
    - `ModelGuidedMigration.latex` and `ModelGuidedMigration.pdf`
- The AToMPM exporter has bugs, so its output can't be loaded by EMF as is:
    - metamodel: `upperBound="Infinity"` (3 places), only the root class's references are containment, and `subsubtopics` is single-valued.
    - model: the root element is `<undefined>`.
- The workflow has 4 steps:
    1. FixExportComponent: Repair the model export, and save it as `output/courseProject.xmi`.
    2. EtlComponent: Run task 2's `MindMap2LaTeX.etl` on it, through Epsilon's Java API, and save the LaTeX model.
    3. Reader + LaTeXGeneratorComponent: Load the LaTeX model and apply task 3's templates to write the `.latex` file.
    4. PdfLatexComponent: Run pdflatex on the `.latex` file to produce the PDF.
    The metamodel export was repaired once by hand (`model/MindMapAToMPM.ecore`), not in the workflow. The fixes depend on what each reference means (which ones are containment, which are many-valued), which the exporter doesn't record. A script would have to be rewritten for every metamodel. The metamodel only changes when the formalism does, so a one-time repair is enough.
    The chain uses the AToMPM metamodel instead of the Xtext `MindMap.ecore`. The export nests each topic's marker inside the topic, and in the Xtext metamodel `Topic.marker` is a non-containment reference, so the nested markers can't be loaded.
    The ETL transformation and the templates are reused unchanged. We can verify the chain by comparing its output with task 3's: `output/ModelGuidedMigration.latex` is identical to `latex/ModelGuidedMigration.latex`. To update the PDF after editing the model in AToMPM, export it again and rerun the workflow.