package com.github.lerkasan.teads;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Connections {
	private Map<Integer, Set<Integer>> connectionsMap;

	public Connections() {
		connectionsMap = new HashMap<>();
	}
	
	public Map<Integer, Set<Integer>> getConnectionsMap() {
		return connectionsMap;
	}

	public void setConnectionsMap(Map<Integer, Set<Integer>> connectionsMap) {
		this.connectionsMap = connectionsMap;
	}

	public boolean add(Integer fromNode, Integer toNode) {
		Set<Integer> connectedNumbers = connectionsMap.get(fromNode);
		if (connectedNumbers == null) {
			connectedNumbers = new HashSet<>();
			connectedNumbers.add(toNode);
			connectionsMap.put(fromNode, connectedNumbers);
			return true;
		}
		return connectedNumbers.add(toNode);
	}

}
