import java.util.*;

public class HillCipher {

    static int n;
    static int[][] key;
    static int[][] inverse;

    static String prepare(String text) {
        text = text.toLowerCase().replaceAll("[^a-z]", "");
        while (text.length() % n != 0)
            text += "x";
        return text;
    }

    static String encrypt(String text) {
        text = prepare(text);
        StringBuilder cipher = new StringBuilder();

        for (int i = 0; i < text.length(); i += n) {
            for (int row = 0; row < n; row++) {
                int sum = 0;
                for (int col = 0; col < n; col++) {
                    sum += key[row][col] * (text.charAt(i + col) - 'a');
                }
                cipher.append((char) ((sum % 26 + 26) % 26 + 'a'));
            }
        }
        return cipher.toString();
    }

    static int determinant(int[][] matrix, int size) {
        if (size == 1) return matrix[0][0];

        int det = 0;
        int[][] sub = new int[size][size];

        for (int x = 0; x < size; x++) {
            int subi = 0;
            for (int i = 1; i < size; i++) {
                int subj = 0;
                for (int j = 0; j < size; j++) {
                    if (j == x) continue;
                    sub[subi][subj++] = matrix[i][j];
                }
                subi++;
            }
            det += (int) Math.pow(-1, x) * matrix[0][x] * determinant(sub, size - 1);
        }
        return det;
    }

    static void findInverse() {
        int det = determinant(key, n) % 26;
        if (det < 0) det += 26;

        int detInv = -1;
        for (int i = 1; i < 26; i++) {
            if ((det * i) % 26 == 1) {
                detInv = i;
                break;
            }
        }

        if (detInv == -1) {
            System.out.println("Invalid Key Matrix! No inverse exists.");
            System.exit(0);
        }

        inverse = new int[n][n];
        int[][] adj = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {

                int[][] sub = new int[n - 1][n - 1];
                int r = 0;

                for (int x = 0; x < n; x++) {
                    if (x == i) continue;
                    int c = 0;
                    for (int y = 0; y < n; y++) {
                        if (y == j) continue;
                        sub[r][c++] = key[x][y];
                    }
                    r++;
                }

                adj[j][i] = (int) (Math.pow(-1, i + j) * determinant(sub, n - 1));
                adj[j][i] = ((adj[j][i] % 26) + 26) % 26;
            }
        }

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                inverse[i][j] = (adj[i][j] * detInv) % 26;
    }

    static String decrypt(String text) {
        StringBuilder plain = new StringBuilder();

        for (int i = 0; i < text.length(); i += n) {
            for (int row = 0; row < n; row++) {
                int sum = 0;
                for (int col = 0; col < n; col++) {
                    sum += inverse[row][col] * (text.charAt(i + col) - 'a');
                }
                plain.append((char) ((sum % 26 + 26) % 26 + 'a'));
            }
        }
        return plain.toString();
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter matrix size (n): ");
        n = sc.nextInt();

        key = new int[n][n];

        System.out.println("Enter key matrix:");
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                key[i][j] = sc.nextInt();

        findInverse();

        sc.nextLine();

        while (true) {

            System.out.println("\nHILL CIPHER");
            System.out.println("1. Encrypt");
            System.out.println("2. Decrypt");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");

            int ch = sc.nextInt();
            sc.nextLine();

            switch (ch) {

                case 1:
                    System.out.print("Enter plaintext: ");
                    String pt = sc.nextLine();
                    System.out.println("Cipher Text: " + encrypt(pt));
                    break;

                case 2:
                    System.out.print("Enter ciphertext: ");
                    String ct = sc.nextLine().toLowerCase().replaceAll("[^a-z]", "");
                    System.out.println("Decrypted Text: " + decrypt(ct));
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