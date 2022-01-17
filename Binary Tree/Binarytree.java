import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Binarytree {

	Node root; 
	
	public void add(int value) {
		root = addRecursive(root, value);
	}

	
	private Node addRecursive(Node current, int value) {
		
		if(current == null) {
			return new Node(value);
		}else if(value < current.value) {
			current.left = addRecursive(current.left, value);
		}else if(value > current.value) {
			current.right = addRecursive(current.right,value);
		}else {
			//value already exists 
			return current;
		}
		
		return current;
	}

	private boolean containsNodeRecursive(Node current, int value) {
		
		if(current == null) {
			return false;
		}
		
		if(value == current.value) {
			return true;
		}
		
		return value < current.value ? containsNodeRecursive(current.left, value) : containsNodeRecursive(current.right, value); 
	}
	
	public boolean containsNode(int value) {
		return containsNodeRecursive(root, value);
	}
	
	public String serialize(Node current) {
		
		String s = ""+current.value+"-";
		if(current.left != null && current.right != null) {
			return s+serialize(current.left)+serialize(current.right);
		}else if(current.left == null && current.right != null) {
			return s+serialize(current.right);
		}else if(current.left != null && current.right == null) {
			return s+serialize(current.left);
		}else {
			return s;
		}
		
	}
	
	public Binarytree deserialize(String s) {
		
		Binarytree bt = new Binarytree();
		String[] stringvalues = s.split("-");
		for(String m : stringvalues) {
			bt.add(Integer.parseInt(m));
		}
		
		return bt;
		
	}
	
	private String seralizeLeft(Node current, String s) {
		s = ""+current.value;
		return current.left != null ? s+(seralizeLeft(current.left, s)) : s;
	}
	
	private String seralizeRight(Node current, String s) {
		s = ""+current.value;
		return current.right != null ? s+(seralizeRight(current.right, s)) : s;
	}

	
	public Node getRoot() {
		return root;
	}
	
	public int countNodes(Node n) {
		
		int total = 0;
		
		if(n == null) return 0;
		if(n.left == null && n.right == null) {
			return 1;
		}
		
		total += countNodes(n.left) + countNodes(n.right) + 1; 
		
		return total;
	}
	
	public ArrayList<EnhancedNode> leafList(Node root, int depth) {
		ArrayList<EnhancedNode> eh = new ArrayList<EnhancedNode>();
	
		
		if(root.left != null) {
			eh.addAll(leafList(root.left, depth+1));
		}if(root.right != null) {
			eh.addAll(leafList(root.right, depth+1));
		}
		
		if(root.left == null && root.right == null) {
			eh.add(new EnhancedNode(depth, root));
		}
		
		return eh;
	}
	
	public ArrayList<EnhancedNode> deepestNodes() {
		
		ArrayList<EnhancedNode> deepest = new ArrayList<EnhancedNode>();
		ArrayList<EnhancedNode> eh = leafList(root, 0);
		eh.sort(new EnhancedNodeComparator());
		
		for(EnhancedNode e : eh) {
			if(e.depth == eh.get(eh.size()-1).depth) {
				deepest.add(e);
			}
		}
		return deepest;
	}
	
	public Node search(int value, Node node) {
		
		if(node.value > value) return search(value, node.left);
		if(node.value < value) return search(value, node.right);
		if(node.value == value) return node;
		
		return null;
	}
}
