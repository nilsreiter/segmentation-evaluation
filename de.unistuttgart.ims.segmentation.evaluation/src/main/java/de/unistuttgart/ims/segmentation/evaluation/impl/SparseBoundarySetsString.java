package de.unistuttgart.ims.segmentation.evaluation.impl;

import java.util.Collection;
import java.util.Set;

import org.apache.commons.collections4.multimap.HashSetValuedHashMap;

import com.google.common.collect.Sets;

import de.unistuttgart.ims.segmentation.evaluation.BoundarySetsString;

public class SparseBoundarySetsString<T> extends HashSetValuedHashMap<Integer, T> implements BoundarySetsString<T> {

	private static final long serialVersionUID = 1L;

	public SparseBoundarySetsString(T... array) {
		super();
		for (int i = 0; i < array.length; i++) {
			this.add1(i, array[i]);
		}
	}

	public SparseBoundarySetsString(T[]... array) {
		super();
		for (int i = 0; i < array.length; i++) {
			super.putAll(i, Sets.newHashSet(array[i]));
		}
	}

	@Override
	public void add(int index, Set<T> element) {
		super.remove(index);
		super.putAll(index, element);
	}

	@Override
	public boolean addAll(int index, Collection<? extends Set<T>> element) {
		boolean b = true;
		for (Set<T> e : element)
			b = b && this.putAll(index, e);
		return b;
	}

	@Override
	public void add1(int index, T element) {
		super.put(index, element);

	}

	@Override
	public Set<T> get(int index) {
		return super.get(index);
	}

}
