package de.unistuttgart.ims.segmentation.evaluation.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestMassesToPositions {

	@Test
	public void testMassesToPositions() {
		int[] pos;
		pos = SegmentationUtil.massesToPositions(new int[] { 2, 1, 5 });
		assertEquals(8, pos.length);
		assertEquals(0, pos[0]);
		assertEquals(0, pos[1]);
		assertEquals(1, pos[2]);
		assertEquals(2, pos[3]);
	}
}
