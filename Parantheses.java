public class Parantheses {
	public static void main(String[] args) {
		
		System.out.println(balanced("(*********))"));
		
	}
	
	static boolean balanced(String s) {

	int indexl = 0;
	int indexr = 0;
	
	indexl = s.indexOf("(");
	indexr = s.indexOf(")");
	
	
	if(indexl == -1 && indexr == -1) {
		return true;
	}else if(s.replace("*", "").equals(")(")) {
		return false;
	}else if(indexl == -1 && !(indexr == -1) || !(indexl == -1) && indexr == -1) {
		if(indexl == -1 && s.contains("*") && s.indexOf("*") < indexr) return true;
		if(indexr == -1 && s.contains("*") && s.indexOf("*") > indexl) return true;
		return false;
	}
	
	char[] chars = s.toCharArray();
	chars[indexl] = ' ';
	chars[indexr] = ' ';
	
	s = "";
	
	for(char c : chars) {
		if(c != ' ') 
			s+=c;
	}
	
	return balanced(s);
	}
}
