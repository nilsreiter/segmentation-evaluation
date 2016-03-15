package de.unistuttgart.ims.segmentation.agreement;

import org.apache.uima.jcas.JCas;

import de.unistuttgart.ims.segmentation.evaluation.Metric;

public interface Agreement {
	public double agr(JCas... jcas);

	void setObservedAgreementMetric(Metric metric);

	Metric getObservedAgreementMetric();

}
