import java.util.ArrayList;
import java.util.HashMap;

public class Anagramo {
	public static void main(String[] args) {
		
		String s = "abxaba".toLowerCase();
		String w = "ab".toLowerCase();
		
		HashMap<Integer, Integer> letterMap = getLetterMap(w);
		letterMap.forEach((k,v) -> System.out.println((char) k.intValue() + " : " + v));
	
		System.out.println(anagramIndices(s, w));
	}
	
	public static HashMap<Integer, Integer> getLetterMap(String w){
		
		HashMap<Integer, Integer> lettercount = new HashMap<Integer, Integer>();
		
		for(int i = 0; i < w.length(); i++) {
			lettercount.putIfAbsent((int) w.charAt(i), 0);
			lettercount.replace((int) w.charAt(i), lettercount.get((int) w.charAt(i))+1);
		}
		
		return lettercount;
		
	}
	
	public static boolean isAnagram(String s1, String s2) {
		
		if(getLetterMap(s1).equals(getLetterMap(s2))) {
			return true;
		}
		
		return false;
	}
	
	public static ArrayList<Integer> anagramIndices(String s, String a) {
		
	ArrayList<Integer> anagramindices = new ArrayList<Integer>();
	
	for(int i = 0; i < s.length(); i++) {
		
		if(i+a.length() <= s.length()) {
			if(isAnagram(s.substring(i, i+a.length()), a)) {
			anagramindices.add(i);
			}
		}
	}
	
	return anagramindices;
		
	}
	
}
