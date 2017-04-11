package de.unistuttgart.ims.segmentation.uima.evaluation;

@Deprecated
public interface WindowDifference extends Metric {
	int getWindowSize();

	void setWindowSize(int windowSize);
}
