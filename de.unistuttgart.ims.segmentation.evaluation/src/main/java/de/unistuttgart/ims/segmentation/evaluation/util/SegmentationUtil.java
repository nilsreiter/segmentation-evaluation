package de.unistuttgart.ims.segmentation.evaluation.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;

public class SegmentationUtil {
	public static boolean[][] getBoundaries(int[] ms1, int[] ms2) {
		final boolean[][] boundaries = new boolean[2][];
		boundaries[0] = SegmentationUtil.getBoundaryString(ms1);
		boundaries[1] = SegmentationUtil.getBoundaryString(ms2);
		return boundaries;
	}

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

	public static boolean[] getBoundaryString(int[] massString) {
		final boolean[] boundaries = new boolean[sum(massString)];
		Arrays.fill(boundaries, false);
		int index = 0;
		for (int i = 0; i < (massString.length - 1); i++) {
			index += massString[i];
			if (index < boundaries.length)
				boundaries[index - 1] = true;
		}
		return boundaries;
	}

	public static int sum(int[] array) {
		int s = 0;
		for (final int element : array) {
			s += element;
		}
		return s;
	}
}
