package de.unistuttgart.ims.segmentation.evaluation;

import de.unistuttgart.ims.segmentation.evaluation.impl.Pk_impl;
import de.unistuttgart.ims.segmentation.evaluation.impl.WindowDifference_impl;

public class MetricFactory {

	@SuppressWarnings("unchecked")
	public static <T extends Metric> T getMetric(Class<T> mClass) {
		if (mClass.equals(Pk.class))
			return (T) new Pk_impl();
		if (mClass.equals(WindowDifference.class))
			return (T) new WindowDifference_impl();
		return null;

	}
}
