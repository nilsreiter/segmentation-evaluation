package de.unistuttgart.ims.segmentation.evaluation;

public interface BoundarySetsMetric<T> extends Metric {
	double score(BoundarySetsString<T> gold, BoundarySetsString<T> silver, int window);

}
