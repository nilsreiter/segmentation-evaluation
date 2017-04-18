package de.unistuttgart.ims.segmentation.evaluation.impl;

import java.util.Objects;

public class Transposition<T> {
	public Transposition(int from, int to, T type) {
		super();
		this.from = from;
		this.to = to;
		this.type = type;
	}

	int from, to;
	T type;

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Transposition))
			return false;
		@SuppressWarnings("unchecked")
		Transposition<T> tp = (Transposition<T>) o;
		return (tp.to == this.to && tp.from == this.from && tp.type.equals(this.type));
	}

	@Override
	public int hashCode() {
		return Objects.hash(from, to, type);

	}

	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		b.append("(").append(from).append(",").append(to).append(",").append(type).append(')');
		return b.toString();
	}
}