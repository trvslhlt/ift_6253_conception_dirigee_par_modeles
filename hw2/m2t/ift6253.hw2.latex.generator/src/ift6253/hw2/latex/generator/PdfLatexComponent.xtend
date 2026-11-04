package ift6253.hw2.latex.generator

import ift6253.hw2.latex.Document
import java.io.File
import org.eclipse.emf.mwe2.runtime.workflow.IWorkflowComponent
import org.eclipse.emf.mwe2.runtime.workflow.IWorkflowContext
import org.eclipse.xtend.lib.annotations.Accessors

class PdfLatexComponent implements IWorkflowComponent {
	@Accessors String modelSlot
	@Accessors String outputDir
	@Accessors String pdflatex = 'pdflatex'

	override preInvoke() {}

	override invoke(IWorkflowContext context) {
		val document = context.get(modelSlot) as Document
		val process = new ProcessBuilder(pdflatex, '-interaction=nonstopmode', '-halt-on-error', document.name + '.latex')
			.directory(new File(outputDir))
			.redirectErrorStream(true)
			.redirectOutput(ProcessBuilder.Redirect.DISCARD) // the same text goes to the .log
			.start
		if (process.waitFor != 0) {
			throw new IllegalStateException('pdflatex failed, see ' + outputDir + '/' + document.name + '.log')
		}
	}

	override postInvoke() {}
}
