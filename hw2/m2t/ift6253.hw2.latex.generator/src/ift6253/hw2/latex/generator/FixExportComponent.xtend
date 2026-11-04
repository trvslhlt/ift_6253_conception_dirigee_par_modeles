package ift6253.hw2.latex.generator

import java.nio.file.Files
import java.nio.file.Paths
import org.eclipse.emf.mwe2.runtime.workflow.IWorkflowComponent
import org.eclipse.emf.mwe2.runtime.workflow.IWorkflowContext
import org.eclipse.xtend.lib.annotations.Accessors

// AToMPM exporter names the root element "undefined"
// name it after the root class
class FixExportComponent implements IWorkflowComponent {
	@Accessors String source
	@Accessors String target

	override preInvoke() {}

	override invoke(IWorkflowContext context) {
		val text = Files.readString(Paths.get(source))
		val fixed = text.replaceFirst('<undefined ', '<MindMap ').replace('</undefined>', '</MindMap>')
		val file = Paths.get(target)
		Files.createDirectories(file.parent)
		Files.writeString(file, fixed)
	}

	override postInvoke() {}
}