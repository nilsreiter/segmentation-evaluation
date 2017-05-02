package de.unistuttgart.ims.segmentation.evaluation.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.google.common.collect.Sets;

import de.unistuttgart.ims.segmentation.evaluation.BoundarySetsString;

public class ListBoundarySetsString<T> extends ArrayList<Set<T>> implements BoundarySetsString<T> {

	private static final long serialVersionUID = 1L;

	public ListBoundarySetsString() {
	}

	public ListBoundarySetsString(Iterable<T> ts) {
		super();
		int i = 0;
		for (T t : ts) {
			if (t != null) {
				Set<T> s = new HashSet<T>();
				s.add(t);
				add(i, s);
			} else {
				add(i, Sets.newHashSet());
			}
			i++;
		}

	}

	public ListBoundarySetsString(@SuppressWarnings("unchecked") T... ts) {
		super();
		for (int i = 0; i < ts.length; i++) {
			T o = ts[i];
			if (o != null) {
				Set<T> s = new HashSet<T>();
				s.add(o);
				add(i, s);
			} else {
				add(i, Sets.newHashSet());
			}
		}
	}

	public ListBoundarySetsString(@SuppressWarnings("unchecked") T[]... ts) {
		for (int i = 0; i < ts.length; i++) {
			T[] o = ts[i];
			if (o != null) {
				add(i, Sets.newHashSet(o));
			} else {
				add(i, Sets.newHashSet());
			}
		}
	}

	public static <T> BoundarySetsString<T> createBoundarySetsString(@SuppressWarnings("unchecked") T... ts) {
		return new ListBoundarySetsString<T>(ts);

	}

	public static <T> BoundarySetsString<T> createBoundarySetsString(@SuppressWarnings("unchecked") T[]... ts) {
		return new ListBoundarySetsString<T>(ts);
	}

	@Override
	public void add1(int index, T element) {
		super.get(index).add(element);
	}

	@Override
	public int numberOfBoundaries() {
		int r = 0;
		for (Set<T> s : this) {
			r += s.size();
		}
		return r;
	}

}
