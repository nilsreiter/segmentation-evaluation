package de.unistuttgart.ims.segmentation.evaluation.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.iterators.PermutationIterator;
import org.apache.commons.collections4.multimap.HashSetValuedHashMap;
import org.apache.commons.math3.util.Pair;

import com.google.common.collect.Sets;

import de.unistuttgart.ims.segmentation.evaluation.BoundarySetsMetric;

public class BoundaryEditDistance<T> implements BoundarySetsMetric<T> {
	public class Transposition {
		public Transposition(int from, int to, T type) {
			super();
			this.from = from;
			this.to = to;
			this.type = type;
		}

		int from, to;
		T type;

		@Override
		public boolean equals(Object o) {
			if (!(o instanceof BoundaryEditDistance.Transposition))
				return false;
			@SuppressWarnings("unchecked")
			BoundaryEditDistance<T>.Transposition tp = (BoundaryEditDistance<T>.Transposition) o;
			return (tp.to == this.to && tp.from == this.from && tp.type.equals(this.type));
		}

		@Override
		public int hashCode() {
			return Objects.hash(from, to, type);

		}

		@Override
		public String toString() {
			StringBuilder b = new StringBuilder();
			b.append("(").append(from).append(",").append(to).append(",").append(type).append(')');
			return b.toString();
		}
	}

	List<Set<T>> gold, silver;
	int windowSize;

	Set<Integer> matches = new HashSet<Integer>();
	List<Set<T>> deletions, additions, substitutions;
	Map<Integer, Integer> mismatches = new HashMap<Integer, Integer>();
	Set<Transposition> transpositions = new HashSet<Transposition>();
	MultiValuedMap<Integer, Transposition> tpIndex = new HashSetValuedHashMap<Integer, Transposition>();
	MultiValuedMap<Integer, T> final_additions = new HashSetValuedHashMap<Integer, T>();
	MultiValuedMap<Integer, T> final_subsitutions = new HashSetValuedHashMap<Integer, T>();

	protected void calculate() {
		deletions = new ArrayList<Set<T>>(gold.size());
		additions = new ArrayList<Set<T>>(gold.size());
		substitutions = new ArrayList<Set<T>>(gold.size());

		// calculate optional set edits
		for (int i = 0; i < gold.size(); i++) {
			Set<T> gold_set = gold.get(i);
			Set<T> silver_set = silver.get(i);
			additions.add(i, Sets.newHashSet(Sets.difference(gold_set, silver_set)));
			deletions.add(i, Sets.newHashSet(Sets.difference(silver_set, gold_set)));
			substitutions.add(i, Sets.newHashSet(Sets.symmetricDifference(gold_set, silver_set)));
		}

		// calculate transpositions
		for (int i = 0; i < windowSize; i++) {
			for (int lower = 0; lower < gold.size() - i; lower++) {
				int upper = lower + i;
				Set<T> g_lower = gold.get(lower), g_upper = gold.get(upper), s_lower = silver.get(lower),
						s_upper = silver.get(upper);
				Set<T> d_g = Sets.symmetricDifference(g_lower, g_upper),
						d_s = Sets.symmetricDifference(s_lower, s_upper),
						d_lower = Sets.symmetricDifference(g_lower, s_lower),
						d_upper = Sets.symmetricDifference(g_upper, s_upper);
				Set<T> potentialTranspositions = Sets.intersection(d_g, d_s);
				potentialTranspositions = Sets.intersection(potentialTranspositions, d_lower);
				potentialTranspositions = Sets.intersection(potentialTranspositions, d_upper);
				for (T d : potentialTranspositions) {
					Transposition t = new Transposition(lower, upper, d);
					if (!overlaps(lower, upper, t) && !has_substitutions(lower, upper, d)) {
						transpositions.add(t);
						tpIndex.put(lower, t);
						tpIndex.put(upper, t);
						additions.get(lower).remove(d);
						deletions.get(lower).remove(d);
						substitutions.get(lower).remove(d);
						additions.get(upper).remove(d);
						deletions.get(upper).remove(d);
						substitutions.get(upper).remove(d);
					}
				}
			}
		}

		for (int i = 0; i < additions.size(); i++) {
			if (!additions.get(i).isEmpty() || !deletions.get(i).isEmpty() || !substitutions.get(i).isEmpty()) {
				List<Set<T>> ass = additions_substitutions_set(substitutions.get(i), additions.get(i),
						deletions.get(i));
				final_additions.putAll(i, ass.get(0));
				final_subsitutions.putAll(i, ass.get(1));
			}

		}

	}

	public int[] additions_substitutions(Set<T> d, Set<T> a, Set<T> b) {
		int add = Math.abs(a.size() - d.size());
		int sub = (int) Math.ceil((d.size() - add) / 2.0);
		return new int[] { add, sub };
	}

	public List<Set<T>> additions_substitutions_set(Set<T> d, Set<T> a, Set<T> b) {
		int delta = -1;
		Set<Pair<Collection<T>, Collection<T>>> Osub = new HashSet<Pair<Collection<T>, Collection<T>>>();
		Set<T> de = new HashSet<T>();
		Iterator<List<T>> a_iter = new PermutationIterator<T>(a);
		while (a_iter.hasNext()) {
			Iterator<List<T>> b_iter = new PermutationIterator<T>(b);
			List<T> pa = a_iter.next();
			while (b_iter.hasNext()) {
				List<T> pb = b_iter.next();
				int delta_p = 0;
				for (T ai : pa) {
					for (T bi : pb) {
						delta_p += Math.abs(ai.hashCode() - bi.hashCode());
						if (delta_p < delta || delta == -1) {
							delta = delta_p;
							Osub.add(new Pair<Collection<T>, Collection<T>>(pa, pb));
						}
					}
				}
			}
		}
		for (Pair<Collection<T>, Collection<T>> p : Osub) {
			de.addAll(p.getFirst());
			de.addAll(p.getSecond());
		}
		Set<T> ae = d;
		ae = Sets.difference(ae, de);

		// System.out.println(ae);
		// System.out.println(de);

		return Arrays.asList(ae, de);
	}

	protected boolean has_substitutions(int lower, int upper, T t) {
		if (substitutions.get(lower).contains(t) && substitutions.get(upper).contains(t)) {
			if (additions_substitutions(substitutions.get(lower), additions.get(lower), deletions.get(lower))[1] > 0
					&& additions_substitutions(substitutions.get(upper), additions.get(upper),
							deletions.get(upper))[1] > 0) {
				return true;
			}
		}
		return false;
	}

	protected boolean overlaps(int position, T type) {
		for (Transposition tp : tpIndex.get(position)) {
			if (tp.type.equals(type))
				return true;
		}
		return false;
	}

	protected boolean overlaps(int i, int j, Transposition tp) {
		return overlaps(i, tp.type) || overlaps(j, tp.type);
	}

	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		b.append("transpositions = ").append(transpositions).append("\n");
		b.append("additions = ").append(final_additions).append("\n");
		b.append("sub = ").append(this.final_subsitutions).append("\n");
		b.append("edits = ").append(this.getNumberOfEdits());
		return b.toString();
	}

	static class MissError {
		int sequence, position, type;

		public MissError(int sequence, int position, int type) {
			super();
			this.sequence = sequence;
			this.position = position;
			this.type = type;
		}

	}

	public MultiValuedMap<Integer, T> getAdditions() {
		return final_additions;
	}

	public MultiValuedMap<Integer, T> getSubsitutions() {
		return final_subsitutions;
	}

	public int getNumberOfAdditions() {
		return this.getAdditions().keySet().size();
	}

	public int getNumberOfSubstitutions() {
		return this.getSubsitutions().keySet().size();
	}

	public int getNumberOfTranspositions() {
		return this.transpositions.size();
	}

	public int getNumberOfEdits() {
		return this.getNumberOfAdditions() + this.getNumberOfSubstitutions() + this.getNumberOfTranspositions();
	}

	public static <T> int distance(List<Set<T>> a, List<Set<T>> b, int w) {
		BoundaryEditDistance<T> bes = new BoundaryEditDistance<T>();
		return (int) bes.score(a, b, w);
	}

	@Override
	public double score(List<Set<T>> gold, List<Set<T>> silver, int windowSize) {
		this.init();
		this.gold = gold;
		this.silver = silver;
		this.windowSize = windowSize;

		calculate();
		return getNumberOfEdits();
	}

	private void init() {
		matches = new HashSet<Integer>();
		mismatches = new HashMap<Integer, Integer>();
		transpositions = new HashSet<Transposition>();
		tpIndex = new HashSetValuedHashMap<Integer, Transposition>();
		final_additions = new HashSetValuedHashMap<Integer, T>();
		final_subsitutions = new HashSetValuedHashMap<Integer, T>();
	}
}
