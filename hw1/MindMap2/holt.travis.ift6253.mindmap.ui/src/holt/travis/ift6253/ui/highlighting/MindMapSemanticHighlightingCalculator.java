package holt.travis.ift6253.ui.highlighting;

import java.util.ArrayList;
import java.util.List;

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
		// pass 1: every "normal" highlight, tags/priority/related/depth, for every object
		List<Topic> doneTopics = new ArrayList<>();
		TreeIterator<EObject> contents = resource.getAllContents();
		while (contents.hasNext()) {
			EObject content = contents.next();
			if (content instanceof Tag) {
				highlightFeature(content, MindMapPackage.Literals.TAG__NAME, MindMapHighlightingConfiguration.TAG_ID, acceptor);
			} else if (content instanceof Topic) {
				Topic topic = (Topic) content;
				if (topic.isDone()) {
					// pass 2 covers this topic's whole span -- itself and every descendant -- so
					// there's no point computing per-feature highlights here, and no need to
					// descend into its children at all.
					doneTopics.add(topic);
					contents.prune();
					continue;
				}
				highlightFeature(topic, MindMapPackage.Literals.TOPIC__TAGS, MindMapHighlightingConfiguration.TAG_ID, acceptor);
				highlightFeature(topic, MindMapPackage.Literals.TOPIC__RELATED, MindMapHighlightingConfiguration.RELATED_TOPIC_ID, acceptor);
				highlightFeature(topic, MindMapPackage.Literals.TOPIC__PRIORITY, MindMapHighlightingConfiguration.PRIORITY_ID, acceptor);
				highlightFeature(topic, MindMapPackage.Literals.TOPIC__NAME, depthHighlightingId(dimensionDepth(topic)), acceptor);
			}
		}
		// pass 2: struck-through subtrees, applied over each done topic's whole text span after
		// every other highlight -- including its descendants' own -- so it always wins, regardless
		// of nesting order, and covers every feature (including ones added later) with no need to
		// enumerate them here.
		for (Topic topic : doneTopics) {
			INode wholeTopic = NodeModelUtils.getNode(topic);
			if (wholeTopic != null) {
				acceptor.addPosition(wholeTopic.getOffset(), wholeTopic.getLength(), MindMapHighlightingConfiguration.IS_DONE_ID);
			}
		}
	}

	// count of dimension ('->') transitions from the mindmap root to this topic;
	private int dimensionDepth(Topic topic) {
		EObject container = topic.eContainer();
		int parentDepth = container instanceof Topic ? dimensionDepth((Topic) container) : 0;
		return topic.isDeeper() ? parentDepth + 1 : parentDepth;
	}

	private String depthHighlightingId(int depth) {
		switch (depth) {
			case 0:
				return MindMapHighlightingConfiguration.DEPTH_0_ID;
			case 1:
				return MindMapHighlightingConfiguration.DEPTH_1_ID;
			default:
				return MindMapHighlightingConfiguration.DEPTH_2_ID;
		}
	}

	private void highlightFeature(EObject object, EStructuralFeature feature, String highlightingId, IHighlightedPositionAcceptor acceptor) {
		for (INode node : NodeModelUtils.findNodesForFeature(object, feature)) {
			acceptor.addPosition(node.getOffset(), node.getLength(), highlightingId);
		}
	}
}
