package de.unistuttgart.ims.segmentation.evaluation;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestPk {
	static double range = 1e-4;

	@Test
	public void testPk() {
		int[] a, b;
		Pk pk = MetricFactory.getMetric(Pk.class);

		a = new int[] { 4, 3, 1 };
		b = new int[] { 4, 3, 1 };
		assertEquals(0, pk.score(a, b, true), range);

		a = new int[] { 13 };
		b = new int[] { 4, 4, 5 };
		assertEquals(1.0, pk.score(a, b, true), range);
		assertEquals(0.3636, pk.score(b, a, true), range);

		a = new int[] { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
		b = new int[] { 4, 4, 5 };
		assertEquals(0.6363, pk.score(a, b, true), range);
		assertEquals(0.6363, pk.score(b, a, true), range);

		a = new int[] { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
		b = new int[] { 13 };
		assertEquals(1.0, pk.score(a, b, true), range);
		assertEquals(1.0, pk.score(b, a, true), range);

		a = new int[] { 4, 4, 5 };
		b = new int[] { 5, 1, 2, 5 };
		assertEquals(0.2727, pk.score(a, b, true), range);
		assertEquals(0.2727, pk.score(b, a, true), range);

		a = new int[] { 5, 3, 5 };
		b = new int[] { 5, 1, 2, 5 };
		assertEquals(0.0909, pk.score(a, b, true), range);
		assertEquals(0.0909, pk.score(b, a, true), range);

	}

	@Test
	public void testComputeWindowSize() {
		Pk pk = MetricFactory.getMetric(Pk.class);
		assertEquals(2, pk.computeWindowSize(new int[] { 2, 8, 2, 4, 2, 3 }));
		assertEquals(2, pk.computeWindowSize(new int[] { 4, 4, 5 }));
	}
}
