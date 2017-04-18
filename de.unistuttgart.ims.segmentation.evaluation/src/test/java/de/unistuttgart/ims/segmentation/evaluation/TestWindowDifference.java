package de.unistuttgart.ims.segmentation.evaluation;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.unistuttgart.ims.segmentation.evaluation.impl.WindowDifference_impl;

public class TestWindowDifference {
	static double range = 1e-4;

	@Test
	public void testWindowDifference() {
		int[] a, b;
		WindowDifference_impl pk = new WindowDifference_impl();

		a = new int[] { 4, 3, 1 };
		b = new int[] { 4, 3, 1 };
		assertEquals(0, pk.score(a, b), range);

		// none vs. some
		a = new int[] { 13 };
		b = new int[] { 4, 4, 5 };
		assertEquals(1.0, pk.score(a, b), range);
		assertEquals(0.3636, pk.score(b, a), range);

		// all vs. some
		a = new int[] { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
		b = new int[] { 4, 4, 5 };
		assertEquals(1.0, pk.score(a, b), range);
		assertEquals(1.0, pk.score(b, a), range);

		a = new int[] { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
		b = new int[] { 13 };
		assertEquals(1.0, pk.score(a, b), range);
		assertEquals(1.0, pk.score(b, a), range);

		a = new int[] { 4, 4, 5 };
		b = new int[] { 5, 1, 2, 5 };
		assertEquals(0.2727, pk.score(a, b), range);
		assertEquals(0.2727, pk.score(b, a), range);

		a = new int[] { 5, 3, 5 };
		b = new int[] { 5, 1, 2, 5 };
		assertEquals(0.1818, pk.score(a, b), range);
		assertEquals(0.1818, pk.score(b, a), range);

	}

	@Test
	public void testComputeWindowSize() {
		WindowDifference_impl pk = new WindowDifference_impl();
		assertEquals(2, pk.computeWindowSize(new int[] { 2, 8, 2, 4, 2, 3 }));
		assertEquals(2, pk.computeWindowSize(new int[] { 4, 4, 5 }));
	}
}
