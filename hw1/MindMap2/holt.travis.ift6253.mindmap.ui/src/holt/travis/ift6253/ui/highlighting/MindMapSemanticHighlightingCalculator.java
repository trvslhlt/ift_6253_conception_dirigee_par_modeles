package holt.travis.ift6253.ui.highlighting;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.xtext.ide.editor.syntaxcoloring.DefaultSemanticHighlightingCalculator;
import org.eclipse.xtext.ide.editor.syntaxcoloring.IHighlightedPositionAcceptor;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.util.CancelIndicator;

import holt.travis.ift6253.mindMap.MindMap;
import holt.travis.ift6253.mindMap.MindMapPackage;
import holt.travis.ift6253.mindMap.Topic;

public class MindMapSemanticHighlightingCalculator extends DefaultSemanticHighlightingCalculator {

	@Override
	public void provideHighlightingFor(XtextResource resource, IHighlightedPositionAcceptor acceptor, CancelIndicator cancelIndicator) {
		if (resource == null) {
			return;
		}
		TreeIterator<EObject> contents = resource.getAllContents();
		while (contents.hasNext()) {
			EObject content = contents.next();
			if (content instanceof MindMap) {
				highlightFeature(content, MindMapPackage.Literals.MIND_MAP__TAGS, acceptor);
			} else if (content instanceof Topic) {
				highlightFeature(content, MindMapPackage.Literals.TOPIC__TAGS, acceptor);
			}
		}
	}

	private void highlightFeature(EObject object, EStructuralFeature feature, IHighlightedPositionAcceptor acceptor) {
		for (INode node : NodeModelUtils.findNodesForFeature(object, feature)) {
			acceptor.addPosition(node.getOffset(), node.getLength(), MindMapHighlightingConfiguration.TAG_ID);
		}
	}
}
