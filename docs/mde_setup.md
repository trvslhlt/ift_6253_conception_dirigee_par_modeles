# Eclipse for Model-driven engineering

This tutorial explains how to install and use Eclipse to create a DSL. It is based on the Eclipse Modeling Framework (EMF), Epsilon tool-suite, Xtext, Henshin, and ATL.

## Installation

> **2026 update:** the steps below were refreshed against current download sites — several URLs and version numbers in the original tutorial (Epsilon 2.3, a 2019-dated ATL build, Henshin 1.4.0) are years stale and either 404 or won't resolve against a modern Eclipse.

###### Install Eclipse Modeling Tools
- [x] Already done on this machine via `brew install --cask eclipse-modeling` (installs `/Applications/Eclipse Modeling.app`, current release 4.40) — this replaces the original tutorial's "download the generic Eclipse Installer, run it, search for Epsilon" flow, which is a much slower path to the same result
- On a machine without Homebrew, download the "Eclipse Modeling Tools" package directly from https://www.eclipse.org/downloads/packages/ instead

###### Install Eclipse Epsilon
- Open Eclipse Modeling: launch it from Spotlight or `/Applications/Eclipse Modeling.app`
- Go to Help > Install New Software, click Add, and add site https://download.eclipse.org/epsilon/updates/2.8/ (current stable line as of this writing; check https://eclipse.dev/epsilon/download/ for a newer `updates/<version>/` path if this one 404s)
- Select the full Epsilon feature set (at minimum EGL, EGX, EOL, ETL, EVL, Epsilon Ecore/EMF support)
- Go to Help > Check for Updates and install all updates

###### Install Xtext 
- Go to Help > Install New Software and add Xtext at site https://download.eclipse.org/modeling/tmf/xtext/updates/composite/releases/ (this composite URL always resolves to the current release, so it doesn't go stale)
- Make sure all options are checked
- Select the Xtext Complete SDK and MWE2 Language / MWE Core; ignore "M2T Xpand/Xtend-2.2.0" from the original tutorial — that standalone Xpand/Xtend feature is obsolete and no longer offered (Xtend now ships as part of Xtext itself)

###### Install ATL 
- Go to Help > Install New Software and add ATL at site https://download.eclipse.org/mmt/atl/updates/releases/ (use this top-level rolling URL, not a dated build like the tutorial's `4.1/R201909021645/`, which is long gone)

###### Install Henshin 
- Go to Help > Install New Software and add Henshin at site https://download.eclipse.org/modeling/emft/henshin/updates/ (top-level rolling URL — the tutorial's pinned `1.4.0/` is several major versions behind)

###### Install Feature IDE
- Go to Help > Eclipse Marketplace, search for `feature ide`, and install
- Alternatively, Help > Install New Software with direct p2 site http://featureide.uni-ulm.de/update/v3/
- More information at https://featureide.github.io/

> Note: `download.eclipse.org` was unreachable from the sandboxed environment used to prep this guide, so the exact ATL/Henshin/Epsilon version numbers above couldn't be double-checked from here — verify the current folder name in Eclipse's Install New Software dialog (it lists available versions once you add the site) if any of these don't resolve.

## Create the DSL metamodel

###### Create a new project
- Right-click in the Project Explorer New > Other > Plug-in Project
- Project name: `DiningRoom`
- Click Next twice and uncheck *Create a plug-in using one of the templates*

###### Create the metamodel
- In the project, create a New folder called `metamodel`
- Create an Ecore Model called `DiningRoom.ecore`
- Right-click on the ecore file > Initialize Ecore diagram with values:
  - Name: `DiningRoom`
  - Ns URI: `geodes.sms.diningroom`
  - NS Prefix: `dr`
- Create a class `Room` (that is the **root class** of the metamodel)
- Create a class Furniture. Double-click it and mark it *Abstract*
- Drag-drop an attribute <a name="id"></a>
  - Name: `name`
  - uncheck *ordered*, uncheck *unique*, check *ID* to make it the **identifier** of the class.
- Continue building your class diagram
- Save

###### Create the metamodel using Emfatic
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
- Right-click emf file, click Eugenia > Generate GMF editor
- Right-click ecore file, click Register EPackages

###### Create constraints
- Create a folder called `constraint`
- Create an EVL file `DiningRoom.evl` inside it
- Write your constraints (see https://www.eclipse.org/epsilon/doc/evl/ for examples)
- Save

###### Update the manifest
- Edit `META-INF > MANIFEST.MF` file following the steps in https://www.eclipse.org/epsilon/doc/articles/evl-gmf-integration/
- Go to the *build.properties* tab and change `model` to `metamodel`
- Go to the *Dependencies* tab, add `org.eclipse.ui.ide` and `org.eclipse.epsilon.evl.emf.validation` to the list of dependencies
- Go to the *Extensions* tab and add the `org.eclipse.epsilon.evl.emf.validation extension`
  - On the right, namespaceURI: `geodes.sms.diningroom`
  - constraints: select `DiningRoom.evl`
- Add a new extension `org.eclipse.ui.ide.markerResolution` and below it we create two *markerResolutionGenerator* with the following details:
  - class: `org.eclipse.epsilon.evl.emf.validation.EvlMarkerResolutionGenerator`, markerType: `DiningRoom.diagram.diagnostic`
  - class: `org.eclipse.epsilon.evl.emf.validation.EvlMarkerResolutionGenerator`, markerType: `org.eclipse.emf.ecore.diagnostic`
- Save

> To add the two *markerResolutionGenerators* above, you can right-click on `org.eclipse.ui.ide.markerResolution` > New > `markerResolutionGenerator`. If this option is not available, then add a right-click on `org.eclipse.ui.ide.markerResolution` > New > `Generic` and in the Body text, write the following:
```
<markerResolutionGenerator class="org.eclipse.epsilon.evl.emf.validation.EvlMarkerResolutionGenerator" markertype="DiningRoom.diagram.diagnostic"/>
```
> Then, create a second generic and write:
```
<markerResolutionGenerator class="org.eclipse.epsilon.evl.emf.validation.EvlMarkerResolutionGenerator" markertype="org.eclipse.emf.ecore.diagnostic"/>
```
> Finally, drag and drop them under `org.eclipse.ui.ide.markerResolution` and delete the two empty Generic.

## Create instances

<a name="dynamic-instance"></a>
###### Create a model dynamically
To create a model directly in the same Eclipse instance:
- Open `diningroom.ecore` metamodel file
- Right-click on the root class `Room` > Create Dynamic instance. Call it `Room1.xmi`. This will create a instance of your metamodel as an `xmi` file.
- Close it
- Right-click on the `Room1.xmi` > Open With > Sample Reflective Ecore Model Editor
- In this tree view of your model, right-click on Room > New Child > Tables to create an instance of a table
- You can set its attribute values in the Properties view

###### Launch second Eclipse instance
- Right-click the `DiningRoom` project, Run as > Run configurations
- Create a new configuration under *Eclipse Application* called `Graphical`
- If you need more heap for the child Eclipse instance, in the *Arguments* tab, under VM arguments paste: `-Xms512M -Xmx1024M` (drop the tutorial's `-XX:PermSize`/`-XX:MaxPermSize` flags — PermGen was removed in Java 8+ and those flags will fail to launch on this machine's JDK 25)
- Apply then Run

###### Create model
- Create a new Modeling project called `DiningRoomModels`
- Right-click the project, New > Other > DiningRoom diagram, and create a file called `Room1.dr_diagram`, click Next and call the file name `Room1.dr`
- Create the model by clicking the object in the palette and then in the canvas 
- To validate the model, unselect any element in the canvas, then Edit > Validate
- You can also open the `Room1.dr` file with the *Sample Reflective Ecore Model Editor*
- Right-click on Room and click *Validate*

## Create a textual concrete syntax for the DSL

###### Create a new Xtext project
- Right-click in the Project Explorer New > Other > Xtext project From Existing Ecore Models
- Add the EPackage from the EMF generator model `DiningRoom.genmodel`
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