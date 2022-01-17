/*Prompt: Given a string, split it into as few strings as possible such that each string is a palindrome.

For example, given the input string racecarannakayak, return ["racecar", "anna", "kayak"].

Given the input string abc, return ["a", "b", "c"].

Note: This contains multiple solutions: getPalindromes and getPalindromes2. Both produce valid results.*/

import java.util.ArrayList;
import java.util.Scanner;

public class SplitDrome {
	
	public static void main(String[] args) {
		
		String word = "racecarkayakannafada11";
		
		System.out.println(getPalindromes2(word));
		
	}
	
	
	public static ArrayList<String> getPalindromes(String s){
		
		ArrayList<String> palindromes = new ArrayList<String>();
		
		if(isPalindrome2(s)) {
			palindromes.add(s);
			return palindromes;
		}
		
		
		String temp1 = s;
		String temp2 = "";
		
		while(!temp1.isEmpty() || !temp2.isEmpty()) {
			//System.out.println(temp1);
			
			if(isPalindrome2(temp1)) { 
				palindromes.add(temp1);
				temp1 = temp2;
				temp2 = "";
			
			}else {
			
				temp2 += temp1.charAt(temp1.length()-1);
				temp1 = temp1.substring(0, temp1.length()-1);
			}
			
		}
		
		
		return palindromes;
	}
	
	public static ArrayList<String> getPalindromes2(String s){
		
		ArrayList<String> palindromes = new ArrayList<String>();
		
		int x = 0;
		int i = s.length();
		
		while(x < i) {
			if(isPalindrome2(s.substring(x, i))){
				palindromes.add(s.substring(x, i));
				x = i;
				i = s.length();
			}else {
				i--;
			}
		}
		
		return palindromes;
	}
	
	public static boolean isPalindrome(String s) {
		
		String reverse = "";
		
		for(int i = s.length()-1; i > -1; i--) {
			reverse += s.charAt(i);
		}
		
		
		if(s.equals(reverse)) {
			return true;
		}
			
		
		return false;
	}
	
	public static boolean isPalindrome2(String s) {
		
		for(int i = 0; i < s.length()/2; i++) {
		
			if(s.charAt(i) != s.charAt(s.length()-1-i)) {
				return false;
			}
		}
		
		
		
		return true;
	}
	
	
}
