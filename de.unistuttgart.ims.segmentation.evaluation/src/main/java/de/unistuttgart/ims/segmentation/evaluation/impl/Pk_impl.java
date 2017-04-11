package de.unistuttgart.ims.segmentation.evaluation.impl;

import de.unistuttgart.ims.segmentation.evaluation.Pk;
import de.unistuttgart.ims.segmentation.evaluation.util.SegmentationUtil;

public class Pk_impl implements Pk {

	@Override
	public double score(int[] gold, int[] silver) {
		return score(gold, silver, computeWindowSize(gold));
	}

	@Override
	public int computeWindowSize(int[] goldMass) {
		int sum = 0;
		for (int i : goldMass)
			sum += i;
		int r = (int) Math.round((sum / (double) (2 * goldMass.length)));
		return (r > 1 ? r : 2);
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
			boolean agree_gold = (pos_gold[i] == pos_gold[upper]);
			boolean agree_silver = (pos_silver[i] == pos_silver[upper]);

			if (agree_gold != agree_silver) {
				sum_differences++;
			}
			measurements++;
		}

		if (measurements == 0)
			return 0.0;
		return sum_differences / (double) measurements;
	}

}
