package de.unistuttgart.ims.segmentation.evaluation;

public interface MassStringMetric {
	double score(int[] gold, int[] silver);

}
