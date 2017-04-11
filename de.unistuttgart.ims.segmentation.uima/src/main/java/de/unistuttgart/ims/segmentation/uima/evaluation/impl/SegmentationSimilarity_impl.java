package de.unistuttgart.ims.segmentation.uima.evaluation.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;

import de.unistuttgart.ims.commons.Counter;
import de.unistuttgart.ims.segmentation.type.SegmentationUnit;
import de.unistuttgart.ims.segmentation.uima.evaluation.SegmentationSimilarity;

@Deprecated
public class SegmentationSimilarity_impl extends AbstractFournierMetric implements SegmentationSimilarity {
	Class<? extends Annotation> boundaryType;

	public SegmentationSimilarity_impl(Class<? extends Annotation> annotationType) {
		boundaryType = annotationType;
	}

	public boolean init(JCas gold) {
		return true;
	}

	public Map<String, Double> scores(JCas jcas1, JCas jcas2) {
		final HashMap<String, Double> map = new HashMap<String, Double>();
		map.put(getClass().getSimpleName(), score(jcas1, jcas2));
		return map;
	}

	public double getEditDistance(JCas jcas1, JCas jcas2) {

		final int length1 = JCasUtil.select(jcas1, SegmentationUnit.class).size();
		final int length2 = JCasUtil.select(jcas2, SegmentationUnit.class).size();
		if (length1 != length2) {
			throw new RuntimeException("Numbers of segmentation units differ.");
		}

		final int[] massString1 = SegmentationUtil.getMassTuple(jcas1, boundaryType);
		final int[] massString2 = SegmentationUtil.getMassTuple(jcas2, boundaryType);

		final boolean[][] boundaries = getBoundaries(massString1, massString2);

		// finding possible substitution operations
		final List<Substitution> substOperations = getPotentialSubstitions2(boundaries);

		// finding possible transposition operations
		final Counter<Transposition> potTranspositions = getTranspositions2(substOperations);

		for (final Transposition tp : potTranspositions.keySet()) {
			substOperations.removeIf(new Predicate<Substitution>() {

				public boolean test(Substitution t) {
					return ((tp.getTarget() == t.getPosition()) || (tp.getSource() == t.getPosition()));
				}
			});
		}
		final double editDistance = getSubstOperationsWeight(substOperations)
				+ getTranspositionsWeight(potTranspositions.keySet());

		return editDistance;
	}

	protected double getTranspositionsWeight(Collection<Transposition> trans) {
		double d = 0.0;
		for (final Transposition tp : trans) {
			d += tpFunction.getWeight(tp);
		}
		return d / getWindowSize();
	}

	protected double getSubstOperationsWeight(Collection<?> substOp) {
		return substOp.size();
	}

	public double score(JCas jcas1, JCas jcas2) {
		final double mass = JCasUtil.select(jcas1, SegmentationUnit.class).size();
		final double editDistance = getEditDistance(jcas1, jcas2);

		final double d = (mass - 1.0 - editDistance) / (mass - 1.0);
		return d;
	}

}
