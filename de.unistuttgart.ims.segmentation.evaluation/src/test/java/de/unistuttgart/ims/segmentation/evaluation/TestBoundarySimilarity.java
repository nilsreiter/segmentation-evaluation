package de.unistuttgart.ims.segmentation.evaluation;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestBoundarySimilarity {

	BoundarySimilarity metric = MetricFactory.getMetric(BoundarySimilarity.class);

	@Test
	public void testFN() {
		assertEquals(0.666, metric.score(new int[] { 2, 3, 6 }, new int[] { 2, 3, 3, 3 }, 2), 1e-2);
	}

	@Test
	public void testFP() {
		assertEquals(0.5, metric.score(new int[] { 2, 3, 6 }, new int[] { 5, 6 }, 2), 1e-2);
	}

	@Test
	public void testNearMiss() {
		assertEquals(0.75, metric.score(new int[] { 2, 3, 6 }, new int[] { 2, 2, 7 }, 2), 1e-2);
	}

	@Test
	public void testIdentity() {
		assertEquals(1.0, metric.score(new int[] { 2, 3, 6 }, new int[] { 2, 3, 6 }, 2), 1e-2);

	}

}
