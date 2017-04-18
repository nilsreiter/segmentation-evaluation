package de.unistuttgart.ims.segmentation.evaluation.impl;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

import org.apache.commons.collections4.Bag;

import com.google.common.base.Function;

import de.unistuttgart.ims.segmentation.evaluation.BoundarySimilarity;
import de.unistuttgart.ims.segmentation.evaluation.impl.FournierUtil.Substitution;
import de.unistuttgart.ims.segmentation.evaluation.impl.FournierUtil.Transposition;
import de.unistuttgart.ims.segmentation.evaluation.util.SegmentationUtil;

public class BoundarySimilarity_impl implements BoundarySimilarity {

	protected Function<Transposition, Double> tpFunction = new Function<Transposition, Double>() {

		@Override
		public Double apply(Transposition arg0) {
			return new Double(arg0.getMass());
		}
	};

	@Override
	public double score(int[] gold, int[] silver, int windowSize) {
		final boolean[][] b = SegmentationUtil.getBoundaries(gold, silver);

		// finding (number of) matches
		int m = 0;
		for (int i = 0; i < b[0].length; i++) {
			m += ((b[0][i] == b[1][i]) && b[0][i] ? 1 : 0);
		}

		// finding possible substitution operations
		final List<Substitution> substOperations = FournierUtil.getPotentialSubstitions2(b);

		// finding possible transposition operations
		final Bag<Transposition> potTranspositions = FournierUtil.getTranspositions2(substOperations, windowSize);

		for (final Transposition tp : potTranspositions) {
			substOperations.removeIf(new Predicate<Substitution>() {

				@Override
				public boolean test(Substitution t) {
					return ((tp.getTarget() == t.getPosition()) || (tp.getSource() == t.getPosition()));
				}
			});
		}

		final double num = substOperations.size() + getTranspositionsWeight(potTranspositions, windowSize);
		final double denom = substOperations.size() + potTranspositions.size() + m;

		return 1 - (num / denom);
	}

	@Override
	public double score(int[] gold, int[] silver) {
		return score(gold, silver, 1);
	}

	protected double getTranspositionsWeight(Collection<Transposition> trans, int windowSize) {
		double d = 0.0;
		for (final Transposition tp : trans) {
			d += tpFunction.apply(tp);
		}
		return d / windowSize;
	}

	@Override
	public int computeWindowSize(int[] goldMass) {
		return 1;
	}

}
