import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.*;

public class DESlib {

    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter plaintext (8 chars): ");
        String text = sc.nextLine();

        while (text.length() < 8) text += "X";
        if (text.length() > 8) text = text.substring(0, 8);

        System.out.print("Enter key (8 chars): ");
        String keyStr = sc.nextLine();

        while (keyStr.length() < 8) keyStr += "X";
        if (keyStr.length() > 8) keyStr = keyStr.substring(0, 8);

        byte[] keyBytes = keyStr.getBytes();
        SecretKey key = new SecretKeySpec(keyBytes, "DES");

        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encrypted = cipher.doFinal(text.getBytes());

        System.out.println("Encrypted: " + Base64.getEncoder().encodeToString(encrypted));

        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decrypted = cipher.doFinal(encrypted);

        System.out.println("Decrypted: " + new String(decrypted));
    }
}