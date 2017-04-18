package de.unistuttgart.ims.segmentation.evaluation.impl;

import de.unistuttgart.ims.segmentation.evaluation.WindowMetric;
import de.unistuttgart.ims.segmentation.evaluation.util.SegmentationUtil;

public class WindowDifference_impl implements WindowMetric {

	@Override
	public double score(int[] gold, int[] silver) {
		return score(gold, silver, computeWindowSize(gold));
	}

	@Override
	public double score(int[] gold, int[] silver, int windowSize) {
		int[] pos_gold = SegmentationUtil.massesToPositions(gold);
		int[] pos_silver = SegmentationUtil.massesToPositions(silver);

		if (pos_gold.length != pos_silver.length)
			throw new RuntimeException("");

		int sum_differences = 0;
		int measurements = 0;
		for (int i = 0; i < (pos_gold.length - windowSize); i++) {
			int upper = i + windowSize;

			// if upper segment number is 5 and lower is 3, 2 boundaries are in
			// this window
			int g_boundaries = pos_gold[upper] - pos_gold[i];
			int s_boundaries = pos_silver[upper] - pos_silver[i];

			if (g_boundaries != s_boundaries)
				sum_differences++;
			measurements++;
		}
		if (measurements == 0)
			return 0.0;
		return sum_differences / (double) measurements;
	}

	@Override
	public int computeWindowSize(int[] goldMass) {
		int sum = 0;
		for (int i : goldMass)
			sum += i;
		int r = (int) Math.round((sum / (double) (2 * goldMass.length)));
		return (r > 1 ? r : 2);
	}

}
