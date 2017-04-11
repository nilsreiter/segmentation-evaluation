package de.unistuttgart.ims.segmentation.evaluation;

import de.unistuttgart.ims.segmentation.evaluation.impl.Pk_impl;

public class MetricFactory {

	@SuppressWarnings("unchecked")
	public static <T extends Metric> T getMetric(Class<T> mClass) {
		if (mClass.equals(Pk.class))
			return (T) new Pk_impl();
		return null;

	}
}
