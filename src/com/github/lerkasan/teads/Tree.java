package com.github.lerkasan.teads;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Tree {
	
	public void build(Connections connectedNumbers) {
		Node fromNode;
		Node toNode;
		Set<Node> possibleRoots = new HashSet<>();
		Map<Integer, Node> createdNodes = new HashMap<>();
		Set<Pair<Node>> builtConnections = new HashSet<>();
		for (Map.Entry<Integer, Set<Integer>> entry : connectedNumbers.getConnectionsMap().entrySet()) {
			Integer key = entry.getKey();
			if (! createdNodes.containsKey(key)) {
				fromNode = new Node(key, null);
				createdNodes.put(key, fromNode);
				possibleRoots.add(fromNode);
			} else {
				fromNode = createdNodes.get(key);
			}
			for (Integer value : entry.getValue()) {
				if (! createdNodes.containsKey(value)) {
					toNode = new Node(value, fromNode);
					createdNodes.put(value, toNode);
					possibleRoots.add(fromNode);
				} else {
					toNode = createdNodes.get(value);
					toNode.setFather(fromNode);
					possibleRoots.remove(toNode);
				}
				builtConnections.add(new Pair<>(fromNode,toNode));
			}
		}	
		for (Pair<Node> i : builtConnections) {
			System.out.println("Connection from " + i.getFrom() + " to " + i.getTo());
			System.out.print(i.getFrom().toString() + " <-- ");
			if (i.getFrom().getFather() != null) {
				System.out.println(i.getFrom().getFather().toString() + " (father)");
			} else {
				System.out.println("no father");
			}
			System.out.print(i.getTo().toString() + " <-- ");
			if (i.getTo().getFather() != null) {
				System.out.println(i.getTo().getFather().toString() + " (father)");
			} else {
				System.out.println("no father");
			}
			System.out.println();
		}
		
			System.out.println("Possible roots: ");
			for (Node i : possibleRoots) {
				System.out.print(i+" ");
			}
			System.out.println();
		
	}
}
