package de.unistuttgart.ims.segmentation.evaluation;

public interface Metric {
	double score(int[] gold, int[] silver);

}
