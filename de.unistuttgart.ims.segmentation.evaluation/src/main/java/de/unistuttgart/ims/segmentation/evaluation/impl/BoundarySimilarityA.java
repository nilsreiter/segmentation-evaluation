package de.unistuttgart.ims.segmentation.evaluation.impl;

import java.util.Set;
import java.util.function.BiFunction;
import java.util.logging.Logger;

import org.apache.commons.collections4.MultiValuedMap;

import de.unistuttgart.ims.segmentation.evaluation.BoundarySetsMetric;
import de.unistuttgart.ims.segmentation.evaluation.BoundarySetsString;

public class BoundarySimilarityA<T> implements BoundarySetsMetric<T> {

	Logger logger = Logger.getLogger(BoundarySimilarityA.class.getName());

	BiFunction<T, T, Double> distanceFunction = new BiFunction<T, T, Double>() {

		@Override
		public Double apply(T t, T u) {
			return (double) Math.abs(t.hashCode() - u.hashCode());
		}

	};
	double maximalDistance = Integer.MAX_VALUE;

	@Override
	public double score(BoundarySetsString<T> gold, BoundarySetsString<T> silver, int window) {
		BoundaryEditDistance<T> bed = new BoundaryEditDistance<T>();
		bed.score(gold, silver, window);

		logger.info("additions=" + bed.getNumberOfAdditions());
		logger.info("ws_ordinal=" + ws_ordinal(bed.getSubsitutions()));
		logger.info("w_span = " + w_span(bed.getTranspositions(), window));
		double num = bed.getNumberOfAdditions() + ws_ordinal(bed.getSubsitutions())
				+ w_span(bed.getTranspositions(), window);
		double den = gold.numberOfBoundaries() + silver.numberOfBoundaries();

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
			sum += (wt + (Math.abs(t.from - t.to) / ((double) window - 1)));
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
