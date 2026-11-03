package ift6253.mindmap.naming;

import org.eclipse.xtext.naming.DefaultDeclarativeQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;

import ift6253.mindmap.mindmap.Marker;

/**
 * Markers have a symbol rather than a name, and topics refer to them by that
 * symbol ("* 1 Read"), so the symbol is what the scope indexes them under.
 */
public class MindMapQualifiedNameProvider extends DefaultDeclarativeQualifiedNameProvider {

	protected QualifiedName qualifiedName(Marker marker) {
		return marker.getSymbol() == null ? null : QualifiedName.create(marker.getSymbol());
	}
}
