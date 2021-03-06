import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

class Pair<T> {
	private T from;
	private T to;
	
	public Pair(T from, T to) {
		this.from = from;
		this.to = to;
	}

	public T getFrom() {
		return from;
	}

	public void setFrom(T from) {
		this.from = from;
	}

	public T getTo() {
		return to;
	}

	public void setTo(T to) {
		this.to = to;
	}
	
}

class Node {
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

class Tree {
	int steps = 0;
	int maxDepth;
	Node root;
	Set<Node> possibleRoots;
	boolean rootFromLeaves;
	Set<Integer> intLeaves;
	Set<Node> leaves;
	Set<Integer> allIntNodes;
	Map<Integer, Node> createdNodes;
	Map<Integer, Set<Integer>> nodeConnections;
	
	public Tree() {
		maxDepth = 0;
		root = new Node();
		possibleRoots = new HashSet<>();
		rootFromLeaves = false;
		intLeaves = new HashSet<>();
		leaves = new HashSet<>();
		allIntNodes = new HashSet<>();
		createdNodes = new HashMap<>();
		nodeConnections = new HashMap<>();
	}
	
	public int getSteps() {
		return steps;
	}
	
	public boolean isRootFromLeaves() {
		return rootFromLeaves;
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

	public void init() { 
        try (Scanner in = new Scanner(System.in)) {
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
        		addConnection(fromNumber, toNumber);
        	}
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
				possibleRoots.add(fromNode);
			}
			for (Integer value : entry.getValue()) {
				if (! createdNodes.containsKey(value)) {
					toNode = new Node(value, fromNode);
					createdNodes.put(value, toNode);
					possibleRoots.remove(toNode);
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
		/*for (Pair<Node> i : builtConnections) {
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
			System.out.println();*/
	}
	
	public Set<Node> getFirstLevelNodes() {
		Set<Node> firstLevelNodes = new HashSet<>();
	//	System.out.println("\nFirst level:");
		for (Integer leaf : intLeaves) {
			if (nodeConnections.containsKey(leaf)) {
				for (Integer connected : nodeConnections.get(leaf)) {
					createdNodes.get(connected).setDepth(1);
					firstLevelNodes.add(createdNodes.get(connected));
					//System.out.println(connected+" ");
				}
			}
			for (Map.Entry<Integer, Set<Integer>> entry : nodeConnections.entrySet()) {
				if (entry.getValue().contains(leaf)) {
					createdNodes.get(entry.getKey()).setDepth(1);
					firstLevelNodes.add(createdNodes.get(entry.getKey()));
					//System.out.println(entry.getKey()+" ");
				}
			}
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
		if (! nextNodes.isEmpty()) {
		//	System.out.println("\nNext nodes:");
			for (Node i : nextNodes) {
				//System.out.print(i.getNumber()+ " ");
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
				//	System.out.println("Root is from leaves. " + root);
					rootFromLeaves = true;
					return root;
				}
			}
		} else {
		//	System.out.println("There are no leaves");
			if (! possibleRoots.isEmpty()) {
				for (Node possibleRoot : possibleRoots) {
					if (possibleRoot.getFather() == null) {
						root = possibleRoot;
					//	System.out.println("Root is from possible roots. " + root);
						return root;
					}
				}
			}
		}
		return root;
	}
	
	public int getMaxSteps() {
		if  (isRootFromLeaves())  {
			return steps/2 + steps%2;
		} 
		return steps;
	}
	
}

class Solution {
    
    private Solution() { }
	
	public static void main(String[] args) {	
		Tree tree1 = new Tree();
		tree1.init();
		tree1.build();
		Node root = tree1.chooseRoot();
	//	tree1.getLeaves().remove(root);
//		tree1.getintLeaves().remove(root.getNumber());
		//tree1.printLeaves();
		tree1.walkFromLeavesCountingSteps(tree1.getLeaves());
		System.out.print(tree1.getMaxSteps());
	}
}