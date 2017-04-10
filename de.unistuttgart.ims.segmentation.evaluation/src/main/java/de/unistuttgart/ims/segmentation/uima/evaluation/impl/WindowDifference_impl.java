package de.unistuttgart.ims.segmentation.uima.evaluation.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;

import de.unistuttgart.ims.segmentation.uima.evaluation.WindowDifference;

public class WindowDifference_impl implements WindowDifference {
	Class<? extends Annotation> annoType;

	int windowSize = 10;

	public WindowDifference_impl(Class<? extends Annotation> annotationType) {
		annoType = annotationType;
	}

	public Map<String, Double> scores(JCas gold, JCas silver) {

		final Map<String, Double> res = new HashMap<String, Double>();
		res.put(getClass().getSimpleName(), score(gold, silver));
		return res;
	}

	public int getWindowSize() {
		return windowSize;
	}

	public void setWindowSize(int windowSize) {
		this.windowSize = windowSize;
	}

	public boolean init(JCas gold) {
		int pos = 0;
		final Iterator<? extends Annotation> iter = JCasUtil.iterator(gold, annoType);
		int n = 0;
		int length = 0;
		while (iter.hasNext()) {
			final Annotation anno = iter.next();
			length += anno.getBegin() - pos;
			n++;
			pos = anno.getBegin();
		}
		setWindowSize((int) Math.floor((double) length / (double) (n + 1)));
		return true;
	}

	public double score(JCas gold, JCas silver) {
		int wBegin = 0;
		int wEnd = wBegin + windowSize;
		final int length = gold.getDocumentText().length();
		int sum = 0;
		do {
			final int num_gold = JCasUtil.selectCovered(gold, annoType, wBegin, wEnd).size();
			final int num_silver = JCasUtil.selectCovered(silver, annoType, wBegin, wEnd).size();
			if (num_gold != num_silver)
				sum++;
			// sum += Math.abs(num_gold - num_silver);
			wBegin = wEnd + 1;
			wEnd = wBegin + windowSize;
		} while (wEnd <= length);
		return (double) sum / (double) (length - windowSize);
	}
}
