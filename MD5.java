import java.util.*;
import java.nio.charset.StandardCharsets;

public class MD5 {

    static int[] S = {
        7
    };

    static int[] K = {
        (int)((1L << 32) * Math.abs(Math.sin(1)))
    };

    static int leftRotate(int x, int c) {
        return (x << c) | (x >>> (32 - c));
    }

    static byte[] oneRoundMD5(byte[] message) {

        int originalLength = message.length;
        int newLength = originalLength + 1;

        while (newLength % 64 != 56) newLength++;

        byte[] padded = new byte[newLength + 8];
        System.arraycopy(message, 0, padded, 0, originalLength);

        padded[originalLength] = (byte) 0x80;

        long bitLength = (long) originalLength * 8;
        for (int i = 0; i < 8; i++)
            padded[newLength + i] = (byte) (bitLength >>> (8 * i));

        int A = 0x67452301;
        int B = 0xefcdab89;
        int C = 0x98badcfe;
        int D = 0x10325476;

        int[] M = new int[16];

        for (int j = 0; j < 16; j++) {
            int index = j * 4;
            M[j] = ((padded[index] & 0xff)) |
                   ((padded[index + 1] & 0xff) << 8) |
                   ((padded[index + 2] & 0xff) << 16) |
                   ((padded[index + 3] & 0xff) << 24);
        }

        int a = A, b = B, c = C, d = D;

        int F = (b & c) | (~b & d);
        int g = 0;

        int temp = d;
        d = c;
        c = b;
        b = b + leftRotate(a + F + K[0] + M[g], S[0]);
        a = temp;

        A += a;
        B += b;
        C += c;
        D += d;

        byte[] digest = new byte[16];
        int[] result = {A, B, C, D};

        for (int i = 0; i < 4; i++) {
            digest[i * 4] = (byte) (result[i]);
            digest[i * 4 + 1] = (byte) (result[i] >>> 8);
            digest[i * 4 + 2] = (byte) (result[i] >>> 16);
            digest[i * 4 + 3] = (byte) (result[i] >>> 24);
        }

        return digest;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter message: ");
        String input = sc.nextLine();

        byte[] hash = oneRoundMD5(input.getBytes(StandardCharsets.UTF_8));

        StringBuilder hex = new StringBuilder();

        for (byte b : hash) {
            String h = Integer.toHexString(0xff & b);
            if (h.length() == 1) hex.append('0');
            hex.append(h);
        }

        System.out.println("One Round MD5: " + hex);
    }
}