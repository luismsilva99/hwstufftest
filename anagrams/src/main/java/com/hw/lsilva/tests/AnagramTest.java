package com.hw.lsilva.tests;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class AnagramTest implements Test {

    private String strA;
    private String strB;

    /**
     * An anagram test
     *
     * @param strA String to be tested
     * @param strB String to be tested
     */
    public AnagramTest(String strA, String strB) {
        this.strA = strA;
        this.strB = strB;
    }

    /**
     * Check if the provided Strings are anagrams
     *
     * @return true if the provided Strings are anagrams or false if they aren't
     */
    public boolean test() {

        if (strA == null || strB == null) {
            return false;
        }

        strA = strA.replaceAll("\\s+", ""); //remove white space
        strA = strA.replaceAll("[^a-zA-Z ]", ""); //remove all punctuation
        strA = strA.toUpperCase(); //convert to Upper case

        strB = strB.replaceAll("\\s+", ""); //remove white space
        strB = strB.replaceAll("[^a-zA-Z ]", ""); //remove all punctuation
        strB = strB.toUpperCase(); //convert to Upper case

        if (strA.length() != strB.length()) {
            return false;
        }

        Map<String, Integer> anagramsHash = new HashMap(strA.length());

        for (int i = 0; i < strA.length(); i++) {
            String charAtA = String.valueOf(strA.charAt(i));
            if (anagramsHash.containsKey(charAtA)) {
                int nrAChars = anagramsHash.get(charAtA);
                anagramsHash.put(charAtA, ++nrAChars);
            } else {
                anagramsHash.put(charAtA, 1);
            }

            String charAtB = String.valueOf(strB.charAt(i));
            if (anagramsHash.containsKey(charAtB)) {
                int nrBChars = anagramsHash.get(charAtB);
                anagramsHash.put(charAtB, --nrBChars);
            } else {
                anagramsHash.put(charAtB, -1);
            }
        }

        return anagramsHash.values().stream().allMatch(o -> o == 0);
    }

    /**
     * Prints the AnagramTest result
     *
     * @return the anagram test result
     */
    @Override
    public String toString() {
        String areAnagrams = test() ? "are" : "aren't";

        return "AnagramTest{" +
                "strA='" + strA + '\'' +
                ", strB='" + strB + '\'' +
                '}' +
                " " + areAnagrams + " anagrams";
    }
}
