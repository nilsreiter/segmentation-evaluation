package de.unistuttgart.ims.segmentation.uima.evaluation;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.apache.uima.fit.factory.AnnotationFactory;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.jcas.JCas;
import org.junit.Before;
import org.junit.Test;

import de.unistuttgart.ims.segmentation.type.Segment;
import de.unistuttgart.ims.segmentation.uima.evaluation.Metric;
import de.unistuttgart.ims.segmentation.uima.evaluation.MetricFactory;
import de.unistuttgart.ims.segmentation.uima.evaluation.PRF;
import de.unistuttgart.ims.segmentation.uima.evaluation.Strings;

public class TestPRF {
	JCas gold, silv;

	Metric bd;

	String text = "The dog barks. It is hungry.";

	@Before
	public void setUp() throws Exception {
		gold = JCasFactory.createJCas();
		gold.setDocumentText(text);
		AnnotationFactory.createAnnotation(gold, 0, 5, Segment.class).setValue("1");
		AnnotationFactory.createAnnotation(gold, 6, 9, Segment.class).setValue("2");

		silv = JCasFactory.createJCas();
		silv.setDocumentText(text);

		bd = MetricFactory.getMetric(PRF.class, Segment.class);
		bd.init(gold);
	}

	@Test(expected = RuntimeException.class)
	public void testNoSilverAnnotations() {
		bd.scores(gold, silv);
	}

	@Test
	public void testPerfectAnnotations() {
		AnnotationFactory.createAnnotation(silv, 0, 5, Segment.class).setValue("1");
		AnnotationFactory.createAnnotation(silv, 6, 9, Segment.class).setValue("2");
		final Map<String, Double> result = bd.scores(gold, silv);
		assertEquals(1.0, result.get("_" + Strings.PRECISION), 1e-5);
		assertEquals(1.0, result.get("_" + Strings.RECALL), 1e-5);
		assertEquals(1.0, result.get("_" + Strings.FSCORE), 1e-5);
	}
}
