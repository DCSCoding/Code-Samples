/* Prompt: You're given a string consisting solely of (, ), and *. * can represent either a (, ), or an empty string. Determine whether the parentheses are balanced.

For example, (()* and (*) are balanced. )*( is not balanced. */

public class Parantheses {
	public static void main(String[] args) {
		
		System.out.println(balanced("(*********))"));
		
	}
	
	/*The basic idea is that parantheses are removed from the string until either the string is only wild cards (representing blank space) or is empty.
	If this condition can be met, than the string is balanced and returns true. Otherwise the string is unbalanced and returns false. */
	static boolean balanced(String s) {

	int indexl = 0;
	int indexr = 0;
	
	/*Gets the first index of ( on the left and ) on the right*/
	indexl = s.indexOf("(");
	indexr = s.indexOf(")");
	
	/*If neither exist in the string, return true- this means the paranthesis are balanced*/
	/*Else, if replacing the wild card with an empty string produces )(, then the paranthesis are not balanced.*/
	/*Else, if there is a paranthesis on either side of the string, and a wildcard cannot be used to balance it, then the paranthesis are not ballanced.*/
	if(indexl == -1 && indexr == -1) {
		return true;
	}else if(s.replace("*", "").equals(")(")) {
		return false;
	}else if(indexl == -1 && !(indexr == -1) || !(indexl == -1) && indexr == -1) {
		if(indexl == -1 && s.contains("*") && s.indexOf("*") < indexr) return true;
		if(indexr == -1 && s.contains("*") && s.indexOf("*") > indexl) return true;
		return false;
	}
	
	/*If there is still at least one ( and ), convert the string to a char array, delete the char at that index, and rebuild the string. The pass the 
	string back into this function. */
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
