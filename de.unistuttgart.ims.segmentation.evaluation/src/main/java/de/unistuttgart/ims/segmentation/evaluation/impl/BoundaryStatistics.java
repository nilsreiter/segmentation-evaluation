package de.unistuttgart.ims.segmentation.evaluation.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.unistuttgart.ims.segmentation.evaluation.impl.FournierUtil.Transposition;

public class BoundaryStatistics {
	boolean[][] boundaries;
	int windowSize;

	Set<Integer> matches = new HashSet<Integer>();
	Map<Integer, Integer> mismatches = new HashMap<Integer, Integer>();
	List<Transposition> transpositions = new LinkedList<Transposition>();

	public BoundaryStatistics(boolean[][] boundaries, int windowSize) {
		this.boundaries = boundaries;
		this.windowSize = windowSize;

		calculate();
	}

	protected void calculate() {

		for (int i = 0; i < boundaries[0].length; i++)
			if (boundaries[0][i] ^ boundaries[1][i]) {
				mismatches.put(i, boundaries[0][i] ? 0 : 1);
			} else
				matches.add(i);

		Iterator<Integer> iterator = mismatches.keySet().iterator();
		if (iterator.hasNext()) {
			int current = iterator.next(), next;
			while (iterator.hasNext()) {
				next = iterator.next();
				if (Math.abs(Math.abs(next) - Math.abs(current)) <= windowSize) {
					transpositions.add(new Transposition(current, next));
					mismatches.remove(next);
					mismatches.remove(current);
				}
				current = next;
			}
		}
	}

	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		b.append("matches = ").append(matches).append("\n");
		b.append("mismatches = ").append(mismatches).append("\n");
		b.append("transpositions = ").append(transpositions).append("\n");
		return b.toString();
	}

	static class MissError {
		int sequence, position, type;

		public MissError(int sequence, int position, int type) {
			super();
			this.sequence = sequence;
			this.position = position;
			this.type = type;
		}

	}
}
