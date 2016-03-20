package com.github.lerkasan.teads;

import java.util.HashSet;
import java.util.Set;

public class NumberNodes {
	private Set<Integer> nodeSet;
	
	public NumberNodes() {
		nodeSet = new HashSet<>();
	}

	public boolean add(Integer numbNode) {
		return nodeSet.add(numbNode);
	}
	
	public boolean remove(Integer numbNode) {
		return nodeSet.remove(numbNode);
	}

	public Set<Integer> getNodeSet() {
		return nodeSet;
	}

}
