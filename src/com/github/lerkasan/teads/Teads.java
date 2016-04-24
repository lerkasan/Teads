package com.github.lerkasan.teads;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

class Tree3 {
	Map<Integer, Set<Integer>> nodeConnections;

	public Tree3() {
		nodeConnections = new HashMap<>();
	}

	public void init(String filePath) throws IOException {
		// public void init() {
		//try (Scanner in = new Scanner(System.in)) {
		try (Scanner in = new Scanner(Paths.get(filePath))) {
			int n = in.nextInt(); // the number of adjacency relations
			for (int i = 0; i < n; i++) {
				int fromNumber = in.nextInt();
				int toNumber = in.nextInt();
				addConnection(fromNumber, toNumber);
				addConnection(toNumber, fromNumber);
			}
		}
	}

	public boolean addConnection(Integer fromNode, Integer toNode) {
		Set<Integer> connectedNumbers = nodeConnections.get(fromNode);
		if (connectedNumbers == null) {
			connectedNumbers = new HashSet<>();
			connectedNumbers.add(toNode);
			nodeConnections.put(fromNode, connectedNumbers);
			return true;
		}
		return connectedNumbers.add(toNode);
	}

	public boolean removeNodeAndConnections(Integer node) {
		Set<Integer> connectedNumbers = nodeConnections.get(node);
		if (connectedNumbers != null) {
			for (Integer connected : connectedNumbers) {
				nodeConnections.get(connected).remove(node);
			}
			nodeConnections.remove(node);
			return true;
		}
		return false;
	}

	public boolean isLeaf(Integer node) {
		Set<Integer> connections = nodeConnections.get(node);
		if (connections != null && connections.size() == 1) {
			return true;
		}
		return false;
	}

	public Set<Integer> findLeaves() {
		Set<Integer> leaves = new HashSet<>();
		Iterator<Integer> iter = nodeConnections.keySet().iterator();
		while (iter.hasNext()) {
			Integer node = (Integer) iter.next();
			if (isLeaf(node)) {
				leaves.add(node);
			}
		}
		return leaves;
	}

	public int traverse() {
		int steps = 0;
		Set<Integer> leaves;
		while (!(leaves = findLeaves()).isEmpty()) {
			for (Integer leaf : leaves) {
				Set<Integer> connectedNumbers = nodeConnections.get(leaf);
				for (Integer connected : connectedNumbers) {
					nodeConnections.get(connected).remove(leaf);
				}
				nodeConnections.remove(leaf);
			}
			steps++;
		}
		return steps;
	}
}

class Solution {
    
    private Solution() { }
	
	public static void main(String[] args) throws IOException {	
		Tree3 tree1 = new Tree3();
		tree1.init("input\\test10.txt");
		System.out.print(tree1.traverse());
	}
}