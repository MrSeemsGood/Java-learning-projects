import java.util.Scanner;
import java.nio.file.*;

public class RollingHash {

    /*
    ROLLING HASH
    task: find a given substring in a given string
    variables:
        size: size of a substring
        base: assuming string is written in ASCII, base = 256
        p: prime number used for hashing, ~ the base, p = 211
        PRECOMPUTE:
        "magic" number: helper = [base^size] mod p
        multiplicative inverse of base: minv = [base^(-1)] mod p
    algorithm:
        convert string and substring into numbers base 256 (ASCII)
        set sliding window for rolling hash at [0:size]
        compute hash of a substring (key)
        on each step:
            - compute hash of sliding window and compare it to the hash of the key
                - if they don't match, continue. if they do, COMPARE THE STRINGS, because hashing is prone to collision (1/p on average)
                - if both hashes AND strings match, output the position
            - append next character from the string into the sliding window
            - pop first character from the sliding window
            - repeat
     */
    private int base;
    private int p;
    private String string;
    private int substringSize;
    private long hash;
    private long helper;

    public RollingHash(int base, int p, String string, int substringSize) {
        this.base = base;
        this.p = p;
        this.substringSize = substringSize;
        hash = 0;

        // put the first substringSize symbols of string into the hash
        // compute the helper
        for (int x = 0; x < substringSize; x++) {
            hash += string.charAt(x) * (long) Math.pow(base, substringSize - x - 1);
        }
        helper = (long) Math.pow(base, substringSize - 1);
    }

    public void appendNew(int newNumber) {
        hash = hash * base + newNumber;
    }

    public void skipOld(int oldNumber) {
        hash -= oldNumber * helper;
    }

    public long getHash() {
        /*
         hash the actual long value represented by a substring.
         there are many hash functions, and using 'x mod p' is the simplest one of them.
         */
        return hash % p;
    }
}

class RollingHashImplementation {

    String myString;
    String mySubstring = "";
    int size;

    private static final int BASE = 178;    // ASCII string base
    private static final int PRIME = 213;   // slightly larger than that

    public static String readFileAsString(String fileName) throws Exception {
        String data = "";
        data = new String(Files.readAllBytes(Paths.get(fileName)));
        return data;
    }

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("Type the substring to search for:");
        String mySubstring = sc.nextLine().toLowerCase();
        int size = mySubstring.length();
        String myString = readFileAsString("C:\\Users\\Egor\\Desktop\\Java\\MIT 6.006 Introduction to Algorithms and DSs\\src\\rolling_hash_test.txt").toLowerCase();

        RollingHash rollingHash = new RollingHash(BASE, PRIME, myString, size);

        // compute the key hash
        long keyHash = 0;
        for (int x = 0; x < size; x++) {
            keyHash += (int) mySubstring.charAt(x) * Math.pow(BASE, size - x - 1);
        }
        keyHash = keyHash % PRIME;
        System.out.println("Key substring hash: " + keyHash);

        // compute hashes of sliding windows
        for (int j = size; j < myString.length(); j++) {
            //System.out.println("Current hash: " + rollingHash.getHash());

            // compare hashes
            if (rollingHash.getHash() == keyHash) {
                System.out.println("Hashes match at index " + j + " at substring " + myString.substring(j - size, j));

                // compare the actual strings
                if (mySubstring.equals(myString.substring(j - size, j))) {
                    System.out.println("Strings match too!");
                } else {
                    System.out.println("Strings don't match. Hashes collided.");
                }
            }

            // change the current hashable string
            rollingHash.skipOld(myString.charAt(j - size));
            rollingHash.appendNew(myString.charAt(j));
        }

        sc.close();
    }
}
