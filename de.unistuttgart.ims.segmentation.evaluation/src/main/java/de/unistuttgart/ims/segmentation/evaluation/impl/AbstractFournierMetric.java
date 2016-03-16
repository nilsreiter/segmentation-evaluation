package de.unistuttgart.ims.segmentation.evaluation.impl;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import de.unistuttgart.ims.commons.Counter;
import de.unistuttgart.ims.segmentation.evaluation.FournierMetric;

public abstract class AbstractFournierMetric implements FournierMetric {

	int windowSize = 2;
	protected TranspositionWeightingFunction tpFunction = new TranspositionWeightingFunction() {

		public double getWeight(Transposition tp) {
			return tp.getMass();
		}
	};

	public int getWindowSize() {
		return windowSize;
	}

	public void setWindowSize(int windowSize) {
		this.windowSize = windowSize;
	}

	public void setTranspositionPenaltyFunction(TranspositionWeightingFunction tpf) {
		tpFunction = tpf;
	}

	public TranspositionWeightingFunction getTranspositionPenaltyFunction() {
		return tpFunction;
	}

	@Deprecated
	public List<Integer> getPotentialSubstitions(boolean[][] boundaries) {
		final List<Integer> substOperations = new LinkedList<Integer>();
		for (int i = 0; i < boundaries[0].length; i++) {
			if (boundaries[0][i] ^ boundaries[1][i]) {
				substOperations.add((boundaries[0][i] ? i : -i));
			}
		}
		return substOperations;
	}

	public List<Substitution> getPotentialSubstitions2(boolean[][] boundaries) {
		final List<Substitution> substOperations = new LinkedList<Substitution>();
		for (int i = 0; i < boundaries[0].length; i++) {
			if (boundaries[0][i] ^ boundaries[1][i]) {
				substOperations.add(new Substitution(i, (boundaries[0][i] ? 0 : 1)));
			}
		}
		return substOperations;
	}

	@Deprecated
	public Counter<Transposition> getTranspositions(List<Integer> substOperations) {
		final Counter<Transposition> potTranspositions = new Counter<Transposition>();

		// finding possible transpositions
		final Iterator<Integer> iterator = substOperations.iterator();
		while (iterator.hasNext()) {
			final int j = iterator.next();
			if (iterator.hasNext()) {
				final int i = iterator.next();
				if (((Math.abs(i) - Math.abs(j)) < getWindowSize()) && ((i * j) <= 0)) {
					potTranspositions.add(new Transposition_impl(Math.abs(j), Math.abs(i)), i - j);
				}
			}

		}

		return potTranspositions;
	}

	public Counter<Transposition> getTranspositions2(List<Substitution> substOperations) {
		final Counter<Transposition> potTranspositions = new Counter<Transposition>();

		// finding possible transpositions
		final Iterator<Substitution> iterator = substOperations.iterator();
		while (iterator.hasNext()) {
			final Substitution j = iterator.next();
			if (iterator.hasNext()) {
				final Substitution i = iterator.next();
				if (((i.getPosition() - j.getPosition()) < getWindowSize()) && (i.getSequence() != j.getSequence())) {
					potTranspositions.add(new Transposition_impl(j.getPosition(), i.getPosition()),
							i.getPosition() - j.getPosition());
				}
			}

		}

		return potTranspositions;
	}

	public boolean[][] getBoundaries(int[] ms1, int[] ms2) {
		final boolean[][] boundaries = new boolean[2][];
		boundaries[0] = SegmentationUtil.getBoundaryString(ms1);
		boundaries[1] = SegmentationUtil.getBoundaryString(ms2);
		return boundaries;
	}

	public class Transposition_impl implements Transposition {
		int source, target;

		public Transposition_impl(int s1, int s2) {
			source = s1;
			target = s2;
		}

		public int getMass() {
			return Math.max(source, target) - Math.min(target, source);
		}

		@Override
		public String toString() {
			return "(" + source + "," + target + ")";
		}

		public int getSource() {
			return source;
		}

		public int getTarget() {
			return target;
		}
	}

	public class Substitution {
		int position;
		int sequence;

		public Substitution(int position, int sequence) {
			super();
			this.position = position;
			this.sequence = sequence;
		}

		public int getPosition() {
			return position;
		}

		public int getSequence() {
			return sequence;
		}

	}
}
