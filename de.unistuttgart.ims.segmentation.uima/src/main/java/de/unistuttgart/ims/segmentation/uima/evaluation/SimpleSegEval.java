package de.unistuttgart.ims.segmentation.uima.evaluation;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;

import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;

import de.unistuttgart.ims.segmentation.evaluation.BoundarySetsMetric;
import de.unistuttgart.ims.segmentation.evaluation.Metric;
import de.unistuttgart.ims.segmentation.evaluation.WindowMetric;
import de.unistuttgart.ims.segmentation.uima.evaluation.util.Util;

public class SimpleSegEval<SEGMENT_ANNOTATION extends Annotation, BASE_ANNOTATION extends Annotation> {
	Metric metric;
	Class<SEGMENT_ANNOTATION> segmentClass;
	Class<BASE_ANNOTATION> baseClass;

	public SimpleSegEval(Metric metric, Class<SEGMENT_ANNOTATION> segmentClass, Class<BASE_ANNOTATION> baseClass) {
		this.metric = metric;
		this.segmentClass = segmentClass;
		this.baseClass = baseClass;
	}

	public double score(JCas goldView, JCas silverView, int windowSize) {

		if (metric instanceof WindowMetric) {
			int[] gold = getMassString(goldView, segmentClass, baseClass);
			int[] silver = getMassString(silverView, segmentClass, baseClass);
			return ((WindowMetric) metric).score(gold, silver, windowSize);
		} else if (metric instanceof BoundarySetsMetric) {

			BiFunction<SEGMENT_ANNOTATION, String, String> fnc = Util.getFunction();
			List<Set<String>> goldBS = Util.getBoundarySetSequence(goldView, segmentClass, baseClass, fnc);
			List<Set<String>> silverBS = Util.getBoundarySetSequence(silverView, segmentClass, baseClass, fnc);

			return ((BoundarySetsMetric) metric).score(goldBS, silverBS, windowSize);

		}
		return Double.NaN;

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
