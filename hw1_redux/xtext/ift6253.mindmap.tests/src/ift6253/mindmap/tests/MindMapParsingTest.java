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
				topic C includes {
					* A includes {
						- B includes { * D }
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

	/** Task 2: the course project map is valid and every MainTopic has at least 3 levels. */
	@Test
	public void courseProjectSample() throws Exception {
		MindMap map = parseHelper.parse(Files.readString(sample("courseProject.mindmap")));
		validation.assertNoIssues(map);
		for (MainTopic main : map.getTopic().getMaintopics()) {
			int depth = 1 + main.getSubtopics().stream().mapToInt(MindMapParsingTest::depth).max().orElse(0);
			assertTrue(depth >= 3, main.getName() + " has only " + depth + " levels");
		}
	}

	/**
	 * The samples folder sits next to the projects. A plug-in test does not run
	 * from the project folder, so look upwards from where this class was loaded
	 * (test-bin/ or the bundle folder) as well as from the working directory.
	 */
	private static Path sample(String name) throws Exception {
		List<Path> starts = List.of(
				Path.of(MindMapParsingTest.class.getProtectionDomain().getCodeSource().getLocation().toURI()),
				Path.of("").toAbsolutePath());
		for (Path start : starts) {
			for (Path dir = start; dir != null; dir = dir.getParent()) {
				Path candidate = dir.resolve("samples").resolve(name);
				if (Files.exists(candidate)) return candidate;
			}
		}
		throw new IllegalStateException("samples/" + name + " not found above " + starts);
	}

	private static int depth(SubTopic topic) {
		return 1 + topic.getSubsubtopics().stream().mapToInt(MindMapParsingTest::depth).max().orElse(0);
	}
}
