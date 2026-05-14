import java.util.*;

public class Caesar {

    public static void main(String[] args) {

        Scanner scn = new Scanner(System.in);

        System.out.print("Enter key value: ");
        int key = scn.nextInt();
        scn.nextLine();

        while (true) {

            System.out.println("\nCAESAR CIPHER");
            System.out.println("1. Encrypt");
            System.out.println("2. Decrypt");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");

            int ch = scn.nextInt();
            scn.nextLine();

            switch (ch) {

                case 1: {
                    System.out.print("Enter PLAIN TEXT: ");
                    String data = scn.nextLine().toUpperCase();

                    StringBuilder CT = new StringBuilder();

                    for (char c : data.toCharArray()) {
                        if (Character.isLetter(c)) {
                            CT.append(encrypt(c, key));
                        } else {
                            CT.append(c);
                        }
                    }

                    System.out.println("CIPHER TEXT: " + CT.toString());
                    break;
                }

                case 2: {
                    System.out.print("Enter CIPHER TEXT: ");
                    String data = scn.nextLine().toUpperCase();

                    StringBuilder PT = new StringBuilder();

                    for (char c : data.toCharArray()) {
                        if (Character.isLetter(c)) {
                            PT.append(decrypt(c, key));
                        } else {
                            PT.append(c);
                        }
                    }

                    System.out.println("PLAIN TEXT: " + PT.toString());
                    break;
                }

                case 3:
                    scn.close();
                    return;

                default:
                    System.out.println("TRY AGAIN!");
            }
        }
    }

    public static char encrypt(char c, int key) {
        int n = c - 'A';
        int val = (n + key) % 26;
        return (char) ('A' + val);
    }

    public static char decrypt(char c, int key) {
        int n = c - 'A';
        int val = (n - key) % 26;
        if (val < 0) val += 26;
        return (char) ('A' + val);
    }
}