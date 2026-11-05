package ift6253.hw2.mindmap.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.extensions.InjectionExtension;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.eclipse.xtext.testing.validation.ValidationTestHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.google.inject.Inject;

import ift6253.hw2.mindmap.mindmap.MainTopic;
import ift6253.hw2.mindmap.mindmap.MindMap;
import ift6253.hw2.mindmap.mindmap.MindMapPackage;
import ift6253.hw2.mindmap.mindmap.SubTopic;
import ift6253.hw2.mindmap.validation.MindMapValidator;

@ExtendWith(InjectionExtension.class)
@InjectWith(MindMapInjectorProvider.class)
public class MindMapParsingTest {

	@Inject
	private ParseHelper<MindMap> parseHelper;

	@Inject
	private ValidationTestHelper validation;

	private static final String EXAMPLE = """
			mindmap MM (marker 1, marker 2)
			topic CT includes {
				* 1 MT1 includes {
					- ST11 includes {
						- 1 SST111
						- 2 SST112
					}
				}
				* 2 MT2
				* MT3
			}
			""";

	@Test
	public void parsesTheAssignmentExample() throws Exception {
		MindMap map = parseHelper.parse(EXAMPLE);
		validation.assertNoIssues(map);

		assertEquals("MM", map.getTitle());
		assertEquals(2, map.getMarkers().size());
		assertEquals("CT", map.getTopic().getName());

		MainTopic mt1 = map.getTopic().getMaintopics().get(0);
		assertSame(map.getMarkers().get(0), mt1.getMarker());
		SubTopic sst112 = mt1.getSubtopics().get(0).getSubsubtopics().get(1);
		assertEquals("SST112", sst112.getName());
		assertSame(map.getMarkers().get(1), sst112.getMarker());
		assertNull(map.getTopic().getMaintopics().get(2).getMarker());
	}

	@Test
	public void mainTopicCannotBeNestedUnderSubTopic() throws Exception {
		MindMap map = parseHelper.parse("""
				mindmap MM
				topic CT includes {
					* MT includes {
						- ST includes { * MT2 }
					}
				}
				""");
		assertFalse(map.eResource().getErrors().isEmpty());
	}

	@Test
	public void needsExactlyOneCentralTopic() throws Exception {
		assertFalse(parseHelper
				.parse("mindmap MM")
				.eResource()
				.getErrors()
				.isEmpty());
		assertFalse(parseHelper
				.parse("mindmap MM topic CT1 topic CT2")
				.eResource()
				.getErrors()
				.isEmpty());
	}

	@Test
	public void undefinedMarkerDoesNotResolve() throws Exception {
		MindMap map = parseHelper.parse("mindmap MM (marker 1) topic CT includes { * 2 MT }");
		validation.assertError(map, MindMapPackage.Literals.MAIN_TOPIC, "org.eclipse.xtext.diagnostics.Diagnostic.Linking");
	}

	@Test
	public void flagsDuplicateAndUnusedMarkers() throws Exception {
		MindMap map = parseHelper.parse("mindmap MM (marker 1, marker 1, marker 2) topic CT includes { * 1 MT }");
		validation.assertError(map, MindMapPackage.Literals.MARKER, MindMapValidator.DUPLICATE_MARKER);
		validation.assertWarning(map, MindMapPackage.Literals.MARKER, MindMapValidator.UNUSED_MARKER);
	}

	@Test
	public void flagsDuplicateSiblings() throws Exception {
		MindMap map = parseHelper.parse("mindmap MM topic CT includes { * MT * MT }");
		validation.assertWarning(map, MindMapPackage.Literals.MAIN_TOPIC, MindMapValidator.DUPLICATE_TOPIC);
	}

}
