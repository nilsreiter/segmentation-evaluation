package de.unistuttgart.ims.segmentation.agreement;

import org.apache.uima.jcas.JCas;

import de.unistuttgart.ims.segmentation.evaluation.Metric;

public interface ScottPi extends Agreement {
	void setObservedAgreementMetric(Metric metric);

	double getObservedAgreement(JCas... jcas);

	double getChanceAgreement(JCas... jcas);

	Metric getObservedAgreementMetric();
}
