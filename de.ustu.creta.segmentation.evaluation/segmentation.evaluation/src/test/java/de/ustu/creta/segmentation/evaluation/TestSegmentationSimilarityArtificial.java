package de.ustu.creta.segmentation.evaluation;

import static org.apache.uima.fit.factory.AnnotationFactory.createAnnotation;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.apache.uima.fit.factory.AnnotationFactory;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.jcas.JCas;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.nilsreiter.pipeline.segmentation.type.SegmentBoundary;
import de.nilsreiter.pipeline.segmentation.type.SegmentationUnit;
import de.ustu.creta.segmentation.evaluation.impl.SegmentationSimilarity_impl;

public class TestSegmentationSimilarityArtificial {
	JCas gold, silv;

	SegmentationSimilarity_impl bd;

	String text = "123456789012345";

	@BeforeClass
	public static void setUpClass() {
		System.setProperty("python.path",
				"src/main/resources/python/segeval-2.0.11");
	}

	@Before
	public void setUp() throws Exception {
		gold = JCasFactory.createJCas();
		gold.setDocumentText(text);

		// AnnotationFactory.createAnnotation(gold, 14, 14,
		// SegmentBoundary.class);

		for (int i = 0; i < text.length() - 1; i++) {
			createAnnotation(gold, i, i + 1, SegmentationUnit.class);
		}

		silv = JCasFactory.createJCas();
		silv.setDocumentText(text);
		for (int i = 0; i < text.length() - 1; i++) {
			createAnnotation(silv, i, i + 1, SegmentationUnit.class);
		}

		bd =
				(SegmentationSimilarity_impl) MetricFactory.getMetric(
						SegmentationSimilarity.class, SegmentBoundary.class);

	}

	@Test
	public void testInit() {
		assertTrue(bd.init(gold));
	}

	@Test
	public void testEditDistance1() {
		AnnotationFactory.createAnnotation(gold, 1, 1, SegmentBoundary.class);
		AnnotationFactory.createAnnotation(gold, 3, 3, SegmentBoundary.class);
		AnnotationFactory.createAnnotation(gold, 5, 5, SegmentBoundary.class);
		AnnotationFactory.createAnnotation(gold, 8, 8, SegmentBoundary.class);
		AnnotationFactory.createAnnotation(gold, 11, 11, SegmentBoundary.class);
		AnnotationFactory.createAnnotation(gold, 12, 12, SegmentBoundary.class);

		// no silver annotations
		assertEquals(6, bd.getEditDistance(gold, silv, 2));
		assertEquals(0, bd.getEditDistance(silv, silv, 10));
		assertEquals(0, bd.getEditDistance(gold, gold, 10));

		// some silver annotations
		AnnotationFactory.createAnnotation(silv, 1, 1, SegmentBoundary.class);
		AnnotationFactory.createAnnotation(silv, 3, 3, SegmentBoundary.class);
		AnnotationFactory.createAnnotation(silv, 4, 4, SegmentBoundary.class);
		AnnotationFactory.createAnnotation(silv, 6, 6, SegmentBoundary.class);
		AnnotationFactory.createAnnotation(silv, 12, 12, SegmentBoundary.class);

		assertEquals(4, bd.getEditDistance(gold, silv, 2));
		assertEquals(0, bd.getEditDistance(silv, silv, 10));
		assertEquals(0, bd.getEditDistance(gold, gold, 10));
	}

	@Test
	public void testMaxVsMin() {
		for (int i = 1; i < text.length() - 1; i++) {
			AnnotationFactory.createAnnotation(gold, i, i,
					SegmentBoundary.class);
		}

		assertEquals(0, bd.getSegmentationSimilarity(gold, silv), 1e-5);
		assertEquals(0, bd.getSegmentationSimilarity(silv, gold), 1e-5);

		assertEquals(1, bd.getSegmentationSimilarity(silv, silv), 1e-5);
		assertEquals(1, bd.getSegmentationSimilarity(gold, gold), 1e-5);

	}

	@Test
	public void testFullMisses() {
		AnnotationFactory.createAnnotation(gold, 1, 1, SegmentBoundary.class);
		AnnotationFactory.createAnnotation(gold, 3, 3, SegmentBoundary.class);
		AnnotationFactory.createAnnotation(gold, 5, 5, SegmentBoundary.class);
		AnnotationFactory.createAnnotation(gold, 7, 7, SegmentBoundary.class);
		AnnotationFactory.createAnnotation(gold, 11, 11, SegmentBoundary.class);
		AnnotationFactory.createAnnotation(gold, 13, 13, SegmentBoundary.class);

		AnnotationFactory.createAnnotation(silv, 1, 1, SegmentBoundary.class);
		AnnotationFactory.createAnnotation(silv, 3, 3, SegmentBoundary.class);
		AnnotationFactory.createAnnotation(silv, 11, 11, SegmentBoundary.class);
		AnnotationFactory.createAnnotation(silv, 13, 13, SegmentBoundary.class);

		assertEquals(0.8461, bd.getSegmentationSimilarity(gold, silv), 1e-3);

	}

	@Test
	public void testNearMisses() {
		AnnotationFactory.createAnnotation(gold, 6, 6, SegmentBoundary.class);
		AnnotationFactory.createAnnotation(silv, 7, 7, SegmentBoundary.class);

		assertEquals(0.9231, bd.getSegmentationSimilarity(gold, silv), 1e-3);

	}
}
