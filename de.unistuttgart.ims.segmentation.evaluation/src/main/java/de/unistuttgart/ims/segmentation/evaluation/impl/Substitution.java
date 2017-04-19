package de.unistuttgart.ims.segmentation.evaluation.impl;

public class Substitution<T> {

	T from;
	T to;

	public Substitution(T from, T to) {
		super();
		this.from = from;
		this.to = to;
	}

	public T getFrom() {
		return from;
	}

	public void setFrom(T from) {
		this.from = from;
	}

	public T getTo() {
		return to;
	}

	public void setTo(T to) {
		this.to = to;
	}

	@Override
	public String toString() {
		return "(" + from.toString() + "," + to.toString() + ")";
	}
}
