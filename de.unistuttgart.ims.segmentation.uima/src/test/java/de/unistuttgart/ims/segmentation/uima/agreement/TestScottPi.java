package de.unistuttgart.ims.segmentation.uima.agreement;

import static org.apache.uima.fit.factory.AnnotationFactory.createAnnotation;
import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.jcas.JCas;
import org.junit.Before;
import org.junit.Test;

import de.unistuttgart.ims.segmentation.type.SegmentBoundary;
import de.unistuttgart.ims.segmentation.type.SegmentationUnit;
import de.unistuttgart.ims.segmentation.uima.agreement.ScottPi;
import de.unistuttgart.ims.segmentation.uima.agreement.impl.ScottPi_impl;
import de.unistuttgart.ims.segmentation.uima.evaluation.MetricFactory;
import de.unistuttgart.ims.segmentation.uima.evaluation.SegmentationSimilarity;
import de.unistuttgart.ims.segmentation.uima.evaluation.impl.AbstractFournierMetric;

public class TestScottPi {
	JCas gold, silv;

	AbstractFournierMetric bd;

	String text = "123456789012345";
	ScottPi spi;

	@Before
	public void setUp() throws Exception {
		gold = JCasFactory.createJCas();
		gold.setDocumentText(text);

		for (int i = 0; i < (text.length() - 1); i++) {
			createAnnotation(gold, i, i + 1, SegmentationUnit.class);
		}

		silv = JCasFactory.createJCas();
		silv.setDocumentText(text);
		for (int i = 0; i < (text.length() - 1); i++) {
			createAnnotation(silv, i, i + 1, SegmentationUnit.class);
		}

		spi = new ScottPi_impl();
		spi.setObservedAgreementMetric(MetricFactory.getMetric(SegmentationSimilarity.class, SegmentBoundary.class));

	}

	@Test
	public void testPerfectAgreements() {
		final Random random = new Random();
		for (int i = 0; i < 5; i++) {
			final int r = random.nextInt(text.length() - 2) + 1;
			createAnnotation(gold, r, r, SegmentBoundary.class);
			createAnnotation(silv, r, r, SegmentBoundary.class);

			assertEquals(1.0, spi.agr(gold, silv), 1e-3);
		}
	}

	@Test
	public void testPerfectAgreementsObserved() {
		final Random random = new Random();
		for (int i = 0; i < 5; i++) {
			final int r = random.nextInt(text.length() - 2) + 1;
			createAnnotation(gold, r, r, SegmentBoundary.class);
			createAnnotation(silv, r, r, SegmentBoundary.class);

			assertEquals(1.0, spi.getObservedAgreement(gold, silv), 1e-3);
		}
	}

	@Test
	public void testPerfectAgreementsChance() {
		final Random random = new Random();

		int r;
		r = random.nextInt(text.length() - 2) + 1;
		createAnnotation(gold, r, r, SegmentBoundary.class);
		assertEquals(0.01923, spi.getChanceAgreement(gold, silv), 1e-3);

		r = random.nextInt(text.length() - 2) + 1;
		createAnnotation(gold, r, r, SegmentBoundary.class);
		assertEquals(0.03846, spi.getChanceAgreement(gold, silv), 1e-3);

		r = random.nextInt(text.length() - 2) + 1;
		createAnnotation(gold, r, r, SegmentBoundary.class);
		assertEquals(0.05769, spi.getChanceAgreement(gold, silv), 1e-3);

		r = random.nextInt(text.length() - 2) + 1;
		createAnnotation(gold, r, r, SegmentBoundary.class);
		assertEquals(0.07692, spi.getChanceAgreement(gold, silv), 1e-3);
	}
}
