package de.unistuttgart.ims.segmentation.evaluation;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.unistuttgart.ims.segmentation.evaluation.impl.BoundaryEditDistance;
import de.unistuttgart.ims.segmentation.evaluation.impl.ListBoundarySetsString;

public class TestBoundaryEditDistance {
	BoundaryEditDistance<Integer> bs = new BoundaryEditDistance<Integer>();

	@Test
	public void testRegular() {
		BoundarySetsString<Integer> gold = ListBoundarySetsString.createBoundarySetsString(new Integer(1), null, null,
				null, new Integer(2));
		BoundarySetsString<Integer> silver = ListBoundarySetsString.createBoundarySetsString(new Integer(1), null, null,
				null, new Integer(1));

		bs.score(gold, gold, 2);
		assertEquals(0, bs.getNumberOfSubstitutions());
		assertEquals(0, bs.getNumberOfAdditions());
		assertEquals(0, bs.getNumberOfTranspositions());

		bs.score(gold, silver, 2);
		assertEquals(1, bs.getNumberOfSubstitutions());
		assertEquals(0, bs.getNumberOfAdditions());
		assertEquals(0, bs.getNumberOfTranspositions());

		silver = ListBoundarySetsString.createBoundarySetsString(1, null, null, 2, 1);
		bs.score(gold, silver, 2);
		// System.err.println(bs.toString());
		assertEquals(0, bs.getNumberOfSubstitutions());
		assertEquals(1, bs.getNumberOfAdditions());
		assertEquals(1, bs.getNumberOfTranspositions());

		silver = ListBoundarySetsString.createBoundarySetsString(1, null, null, null, null);
		bs.score(gold, silver, 2);
		assertEquals(0, bs.getNumberOfSubstitutions());
		assertEquals(1, bs.getNumberOfAdditions());
		assertEquals(0, bs.getNumberOfTranspositions());

		silver = ListBoundarySetsString.createBoundarySetsString(new Integer[] { 1, 2 }, null, null, null, null);
		bs.score(gold, silver, 2);
		assertEquals(0, bs.getNumberOfSubstitutions());
		assertEquals(2, bs.getNumberOfAdditions());
		assertEquals(0, bs.getNumberOfTranspositions());

		silver = ListBoundarySetsString.createBoundarySetsString(new Integer[] { 1, 2 }, null, null,
				new Integer[] { 2, 3 }, null);
		bs.score(gold, silver, 2);
		assertEquals(0, bs.getNumberOfSubstitutions());
		assertEquals(2, bs.getNumberOfAdditions());
		assertEquals(1, bs.getNumberOfTranspositions());

	}

	public void testExp() {
		BoundarySetsString<Integer> gold = ListBoundarySetsString.createBoundarySetsString(new Integer[] { 1 }, null,
				new Integer[] { 0, 2 }, null, null);
		BoundarySetsString<Integer> silver = ListBoundarySetsString.createBoundarySetsString(new Integer[] { 1 }, null,
				new Integer[] { 1, 5 }, null, null);

		// List<Set<Integer>> gold = Arrays.asList(Sets.newHashSet(1),
		// Sets.newHashSet(), Sets.newHashSet(0, 2),
		// Sets.newHashSet(), Sets.newHashSet());
		// List<Set<Integer>> silver = Arrays.asList(Sets.newHashSet(1),
		// Sets.newHashSet(), Sets.newHashSet(1, 5),
		// Sets.newHashSet(), Sets.newHashSet());
		// System.err.println(gold);
		// System.err.println(silver);
		bs.score(gold, silver, 2);
		// System.err.println(bs.toString());

	}

}
