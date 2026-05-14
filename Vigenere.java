import java.util.*;

public class Vigenere {

    static String makeKey(String text, String key) {
        StringBuilder k = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            k.append(key.charAt(i % key.length()));
        }
        return k.toString();
    }

    static String encrypt(String text, String key) {
        String k = makeKey(text, key);
        StringBuilder r = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char c = (char) (((text.charAt(i) - 'a') + (k.charAt(i) - 'a')) % 26 + 'a');
            r.append(c);
        }
        return r.toString();
    }

    static String decrypt(String text, String key) {
        String k = makeKey(text, key);
        StringBuilder r = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char c = (char) (((text.charAt(i) - 'a') - (k.charAt(i) - 'a') + 26) % 26 + 'a');
            r.append(c);
        }
        return r.toString();
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter key: ");
        String key = sc.nextLine().toLowerCase().replaceAll("[^a-z]", "");

        while (true) {

            System.out.println("\nChoices:");
            System.out.println("1. Encrypt");
            System.out.println("2. Decrypt");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");

            int ch = sc.nextInt();
            sc.nextLine();

            switch (ch) {

                case 1:
                    System.out.print("Enter plaintext: ");
                    String pt = sc.nextLine().toLowerCase().replaceAll("[^a-z]", "");
                    System.out.println("Ciphertext: " + encrypt(pt, key));
                    break;

                case 2:
                    System.out.print("Enter ciphertext: ");
                    String ct = sc.nextLine().toLowerCase().replaceAll("[^a-z]", "");
                    System.out.println("Plaintext: " + decrypt(ct, key));
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