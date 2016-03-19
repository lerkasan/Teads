package com.github.lerkasan.teads;

public class Node {
	private int number;
	private Node father;
	private int depth;
	
	public Node() {
		number = 0;
		father = null;
		depth = 0;
	}
	
	public Node(int number, Node father) {
		this.number = number;
		this.father = father;
		this.depth = 0;
	}

}
