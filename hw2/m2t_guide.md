# HW2: M2T guide (task 3, Xtend)

Task 3 turns a LaTeX model (`.xmi`, the output of task 2) into a `.latex` text file, with Xtend templates run by an MWE2 workflow. It reads only the LaTeX model, never the mind map, so it runs in your normal workspace: no runtime Eclipse needed.

## What is graded

| Item | Weight | Step |
|---|---|---|
| Templates (`.xtend`), workflow (`.mwe2`), output `.latex` for the task 2 model | 23% | 1 to 5 |
| 3.1 Characterization | 5% | 7 |
| 3.2 Why it meets the requirements, how you would verify it | 5% | 6, 7 |

The required output is the `.latex` produced from task 2's model, so `courseProject`. Do `example` too: the assignment shows its exact expected text.

## What the output must look like

The assignment's `DocumentName.latex` defines the format. For `example.xmi`, with bonus 2 (so Paragraph8 and Paragraph9 are there), the output should be exactly:

```latex
\documentclass{article}
\begin{document}
\title{TitleOfMyPaper}
\author{}
\maketitle
\section{Section1}
\subsection{Subsection11}
\subsubsection{Subsubsection111}
Paragraph1

Paragraph2

\subsubsection{Subsubsection112}
Paragraph3

Paragraph4

\subsection{Subsection12}
\subsubsection{Subsubsection121}
\subsubsection{Subsubsection122}
Paragraph6

\subsection{Subsection13}
\section{Section2}
\subsection{Subsection21}
\subsubsection{Subsubsection211}
Paragraph7

Paragraph8

Paragraph9

\section{Section3}
\end{document}
```

Rules that follow from it:

| Model element | Text |
|---|---|
| Document | `\documentclass{article}`, `\begin{document}`, `\title{title}`, `\author{author}` (empty when unset), `\maketitle`, its sections, `\end{document}` |
| Section | `\section{name}`, then its subsections |
| SubSection | `\subsection{name}`, then its subsubsections |
| SubSubSection | `\subsubsection{name}`, then its paragraphs |
| Paragraph | its text, then one empty line (that is what makes LaTeX start a new paragraph) |
| File name | `<Document name>.latex`: `DocumentName.latex`, `ModelGuidedMigration.latex` |

Not in the example, but in the metamodel, so handle them if present: each of `packages` as `\usepackage{…}` before `\begin{document}`; `abstract` as `\begin{abstract}…\end{abstract}` after `\maketitle`; `bibliography` before `\end{document}`. Task 2 never sets them, so they produce nothing.

Order needs no work here: task 2 already put every list in marker order, and the templates print lists as they are.

## Where things go

| Path | What |
|---|---|
| `hw2/m2t/ift6253.hw2.latex.generator/` | New plug-in project: the Xtend templates, the workflow component, the `.mwe2`, and the output |
| `…/src/ift6253/hw2/latex/generator/LaTeXTemplate.xtend` | The templates |
| `…/src/ift6253/hw2/latex/generator/LaTeXGeneratorComponent.xtend` | Glue: an MWE2 component that takes the model from the workflow and writes the file |
| `…/src/ift6253/hw2/latex/generator/GenerateLaTeX.mwe2` | The workflow |
| `…/latex/` | The generated `.latex` files (and PDFs if you compile them) |
| `xtend-gen/` | Java generated from the Xtend files; ignored by git like `src-gen/` |

For the ZIP, `m2t/` needs the generator project, `ift6253.hw2.latex`, and the input `.xmi` files.

## Step 0: Prerequisites

- [x] Xtend 2.44 and MWE2 installed (they come with Eclipse Modeling)
- [x] `ift6253.hw2.latex` with its generated code (task 2, step 1)
- [x] `example.xmi` and `courseProject.xmi` in `hw2/m2m/hw2.m2m/models/` (task 2, step 5)
- [x] `pdflatex` installed, to check the output compiles

## Step 1: The project

1. File > New > Plug-in Project. Name `ift6253.hw2.latex.generator`, location `hw2/m2t/ift6253.hw2.latex.generator`. On the next page: no activator, no UI contributions, not a rich client application. No template.
2. In `META-INF/MANIFEST.MF` > Dependencies, add:
   - `ift6253.hw2.latex` (the metamodel's Java classes)
   - `org.eclipse.emf.ecore`, `org.eclipse.emf.ecore.xmi`
   - `org.eclipse.emf.mwe2.runtime`, `org.eclipse.emf.mwe2.lib`, `org.eclipse.emf.mwe2.launch`, `org.eclipse.emf.mwe.utils`
   - `org.eclipse.xtend.lib`
3. Xtend compiles each `.xtend` into Java under `xtend-gen/`. That folder is set up as a second source folder (`.classpath`, `build.properties`), and the project has the Xtext builder and nature that run the Xtend compiler. If a new Xtend file still says a library is missing, take the quick fix "Add Xtend libs to classpath".

Status: 1.1 done by you; 1.2 and 1.3 done in the files. Refresh the project in Eclipse (F5) to pick them up.

## Step 2: The templates

`LaTeXTemplate.xtend`: one method per model element, each returning the element's text. Xtend writes templates as rich strings, between `'''`, with expressions in guillemets `«…»`. On a Mac keyboard: Option+\ gives `«`, Option+Shift+\ gives `»`; content assist (Ctrl+Space) inside a template offers them too.

The shape, with the Document and Paragraph methods written out:

```xtend
package ift6253.hw2.latex.generator

import ift6253.hw2.latex.Document
import ift6253.hw2.latex.Paragraph
// import Section, SubSection, SubSubSection likewise

class LaTeXTemplate {

	def generate(Document it) '''
		\documentclass{article}
		«FOR p : packages»
		\usepackage{«p»}
		«ENDFOR»
		\begin{document}
		\title{«title.escape»}
		\author{«(author ?: '').escape»}
		\maketitle
		«FOR s : sections»
		«s.generate»
		«ENDFOR»
		\end{document}
	'''

	// generate(Section), generate(SubSection), generate(SubSubSection):
	// the command with the name, then each child through generate(...)

	def generate(Paragraph it) '''
		«text.escape»

	'''
}
```

How rich strings handle whitespace, which is most of the work in getting the text exact:

- Indentation inside `'''` relative to the template's first line is removed, so indent for readability.
- A line holding only `«FOR …»`, `«ENDFOR»`, `«IF …»` or `«ENDIF»` produces no output line.
- A line holding only an expression, such as `«s.generate»`, is replaced by the expression's text, which already ends with its own line break; check the output for doubled or missing empty lines and adjust.
- The empty line in `generate(Paragraph)` is the empty line after each paragraph.

Two things that will bite:

- **Escaping.** `_`, `&`, `%`, `$`, `#`, `{`, `}`, `~`, `^` and `\` are special in LaTeX. Your course project has `semver_sort`, and an unescaped `_` stops `pdflatex` with "Missing $ inserted". Add an `escape(String)` method that puts a backslash before `_ & % $ # { }` (and replaces `\`, `~`, `^` with `\textbackslash{}`, `\textasciitilde{}`, `\textasciicircum{}`, the backslash first). Use it on every name, title, author and text.
- **`abstract` is a keyword in Xtend** (and in Java, which is why the getter is `getAbstract()`). Write it `^abstract` in Xtend: `«IF ^abstract !== null»`.

## Step 3: The workflow component

`LaTeXGeneratorComponent.xtend`: MWE2 runs components in order and passes models between them in named slots. MWE's `Reader` (step 4) puts the loaded Document in a slot; this component takes it from there, runs the template and writes the file.

```xtend
package ift6253.hw2.latex.generator

import ift6253.hw2.latex.Document
import java.nio.file.Files
import java.nio.file.Paths
import org.eclipse.emf.mwe2.runtime.workflow.IWorkflowComponent
import org.eclipse.emf.mwe2.runtime.workflow.IWorkflowContext
import org.eclipse.xtend.lib.annotations.Accessors

class LaTeXGeneratorComponent implements IWorkflowComponent {
	@Accessors String modelSlot
	@Accessors String outputDir

	override preInvoke() {}

	override invoke(IWorkflowContext context) {
		val document = context.get(modelSlot) as Document
		val file = Paths.get(outputDir, document.name + '.latex')
		Files.createDirectories(file.parent)
		Files.writeString(file, new LaTeXTemplate().generate(document))
	}

	override postInvoke() {}
}
```

`@Accessors` generates the setters MWE2 calls for `modelSlot = …` and `outputDir = …` in the workflow.

## Step 4: The workflow

`GenerateLaTeX.mwe2`, next to the Xtend files:

```
module ift6253.hw2.latex.generator.GenerateLaTeX

import org.eclipse.emf.mwe.utils.*

// The workflow runs in this project's folder: ../.. is hw2/, where
// platform:/resource/ finds hw2.m2m (in m2m/) and the other projects.
var hw2 = "../.."

Workflow {
	bean = StandaloneSetup {
		platformUri = hw2
		registerGeneratedEPackage = "ift6253.hw2.latex.LatexPackage"
	}

	component = Reader {
		uri = "platform:/resource/hw2.m2m/models/courseProject.xmi"
		modelSlot = "courseProject"
	}
	component = LaTeXGeneratorComponent {
		modelSlot = "courseProject"
		outputDir = "latex"
	}

	component = Reader {
		uri = "platform:/resource/hw2.m2m/models/example.xmi"
		modelSlot = "example"
	}
	component = LaTeXGeneratorComponent {
		modelSlot = "example"
		outputDir = "latex"
	}
}
```

`StandaloneSetup` and `Reader` were tried outside Eclipse with these settings: `platformUri` set to `hw2/` finds `hw2.m2m` two folders down and `example.xmi` loads; a wrong path fails with "Couldn't load resource …". `LaTeXGeneratorComponent` is in the same package as the workflow, so it needs no import.

## Step 5: Run it

1. Right-click `GenerateLaTeX.mwe2` > Run As > **MWE2 Workflow**. The console should end with `Done.`
2. Refresh the project (F5): `latex/ModelGuidedMigration.latex` and `latex/DocumentName.latex` appear.
3. Run > Run Configurations: the run created a configuration for the workflow. On its Common tab, choose **Shared file** in `/ift6253.hw2.latex.generator`, so it goes in the ZIP.

## Step 6: Check the output

1. **Against the assignment:** `DocumentName.latex` must equal the listing above line for line. From a terminal: save the listing as `expected.latex` and run `diff expected.latex latex/DocumentName.latex`; no output means identical.
2. **It compiles:** in `latex/`, run `pdflatex DocumentName.latex` and `pdflatex ModelGuidedMigration.latex`. Both must finish without errors and produce a PDF whose numbering follows the sections (1, 1.1, 1.1.1, …). `ModelGuidedMigration.pdf` is the one that tests escaping (`semver\_sort`).
3. **Course project content:** sections Experiment, Models, Migration, Evaluation; under Conditions the subsubsections Models, Plain, Notes, Static, StaticNotes, StaticModels; under Domain the paragraphs Entity, Description, Traces, Attributes, Operations, Relationship, Invariant.

`pdflatex` leaves `.aux` and `.log` files next to the PDF. Delete them, or add `hw2/m2t/**/latex/*.aux` and `*.log` to `.gitignore`.

## Step 7: Report items

Draft positions for 3.1. Check the wording against the course slides.

| Concept | Position | Reason |
|---|---|---|
| Intent | Serialization / code generation | Writes the model in LaTeX's concrete syntax |
| In-place or out-place | Out-place | Produces a new artifact; the model is read only |
| Endogenous or exogenous | Exogenous | LaTeX metamodel in, LaTeX text (a different language: LaTeX's grammar) out |
| Horizontal or vertical | Your call | Vertical if text is seen as one step closer to the final document (the PDF); horizontal if model and text are two forms of the same content. Be consistent with your choice for task 2. |
| Syntactic or semantic | Syntactic | Each element type is printed by a fixed template; nothing depends on what the text means |

What to argue for 3.2:

- **Completeness:** each element type has exactly one template, and every containment list is printed, so every element of the model appears in the text, once.
- **Structure:** the nesting of the text (`\section` before its `\subsection`s, and so on) follows the containment of the model.
- **Order:** lists are printed in model order, which task 2 set to marker order.
- **Validity:** special characters are escaped, so the text compiles.

How to check it: the diff against the assignment's listing (an exact expected output), compiling both files with `pdflatex`, and for the course project a count of `\section`, `\subsection`, `\subsubsection` and paragraphs against the number of elements in `courseProject.xmi` (`grep -c '\\section{' …`).

Report checklist:

- [ ] The `.xtend` templates and the `.mwe2`, explained
- [ ] `ModelGuidedMigration.latex` (and the PDF, as a picture), `DocumentName.latex` against the assignment's listing
- [ ] 3.1 and 3.2

## Troubleshooting

| Symptom | Likely cause |
|---|---|
| Red Xtend file: "mandatory library … not found" | Xtend libraries not on the classpath: take the quick fix (step 1.3) |
| `LaTeXGeneratorComponent` unknown in the workflow | `xtend-gen/` is not a source folder, or the Xtend file has errors so no Java was generated |
| `Couldn't load resource under platform:/resource/hw2.m2m/…` | `hw2` in the workflow does not point at `hw2/`: the workflow runs in the project's folder |
| `Package with uri '…/latex' not found` | `registerGeneratedEPackage` missing or misspelled |
| `ClassCastException` to `Document` | The slot name in `Reader` and in the component differ, so the component got nothing or another model |
| `pdflatex`: "Missing $ inserted" | An unescaped `_` (or another special character) |
| Doubled or missing empty lines | Rich string whitespace: see step 2 |
| `«` and `»` typed as `<<` `>>` | Use Option+\ and Option+Shift+\, or content assist |
