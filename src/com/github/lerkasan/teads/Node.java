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

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Node getFather() {
		return father;
	}

	public void setFather(Node father) {
		this.father = father;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		if (depth >=0) {
			this.depth = depth;
		} else {
			throw new IllegalArgumentException("Node depth can't be negative.");
		}
	}
	
	public String toString() {
		return ""+number;
	}
	
	public int updateFatherDepth() {
        if ( (father != null) && (depth + 1 > father.depth) ) {
            father.setDepth(depth + 1);
        }
        return father.depth;
    /*  if (maxDepth < aNode.getDepth() + 1) {
    		maxDepth = aNode.getDepth() + 1;
    	} */
    }  

}
