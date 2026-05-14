import java.util.*;

public class DES {

    static int[] initial_perm = {
        58,50,42,34,26,18,10,2,
        60,52,44,36,28,20,12,4,
        62,54,46,38,30,22,14,6,
        64,56,48,40,32,24,16,8,
        57,49,41,33,25,17,9,1,
        59,51,43,35,27,19,11,3,
        61,53,45,37,29,21,13,5,
        63,55,47,39,31,23,15,7
    };

    static int[] exp_d = {
        32,1,2,3,4,5,4,5,
        6,7,8,9,8,9,10,11,
        12,13,12,13,14,15,16,17,
        16,17,18,19,20,21,20,21,
        22,23,24,25,24,25,26,27,
        28,29,28,29,30,31,32,1
    };

    static int[] per = {
        16,7,20,21,29,12,28,17,
        1,15,23,26,5,18,31,10,
        2,8,24,14,32,27,3,9,
        19,13,30,6,22,11,4,25
    };

    static int[][] sbox = {
        {14,4,13,1,2,15,11,8,3,10,6,12,5,9,0,7},
        {0,15,7,4,14,2,13,1,10,6,12,11,9,5,3,8},
        {4,1,14,8,13,6,2,11,15,12,9,7,3,10,5,0},
        {15,12,8,2,4,9,1,7,5,11,3,14,10,0,6,13}
    };

    static String permute(String input, int[] table) {
        StringBuilder out = new StringBuilder();
        for (int i : table) out.append(input.charAt(i - 1));
        return out.toString();
    }

    static String xor(String a, String b) {
        StringBuilder r = new StringBuilder();
        for (int i = 0; i < a.length(); i++)
            r.append(a.charAt(i) == b.charAt(i) ? '0' : '1');
        return r.toString();
    }

    static String decToBin(int num) {
        String b = Integer.toBinaryString(num);
        while (b.length() < 4) b = "0" + b;
        return b;
    }

    static int binToDec(String b) {
        return Integer.parseInt(b, 2);
    }

    static String sboxSub(String input) {
        String out = "";
        for (int i = 0; i < 8; i++) {
            String block = input.substring(i * 6, i * 6 + 6);
            int row = binToDec("" + block.charAt(0) + block.charAt(5)) % 4;
            int col = binToDec(block.substring(1, 5));
            out += decToBin(sbox[row][col]);
        }
        return out;
    }

    static String toBinary(String text) {
        StringBuilder b = new StringBuilder();
        for (char c : text.toCharArray()) {
            String bin = Integer.toBinaryString(c);
            while (bin.length() < 8) bin = "0" + bin;
            b.append(bin);
        }
        return b.toString();
    }

    static String binaryToText(String bin) {
        StringBuilder t = new StringBuilder();
        for (int i = 0; i < bin.length(); i += 8) {
            int val = Integer.parseInt(bin.substring(i, i + 8), 2);
            t.append((char) val);
        }
        return t.toString();
    }

    static String encrypt(String ptBin, String keyBin) {

        System.out.println("\n--- ENCRYPTION ---");

        System.out.println("Plaintext Binary: " + ptBin);

        String ip = permute(ptBin, initial_perm);
        System.out.println("After Initial Permutation: " + ip);

        String L = ip.substring(0, 32);
        String R = ip.substring(32, 64);

        System.out.println("L0: " + L);
        System.out.println("R0: " + R);

        String expanded = permute(R, exp_d);
        System.out.println("Expansion: " + expanded);

        String xored = xor(expanded, keyBin);
        System.out.println("After XOR: " + xored);

        String sboxOut = sboxSub(xored);
        System.out.println("After S-Box: " + sboxOut);

        String perm = permute(sboxOut, per);
        System.out.println("After Permutation: " + perm);

        String newR = xor(L, perm);
        System.out.println("R1: " + newR);

        return R + newR;
    }

    static String decrypt(String ctBin, String keyBin) {

        System.out.println("\n--- DECRYPTION ---");

        System.out.println("Cipher Binary: " + ctBin);

        String L1 = ctBin.substring(0, 32);
        String R1 = ctBin.substring(32, 64);

        System.out.println("L1: " + L1);
        System.out.println("R1: " + R1);

        String expanded = permute(L1, exp_d);
        System.out.println("Expansion: " + expanded);

        String xored = xor(expanded, keyBin);
        System.out.println("After XOR: " + xored);

        String sboxOut = sboxSub(xored);
        System.out.println("After S-Box: " + sboxOut);

        String perm = permute(sboxOut, per);
        System.out.println("After Permutation: " + perm);

        String originalL = xor(R1, perm);
        System.out.println("Recovered L0: " + originalL);

        return originalL + L1;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter 8-character plaintext: ");
        String pt = sc.nextLine();

        while (pt.length() < 8) pt += "X";
        if (pt.length() > 8) pt = pt.substring(0, 8);

        System.out.print("Enter 6-character key: ");
        String key = sc.nextLine();

        while (key.length() < 6) key += "X";
        if (key.length() > 6) key = key.substring(0, 6);

        String ptBin = toBinary(pt);
        String keyBin = toBinary(key);

        System.out.println("Key Binary: " + keyBin);

        String cipher = encrypt(ptBin, keyBin);
        System.out.println("Cipher (Binary): " + cipher);

        String decryptedBin = decrypt(cipher, keyBin);
        String decryptedText = binaryToText(decryptedBin);

        System.out.println("Decrypted Binary: " + decryptedBin);
        System.out.println("Decrypted Text: " + decryptedText);
    }
}