package holt.travis.ift6253.ui.highlighting;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.xtext.ui.editor.syntaxcoloring.DefaultHighlightingConfiguration;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightingConfigurationAcceptor;
import org.eclipse.xtext.ui.editor.utils.TextStyle;

public class MindMapHighlightingConfiguration extends DefaultHighlightingConfiguration {

	public static final String TAG_ID = "tag";
	public static final String RELATED_TOPIC_ID = "relatedTopic";
	public static final String PRIORITY_ID = "priority";

	// topic dimension depth (count of '->' transitions from root)
	public static final String DEPTH_0_ID = "depth0";
	public static final String DEPTH_1_ID = "depth1";
	public static final String DEPTH_2_ID = "depth2";

	@Override
	public void configure(IHighlightingConfigurationAcceptor acceptor) {
		super.configure(acceptor);
		acceptor.acceptDefaultHighlighting(TAG_ID, "Tag", tagTextStyle());
		acceptor.acceptDefaultHighlighting(RELATED_TOPIC_ID, "Related Topic Reference", relatedTopicTextStyle());
		acceptor.acceptDefaultHighlighting(PRIORITY_ID, "Priority", priorityTextStyle());
		acceptor.acceptDefaultHighlighting(DEPTH_0_ID, "Topic Depth 0", depthTextStyle(new RGB(200, 200, 255)));
		acceptor.acceptDefaultHighlighting(DEPTH_1_ID, "Topic Depth 1", depthTextStyle(new RGB(140, 140, 255)));
		acceptor.acceptDefaultHighlighting(DEPTH_2_ID, "Topic Depth 2+", depthTextStyle(new RGB(80, 80, 255)));
	}

	private TextStyle tagTextStyle() {
		TextStyle style = defaultTextStyle().copy();
		style.setColor(new RGB(255, 165, 0));
		style.setStyle(SWT.ITALIC);
		return style;
	}

	private TextStyle relatedTopicTextStyle() {
		TextStyle style = defaultTextStyle().copy();
		style.setColor(new RGB(0, 0, 255));
		style.setStyle(TextAttribute.UNDERLINE);
		return style;
	}

	private TextStyle priorityTextStyle() {
		TextStyle style = defaultTextStyle().copy();
		style.setColor(new RGB(0, 255, 0));
		style.setStyle(SWT.BOLD);
		return style;
	}

	private TextStyle depthTextStyle(RGB color) {
		TextStyle style = defaultTextStyle().copy();
		style.setColor(color);
		return style;
	}
}
