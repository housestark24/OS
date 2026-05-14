import java.util.*;

public class Playfair {

    static char[][] m = new char[5][5];

    static void generateMatrix(String key) {
        boolean[] used = new boolean[26];
        key = key.toLowerCase().replace("j", "i");

        int k = 0;

        for (char c : key.toCharArray()) {
            if (c < 'a' || c > 'z') continue;
            if (!used[c - 'a']) {
                m[k / 5][k % 5] = c;
                used[c - 'a'] = true;
                k++;
            }
        }

        for (char c = 'a'; c <= 'z'; c++) {
            if (c == 'j') continue;
            if (!used[c - 'a']) {
                m[k / 5][k % 5] = c;
                k++;
            }
        }
    }

    static String format(String s) {
        s = s.toLowerCase().replace("j", "i").replaceAll("[^a-z]", "");
        StringBuilder t = new StringBuilder();

        for (int i = 0; i < s.length(); i++) {
            t.append(s.charAt(i));
            if (i + 1 < s.length() && s.charAt(i) == s.charAt(i + 1)) {
                t.append('x');
            }
        }

        if (t.length() % 2 != 0) t.append('x');

        return t.toString();
    }

    static String playfair(String s, int mode) {
        StringBuilder r = new StringBuilder();

        for (int i = 0; i < s.length(); i += 2) {
            char a = s.charAt(i);
            char b = s.charAt(i + 1);

            int r1 = 0, c1 = 0, r2 = 0, c2 = 0;

            for (int r0 = 0; r0 < 5; r0++) {
                for (int c0 = 0; c0 < 5; c0++) {
                    if (m[r0][c0] == a) { r1 = r0; c1 = c0; }
                    if (m[r0][c0] == b) { r2 = r0; c2 = c0; }
                }
            }

            if (r1 == r2) {
                if (mode == 1) {
                    r.append(m[r1][(c1 + 1) % 5]);
                    r.append(m[r2][(c2 + 1) % 5]);
                } else {
                    r.append(m[r1][(c1 + 4) % 5]);
                    r.append(m[r2][(c2 + 4) % 5]);
                }
            } else if (c1 == c2) {
                if (mode == 1) {
                    r.append(m[(r1 + 1) % 5][c1]);
                    r.append(m[(r2 + 1) % 5][c2]);
                } else {
                    r.append(m[(r1 + 4) % 5][c1]);
                    r.append(m[(r2 + 4) % 5][c2]);
                }
            } else {
                r.append(m[r1][c2]);
                r.append(m[r2][c1]);
            }
        }

        return r.toString();
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter key: ");
        String key = sc.nextLine();

        generateMatrix(key);

        while (true) {

            System.out.println("\n1. Encrypt");
            System.out.println("2. Decrypt");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");

            int ch = sc.nextInt();
            sc.nextLine();

            switch (ch) {

                case 1:
                    System.out.print("Enter plaintext: ");
                    String pt = format(sc.nextLine());
                    System.out.println("Ciphertext: " + playfair(pt, 1));
                    break;

                case 2:
                    System.out.print("Enter ciphertext: ");
                    String ct = sc.nextLine();
                    System.out.println("Plaintext: " + playfair(ct, 2));
                    break;

                case 3:
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice");
            }
        }
    }
}