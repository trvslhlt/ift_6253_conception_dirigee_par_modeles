package holt.travis.ift6253.ui.highlighting;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.xtext.ui.editor.syntaxcoloring.DefaultHighlightingConfiguration;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightingConfigurationAcceptor;
import org.eclipse.xtext.ui.editor.utils.TextStyle;

public class MindMapHighlightingConfiguration extends DefaultHighlightingConfiguration {

	public static final String TAG_ID = "tag";

	@Override
	public void configure(IHighlightingConfigurationAcceptor acceptor) {
		super.configure(acceptor);
		acceptor.acceptDefaultHighlighting(TAG_ID, "Tag", tagTextStyle());
	}

	private TextStyle tagTextStyle() {
		TextStyle style = defaultTextStyle().copy();
		style.setColor(new RGB(255, 165, 0));
		style.setStyle(SWT.ITALIC);
		return style;
	}
}
