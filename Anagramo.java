/* Prompt:

Given a word W and a string S, find all starting indices in S which are anagrams of W.

For example, given that W is "ab", and S is "abxaba", return 0, 3, and 4..*/

import java.util.ArrayList;
import java.util.HashMap;

public class Anagramo {
	public static void main(String[] args) {
		
		/* Sets both strings to lower case, as case is not important */
		String s = "abxaba".toLowerCase();
		String w = "ab".toLowerCase();
		
		/* Creates a hash map which records the frequency of each letter in the word */
		HashMap<Integer, Integer> letterMap = getLetterMap(w);
		/* Prints each letter in the string and their occurences, for debugging */
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
	
	/* Starting at the beginning of the first string... */
	for(int i = 0; i < s.length(); i++) {
		
		/*If the string "a" fits in s from the current index... */
		if(i+a.length() <= s.length()) {
			/* Check if an anagram exists at this index, and if it does, add it to the list of anagram indicies. */
			if(isAnagram(s.substring(i, i+a.length()), a)) {
			anagramindices.add(i);
			}
		}
	}
	
	return anagramindices;
		
	}
	
}
