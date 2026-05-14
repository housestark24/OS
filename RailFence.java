import java.util.*;

public class RailFence {

    static String encrypt(String text, int key) {

        text = text.replaceAll("\\s+", "");
        char[][] rail = new char[key][text.length()];

        for (int i = 0; i < key; i++)
            Arrays.fill(rail[i], '\n');

        boolean down = false;
        int row = 0, col = 0;

        for (int i = 0; i < text.length(); i++) {
            if (row == 0 || row == key - 1)
                down = !down;

            rail[row][col++] = text.charAt(i);
            row += down ? 1 : -1;
        }

        String cipher = "";

        for (int i = 0; i < key; i++)
            for (int j = 0; j < text.length(); j++)
                if (rail[i][j] != '\n')
                    cipher += rail[i][j];

        return cipher;
    }

    static String decrypt(String cipher, int key) {

        char[][] rail = new char[key][cipher.length()];

        for (int i = 0; i < key; i++)
            Arrays.fill(rail[i], '\n');

        boolean down = false;
        int row = 0, col = 0;

        for (int i = 0; i < cipher.length(); i++) {
            if (row == 0 || row == key - 1)
                down = !down;

            rail[row][col++] = '*';
            row += down ? 1 : -1;
        }

        int index = 0;

        for (int i = 0; i < key; i++)
            for (int j = 0; j < cipher.length(); j++)
                if (rail[i][j] == '*' && index < cipher.length())
                    rail[i][j] = cipher.charAt(index++);

        String plain = "";

        down = false;
        row = 0;
        col = 0;

        for (int i = 0; i < cipher.length(); i++) {
            if (row == 0 || row == key - 1)
                down = !down;

            plain += rail[row][col++];
            row += down ? 1 : -1;
        }

        return plain;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        while (true) {

            System.out.println("\nRAIL FENCE CIPHER");
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

                    System.out.print("Enter number of rails: ");
                    int key1 = sc.nextInt();
                    sc.nextLine();

                    System.out.println("Encrypted Text: " + encrypt(pt, key1));
                    break;

                case 2:
                    System.out.print("Enter ciphertext: ");
                    String ct = sc.nextLine();

                    System.out.print("Enter number of rails: ");
                    int key2 = sc.nextInt();
                    sc.nextLine();

                    System.out.println("Decrypted Text: " + decrypt(ct, key2));
                    break;

                case 3:
                    System.out.println("Exiting...");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice");
            }
        }
    }
}