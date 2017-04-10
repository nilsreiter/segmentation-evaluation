package de.unistuttgart.ims.segmentation.uima.evaluation;

public interface WindowDifference extends Metric {
	int getWindowSize();

	void setWindowSize(int windowSize);
}
