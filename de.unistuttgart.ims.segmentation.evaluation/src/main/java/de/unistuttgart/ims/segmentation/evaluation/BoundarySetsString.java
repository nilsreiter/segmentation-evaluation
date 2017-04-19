package de.unistuttgart.ims.segmentation.evaluation;

import java.util.Collection;
import java.util.Set;

public interface BoundarySetsString<T> {
	void add(int index, Set<T> element);

	void add1(int index, T element);

	boolean addAll(int index, Collection<? extends Set<T>> element);

	int size();

	Set<T> get(int index);

}
