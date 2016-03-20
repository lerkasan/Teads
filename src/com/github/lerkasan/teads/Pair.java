package com.github.lerkasan.teads;

public class Pair<T> {
	private T from;
	private T to;
	
	public Pair(T from, T to) {
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
	
}

