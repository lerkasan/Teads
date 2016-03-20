package com.github.lerkasan.teads;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

public class Solution {
	Node root;
	NumberNodes leaves;
	NumberNodes allNodes;
	Connections nodeConnections;
	
	public Solution() {
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
	
	public void printLeaves() {
		System.out.println("Leaves: ");
		for (Integer i : leaves.getNodeSet()) {
			System.out.print(i+" ");
		}
	}
	
	public static void main(String[] args) {
		Solution test1 = new Solution();
		test1.init("input\\test1.txt");
		Tree tree1 = new Tree();
		tree1.build(test1.getNodeConnections());
		test1.printLeaves();

	}

}
