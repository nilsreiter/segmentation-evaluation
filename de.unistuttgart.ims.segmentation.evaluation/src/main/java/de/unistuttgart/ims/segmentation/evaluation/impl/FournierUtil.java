package de.unistuttgart.ims.segmentation.evaluation.impl;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import de.unistuttgart.ims.commons.Counter;

public class FournierUtil {

	public static List<Substitution> getPotentialSubstitions2(boolean[][] boundaries) {
		final List<Substitution> substOperations = new LinkedList<Substitution>();
		for (int i = 0; i < boundaries[0].length; i++) {
			if (boundaries[0][i] ^ boundaries[1][i]) {
				substOperations.add(new Substitution(i, (boundaries[0][i] ? 0 : 1)));
			}
		}
		return substOperations;
	}

	public static Counter<Transposition> getTranspositions2(List<Substitution> substOperations, int windowSize) {
		final Counter<Transposition> potTranspositions = new Counter<Transposition>();

		// finding possible transpositions
		final Iterator<Substitution> iterator = substOperations.iterator();
		while (iterator.hasNext()) {
			final Substitution j = iterator.next();
			if (iterator.hasNext()) {
				final Substitution i = iterator.next();
				if (((i.getPosition() - j.getPosition()) < windowSize) && (i.getSequence() != j.getSequence())) {
					potTranspositions.add(new Transposition(j.getPosition(), i.getPosition()),
							i.getPosition() - j.getPosition());
				}
			}

		}

		return potTranspositions;
	}

	public static class Transposition {
		int source, target;

		public Transposition(int s1, int s2) {
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

	public static class Substitution {
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
