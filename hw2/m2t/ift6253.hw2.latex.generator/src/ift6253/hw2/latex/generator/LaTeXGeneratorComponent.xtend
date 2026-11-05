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
		val latexText = new LaTeXTemplate().generate(document)
		Files.writeString(file, latexText)
	}
	
	override postInvoke() {}
}