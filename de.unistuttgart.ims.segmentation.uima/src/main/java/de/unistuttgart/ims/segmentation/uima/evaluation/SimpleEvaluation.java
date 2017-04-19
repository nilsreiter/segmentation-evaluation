package de.unistuttgart.ims.segmentation.uima.evaluation;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class SimpleEvaluation implements Evaluation {
	List<Double> scores = new LinkedList<Double>();

	public SimpleEvaluation() {
		super();
	}

	public SimpleEvaluation(double score) {
		super();
		this.scores.add(score);
	}

	public synchronized double getScore() {
		double sum = 0.0;
		for (Double d : scores) {
			sum += d;
		}
		return sum / scores.size();
	}

	public void add(double s) {
		scores.add(s);
	}

	public void addAll(Collection<Double> s) {
		scores.addAll(s);
	}

}
