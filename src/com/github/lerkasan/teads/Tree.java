package com.github.lerkasan.teads;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Tree {
	Node root;
	NumberNodes leaves;
	NumberNodes allNodes;
	Connections nodeConnections;
	
	public Tree() {
		root = new Node();
		leaves = new NumberNodes();
		allNodes = new NumberNodes();
		nodeConnections = new Connections();
	}
	
	public Node getRoot() {
		return root;
	}

	public NumberNodes getLeaves() {
		return leaves;
	}

	public NumberNodes getAllNodes() {
		return allNodes;
	}

	public Connections getNodeConnections() {
		return nodeConnections;
	}

	public void init(String filePath) { 
       // try (Scanner in = new Scanner(System.in)) {
		try (Scanner in = new Scanner(Paths.get(filePath))) {
        	int n = in.nextInt(); // the number of adjacency relations
        	for (int i = 0; i < n; i++) {
        		int fromNumber = in.nextInt(); 
        		int toNumber = in.nextInt(); 
        		if (allNodes.add(fromNumber)) {
        			leaves.add(fromNumber);
        		} else {
        			leaves.remove(fromNumber);
        		} 
        		if (allNodes.add(toNumber)) {
        			leaves.add(toNumber);
        		} else {
        			leaves.remove(toNumber);
        		}
        		if (nodeConnections.containsKey(fromNumber)) {
        			nodeConnections.add(fromNumber, toNumber);
        		} else if (nodeConnections.containsKey(toNumber)) {
        			nodeConnections.add(toNumber, fromNumber);
        		} else {
        			nodeConnections.add(fromNumber, toNumber);
        		}
        	}
        }
		catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	public void build() {
		Node fromNode;
		Node toNode;
		Set<Node> possibleRoots = new HashSet<>();
		Map<Integer, Node> createdNodes = new HashMap<>();
		Set<Pair<Node>> builtConnections = new HashSet<>();
		for (Map.Entry<Integer, Set<Integer>> entry : nodeConnections.getConnectionsMap().entrySet()) {
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
	
	public void printLeaves() {
		System.out.println("Leaves: ");
		for (Integer i : leaves.getNodeSet()) {
			System.out.print(i+" ");
		}
	}
}
