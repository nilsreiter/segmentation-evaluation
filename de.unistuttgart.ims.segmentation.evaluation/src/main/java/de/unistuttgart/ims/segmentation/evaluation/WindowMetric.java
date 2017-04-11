package de.unistuttgart.ims.segmentation.evaluation;

public interface WindowMetric extends Metric {
	double score(int[] gold, int[] silver, int windowSize);

	int computeWindowSize(int[] goldMass);

}
