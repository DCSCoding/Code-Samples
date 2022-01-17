import java.util.Comparator;

public class EnhancedNodeComparator implements Comparator<EnhancedNode> {

	@Override
	public int compare(EnhancedNode o1, EnhancedNode o2) {
		
		return o1.depth-o2.depth;
	}

}
