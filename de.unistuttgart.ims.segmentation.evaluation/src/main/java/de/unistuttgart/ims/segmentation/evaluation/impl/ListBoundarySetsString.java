package de.unistuttgart.ims.segmentation.evaluation.impl;

import java.util.ArrayList;
import java.util.Set;

import com.google.common.collect.Sets;

import de.unistuttgart.ims.segmentation.evaluation.BoundarySetsString;

public class ListBoundarySetsString<T> extends ArrayList<Set<T>> implements BoundarySetsString<T> {

	private static final long serialVersionUID = 1L;

	public static <T> BoundarySetsString<T> createBoundarySetsString(T... ts) {
		ListBoundarySetsString<T> l = new ListBoundarySetsString<T>();
		for (int i = 0; i < ts.length; i++) {
			T o = ts[i];
			if (o != null) {
				l.add(i, Sets.newHashSet(o));
			} else {
				l.add(i, Sets.newHashSet());
			}
		}
		return l;

	}

	public static <T> BoundarySetsString<T> createBoundarySetsString(T[]... ts) {
		ListBoundarySetsString<T> l = new ListBoundarySetsString<T>();
		for (int i = 0; i < ts.length; i++) {
			T[] o = ts[i];
			if (o != null) {
				l.add(i, Sets.newHashSet(o));
			} else {
				l.add(i, Sets.newHashSet());
			}
		}
		return l;

	}

}
