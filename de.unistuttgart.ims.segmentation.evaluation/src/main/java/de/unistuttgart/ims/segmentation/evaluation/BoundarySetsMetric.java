package de.unistuttgart.ims.segmentation.evaluation;

import java.util.List;
import java.util.Set;

public interface BoundarySetsMetric<T> {
	double score(List<Set<T>> gold, List<Set<T>> silver, int window);

}