package ift6253.mindmap.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.extensions.InjectionExtension;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.eclipse.xtext.testing.validation.ValidationTestHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.google.inject.Inject;

import ift6253.mindmap.mindmap.MainTopic;
import ift6253.mindmap.mindmap.MindMap;
import ift6253.mindmap.mindmap.MindMapPackage;
import ift6253.mindmap.mindmap.SubTopic;
import ift6253.mindmap.validation.MindMapValidator;

@ExtendWith(InjectionExtension.class)
@InjectWith(MindMapInjectorProvider.class)
public class MindMapParsingTest {

	@Inject
	private ParseHelper<MindMap> parseHelper;

	@Inject
	private ValidationTestHelper validation;

	private static final String RESEARCH = """
			mindmap Research (marker 1, marker 2)
			topic HowToConductResearch includes {
				* 1 Read includes {
					- SearchInDatabases includes {
						- 1 Springer
						- 2 Scopus
					}
				}
				* 2 Analyze
				* Implement
			}
			""";

	@Test
	public void parsesTheAssignmentExample() throws Exception {
		MindMap map = parseHelper.parse(RESEARCH);
		validation.assertNoIssues(map);

		assertEquals("Research", map.getTitle());
		assertEquals(2, map.getMarkers().size());
		assertEquals("HowToConductResearch", map.getTopic().getName());

		MainTopic read = map.getTopic().getMaintopics().get(0);
		assertSame(map.getMarkers().get(0), read.getMarker());
		SubTopic scopus = read.getSubtopics().get(0).getSubsubtopics().get(1);
		assertEquals("Scopus", scopus.getName());
		assertSame(map.getMarkers().get(1), scopus.getMarker());
		assertNull(map.getTopic().getMaintopics().get(2).getMarker());
	}

	@Test
	public void mainTopicCannotBeNestedUnderSubTopic() throws Exception {
		MindMap map = parseHelper.parse("""
				mindmap M
				topic T1 includes {
					* T2 includes {
						- T3 includes { * T4 }
					}
				}
				""");
		assertFalse(map.eResource().getErrors().isEmpty());
	}

	@Test
	public void needsExactlyOneCentralTopic() throws Exception {
		assertFalse(parseHelper.parse("mindmap M").eResource().getErrors().isEmpty());
		assertFalse(parseHelper.parse("mindmap M topic A topic B").eResource().getErrors().isEmpty());
	}

	@Test
	public void undefinedMarkerDoesNotResolve() throws Exception {
		MindMap map = parseHelper.parse("mindmap M (marker 1) topic C includes { * 2 A }");
		validation.assertError(map, MindMapPackage.Literals.MAIN_TOPIC, "org.eclipse.xtext.diagnostics.Diagnostic.Linking");
	}

	@Test
	public void flagsDuplicateAndUnusedMarkers() throws Exception {
		MindMap map = parseHelper.parse("mindmap M (marker 1, marker 1, marker 2) topic C includes { * 1 A }");
		validation.assertError(map, MindMapPackage.Literals.MARKER, MindMapValidator.DUPLICATE_MARKER);
		validation.assertWarning(map, MindMapPackage.Literals.MARKER, MindMapValidator.UNUSED_MARKER);
	}

	@Test
	public void flagsDuplicateSiblings() throws Exception {
		MindMap map = parseHelper.parse("mindmap M topic C includes { * A * A }");
		validation.assertWarning(map, MindMapPackage.Literals.MAIN_TOPIC, MindMapValidator.DUPLICATE_TOPIC);
	}

}
