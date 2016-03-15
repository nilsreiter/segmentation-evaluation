package de.ustu.creta.segmentation.agreement.impl;

import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

import de.unistuttgart.ims.segmentation.type.SegmentBoundary;
import de.unistuttgart.ims.segmentation.type.SegmentationUnit;
import de.ustu.creta.segmentation.agreement.ScottPi;
import de.ustu.creta.segmentation.evaluation.Metric;

public class ScottPi_impl implements ScottPi {
	Metric observedAgreementMetric;

	@Override
	public double agr(JCas... jcas) {
		final double obs = getObservedAgreement(jcas);
		final double chc = getChanceAgreement(jcas);
		return (obs - chc) / (1 - chc);
	}

	@Override
	public double getChanceAgreement(JCas... jcas) {
		final int mass = JCasUtil.select(jcas[0], SegmentationUnit.class).size();
		int n = 0, z = 0;
		for (final JCas jca : jcas) {
			z += JCasUtil.select(jca, SegmentBoundary.class).size();
			n += (mass - 1);
		}
		return z / (jcas.length * (double) n);
	}

	@Override
	public double getObservedAgreement(JCas... jcas) {
		final int mass = JCasUtil.select(jcas[0], SegmentationUnit.class).size();
		int z = 0;
		int n = 0;
		for (int i = 0; i < jcas.length; i++) {
			for (int j = i + 1; j < jcas.length; j++) {
				final double score = observedAgreementMetric.scores(jcas[i], jcas[j])
						.get(observedAgreementMetric.getClass().getSimpleName());
				z += mass * score;
				n += mass;
			}
		}

		return z / (double) n;
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
