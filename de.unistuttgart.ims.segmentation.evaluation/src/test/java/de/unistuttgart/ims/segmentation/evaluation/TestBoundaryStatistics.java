package de.unistuttgart.ims.segmentation.evaluation;

import org.junit.Test;

import de.unistuttgart.ims.segmentation.evaluation.impl.BoundaryStatistics;
import de.unistuttgart.ims.segmentation.evaluation.util.SegmentationUtil;

public class TestBoundaryStatistics {
	@Test
	public void testBoundaryStatistics() {
		int[] gold = new int[] { 3, 5, 2 };
		int[] silver = new int[] { 2, 6, 2 };

		BoundaryStatistics bs = new BoundaryStatistics(SegmentationUtil.getBoundaries(gold, silver), 2);
		System.out.println(bs);
	}
}
