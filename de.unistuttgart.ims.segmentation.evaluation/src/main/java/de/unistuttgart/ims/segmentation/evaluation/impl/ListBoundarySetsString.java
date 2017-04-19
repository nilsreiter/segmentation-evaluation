package de.unistuttgart.ims.segmentation.evaluation.impl;

import java.util.ArrayList;
import java.util.Set;

import com.google.common.collect.Sets;

import de.unistuttgart.ims.segmentation.evaluation.BoundarySetsString;

public class ListBoundarySetsString<T> extends ArrayList<Set<T>> implements BoundarySetsString<T> {

	private static final long serialVersionUID = 1L;

	public ListBoundarySetsString(T... ts) {
		super();
		for (int i = 0; i < ts.length; i++) {
			T o = ts[i];
			if (o != null) {
				add(i, Sets.newHashSet(o));
			} else {
				add(i, Sets.newHashSet());
			}
		}
	}

	public ListBoundarySetsString(T[]... ts) {
		for (int i = 0; i < ts.length; i++) {
			T[] o = ts[i];
			if (o != null) {
				add(i, Sets.newHashSet(o));
			} else {
				add(i, Sets.newHashSet());
			}
		}
	}

	public static <T> BoundarySetsString<T> createBoundarySetsString(T... ts) {
		return new ListBoundarySetsString<T>(ts);

	}

	public static <T> BoundarySetsString<T> createBoundarySetsString(T[]... ts) {
		return new ListBoundarySetsString<T>(ts);
	}

	@Override
	public void add1(int index, T element) {
		super.get(index).add(element);
	}

}
