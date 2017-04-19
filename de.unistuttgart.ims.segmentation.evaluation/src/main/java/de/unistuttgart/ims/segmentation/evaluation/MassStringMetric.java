package de.unistuttgart.ims.segmentation.evaluation;

public interface MassStringMetric extends Metric {
	double score(int[] gold, int[] silver);

}
