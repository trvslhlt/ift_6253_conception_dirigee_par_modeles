package ift6253.mindmap.naming;

import org.eclipse.xtext.naming.DefaultDeclarativeQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;

import ift6253.mindmap.mindmap.Marker;


public class MindMapQualifiedNameProvider extends DefaultDeclarativeQualifiedNameProvider {

	protected QualifiedName qualifiedName(Marker marker) {
		String markerSymbol = marker.getSymbol();
		if (markerSymbol == null) {
			return null;
		}
		return QualifiedName.create(markerSymbol);
	}
	
}
