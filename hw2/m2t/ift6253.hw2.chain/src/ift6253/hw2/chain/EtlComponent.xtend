package ift6253.hw2.chain

import ift6253.hw2.latex.LatexPackage
import java.io.File
import org.eclipse.emf.mwe2.runtime.workflow.IWorkflowComponent
import org.eclipse.emf.mwe2.runtime.workflow.IWorkflowContext
import org.eclipse.epsilon.emc.emf.EmfModel
import org.eclipse.epsilon.etl.EtlModule
import org.eclipse.xtend.lib.annotations.Accessors

class EtlComponent implements IWorkflowComponent {
	@Accessors String etl
	@Accessors String metamodel
	@Accessors String source
	@Accessors String target

	override preInvoke() {}

	override invoke(IWorkflowContext context) {
		val mindmap = new EmfModel
		mindmap.name = 'MindMap' // must match .etl
		mindmap.metamodelFile = new File(metamodel).absolutePath
		mindmap.modelFile = new File(source).absolutePath
		mindmap.readOnLoad = true
		mindmap.storedOnDisposal = false
		mindmap.load

		val latex = new EmfModel
		latex.name = 'LaTeX' // must match .etl
		latex.metamodelUri = LatexPackage.eNS_URI
		latex.modelFile = new File(target).absolutePath
		latex.readOnLoad = false
		latex.storedOnDisposal = true
		latex.load

		val module = new EtlModule
		module.parse(new File(etl))
		if (!module.parseProblems.empty) {
			throw new IllegalStateException(module.parseProblems.join('\n'))
		}
		module.context.modelRepository.addModel(mindmap)
		module.context.modelRepository.addModel(latex)
		module.execute
		module.context.modelRepository.dispose
	}

	override postInvoke() {}
}
