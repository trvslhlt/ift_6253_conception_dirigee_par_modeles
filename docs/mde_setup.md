# Eclipse for Model-driven engineering

This tutorial explains how to install and use Eclipse to create a DSL. It is based on the Eclipse Modeling Framework (EMF), Epsilon tool-suite, Xtext, Henshin, and ATL.

## Installation

> **2026 update:** the steps below were refreshed against current download sites — several URLs and version numbers in the original tutorial (Epsilon 2.3, a 2019-dated ATL build, Henshin 1.4.0) are years stale and either 404 or won't resolve against a modern Eclipse.

###### Install Eclipse Modeling Tools
- [x] Already done on this machine via `brew install --cask eclipse-modeling` (installs `/Applications/Eclipse Modeling.app`, current release 4.40) — this replaces the original tutorial's "download the generic Eclipse Installer, run it, search for Epsilon" flow, which is a much slower path to the same result
- On a machine without Homebrew, download the "Eclipse Modeling Tools" package directly from https://www.eclipse.org/downloads/packages/ instead

> Why: this is the base Eclipse distribution preloaded with EMF and other modeling plumbing that every plugin below builds on.

###### Install Eclipse Epsilon
- Open Eclipse Modeling: launch it from Spotlight or `/Applications/Eclipse Modeling.app`
- Go to Help > Install New Software, click Add, and add site https://download.eclipse.org/epsilon/updates/2.8/ (current stable line as of this writing; check https://eclipse.dev/epsilon/download/ for a newer `updates/<version>/` path if this one 404s)
- Select the full Epsilon feature set (at minimum EGL, EGX, EOL, ETL, EVL, Epsilon Ecore/EMF support)
- Go to Help > Check for Updates and install all updates

> Why: EVL is used later to write the DSL's [validation constraints](#create-constraints); ETL is used for the [model-to-model transformation](#using-etl); EGL/EGX are used for the [model-to-text transformation](#using-egl); EOL underlies all of them. Epsilon's EMF/Ecore integration is what lets these languages read and write instances of the `DiningRoom.ecore` metamodel. GMF Integration (part of the full feature set) is needed for the Eugenia-generated [graphical editor](#create-the-metamodel-using-emfatic) later in the tutorial.

###### Install Xtext 
- Go to Help > Install New Software and add Xtext at site https://download.eclipse.org/modeling/tmf/xtext/updates/composite/releases/ (this composite URL always resolves to the current release, so it doesn't go stale)
- Make sure all options are checked
- Select the Xtext Complete SDK and MWE2 Language / MWE Core; ignore "M2T Xpand/Xtend-2.2.0" from the original tutorial — that standalone Xpand/Xtend feature is obsolete and no longer offered (Xtend now ships as part of Xtext itself)

> Why: Xtext generates the grammar, parser, and editor for the DSL's [textual concrete syntax](#create-a-textual-concrete-syntax-for-the-dsl). MWE2 drives the workflow that regenerates those Xtext artifacts. Xtend (bundled with Xtext SDK) is used later to write the [model-to-text generator](#using-xtend).

###### Install ATL 
- Go to Help > Install New Software and add ATL at site https://download.eclipse.org/mmt/atl/updates/releases/ (use this top-level rolling URL, not a dated build like the tutorial's `4.1/R201909021645/`, which is long gone)

> Why: ATL provides the alternative [model-to-model transformation](#using-atl) language used in this tutorial to copy a `DiningRoom` model into another `DiningRoom` model.

###### Install Henshin 
- Go to Help > Install New Software and add Henshin at site https://download.eclipse.org/modeling/emft/henshin/updates/release/ (current-release rolling URL, per https://github.com/eclipse-henshin/henshin/wiki/Installation-instructions — the tutorial's pinned `1.4.0/` is several major versions behind, and the bare `.../updates/` directory without `release/` times out/errors as of this writing)
- Select at minimum the **Henshin SDK** feature
- Henshin SDK depends on a Papyrus bundle (`org.eclipse.papyrus.infra.gmfdiag.tooling.runtime`) that isn't hosted on the Henshin site itself. If the resolver fails with a "Missing requirement... org.eclipse.papyrus..." error, click Add... again and also add a Papyrus update site, e.g. https://download.eclipse.org/modeling/mdt/papyrus/papyrus-desktop/updates/releases/2025-06/ (check https://www.eclipse.org/papyrus/download.html for a newer dated folder if this one 404s). You don't need to check anything under the Papyrus site or switch "Work with" to it — just adding it registers it so p2 can pull the missing dependency from it automatically (with "Contact all update sites during install to find required software" checked), while you keep "Work with" on the Henshin site and only Henshin SDK / Henshin SDK Sources checked

> Why: Henshin provides the graph-based rule editor used for the [in-place model transformation](#create-an-inplace-model-transformation) section, where rules modify a model directly rather than producing a separate output model.

###### Install Feature IDE
- Eclipse Marketplace search for `feature ide` no longer reliably surfaces a listing — skip it and go straight to Help > Install New Software with direct p2 site https://featureide.uni-ulm.de/update/v3/
- The version dropdown will show a range of past releases (e.g. 3.0–3.12); select the latest, **3.12.0** — per its changelog it specifically improves compatibility with newer Eclipse versions
- More information at https://featureide.github.io/

> Why: FeatureIDE supports feature modeling for software product lines. It isn't exercised by any step in this DiningRoom walkthrough — install it if your course covers feature/variability modeling separately; otherwise it can be skipped.

> Note: `download.eclipse.org` was unreachable from the sandboxed environment used to prep this guide, so the exact ATL/Henshin/Epsilon version numbers above couldn't be double-checked from here — verify the current folder name in Eclipse's Install New Software dialog (it lists available versions once you add the site) if any of these don't resolve.

## Create the DSL metamodel

> **What/why/takeaway:** This section defines the *abstract syntax* of a small DSL — a dining room made of furniture (tables and chairs) — as an Ecore metamodel. This is the foundational artifact in MDE: it's the vocabulary and structure (classes, attributes, references) that every later step in this guide builds on — constraints validate against it, the graphical/textual concrete syntaxes are views onto it, and the model-to-model/model-to-text transformations read and write instances of it. The DiningRoom example itself isn't the point; what to take away is the *recipe* — how to pick a root class, mark a class abstract when it should never be instantiated directly, and designate an attribute as the class's `ID` — since that recipe is what you'll reapply when defining a metamodel for your own DSL.

###### Create a new project
- Right-click in the Project Explorer New > Other > Plug-in Project
- Project name: `DiningRoom`
- Click Next twice and uncheck *Create a plug-in using one of the templates*
- On the page before the template selector, also uncheck *"This plug-in will make contributions to the UI"* — leaving it checked adds an unnecessary `org.eclipse.swt`/`org.eclipse.jface` Require-Bundle to the manifest even with no template selected, since that's a separate wizard option from the template choice. If you already created the project and see those two entries in `META-INF/MANIFEST.MF`, just delete those two lines — this plugin doesn't need a UI dependency.

###### Create the metamodel
- In the project, create a New folder called `metamodel`
- Create the `.ecore` file itself via New > Other..., filter `ecore`, and pick **Ecore Model** specifically (not *Ecore Diagram*, which is a different, Sirius-based wizard covered below) — plain "New > File" with a `.ecore` name produces an empty/invalid file and errors when opened
  - Parent folder: `metamodel`, file name: `DiningRoom.ecore`
  - When asked for the root "Model Object", choose **EPackage**
- Right-click the `.ecore` file > **Initialize Ecore Diagram...** — in current Eclipse Modeling Tools this routes through Sirius's Ecore Tools rather than the old GMF-only flow, so the steps look different from a dialog that just asks for Name/Ns URI/NS Prefix up front:
  1. It first asks for an `.aird` file to store the diagram's visual layout (separate from the `.ecore` file, which stays the actual semantic model) — create a new one alongside the `.ecore` file, e.g. `metamodel/DiningRoom.aird`
  2. Pick a representation type: **Entities in a Class Diagram**
  3. Pick the root semantic element: the (as-yet-unnamed) `EPackage` under `DiningRoom.ecore`; leave/rename "Name of the representation" as you like (cosmetic only) and Finish
  - This opens an empty class diagram canvas with a palette (Class, Datatype, Enumeration, Reference, SuperType, etc.) but does **not** prompt for Name/Ns URI/NS Prefix — set those by switching to the `DiningRoom.ecore` tab (its tree editor), selecting the root package node, and setting these fields in the **Properties** view (Window > Show View > Properties if it isn't open):
    - Name: `DiningRoom`
    - Ns URI: `geodes.sms.diningroom`
    - NS Prefix: `dr`
- Back on the class diagram canvas, drag **Class** from the palette to create `Room` (that is the **root class** of the metamodel)
- Create a class Furniture. Double-click it and mark it *Abstract*
- Drag-drop an attribute <a name="id"></a>
  - Name: `name`
  - uncheck *ordered*, uncheck *unique*, check *ID* to make it the **identifier** of the class.
- Create a class `Table`; use the palette's **SuperType** relation to make it extend `Furniture`; add attributes `x` and `y`, both type `EInt`
- Create a class `Chair`; make it extend `Furniture` the same way; add attribute `order`, type `EInt`
- On `Room`, drag-drop a containment **Reference** to `Furniture` named `furniture`, multiplicity `0..*` — this is what holds all the furniture in a room
- On `Table`, drag-drop a (non-containment) **Reference** to `Chair` named `around`, multiplicity `0..*` — this is the reference the later `@gmf.link` annotation attaches to
- Save

> These classes/attributes/references match the full metamodel shown in the Emfatic example further down — building them now means that example (and the `@gmf` annotation steps that follow) apply directly to what's already in your diagram instead of introducing new elements implicitly.

###### Create the metamodel using Emfatic
> **Bigger problem: Eugenia/GMF itself is discontinued, so this whole subsection is a dead end.** The point of the `@gmf.*` annotations below is to feed Eugenia, which generates a GMF-based graphical editor from them. Per Epsilon's own docs (https://www.eclipse.org/epsilon/doc/eugenia/), following the Eclipse Foundation's archival of the GMF Tooling project, "Eugenia has been discontinued from [Epsilon] version 2.5 onwards" — and this guide installs Epsilon 2.8. So `Eugenia > Generate GMF editor` will not appear in any context menu; that's not a step you're missing, the tool simply isn't in the version installed. Epsilon's own docs recommend **Eclipse Sirius** as the actively-maintained replacement for building a graphical editor — which is what you already used for the class diagram earlier — but a Sirius editor *for DiningRoom model instances* (as opposed to the metamodel diagram you already have) would be a separate, larger effort this guide doesn't cover. **Recommended path: skip straight to [Create a textual concrete syntax for the DSL](#create-a-textual-concrete-syntax-for-the-dsl)** — it's unaffected by any of this and gives you a fully working way to create and edit `DiningRoom` model instances via Xtext. The Emfatic/`@gmf` annotation content below is left for reference/learning only.
>
> **Emfatic itself may also not be installed, separately from the above.** As of this writing it has no Eclipse Marketplace listing, its Eclipse project page 404s, and its GitHub repo (`eclipse-emfatic/emfatic`) has zero releases to point an update site at. If you don't see *Generate Emfatic Source* on the ecore file's context menu, that's why — not a step you missed.

- Right-click on the ecore file, *Generate Emfatic Source*
- Open `DiningRoom.emf`
- Edit it as needed (see https://www.eclipse.org/emfatic/ for details)
- Annotate with @gmf (see https://www.eclipse.org/epsilon/doc/articles/eugenia-gmf-tutorial/ and http://hflorezf-en.blogspot.com/2013/02/gmf-editor-using-eugenia.html for details) for a graphical concrete syntax. here is our example:
```
@namespace(uri="geodes.sms.diningroom", prefix="dr")
package DiningRoom;

@gmf.diagram(model.extension="dr")
class Room {
  val Furniture[*] furniture;
}

abstract class Furniture {
  !ordered id attr String ~name;
}

@gmf.node(color="63,63,63", figure="rectangle", label.icon="false", label="id", label.color="255,255,255", size="200,40")
class Table extends Furniture {
  attr int x;
  attr int y;
	
  @gmf.link(target.decoration="arrow", source.decoration="none", style="solid", color="0,0,0")
  ref Chair[*] around;
}

@gmf.node(color="255,146,79", figure="ellipse", label.icon="false", label="id", size="50,50")
class Chair extends Furniture {
  attr int order;
}
```
- Save

###### Add the @gmf annotations without Emfatic
If Emfatic isn't available, add the same annotations from the example above directly to the `.ecore` model as `EAnnotation`s instead of writing them as Emfatic text — more tedious than Emfatic's one-line `@gmf.node(...)` syntax, but produces the identical annotations Eugenia reads in the next step. Each `@gmf.*(...)` line in the example becomes: one `EAnnotation` child on the element it's attached to, with its `Source` set to the annotation name, plus one `EStringToStringMapEntry` child of that `EAnnotation` per key/value pair inside the parentheses. Do this in the `DiningRoom.ecore` tree editor tab (not the class diagram):

**On `Room`:**
1. Right-click `Room` > New Child > **EAnnotation**; select it, set Source: `gmf.diagram`
2. Right-click that EAnnotation > New Child > **EStringToStringMapEntry**; set Key `model.extension`, Value `dr`

**On `Table`:**
1. Right-click `Table` > New Child > **EAnnotation**; select it, set Source: `gmf.node`
2. Right-click that EAnnotation > New Child > **EStringToStringMapEntry**, six times, setting Key/Value to each pair: `color`→`63,63,63`, `figure`→`rectangle`, `label.icon`→`false`, `label`→`id`, `label.color`→`255,255,255`, `size`→`200,40`

**On `Table.around` (the reference, not `Table` itself):**
1. Expand `Table` in the tree to find the `around` reference; right-click it > New Child > **EAnnotation**; select it, set Source: `gmf.link`
2. Right-click that EAnnotation > New Child > **EStringToStringMapEntry**, four times: `target.decoration`→`arrow`, `source.decoration`→`none`, `style`→`solid`, `color`→`0,0,0`
3. Easy to miss: `around` needs its own `gmf.link` annotation separate from `Table`'s `gmf.node` one — if `around` in the tree has no expand-arrow next to it, that annotation hasn't been added yet

**On `Chair`:**
1. Right-click `Chair` > New Child > **EAnnotation**; select it, set Source: `gmf.node`
2. Right-click that EAnnotation > New Child > **EStringToStringMapEntry**, five times: `color`→`255,146,79`, `figure`→`ellipse`, `label.icon`→`false`, `label`→`id`, `size`→`50,50`

- Save

- ~~Right-click the `.ecore` file (or the `.emf` file if you used Emfatic), click Eugenia > Generate GMF editor~~ — not available; see the discontinued-Eugenia note above. Skip to [Create a textual concrete syntax for the DSL](#create-a-textual-concrete-syntax-for-the-dsl)
- Right-click ecore file, click Register EPackages — still worth doing regardless of the above; this registers the metamodel's EPackage globally so other tools (Xtext, ATL, ETL...) can reference it by URI without browsing to the file each time

###### Create constraints
- Create a folder called `constraint`
- Create an EVL file `DiningRoom.evl` inside it
- Write your constraints (see https://www.eclipse.org/epsilon/doc/evl/ for examples)
- Save

###### Update the manifest
The underlying mechanism follows https://www.eclipse.org/epsilon/doc/articles/evl-gmf-integration/, but you can make all of these edits directly in the plain text/XML files instead of via the PDE manifest editor's GUI tabs — faster if you're comfortable editing them by hand:
- `META-INF/MANIFEST.MF`: add to `Require-Bundle`: `org.eclipse.ui.ide` and `org.eclipse.epsilon.evl.emf.validation`
  - Also add `;singleton:=true` to the `Bundle-SymbolicName` line (e.g. `Bundle-SymbolicName: DiningRoom;singleton:=true`) — any plugin that declares extensions/extension-points must be a singleton, or PDE reports an error
- `plugin.xml` (create this file at the project root if it doesn't exist): add two extensions
  - `org.eclipse.epsilon.evl.emf.validation`, with one `constraintsBinding` child — attributes `namespaceURI="geodes.sms.diningroom"` and `constraints="constraint/DiningRoom.evl"` (note: the element is `constraintsBinding`, plural, matching Epsilon's actual extension-point schema — not `constraintBinding`)
  - `org.eclipse.ui.ide.markerResolution`, with one `markerResolutionGenerator` child — `class="org.eclipse.epsilon.evl.emf.validation.EvlMarkerResolutionGenerator"`, `markerType="org.eclipse.emf.ecore.diagnostic"`. (Skip the `<pkg>.diagram.diagnostic` marker type the original GMF-integration article also adds — that marker type only exists on a GMF diagram, which you don't have since Eugenia is discontinued; including it produces a permanent "Referenced identifier ... cannot be found" warning in the Problems view for no benefit)
- `build.properties`: add `plugin.xml` as its own entry in `bin.includes` — PDE's builder wants it listed explicitly and doesn't treat a bare `.` entry as covering it, even though `.` does include the file when actually packaging
- Save everything, then right-click the project > Refresh so Eclipse picks up the new/changed files
- To trigger validation: right-click your model instance (e.g. `Room` in the reflective editor) > **Validate** — not "Diagram > Validate", which assumes a GMF editor you don't have


## Create instances

> **What/why/takeaway:** Now that the metamodel exists, create actual instances of it — model data that conforms to the `DiningRoom` classes/attributes/references you just defined. Nothing downstream (constraints, transformations, generated text) has anything to operate on until a concrete `.xmi` model exists. Two ways are shown below: a *dynamic instance*, built directly in this Eclipse via the generic reflective editor with zero generated tooling, and a *graphical instance*, built in a second, child Eclipse instance using a GMF editor generated by Eugenia. **The graphical-instance path depends on Eugenia, which is discontinued as of Epsilon 2.5** (see the note under "Create the metamodel using Emfatic") — since this guide installs Epsilon 2.8, there's no generated GMF editor to launch, so "Launch second Eclipse instance" / "Create model" below won't work. Use the dynamic instance for now, and once you reach [Create a textual concrete syntax for the DSL](#create-a-textual-concrete-syntax-for-the-dsl), that Xtext editor is the working replacement for authoring `DiningRoom` instances outside the reflective tree editor.

<a name="dynamic-instance"></a>
###### Create a model dynamically
To create a model directly in the same Eclipse instance:
- Open `diningroom.ecore` metamodel file
- Right-click on the root class `Room` > Create Dynamic instance. Call it `Room1.xmi`. This will create a instance of your metamodel as an `xmi` file.
- Close it
- Right-click on the `Room1.xmi` > Open With > Sample Reflective Ecore Model Editor
- In this tree view of your model, right-click on Room > New Child > Tables to create an instance of a table
- You can set its attribute values in the Properties view

###### Launch second Eclipse instance *(requires the Eugenia-generated GMF editor — not available, see above)*
- Right-click the `DiningRoom` project, Run as > Run configurations
- Create a new configuration under *Eclipse Application* called `Graphical`
- If you need more heap for the child Eclipse instance, in the *Arguments* tab, under VM arguments paste: `-Xms512M -Xmx1024M` (drop the tutorial's `-XX:PermSize`/`-XX:MaxPermSize` flags — PermGen was removed in Java 8+ and those flags will fail to launch on this machine's JDK 25)
- Apply then Run

###### Create model *(requires the Eugenia-generated GMF editor — not available, see above)*
- Create a new Modeling project called `DiningRoomModels`
- Right-click the project, New > Other > DiningRoom diagram, and create a file called `Room1.dr_diagram`, click Next and call the file name `Room1.dr`
- Create the model by clicking the object in the palette and then in the canvas 
- To validate the model, unselect any element in the canvas, then Edit > Validate
- You can also open the `Room1.dr` file with the *Sample Reflective Ecore Model Editor*
- Right-click on Room and click *Validate*

## Create a textual concrete syntax for the DSL

> **What/why/takeaway:** Generate a second, text-based editor for the *same* `DiningRoom` metamodel using Xtext — a grammar-driven parser/editor with auto-complete and validation, as an alternative to the graphical (GMF) editor from the previous section. Why: a metamodel is the abstract syntax; a metamodel can have many concrete syntaxes (graphical, textual, tabular...) that are just different front-ends for authoring the same underlying model instances — Xtext derives its grammar straight from the Ecore model so both views round-trip to identical `.xmi` data. This section also covers the fiddly-but-important detail of qualified names and saving Xtext's internal model out as plain XMI, which matters the moment you want other tools (ATL, ETL, Henshin) to consume what was authored as text. Takeaway: concrete syntax is a projection, not a copy of your metamodel — you can add, swap, or drop a concrete syntax without ever touching the metamodel's semantics.

###### Create a new Xtext project
- This step needs a `.genmodel` file, which no earlier step creates — if you don't already have `metamodel/DiningRoom.genmodel`, make one first:
  1. Right-click `metamodel/DiningRoom.ecore` > New > Other..., filter `genmodel`, pick **EMF Generator Model** (under "Eclipse Modeling Framework") > Next
  2. It should default to `DiningRoom.ecore` as the source — confirm > Next
  3. On the "Select a Root Model Object or Package" page, check the `DiningRoom` package > Finish
- Right-click in the Project Explorer New > Other > Xtext project From Existing Ecore Models
- Add the EPackage from the EMF generator model `DiningRoom.genmodel`: in the wizard's EPackages section, click **Add...**, browse to `metamodel/DiningRoom.genmodel`, and check the `DiningRoom` package listed inside it
- Select Entry rule: `Room - DiningRoom`
- Project name: `diningRoomTextual`
- Name: `geodes.sms.diningroom.DiningRoomTextual`
- Extensions: `drm`
- Finish
- Modify the grammar as you see fit. Here is our example:
```
// automatically generated by Xtext
grammar geodes.sms.diningroom.DiningRoomTextual with org.eclipse.xtext.common.Terminals

import "geodes.sms.diningroom" 
import "http://www.eclipse.org/emf/2002/Ecore" as ecore

Room returns Room:
  {Room}
  'Chambre' ('{' furniture+=Furniture ( "," furniture+=Furniture)* '}' )?;

Furniture returns Furniture:
  Table | Chair;

Table returns Table:
  {Table}
  'Table' name=ID ('x' x=INT)? ('y' y=INT)? ('entourée' 'de' '(' around+=[Chair|ID] ( "," around+=[Chair|ID])* ')' )?;

Chair returns Chair:
  {Chair}
  'Chaise' name=ID ('ordre' order=INT)?;
```

***Important note on qualified names with Xtext***
> If you use Xtext, then every class that is the target of a reference should have a `name` attribute that is its [identifier](#id) in Ecore. If the identifier attribute has a different name, then you must provide another qualified name. In this case, [follow these steps](https://dietrich-it.de/xtext/2011/07/16/iqualifiednameproviders-in-xtext-2.0/).
> You need to create the file `DiningRoomTextualQNP` under `diningRoomTextual/src/geodes.sms.diningroom/`. The file `DiningRoomTextualRuntimeModule` is also located there.

> Here is and [advanced Xtext manual](https://wiki.umontreal.ca/download/attachments/161022004/Advanced_Xtext_Manual.pdf?version=1&modificationDate=1574378088000&api=v2)

###### Launch second Eclipse instance
- When the file `DiningRoomTextual.xtext` is automatically open, right-click in file the Run AS > *Generate Xtext artifacts*
- In the run button (green play button in the toolbar) select *Graphical* to launch the Eclipse instance

Note that the xtext and mwe2 files are located under `diningRoomTextual/src/geodes.sms.diningroom/`

###### Create model
- Right-click the `DiningRoomModels` project, select New > File, and create a file called `Room.drm`, click Next and call the file name `Room1.dr`
- Accept to convert the project into an Xtext project
- Create the model by typing in textual syntax. use CTRL+SPACE to know what comes next
- Validation works automatically

<a name="save-xmi"></a>
###### Save as XMI
Follow [these steps](https://wiki.umontreal.ca/pages/viewpage.action?pageId=156867616) to save your model as XMI.

- In the Xtext project `diningRoomTextual`, open `src/geodes.sms.diningroom.generator/DiningRoomTextualGenerator.xtend`
- Add the following code inside the function `doGenerate` and resolve the imports:
```
Helper.saveResourceAsXmi(resource);
```
- In the same package `geodes.sms.diningroom.generator`, create a class `Helper.java` with the following content:
```
package geodes.sms.diningroom.generator;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;

public class Helper {
    public static void saveResourceAsXmi(Resource resource) {
        try {
            Map<String, String> saveOptions = new HashMap<String, String>();
            Resource xmlResource = new XMIResourceImpl(URI.createURI(resource.getURI().toString().replace("drm", "xmi")));
            xmlResource.getContents().add(resource.getContents().get(0));
            saveOptions.put(org.eclipse.emf.ecore.xmi.XMLResource.OPTION_ENCODING,"UTF-8");
            xmlResource.save(saveOptions);
            System.out.println("XMI file created.");
        } catch (IOException e) {
            System.out.println("Error during the creation of XMI.");
        e.printStackTrace();
        }
    }
}
```
- Right-click on `src/geodes.sms.diningroom/GenerateDiningRoomTextual.mwe2`, Run AS > MWE2 Workflow
- Launch the second Eclipse instance from the `diningRoomTextual` project`
- When you modify an Xtext file like `Room.drm` and save, it will automatically generate `Room.xmi`.

## Create a model-to-model transformation

> **What/why/takeaway:** Transform one model instance into another by rule instead of by hand — model-to-model (M2M) transformation, one of the core operations in MDE (deriving, migrating, or integrating models). The example here is deliberately trivial (copying a `DiningRoom` model to another `DiningRoom` model) so the *mechanics* of a transformation rule are visible without unrelated modeling complexity layered on top. Two languages do the identical job so you can compare them: **ATL**, a dedicated, standalone M2M transformation tool with its own project type and IN/OUT-typed models, common in industry tooling; and **ETL**, part of the Epsilon family, sharing syntax/semantics with EVL (used earlier for constraints) — convenient if you're already invested in Epsilon. Takeaway: don't fixate on which is "correct" — notice what's structurally identical in both (a `rule` binds a source type to a target type and maps/derives fields), since that pattern is what transfers to whichever M2M tool your own project ends up using.

### Using ATL

Follow the steps in the Families2Person tutorial https://wiki.eclipse.org/ATL/Tutorials

###### Create a new ATL transformation
- Right-click in the Project Explorer New > Other > ATL Project
- Project name: `DiningRoom.copy`
- Create a folder `models` in your project
- Add an instance of the DiningRoom DSL in this folder. [You can create a dynamic instance](#dynamic-instance). Call this model `Room.xmi`.
- Create a folder `transformation` in your project
- Right-click the folder `transformation` > New > Other > ATL File, Next, call the file `copy.atl`
- Next
- Add in Input Model section
- Metamodel Name `MM1`
- Click Browse Workspace. Locate and select the `diningroom.ecore` metamodel
- Add in Output Model section
- Metamodel Name `MM2`
- Click Browse Workspace. Locate and select the `diningroom.ecore` metamodel. Note that in this example, we only have one metamodel, so the transformation will take a dining room model and produce another dining room model.
- Make sure "Generate configuration" is selected
- Finish
- Starting on line 7, write your rules and helpers. Use `IN` and `OUT` to refer to the metamodels.
- Type your rules and helpers. Here is our example:
```
-- @path MM1=/DiningRoom/metamodel/DiningRoom.ecore
-- @path MM2=/DiningRoom/metamodel/DiningRoom.ecore

module copy;
create OUT : MM2 from IN : MM1;

rule copyRoom {
  from r1 : MM1!Room
  to r2 : MM2!Room (
        furniture <- r1.furniture
      )
}
rule copyTable {
  from t1 : MM1!Table
  to t2 : MM2!Table (
        id <- t1.id,
        x <- t1.x,
        y <- t1.y,
        around <- t1.around
      )
}
rule copyChair {
  from c1 : MM1!Chair
  to c2 : MM2!Chair (
        id <- c1.id,
        order <- c1.order
      )
}
```

###### Run an ATL transformation
- Right-click on the editor > Run As > ATL transformation
- Select the IN model (source). Note that your input model should reside in the same workspace, therefore in the same Eclipse instance. Click on Workspace, locate and select `Room.xmi` from the `models` folder.
- Enter the path of the OUT model. It could be the same as the IN, but change the name of the XMI file, like `Room_copy.xmi`
- Apply > Run. This creates a new model located where you set the path of the OUT model.

### Using ETL

- In the `DiningRoom` project, create a folder `transformation`
- Right-click the folder `transformation` > New > Other > ETL Transformation, Next, call the file `copy.etl`
- Type `transform` followed by CTRL+SPACE to auto-complete the template of a rule
- Write your rules. You can follow the documentation in https://www.eclipse.org/epsilon/doc/etl/ or the [Epsilon book](https://www.eclipse.org/epsilon/doc/book/EpsilonBook.pdf)
- Here, call the source metamodel MM1 and the target metamodel MM2. For example, `transform s : MM1!Table to t : MM2!Table {...}`.
Here is our example:
```
rule copyRoom
  transform r1 : MM1!Room
  to r2 : MM2!Room { r2.furniture ::= r1.furniture; }

rule copyTable
  transform t1 : MM1!Table
  to t2 : MM2!Table {
        t2.id = t1.id;
        t2.x = t1.x;
        t2.y = t1.y;
        t2.around ::= t1.around;
}

rule copyChair
  transform c1 : MM1!Chair
  to c2 : MM2!Chair {
        c2.id = c1.id;
        c2.order = c1.order;
}
```

###### Run an ETL transformation
- Create a folder `models` in your project
- Add an instance of the DiningRoom DSL in this folder. [You can create a dynamic instance](#dynamic-instance). Call this model `Room.xmi`.
- Create an empty file `Room_copy.xmi` in the `models` folder that will store the target model of the transformation
- Right-click on the editor > Run As > Run configurations
- Double-click on ETL Transformation in the left panel. This creates an ETL configuration called `copy`.
- In the Models tab add the source and target models by clicking Add.
- Name = `MM1`. Locate the model file through Browse Workspace. Type `Room.xmi`. OK
- Click on Add file. Locate the metamodel `DiningRoom.ecore` file. OK
- Since this is the source model, only check Read on load. Uncheck Store on disposal.
- Repeat the same steps to add the target model `MM2` set to the file `Room_copy.xmi`. Only check Store on disposal, uncheck Read on load.
- Apply > Run. This creates a new model stored in `Room_copy.xmi`.

## Create a model-to-text transformation

> **What/why/takeaway:** Generate plain text (or source code, config, docs — anything textual) from a model instance — model-to-text (M2T) transformation, which is how MDE work turns into an artifact something outside Eclipse can actually consume. Two approaches are shown: Epsilon's **EGL/EGX** pair, where EGX is a rule-driven "dispatcher" that says which template applies to which model element and EGL is the template language itself (similar to JSP/ERB — literal text with `[% ... %]` code blocks); and **Xtend**, hooked directly into the Xtext-generated project's save action, wired to trigger every time you save a `.drm` textual model. Takeaway: EGL/EGX is a standalone, on-demand generation run you trigger explicitly; the Xtend variant is continuous, firing automatically as the DSL author edits and saves text — which one fits your own DSL depends on whether generation should be a deliberate build step or feel instantaneous to the model author.

### Using EGL

Follow the steps in the Book2Page tutorial https://www.eclipse.org/epsilon/doc/articles/code-generation-tutorial-egl/

###### Create a new EGX program
- In the `transformation` folder, Right-click the folder > New > Other > EGX Program, Next, call the file `translate.egx`
- Finish
- Type a rule, such as
```
rule Room2Text 
	transform self : M!Room {
	template : "translate.egl"
	target : "../models/" + "room.txt"
}
```
###### Create a new EGL transformation
- In the `transformation` folder, Right-click the folder > New > Other > EGL Template, Next, call the file `translate.egl`
- Finish
- Type the template. Note that you must use `self` (as defined in the EGX program) to refer to the context of type `Room`. For example, you can write:
```
The room has [%= self.furniture.size() %] furniture.
Room {
[%for (c in Chair) { %]
	Chair [%= c.name %] order [%= c.order %]
[% } %]
[%for (t in Table) { %]
	Table [%= t.name %] [%if (t.around.size() > 0) { %] surrounded by [%for (c in t.around) { %]
	[%= c.name %]
	[% } %]
[% } %]
[% } %]
}
```

###### Run an EGL Generator
- Right-click on the editor > Run As > Run Configurations
- Double-click on EGL Generator in the left panel. This creates an EGL configuration called `translate`.
- In the Template tab, select the source to be your EGX program
- In the Model tab, Add > EMF Model > OK
  - Set the name to `M` (as defined in the EGX program)
  - Set the model file to your xmi file, such as `Room.xmi`. Note that your input model should reside in the same workspace, therefore in the same Eclipse instance. [See how to create models dynamically](#dynamic-instance).
  - OK
- Apply > Run. This creates a new text file `room.txt` under the `transformation` folder.

### Using Xtend

###### Create a new Xtend transformation

- In the Xtext project `diningRoomTextual`, open `src/geodes.sms.diningroom.generator/DiningRoomTextualGenerator.xtend`
- If you have added the [serialization to XMI](#dynamic-instance), comment out the line
```
Helper.saveResourceAsXmi(resource);
```
- Add the following code inside the function `doGenerate` and resolve the imports:
```
for (e : resource.allContents.toIterable.filter(Room)) {
	fsa.generateFile(
		resource.getURI().toString().replace("drm", "txt"),
		e.translate)
}
```
- To write the templates for the model-to-text transformation, you can define the `translate` function called above. YOu can overload the function for each type of your metamodel as needed and resolve the imports:
```
private def translate(Room room) '''
	The room has «room.furniture.size()» furniture.
	Room {
		«FOR f : room.furniture »
			«f.translate»
		«ENDFOR»
	}
'''

private def dispatch translate(Chair c) '''
	Chair «c.name» order «c.order»
'''

private def dispatch translate(Table t) '''
	Table «t.name» «IF t.around.size() > 0» surrounded by «FOR c : t.around » «c.name» «ENDFOR»«ENDIF»
'''
```

###### Run an Xtend transformation
- Right-click on `src/geodes.sms.diningroom/GenerateDiningRoomTextual.mwe2`, Run AS > MWE2 Workflow
- Launch the second Eclipse instance from the `diningRoomTextual` project`
- When you modify an Xtext file like `Room.drm` and save, it will automatically generate `src-gen` > `resource` > `DiningRoomModels` > `Room.txt` with the text generated.

## Create an inplace model transformation

> **What/why/takeaway:** Use Henshin to write graph-transformation rules that edit a model instance directly — *in place* — rather than reading a source model and writing a separate target model, e.g. incrementing a `Table`'s `x` coordinate on the model that's already there. Why: not every model change is naturally a "read model A, produce model B" M2M transformation; sometimes you want live, rule-based edits applied to a single evolving model, the way a graph-rewriting or simulation system works — this is the pattern behind stepwise model evolution, refactoring, and simulating a system's state changes over time. Takeaway: notice the vocabulary shift — nodes are marked `preserve`/`create`/`delete`/`forbid`, and a rule has a left-hand-side pattern to match plus a right-hand-side describing the result — because in-place transformation is a genuinely different paradigm from the M2M/M2T sections above, even though "transformation" is in the name for all three.

###### Create a new Henshin transformation
- Create a folder `transformation` in your project
- Right-click the project `DiningRoom` > New > Other > Henshin Diagram, Next, call the file `transfer_chairs.henshin_diagram`
- Click to Add From Workspace, select the `DiningRoom` package under `DiningRoom/metamodel/diningroom.ecore`
- Create a rule by clicking on Rule in the palette and then in the canvas
- Click on the `Rule` text and type `transfer` to give the rule a name
- Create the pattern by clicking the object in the palette and then in the canvas
- You can set Action of an element from the Property window. The options are `preserve`, `create`, `delete` or `forbid`.
- Adding a condition on or setting the value of the attribute `x` of a class `Table`
  - Click on the Node and on the "-" sign and add the attribute to set (in this case `x`)
  - Rename the rule to be called `transfer(var x_val)`
  - Select the attribute `x` and write JavaScript code as follows:
    - `x=x_val+1` so the RHS of the rule will increment the value of `x`
    - `x=1` (if the Table is set to `create`) so the RHS of the rule will create a Table and assign its `x` to 1
    - `x=1` (if the Table is not set to `create`) so the LHS of the rule will only match a Table where `x==1`
    - `x=x_val` (if the Table is set to `create`) so the RHS of the rule will create a Table and assign its `x` to the value that is passed in parameter
    - `x=x_val` (if the Table is not set to `create`) so the LHS of the rule will only match a Table where `x` is the value that is passed in parameter
- Creating a control flow to schedule the execution of the rules
  - Create a unit on the canvas by clicking on `Unit` in the palette and then in the canvas. Then choose the type of unit you want
  - The name of the unit should correspond to the name of a rule or a unit.
More information and examples are available at https://www.eclipse.org/henshin/

###### Run a Henshin transformation
- Right-click on the unit you want to execute > Apply Transformation
- In Input Model, click on Browse Workspace and select the model to transform. Note that your input model should reside in the same workspace, therefore in the same Eclipse instance. [See how to create models dynamically](#dynamic-instance).
- If your unit has parameters, enter them in the Parameters grid
- Uncheck Open Comapre
- Click Transform. This creates a new model with the same name as your input model suffixed with `_transformed`

# Programming with Ecore

> **What/why/takeaway:** Step below Epsilon/Xtext/ATL/Henshin entirely and work with the plain EMF Java API directly — loading a resource, saving a model, and instantiating model elements via the metamodel's generated factory classes. Why: every higher-level tool used earlier in this guide is itself built on top of this same EMF Java API; you need it directly the moment you want to manipulate models from ordinary Java code that isn't running inside one of those Eclipse-hosted DSL tools — a headless build step, a test, a web service, a CLI. Takeaway: this is the escape hatch/foundation everything above compiles down to — reach for it when the DSL-specific languages (EOL/ETL/EGL/ATL/Henshin) are more machinery than you need and you just want to read or write a model programmatically.

## Loading a metamodel in Ecore
```
Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("ecore",new XMIResourceFactoryImpl());
ResourceSet resourceSet = new ResourceSetImpl();
Resource resource = resourceSet.createResource(URI.createFileURI(METAMODEL_FILE));
resource.load(null);
resource.getContents()[0] // returns the root object of the model
```

## Modifying and saving a model
```
resource.getContents().add(SOME_ELEMENT);
StringWriter stringWriter = new StringWriter();
URIConverter.WriteableOutputStream outputStream = new URIConverter.WriteableOutputStream(stringWriter, "UTF-8");
Map<String, String> options = new HashMap<String, String>();
resource.save(outputStream, options);
```

## Creating models with generated EMF code from a metamodel
```
DiningRoomFactory drFactory = DiningRoomFactory.eINSTANCE;
DiningRoomFactory room = drFactory.createRoom();
Chair c1 = drFactory.createChair();
c1.setName("C1");
c1.setOrder(1);
room.addFurniture(c1);
ResourceSet resourceSet = new ResourceSetImpl();
resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
URI fileURI = URI.createFileURI(new File(XMI_FILE).getAbsolutePath());
Resource resource = resourceSet.createResource(fileURI);
resource.getContents().add(room);
resource.save(System.out, Collections.EMPTY_MAP);
resource.save(Collections.EMPTY_MAP);
```


# Video demonstration

<iframe width="560" height="315" sandbox="allow-same-origin allow-scripts allow-popups" src="https://classe.iro.umontreal.ca/videos/embed/5aa79bed-9529-40ba-bf86-71c80f87d01e" frameborder="0" allowfullscreen></iframe>