package de.unistuttgart.ims.segmentation.evaluation;

import static org.junit.Assert.assertEquals;

import java.util.function.BiFunction;

import org.junit.Before;
import org.junit.Test;

import de.unistuttgart.ims.segmentation.evaluation.impl.BoundarySimilarity;
import de.unistuttgart.ims.segmentation.evaluation.impl.FournierUtil;

public class TestBoundarySimilarity {

	BoundarySimilarity<Integer> metric = new BoundarySimilarity<Integer>();

	@Before
	public void setUp() {
		metric.setDistanceFunction(new BiFunction<Integer, Integer, Double>() {

			@Override
			public Double apply(Integer t, Integer u) {
				return (double) Math.abs(t - u);
			}

		});
		metric.setMaximalDistance(1.0);
	}

	@Test
	public void testRegular() {
		double d;
		d = metric.score(FournierUtil.array2SetList(1, null, null, 2), FournierUtil.array2SetList(1, null, null, 2), 1);
		assertEquals(1.0, d, 1e-3);

		d = metric.score(FournierUtil.array2SetList(1, null, null, 2), FournierUtil.array2SetList(1, 2, null, null), 2);
		assertEquals(0.25, d, 1e-3);
	}

	@Test
	public void testFN() {
		// assertEquals(0.666, metric.score(new int[] { 2, 3, 6 }, new int[] {
		// 2, 3, 3, 3 }, 2), 1e-2);
	}

	@Test
	public void testFP() {
		// assertEquals(0.5, metric.score(new int[] { 2, 3, 6 }, new int[] { 5,
		// 6 }, 2), 1e-2);
	}

	@Test
	public void testNearMiss() {
		// assertEquals(0.75, metric.score(new int[] { 2, 3, 6 }, new int[] { 2,
		// 2, 7 }, 2), 1e-2);
	}

	@Test
	public void testIdentity() {
		// assertEquals(1.0, metric.score(new int[] { 2, 3, 6 }, new int[] { 2,
		// 3, 6 }, 2), 1e-2);

	}

}
