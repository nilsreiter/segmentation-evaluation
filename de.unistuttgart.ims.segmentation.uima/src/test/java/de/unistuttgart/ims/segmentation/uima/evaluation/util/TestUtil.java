package de.unistuttgart.ims.segmentation.uima.evaluation.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;

import org.apache.uima.UIMAException;
import org.apache.uima.fit.factory.AnnotationFactory;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.jcas.JCas;
import org.junit.Test;

import de.unistuttgart.ims.segmentation.type.Segment;
import de.unistuttgart.ims.segmentation.type.SegmentationSubUnit;

public class TestUtil {
	@Test
	public void testGetBoundarySetsString() throws UIMAException {
		JCas jcas = JCasFactory.createJCas();
		jcas.setDocumentText("abcdefgh");

		int i = 0;
		AnnotationFactory.createAnnotation(jcas, i, i + 1, SegmentationSubUnit.class);
		i++;
		AnnotationFactory.createAnnotation(jcas, i, i + 1, SegmentationSubUnit.class);
		i++;
		AnnotationFactory.createAnnotation(jcas, i, i + 1, SegmentationSubUnit.class);
		i++;
		AnnotationFactory.createAnnotation(jcas, i, i + 1, SegmentationSubUnit.class);
		i++;
		AnnotationFactory.createAnnotation(jcas, i, i + 1, SegmentationSubUnit.class);
		i++;
		AnnotationFactory.createAnnotation(jcas, i, i + 1, SegmentationSubUnit.class);
		i++;
		AnnotationFactory.createAnnotation(jcas, i, i + 1, SegmentationSubUnit.class);
		i++;
		AnnotationFactory.createAnnotation(jcas, i, i + 1, SegmentationSubUnit.class);

		AnnotationFactory.createAnnotation(jcas, 0, 3, Segment.class);
		AnnotationFactory.createAnnotation(jcas, 5, 6, Segment.class);

		List<Set<String>> l = Util.getBoundarySetSequence(jcas, Segment.class, SegmentationSubUnit.class,
				new BiFunction<Segment, String, String>() {

					public String apply(Segment segment, String arg0) {
						return arg0;
					}
				});
		System.err.println(jcas.getDocumentText().toCharArray());
		System.err.println(l);
		assertEquals(7, l.size());
		assertFalse(l.get(0).isEmpty());
		assertTrue(l.get(1).isEmpty());
		assertTrue(l.get(2).isEmpty());
		assertFalse(l.get(3).isEmpty());
		assertTrue(l.get(4).isEmpty());
		assertFalse(l.get(5).isEmpty());
		assertFalse(l.get(6).isEmpty());
	}
}
