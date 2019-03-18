import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Your implementations of various string searching algorithms.
 *
 * @author Balkrishna Patel
 * @userid bpatel66
 * @GTID 9032023192
 * @version 1.0
 */
public class PatternMatching {

    /**
     * Knuth-Morris-Pratt (KMP) algorithm that relies on the failure table (also
     * called failure function). Works better with small alphabets.
     *
     * Make sure to implement the failure table before implementing this method.
     *
     * @throws IllegalArgumentException if the pattern is null or of length 0
     * @throws IllegalArgumentException if text or comparator is null
     * @param pattern the pattern you are searching for in a body of text
     * @param text the body of text where you search for pattern
     * @param comparator you MUST use this for checking character equality
     * @return list containing the starting index for each match found
     */
    public static List<Integer> kmp(CharSequence pattern, CharSequence text,
                                    CharacterComparator comparator) {
        /*if the pattern is null or is of length 0 throw an
        IllegalArguementException*/
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("The pattern that was passed "
                    + "in is either null or is length 0.");
        }
        /*if the text or comparator is null throw an IllegalArgumentException*/
        if (text == null || comparator == null) {
            throw new IllegalArgumentException("The text and/or comparator "
                    + "that was passed in is null");
        }
        /*get the failure table by calling the buildFailureTable method on
        the pattern*/
        int[] table = buildFailureTable(pattern, comparator);
        /*create an arraylist of integers that will contain the starting
        indices of all the matches*/
        List<Integer> matches = new ArrayList<>();
        if(text.length() < pattern.length()) {
            return matches;
        }
        /*initialize the pivot into the text as i = 0*/
        int i = 0;
        /*initialize the comparting index as j = 0*/
        int j = 0;
        /*if the pattern's length = 0 return an empty arraylist*/
        /*if (pattern.length() == 0) {
            return matches;
        }*/
        /*while the pivot does not go past the threshold of the length of the
         text - the length of the pattern continue the operation (it is
         impossible to find a match after this threshold is exceeded)*/
        while (i <= (text.length() - pattern.length())) {
            /*while j is less than the pattern and the character in the text
            at index i + j and the character in the pattern at index j are
            the same*/
            while (j  < pattern.length() && comparator.compare(text.charAt(i +
                    j), pattern.charAt(j)) == 0) {
                /*increment j*/
                j++;
            }
            /*if j is at index 0 then there was no match*/
            if (j == 0) {
                /*increment i*/
                i++;
            } else {
                /*if j is not at index 0 of the pattern then check if j is at
                 the end of the pattern*/
                if (j == pattern.length()) {
                    /*if j is at the end of the pattern add the the pivot of
                    the text to the matches list*/
                    matches.add(i);
                }
                /*if j found a mismatch anywhere except at the  beginning of
                the pattern and if a match was found*/
                /*i = i + j - failureTable(j - 1)*/
                i = i + j - table[j - 1];
                /*j = failureTable(j -1)*/
                j = table[j - 1];
            }
        }
        /*return all the matching indices*/
        return matches;
    }

    /**
     * Builds failure table that will be used to run the Knuth-Morris-Pratt
     * (KMP) algorithm.
     *
     * The table built should be the length of the input text.
     *
     * Note that a given index i will be the largest prefix of the pattern
     * indices [0..i] that is also a suffix of the pattern indices [1..i].
     * This means that index 0 of the returned table will always be equal to 0
     *
     * Ex. ababac
     *
     * table[0] = 0
     * table[1] = 0
     * table[2] = 1
     * table[3] = 2
     * table[4] = 3
     * table[5] = 0
     *
     * If the pattern is empty, return an empty array.
     *
     * @throws IllegalArgumentException if the pattern or comparator is null
     * @param pattern a {@code CharSequence} you're building a failure table for
     * @param comparator you MUST use this for checking character equality
     * @return integer array holding your failure table
     */
    public static int[] buildFailureTable(CharSequence pattern,
                                          CharacterComparator comparator) {
        if (pattern == null || comparator == null) {
            throw new IllegalArgumentException("The pattern and/or comparator"
                    + " that was passed in is null.");
        }
        /*Initialize the failure table to the length of the pattern*/
        int[] table = new int[pattern.length()];
        /*if the pattern length is 0 return a table of size 0*/
        if (pattern.length() == 0) {
            return table;
        }
        /*initialize i to 0 where i is the beginning of the prefix*/
        int i = 0;
        /*initialize j to 1 where j is the beginning of the suffix*/
        int j = 1;
        /*while j less than the length of the pattern*/
        while (j < pattern.length()) {
            /*if the character at i and j are the same*/
            if (comparator.compare(pattern.charAt(i), pattern.charAt(j)) == 0) {
                /*failuretable(j) = i + 1*/
                table[j] = i + 1;
                /*increment i*/
                i++;
                /*increment j*/
                j++;
            } else {
                /*if the characters at i and j are not the same check if i is
                 at the beginning of the pattern*/
                if (i == 0) {
                    /*if i is at the beginning of the pattern
                    failuretable(j) = i*/
                    table[j] = i;
                    /*increment j*/
                    j++;
                } else {
                    /*if i is not at the beginning of the pattern set i =
                    failuretable(i - 1)*/
                    i = table[i - 1];
                }
            }
        }
        /*return the table*/
        return table;
    }

    /**
     * Boyer Moore algorithm that relies on last occurrence table. Works better
     * with large alphabets.
     *
     * Make sure to implement the last occurrence table before implementing this
     * method.
     *
     * @throws IllegalArgumentException if the pattern is null or of length 0
     * @throws IllegalArgumentException if text or comparator is null
     * @param pattern the pattern you are searching for in a body of text
     * @param text the body of text where you search for the pattern
     * @param comparator you MUST use this for checking character equality
     * @return list containing the starting index for each match found
     */
    public static List<Integer> boyerMoore(CharSequence pattern,
                       CharSequence text, CharacterComparator comparator) {
        /*if the pattern is null or of length 0 throw exception*/
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("The pattern that was passed "
                    + "in is either null or of length 0.");
        }
        /*if text or comparator is null throw exception*/
        if (text == null || comparator == null) {
            throw new IllegalArgumentException("The text and/or comparator "
                    + "that was passed in is null.");
        }
        /*create the last occurrence table by call the buildLastTable method
        on the pattern*/
        Map<Character, Integer> lastTable = buildLastTable(pattern);
        /*create a list called matches to hold the indices of where a
        matching begins*/
        List<Integer> matches = new ArrayList<>();
        /*if (pattern.length() > text.length()) {
            return matches;
        }*/
        /*initialize a pivot into the text at 0*/
        int i = 0;
        /*while the pivot is less than or equal to the threshold continue
        operation (if it exceeds the threshold it is impossible to find a
        match)*/
        while (i <= text.length() - pattern.length()) {
            /*initalize j at the end of the pattern, because boyer moore
            starts comparing from the back*/
            int j = pattern.length() - 1;
            /*while j is greater than or equal to 0 and the character in the
            text at i + j and the character at j are the same*/
            while (j >= 0 && comparator.compare(text.charAt(i + j), pattern
                    .charAt(j)) == 0) {
                /*decrement j*/
                j--;
            }
            /*if j is -1 that means a match was found*/
            if (j == -1) {
                /*add the pivot to the list*/
                matches.add(i);
                /*increment i*/
                i++;
            } else {
                /*if a mismatch occurred, check the last occurrence table to
                find where the pivot needs to be shifted*/
                int shiftedIndex = lastTable.getOrDefault(text.charAt(i + j),
                        -1);
                if (j < shiftedIndex) {
                    /*if j is less than the index from the last occurrence
                    table increment i*/
                    i++;
                } else {
                    /*otherwise realign the the pattern to that index*/
                    i = i + (j - shiftedIndex);
                }
                /*if (shiftedIndex < j) {
                    *//*if j is *//*
                    i = i + (j - shiftedIndex);
                } else {
                    i += 1;
                }*/
            }
        }
        /*return a list of matching indices*/
        return matches;
    }

    /**
     * Builds last occurrence table that will be used to run the Boyer Moore
     * algorithm.
     *
     * Note that each char x will have an entry at table.get(x).
     * Each entry should be the last index of x where x is a particular
     * character in your pattern.
     * If x is not in the pattern, then the table will not contain the key x,
     * and you will have to check for that in your Boyer Moore implementation.
     *
     * Ex. octocat
     *
     * table.get(o) = 3
     * table.get(c) = 4
     * table.get(t) = 6
     * table.get(a) = 5
     * table.get(everything else) = null, which you will interpret in
     * Boyer-Moore as -1
     *
     * If the pattern is empty, return an empty map.
     *
     * @throws IllegalArgumentException if the pattern is null
     * @param pattern a {@code CharSequence} you are building last table for
     * @return a Map with keys of all of the characters in the pattern mapping
     *         to their last occurrence in the pattern
     */
    public static Map<Character, Integer> buildLastTable(CharSequence pattern) {
        if (pattern == null) {
            throw new IllegalArgumentException("The pattern that was passed "
                    + "in is null.");
        }
        /*build a hashmap to store the last occurrence of each unique
        character in the pattern*/
        Map<Character, Integer> lastTable = new HashMap<>();
        /*loop through the pattern*/
        for (int i = 0; i < pattern.length(); i++) {
            /*using the character itself and its index put the pair into the
            hashmap, if the same character appears multiple times the value
            will be replaced*/
            lastTable.put(pattern.charAt(i), i);
        }
        /*return the last occurrence table*/
        return lastTable;
    }

    /**
     * Prime base used for Rabin-Karp hashing.
     * DO NOT EDIT!
     */
    private static final int BASE = 137;

    /**
     * Runs the Rabin-Karp algorithm. This algorithms generates hashes for the
     * pattern and compares this hash to substrings of the text before doing
     * character by character comparisons.
     *
     * When the hashes are equal and you do character comparisons, compare
     * starting from the beginning of the pattern to the end, not from the end
     * to the beginning.
     *
     * You must use the Rabin-Karp Rolling Hash for this implementation. The
     * formula for it is:
     *
     * sum of: c * BASE ^ (pattern.length - 1 - i), where c is the integer
     * value of the current character, and i is the index of the character
     *
     * For example: Hashing "bunn" as a substring of "bunny" with base 137 hash
     * = b * 137 ^ 3 + u * 137 ^ 2 + n * 137 ^ 1 + n * 137 ^ 0 = 98 * 137 ^ 3 +
     * 117 * 137 ^ 2 + 110 * 137 ^ 1 + 110 * 137 ^ 0 = 254203747
     *
     * Note that since you are dealing with very large numbers here, your hash
     * will likely overflow, and that is fine for this implementation.
     *
     * Another key step for this algorithm is that updating the hashcode from
     * one substring to the next one must be O(1). To update the hash:
     *
     *  remove the oldChar times BASE raised to the length - 1, multiply by
     *  BASE, and add the newChar.
     *
     * For example: Shifting from "bunn" to "unny" in "bunny" with base 137
     * hash("unny") = (hash("bunn") - b * 137 ^ 3) * 137 + y * 137 ^ 0 =
     * (254203747 - 98 * 137 ^ 3) * 137 + 121 * 137 ^ 0 = 302928082
     *
     * Keep in mind that calculating exponents is not O(1) in general, so you'll
     * need to keep track of what BASE^{m - 1} is for updating the hash.
     *
     * @throws IllegalArgumentException if the pattern is null or of length 0
     * @throws IllegalArgumentException if text or comparator is null
     * @param pattern a string you're searching for in a body of text
     * @param text the body of text where you search for pattern
     * @param comparator the comparator to use when checking character equality
     * @return list containing the starting index for each match found
     */
    public static List<Integer> rabinKarp(CharSequence pattern,
                      CharSequence text, CharacterComparator comparator) {
        /*if the pattern is null or is length 0 throw exception*/
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("The pattern that was passed "
                    + "in is null or has a length of 0 (is empty).");
        }
        /*if text or comparator are null throw exception*/
        if (text == null || comparator == null) {
            throw new IllegalArgumentException("The text and/or comparator "
                    + "that was passed in null.");
        }
        /*create a list to store the beginning indices of all the matches*/
        List<Integer> matches = new ArrayList<>();
        /*if the text is shorted than the pattern return an empty list*/
        if (text.length() < pattern.length()) {
            return matches;
        }
        /*initialize a pow variable so that we can can perform the hash
        function effiecently without using math.pow*/
        int pow = 1;
        /*initalize the pattern hash as 0*/
        int patternHash = 0;
        /*initalize the text hash as 0*/
        int textHash = 0;
        /*for the length of the pattern*/
        for (int i = 0; i < pattern.length(); i++) {
            /*take the character in the pattern at index pattern.length - 1 -
             i*/
            char patternC = pattern.charAt(pattern.length() - 1 - i);
            /*multiply the character you just got from the pattern by pow*/
            int pCharHash = patternC * pow;
            /*add the hash of the char to the overall hash of the pattern*/
            patternHash += pCharHash;
            /*take the character in the text at index pattern.length- 1 - i*/
            char textC = text.charAt(pattern.length() - 1 - i);
            /*multiply the character you just got from the text by pow*/
            int tCharHash = textC * pow;
            /*add the hash of the char to the overall hash of the text*/
            textHash += tCharHash;
            /*if i is less than the pattern length - 1*/
            if (i < pattern.length() - 1) {
                /*pow = pow * BASE*/
                pow *= BASE;
            }
        }
        /*initialize a pivot into the text at i*/
        int i = 0;
        /*while the pivot does not exceed the threshold continue*/
        while (i <= text.length() - pattern.length()) {
            /*if the pattern's hash and the text's hash are the same*/
            if (patternHash == textHash) {
                /*initialize an index into the pattern to begin character
                comparisons*/
                int j = 0;
                /*while j is less than the length of the pattern and the
                character of the text at index i + j and character of the
                pattern at j are the same*/
                while (j < pattern.length() && comparator.
                        compare(text.charAt(i + j), pattern.charAt(j)) == 0) {
                    /*increment j*/
                    j++;
                }
                /*if j == the pattern's length you've found a match*/
                if (j == pattern.length()) {
                    /*add the index of the pivot to the list of matches*/
                    matches.add(i);
                }
            }
            /*increment the pivot*/
            i++;
            /*i the pivot is within the threshold recalculate the hash of the
             text*/
            if (i <= text.length() - pattern.length()) {
                textHash = (BASE * (textHash - text.charAt(i - 1) * pow))
                        + (text.charAt(i + pattern.length() - 1));
            }
        }
        /*return a list of the matches*/
        return matches;
    }
}