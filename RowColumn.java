import java.util.*;

public class RowColumn {

    static int[] getKeyOrder(String key) {
        int n = key.length();
        int[] order = new int[n];
        boolean[] used = new boolean[n];

        for (int i = 0; i < n; i++) {
            int min = -1;
            for (int j = 0; j < n; j++) {
                if (!used[j]) {
                    if (min == -1 || key.charAt(j) < key.charAt(min))
                        min = j;
                }
            }
            order[i] = min;
            used[min] = true;
        }
        return order;
    }

    static String rowEncrypt(String text, String key) {
        text = text.replaceAll(" ", "").toUpperCase();
        key = key.toUpperCase();

        int cols = key.length();
        int rows = (text.length() + cols - 1) / cols;

        char[][] matrix = new char[rows][cols];
        int k = 0;

        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                matrix[i][j] = (k < text.length()) ? text.charAt(k++) : 'X';

        int[] order = getKeyOrder(key);
        String cipher = "";

        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                cipher += matrix[i][order[j]];

        return cipher;
    }

    static String rowDecrypt(String text, String key) {
        key = key.toUpperCase();

        int cols = key.length();
        int rows = text.length() / cols;

        char[][] matrix = new char[rows][cols];
        int[] order = getKeyOrder(key);

        int k = 0;
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                matrix[i][order[j]] = text.charAt(k++);

        String plain = "";

        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                plain += matrix[i][j];

        while (plain.endsWith("X"))
            plain = plain.substring(0, plain.length() - 1);

        return plain;
    }

    static String columnEncrypt(String text, String key) {
        text = text.replaceAll(" ", "").toUpperCase();
        key = key.toUpperCase();

        int cols = key.length();
        int rows = (text.length() + cols - 1) / cols;

        char[][] matrix = new char[rows][cols];
        int k = 0;

        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                matrix[i][j] = (k < text.length()) ? text.charAt(k++) : 'X';

        int[] order = getKeyOrder(key);
        String cipher = "";

        for (int j = 0; j < cols; j++)
            for (int i = 0; i < rows; i++)
                cipher += matrix[i][order[j]];

        return cipher;
    }

    static String columnDecrypt(String text, String key) {
        key = key.toUpperCase();

        int cols = key.length();
        int rows = text.length() / cols;

        char[][] matrix = new char[rows][cols];
        int[] order = getKeyOrder(key);

        int k = 0;
        for (int j = 0; j < cols; j++)
            for (int i = 0; i < rows; i++)
                matrix[i][order[j]] = text.charAt(k++);

        String plain = "";

        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                plain += matrix[i][j];

        while (plain.endsWith("X"))
            plain = plain.substring(0, plain.length() - 1);

        return plain;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        while (true) {

            System.out.println("\nTRANSPOSITION CIPHER");
            System.out.println("1. Row Transposition");
            System.out.println("2. Column Transposition");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");

            int ch = sc.nextInt();
            sc.nextLine();

            if (ch == 3) {
                System.out.println("Exiting...");
                break;
            }

            System.out.print("Enter key: ");
            String key = sc.nextLine();

            System.out.println("1. Encrypt");
            System.out.println("2. Decrypt");
            System.out.print("Enter option: ");
            int op = sc.nextInt();
            sc.nextLine();

            switch (ch) {

                case 1:
                    if (op == 1) {
                        System.out.print("Enter plaintext: ");
                        String pt = sc.nextLine();
                        System.out.println("Cipher Text: " + rowEncrypt(pt, key));
                    } else {
                        System.out.print("Enter ciphertext: ");
                        String ct = sc.nextLine();
                        System.out.println("Decrypted Text: " + rowDecrypt(ct, key));
                    }
                    break;

                case 2:
                    if (op == 1) {
                        System.out.print("Enter plaintext: ");
                        String pt = sc.nextLine();
                        System.out.println("Cipher Text: " + columnEncrypt(pt, key));
                    } else {
                        System.out.print("Enter ciphertext: ");
                        String ct = sc.nextLine();
                        System.out.println("Decrypted Text: " + columnDecrypt(ct, key));
                    }
                    break;

                default:
                    System.out.println("Invalid choice");
            }
        }

        sc.close();
    }
}