package de.unistuttgart.ims.segmentation.evaluation;

import java.util.Map;

import org.apache.uima.jcas.JCas;

/**
 * The base interface for all evaluation metrics
 *
 * @author reiterns
 *
 */
public interface Metric {

	/**
	 * Initializes the metric (e.g., by counting the number of gold segments).
	 * Not all metrics need this.
	 *
	 * @param gold
	 *            The JCas containing the gold segments
	 * @return false on error, true otherwise
	 */
	boolean init(JCas gold);

	/**
	 * The actual evaluation function. Returns a map containing multiple scores.
	 *
	 * @param gold
	 *            The gold annotations
	 * @param silver
	 *            The system output
	 * @return A map containing evaluation values
	 */
	Map<String, Double> scores(JCas gold, JCas silver);

	/**
	 * Shorthand function to return a single score, usually something like
	 * f-score.
	 *
	 * @param gold
	 *            The gold standard annotations
	 * @param silver
	 *            The system output
	 * @return The evaluation score
	 */
	double score(JCas gold, JCas silver);
}
