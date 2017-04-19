package de.unistuttgart.ims.segmentation.uima.evaluation;

import java.util.Collection;

public interface Evaluation {
	double getScore();

	void add(double s);

	void addAll(Collection<Double> s);
}
