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

import holt.travis.ift6253.mindMap.Tag;
import holt.travis.ift6253.mindMap.Topic;
import holt.travis.ift6253.mindMap.MindMapPackage;

public class MindMapSemanticHighlightingCalculator extends DefaultSemanticHighlightingCalculator {

	@Override
	public void provideHighlightingFor(XtextResource resource, IHighlightedPositionAcceptor acceptor, CancelIndicator cancelIndicator) {
		if (resource == null) {
			return;
		}
		TreeIterator<EObject> contents = resource.getAllContents();
		while (contents.hasNext()) {
			EObject content = contents.next();
			if (content instanceof Tag) {
				// only the tag's value, so the 'tag' keyword keeps default style
				highlightFeature(content, MindMapPackage.Literals.TAG__NAME, MindMapHighlightingConfiguration.TAG_ID, acceptor);
			} else if (content instanceof Topic) {
				highlightFeature(content, MindMapPackage.Literals.TOPIC__TAGS, MindMapHighlightingConfiguration.TAG_ID, acceptor);
				highlightFeature(content, MindMapPackage.Literals.TOPIC__RELATED, MindMapHighlightingConfiguration.RELATED_TOPIC_ID, acceptor);
				highlightFeature(content, MindMapPackage.Literals.TOPIC__PRIORITY, MindMapHighlightingConfiguration.PRIORITY_ID, acceptor);
			}
		}
	}

	private void highlightFeature(EObject object, EStructuralFeature feature, String highlightingId, IHighlightedPositionAcceptor acceptor) {
		for (INode node : NodeModelUtils.findNodesForFeature(object, feature)) {
			acceptor.addPosition(node.getOffset(), node.getLength(), highlightingId);
		}
	}
}
