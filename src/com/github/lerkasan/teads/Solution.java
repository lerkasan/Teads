package com.github.lerkasan.teads;

public class Solution {
	
	private Solution() {
	}
	
	public static void main(String[] args) {	
		Tree tree1 = new Tree();
		tree1.init("input\\test1.txt");
		tree1.build();
		tree1.printLeaves();
	}

}
