package com.github.lerkasan.teads;

import java.io.IOException;
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
	
	public void init() throws IOException { 
        try (Scanner in = new Scanner(System.in)) {
        	int n = in.nextInt(); // the number of adjacency relations
        	int fromNumber = in.nextInt();
    		int toNumber = in.nextInt();
        	for (int i = 0; i < n; i++) {
        		fromNumber = in.nextInt(); 
        		toNumber = in.nextInt(); 
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
        		nodeConnections.add(fromNumber, toNumber);
        	}
        }
	}
	
	public static void main(String[] args) {
		

	}

}
