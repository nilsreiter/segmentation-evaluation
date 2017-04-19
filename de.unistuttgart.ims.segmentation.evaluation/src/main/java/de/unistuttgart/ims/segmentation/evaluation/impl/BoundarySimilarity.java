package de.unistuttgart.ims.segmentation.evaluation.impl;

import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;

import org.apache.commons.collections4.MultiValuedMap;

import de.unistuttgart.ims.segmentation.evaluation.BoundarySetsMetric;

public class BoundarySimilarity<T> implements BoundarySetsMetric<T> {

	BiFunction<T, T, Double> distanceFunction = new BiFunction<T, T, Double>() {

		@Override
		public Double apply(T t, T u) {
			return (double) Math.abs(t.hashCode() - u.hashCode());
		}

	};
	double maximalDistance = Integer.MAX_VALUE;

	@Override
	public double score(List<Set<T>> gold, List<Set<T>> silver, int window) {
		BoundaryEditDistance<T> bed = new BoundaryEditDistance<T>();
		bed.score(gold, silver, window);

		double num = bed.getNumberOfAdditions() + ws_ordinal(bed.getSubsitutions())
				+ w_span(bed.getTranspositions(), window);
		double den = bed.getNumberOfAdditions() + bed.getNumberOfSubstitutions() + bed.getNumberOfTranspositions()
				+ bed.getNumberOfMatches();

		return 1 - (num / den);
	}

	public double ws_ordinal(MultiValuedMap<Integer, Substitution<T>> s) {
		double ws = 1.0;
		double sum = 0.0;
		for (Integer i : s.keySet()) {
			for (Substitution<T> t : s.get(i)) {
				double num = distanceFunction.apply(t.getFrom(), t.getTo());
				double den = maximalDistance;
				sum += (ws + (num / den));
			}
		}
		return sum;
	}

	public double w_span(Set<Transposition<T>> tp, int window) {
		double sum = 0.0;
		double wt = 1.0;
		for (Transposition<T> t : tp) {
			sum += (wt + (Math.abs(t.from - t.to) / (double) window));
		}
		return sum;
	}

	public BiFunction<T, T, Double> getDistanceFunction() {
		return distanceFunction;
	}

	public void setDistanceFunction(BiFunction<T, T, Double> distanceFunction) {
		this.distanceFunction = distanceFunction;
	}

	public double getMaximalDistance() {
		return maximalDistance;
	}

	public void setMaximalDistance(double maximalDistance) {
		this.maximalDistance = maximalDistance;
	}

}
