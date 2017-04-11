package de.unistuttgart.ims.segmentation.evaluation.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;

public class SegmentationUtil {
	public static <T> int[] positionsToMasses(List<T> positions) {
		LinkedList<Integer> l = new LinkedList<Integer>();
		Iterator<T> iter = positions.iterator();
		T prev = iter.next(), next = null;
		int c = 1;
		while (iter.hasNext()) {
			next = iter.next();
			if (prev.equals(next)) {
				c++;
			} else {
				l.add(c);
				c = 1;
				prev = next;
			}
		}
		l.add(c);

		int[] r = new int[l.size()];
		for (int i = 0; i < l.size(); i++) {
			r[i] = l.get(i);
		}

		return r;
	}

	public static int[] massesToPositions(int[] massString) {
		ArrayList<Integer> a = new ArrayList<Integer>();

		for (int i = 0; i < massString.length; i++) {
			for (int j = 0; j < massString[i]; j++) {
				a.add(i);
			}
		}
		return ArrayUtils.toPrimitive(a.toArray(new Integer[a.size()]));
	}
}
