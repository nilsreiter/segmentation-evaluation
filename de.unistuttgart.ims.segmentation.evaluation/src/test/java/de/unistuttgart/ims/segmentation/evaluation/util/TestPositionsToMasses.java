package de.unistuttgart.ims.segmentation.evaluation.util;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

public class TestPositionsToMasses {
	@Test
	public void testPositionsToMasses() {
		int[] massString;
		massString = SegmentationUtil.positionsToMasses(Arrays.asList("a", "a", "a", "b"));
		assertEquals(2, massString.length);
		assertEquals(3, massString[0]);
		assertEquals(1, massString[1]);

		massString = SegmentationUtil.positionsToMasses(Arrays.asList("a", "b"));
		assertEquals(2, massString.length);
		assertEquals(1, massString[0]);
		assertEquals(1, massString[1]);

		massString = SegmentationUtil.positionsToMasses(Arrays.asList("a", "a"));
		assertEquals(1, massString.length);
		assertEquals(2, massString[0]);

	}
}
