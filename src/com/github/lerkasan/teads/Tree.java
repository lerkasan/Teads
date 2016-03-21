package com.github.lerkasan.teads;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Tree {
	int steps = 1;
	int maxDepth;
	Node root;
	Set<Node> possibleRoots;
	Set<Integer> intLeaves;
	Set<Node> leaves;
	Set<Integer> allIntNodes;
	Map<Integer, Node> createdNodes;
	Map<Integer, Set<Integer>> nodeConnections;
	
	public Tree() {
		maxDepth = 0;
		root = new Node();
		possibleRoots = new HashSet<>();
		intLeaves = new HashSet<>();
		leaves = new HashSet<>();
		allIntNodes = new HashSet<>();
		createdNodes = new HashMap<>();
		nodeConnections = new HashMap<>();
	}
	
	public int getSteps() {
		return steps;
	}
	
	public int getMaxDepth() {
		return maxDepth;
	}

	public Node getRoot() {
		return root;
	}

	public Set<Integer> getintLeaves() {
		return intLeaves;
	}
	
	public Set<Node> getLeaves() {
		return leaves;
	}

	public Set<Integer> getallIntNodes() {
		return allIntNodes;
	}

	public Map<Integer, Set<Integer>> getNodeConnections() {
		return nodeConnections;
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

	public void init(String filePath) { 
       // try (Scanner in = new Scanner(System.in)) {
		try (Scanner in = new Scanner(Paths.get(filePath))) {
        	int n = in.nextInt(); // the number of adjacency relations
        	for (int i = 0; i < n; i++) {
        		int fromNumber = in.nextInt(); 
        		int toNumber = in.nextInt(); 
        		if (allIntNodes.add(fromNumber)) {
        			intLeaves.add(fromNumber);
        		} else {
        			intLeaves.remove(fromNumber);
        		} 
        		if (allIntNodes.add(toNumber)) {
        			intLeaves.add(toNumber);
        		} else {
        			intLeaves.remove(toNumber);
        		}
        		if (nodeConnections.containsKey(fromNumber)) {
        			addConnection(fromNumber, toNumber);
        		} else if (nodeConnections.containsKey(toNumber)) {
        			addConnection(toNumber, fromNumber);
        		} else {
        			addConnection(fromNumber, toNumber);
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
		possibleRoots = new HashSet<>();
		Set<Pair<Node>> builtConnections = new HashSet<>();
		for (Map.Entry<Integer, Set<Integer>> entry : nodeConnections.entrySet()) {
			Integer key = entry.getKey();
			if (! createdNodes.containsKey(key)) {
				fromNode = new Node(key, null);
				createdNodes.put(key, fromNode);
				possibleRoots.add(fromNode);
			} else {
				fromNode = createdNodes.get(key);
			}
			if (intLeaves.contains(key)) {
				leaves.add(fromNode);
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
				if (intLeaves.contains(value)) {
					leaves.add(toNode);
				}
			}
		}	
		
		//Printing - to be fully commented to the end
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
	
	public Set<Node> getFirstLevelNodes() {
		Set<Node> firstLevelNodes = new HashSet<>();
		System.out.println("\nFirst level:");
		for (Integer leaf : intLeaves) {
			if (nodeConnections.containsKey(leaf)) {
				for (Integer connected : nodeConnections.get(leaf)) {
					createdNodes.get(connected).setDepth(1);
					firstLevelNodes.add(createdNodes.get(connected));
					System.out.println(connected+" ");
				}
			}
			//if (nodeConnections.containsValue(leaf)) {
				for (Map.Entry<Integer, Set<Integer>> entry : nodeConnections.entrySet()) {
					if (entry.getValue().contains(leaf)) {
						createdNodes.get(entry.getKey()).setDepth(1);
						firstLevelNodes.add(createdNodes.get(entry.getKey()));
						System.out.println(entry.getKey()+" ");
					}
				}
			//}
		}
		return firstLevelNodes;
	}
	
	public void walkFromLeavesCountingSteps(Set<Node> nodes) {
		int currentDepth = 0;
		Set<Node> nextNodes = new HashSet<>();
		for (Node aNode : nodes) {
			if (aNode.getFather() != null) {
				currentDepth = aNode.updateFatherDepth();
				nextNodes.add(aNode.getFather());
			} else {
				currentDepth = aNode.getDepth();
			}
			if (currentDepth > maxDepth) {
				maxDepth = currentDepth;
			}
		}
		nextNodes.remove(root);
		if (! nextNodes.isEmpty()) {
			System.out.println("\nNext nodes:");
			for (Node i : nextNodes) {
				System.out.print(i.getNumber()+ " ");
			}
			steps++;
			walkFromLeavesCountingSteps(nextNodes);
		}
	}
	
	public void printLeaves() {
		System.out.println("Leaves: ");
		for (Integer i : intLeaves) {
			System.out.print(i+" ");
		}
		System.out.println();
		for (Node i : leaves) {
			System.out.print(i.getNumber()+" ");
		}
	}
	
	public Node chooseRoot() {
		if (! leaves.isEmpty()) {
			for (Node leaf : leaves) {
				if (leaf.getFather() == null) {
					root = leaf;
					System.out.println("Root is from leaves. " + root);
					return root;
				}
			}
		} else {
			System.out.println("There are no leaves");
			if (! possibleRoots.isEmpty()) {
				for (Node possibleRoot : possibleRoots) {
					if (possibleRoot.getFather() == null) {
						root = possibleRoot;
						System.out.println("Root is from possible roots. " + root);
						return root;
					}
				}
			}
		}
		return root;
	}
	
	public int getMaxSteps1() {
		return getRoot().getDepth()/2 + getRoot().getDepth()%2;
	}
	
	public int getMaxSteps2() {
		return getSteps()/2 + getSteps()%2;
	}
	
	public static void main(String[] args) {	
		Tree tree1 = new Tree();
		tree1.init("input\\test10.txt");
		tree1.build();
		tree1.printLeaves();
		tree1.chooseRoot();
		tree1.walkFromLeavesCountingSteps(tree1.getFirstLevelNodes());
		System.out.println("\nResult: Root depth div by 2: " + tree1.getMaxSteps1());
		System.out.println("\nResult: Steps div by 2: " + tree1.getSteps());
	}
}
