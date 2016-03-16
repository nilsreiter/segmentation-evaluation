package de.unistuttgart.ims.segmentation.agreement;

import org.apache.uima.jcas.JCas;

public interface CohensKappa extends Agreement {

	double getObservedAgreement(JCas... jcas);

	double getChanceAgreement(JCas... jcas);

}
