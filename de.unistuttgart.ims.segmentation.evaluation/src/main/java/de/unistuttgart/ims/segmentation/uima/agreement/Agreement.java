package de.unistuttgart.ims.segmentation.uima.agreement;

import org.apache.uima.jcas.JCas;

import de.unistuttgart.ims.segmentation.uima.evaluation.Metric;

public interface Agreement {
	public double agr(JCas... jcas);

	void setObservedAgreementMetric(Metric metric);

	Metric getObservedAgreementMetric();

}
