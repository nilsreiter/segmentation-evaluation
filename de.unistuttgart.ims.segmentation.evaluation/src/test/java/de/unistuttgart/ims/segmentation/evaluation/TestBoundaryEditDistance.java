package de.unistuttgart.ims.segmentation.evaluation;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.google.common.collect.Sets;

import de.unistuttgart.ims.segmentation.evaluation.impl.BoundaryEditDistance;
import de.unistuttgart.ims.segmentation.evaluation.impl.FournierUtil;

public class TestBoundaryEditDistance {
	BoundaryEditDistance<Integer> bs = new BoundaryEditDistance<Integer>();

	@Test
	public void testRegular() {
		List<Set<Integer>> gold = Arrays.asList(Sets.newHashSet(1), Sets.newHashSet(), Sets.newHashSet(),
				Sets.newHashSet(), Sets.newHashSet(2));
		List<Set<Integer>> silver = Arrays.asList(Sets.newHashSet(1), Sets.newHashSet(), Sets.newHashSet(),
				Sets.newHashSet(), Sets.newHashSet(1));

		bs.score(gold, gold, 2);
		assertEquals(0, bs.getNumberOfSubstitutions());
		assertEquals(0, bs.getNumberOfAdditions());
		assertEquals(0, bs.getNumberOfTranspositions());

		bs.score(gold, silver, 2);
		assertEquals(1, bs.getNumberOfSubstitutions());
		assertEquals(0, bs.getNumberOfAdditions());
		assertEquals(0, bs.getNumberOfTranspositions());

		silver = FournierUtil.array2SetList(1, null, null, 2, 1);
		bs.score(gold, silver, 2);
		// System.err.println(bs.toString());
		assertEquals(0, bs.getNumberOfSubstitutions());
		assertEquals(1, bs.getNumberOfAdditions());
		assertEquals(1, bs.getNumberOfTranspositions());

		silver = FournierUtil.array2SetList(1, null, null, null, null);
		bs.score(gold, silver, 2);
		assertEquals(0, bs.getNumberOfSubstitutions());
		assertEquals(1, bs.getNumberOfAdditions());
		assertEquals(0, bs.getNumberOfTranspositions());

		silver = FournierUtil.array2SetList(new Integer[] { 1, 2 }, null, null, null, null);
		bs.score(gold, silver, 2);
		assertEquals(0, bs.getNumberOfSubstitutions());
		assertEquals(2, bs.getNumberOfAdditions());
		assertEquals(0, bs.getNumberOfTranspositions());

		silver = FournierUtil.array2SetList(new Integer[] { 1, 2 }, null, null, new Integer[] { 2, 3 }, null);
		bs.score(gold, silver, 2);
		assertEquals(0, bs.getNumberOfSubstitutions());
		assertEquals(2, bs.getNumberOfAdditions());
		assertEquals(1, bs.getNumberOfTranspositions());

	}

	@Test
	public void testExp() {
		List<Set<Integer>> gold = Arrays.asList(Sets.newHashSet(1), Sets.newHashSet(), Sets.newHashSet(0, 2),
				Sets.newHashSet(), Sets.newHashSet());
		List<Set<Integer>> silver = Arrays.asList(Sets.newHashSet(1), Sets.newHashSet(), Sets.newHashSet(1, 5),
				Sets.newHashSet(), Sets.newHashSet());
		System.err.println(gold);
		System.err.println(silver);
		bs.score(gold, silver, 2);
		System.err.println(bs.toString());

	}

}
