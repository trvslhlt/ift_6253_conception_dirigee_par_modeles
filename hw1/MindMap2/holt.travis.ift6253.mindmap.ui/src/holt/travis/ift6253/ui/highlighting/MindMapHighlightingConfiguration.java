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

	@Override
	public void configure(IHighlightingConfigurationAcceptor acceptor) {
		super.configure(acceptor);
		acceptor.acceptDefaultHighlighting(TAG_ID, "Tag", tagTextStyle());
		acceptor.acceptDefaultHighlighting(RELATED_TOPIC_ID, "Related Topic Reference", relatedTopicTextStyle());
		acceptor.acceptDefaultHighlighting(PRIORITY_ID, "Priority", priorityTextStyle());
	}

	private TextStyle tagTextStyle() {
		TextStyle style = defaultTextStyle().copy();
		style.setColor(new RGB(255, 165, 0));
		style.setStyle(SWT.ITALIC);
		return style;
	}

	private TextStyle relatedTopicTextStyle() {
		TextStyle style = defaultTextStyle().copy();
		style.setColor(new RGB(100, 100, 255));
		style.setStyle(TextAttribute.UNDERLINE);
		return style;
	}

	private TextStyle priorityTextStyle() {
		TextStyle style = defaultTextStyle().copy();
		style.setColor(new RGB(0, 255, 0));
		style.setStyle(SWT.BOLD);
		return style;
	}
}
