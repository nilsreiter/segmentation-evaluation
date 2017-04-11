package de.unistuttgart.ims.segmentation.uima.evaluation;

import java.util.Collection;

import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;

import de.unistuttgart.ims.segmentation.evaluation.Metric;
import de.unistuttgart.ims.segmentation.evaluation.WindowMetric;

public class SegEval<SEGMENT_ANNOTATION extends Annotation, BASE_ANNOTATION extends Annotation> {
	Metric metric;
	Class<SEGMENT_ANNOTATION> segmentClass;
	Class<BASE_ANNOTATION> baseClass;

	public SegEval(Metric metric, Class<SEGMENT_ANNOTATION> segmentClass, Class<BASE_ANNOTATION> baseClass) {
		this.metric = metric;
		this.segmentClass = segmentClass;
		this.baseClass = baseClass;
	}

	public double score(JCas goldView, JCas silverView, int windowSize) {
		int[] gold = getMassString(goldView, segmentClass, baseClass);
		int[] silver = getMassString(silverView, segmentClass, baseClass);

		return ((WindowMetric) metric).score(gold, silver, windowSize);
	}

	public double score(JCas goldView, JCas silverView) {
		int[] gold = getMassString(goldView, segmentClass, baseClass);
		int[] silver = getMassString(silverView, segmentClass, baseClass);

		return metric.score(gold, silver);
	}

	protected int[] getMassString(JCas jcas, Class<SEGMENT_ANNOTATION> segmentClass, Class<BASE_ANNOTATION> baseClass) {
		Collection<SEGMENT_ANNOTATION> segments = JCasUtil.select(jcas, segmentClass);
		int[] ms = new int[segments.size()];
		int i = 0;
		for (SEGMENT_ANNOTATION a : segments)
			ms[i++] = JCasUtil.selectCovered(baseClass, a).size();
		return ms;

	}
}
