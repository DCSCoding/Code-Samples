/*Prompt: Given a string, split it into as few strings as possible such that each string is a palindrome.

For example, given the input string racecarannakayak, return ["racecar", "anna", "kayak"].

Given the input string abc, return ["a", "b", "c"].

*/

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class PalindromePartition {

    public static void main(String[] args) {

        String word = "racecarkayakannafada11";

        System.out.println(getPalindromes(word));

    }

    public static ArrayList<String> getPalindromes(String s){

        ArrayList<String> palindromes = new ArrayList<String>();
        Queue<String> nibbles = new LinkedList<String>();
        nibbles.add(s);

        int x = 0;
        String before = "";
        String after = "";

        while(!nibbles.isEmpty()){
            s = nibbles.poll();
            for(x = 0; x < s.length(); x++){
                for(int i = 0; i <= x; i++){
                    String substring = s.substring(i, i+s.length()-x);
                    if(isPalindrome2(substring)){
                        palindromes.add(substring);
                        before = s.substring(0, s.indexOf(substring));
                        after = s.substring(s.indexOf(substring)+substring.length(), s.length());
                        nibbles.add(before);
                        nibbles.add(after);
                        x = s.length();
                        break;
                    };

                }

            }
        }

        return palindromes;
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
