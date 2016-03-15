package de.ustu.creta.segmentation.agreement.impl;

import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

import de.unistuttgart.ims.segmentation.type.SegmentBoundary;
import de.unistuttgart.ims.segmentation.type.SegmentationUnit;
import de.ustu.creta.segmentation.agreement.CohensKappa;
import de.ustu.creta.segmentation.evaluation.Metric;

public class CohensKappa_impl implements CohensKappa {

	Metric observedAgreementMetric;

	@Override
	public double agr(JCas... jcas) {
		final double obs = getObservedAgreement(jcas);
		final double chc = getChanceAgreement(jcas);
		return (obs - chc) / (1 - chc);
	}

	@Override
	public double getObservedAgreement(JCas... jcas) {
		final int mass = JCasUtil.select(jcas[0], SegmentationUnit.class).size();
		double z = 0;
		double n = 0;
		for (int i = 0; i < jcas.length; i++) {
			for (int j = i + 1; j < jcas.length; j++) {
				final double score = observedAgreementMetric.score(jcas[i], jcas[j]);
				z += mass * score;
				n += mass;
			}
		}

		return z / n;
	}

	@Override
	public double getChanceAgreement(JCas... jcas) {
		final int mass = JCasUtil.select(jcas[0], SegmentationUnit.class).size();

		final double cha0 = JCasUtil.select(jcas[0], SegmentBoundary.class).size() / (double) (mass - 1);
		final double cha1 = JCasUtil.select(jcas[1], SegmentBoundary.class).size() / (double) (mass - 1);
		return cha0 * cha1;
	}

	@Override
	public void setObservedAgreementMetric(Metric metric) {
		observedAgreementMetric = metric;

	}

	@Override
	public Metric getObservedAgreementMetric() {
		return observedAgreementMetric;
	}
}
