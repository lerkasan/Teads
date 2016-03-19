package com.github.lerkasan.teads;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Tree {
	
	public void build(Connections connectedNumbers) {
		Set<Integer> created = new HashSet<>();
		Set<Node> builtNodes = new HashSet<>();
		for (Map.Entry<Integer, Set<Integer>> entry : connectedNumbers.getConnectionsMap().entrySet()) {
			if (created.add(entry.getKey())) {
				Node fromNode = new Node(entry.getKey(), null);
				builtNodes.add(fromNode);
				
			}
		}
		
	}
}
