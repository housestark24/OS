import java.util.*;
import java.nio.charset.StandardCharsets;

public class SHA {

    static int[] K = {
        0x428a2f98
    };

    static int r(int x, int n) {
        return (x >>> n) | (x << (32 - n));
    }

    static byte[] oneRoundSHA(byte[] msg) {

        int[] H = {
            0x6a09e667, 0xbb67ae85, 0x3c6ef372, 0xa54ff53a,
            0x510e527f, 0x9b05688c, 0x1f83d9ab, 0x5be0cd19
        };

        int len = msg.length;
        int newLen = len + 1;
        while (newLen % 64 != 56) newLen++;

        byte[] p = new byte[newLen + 8];
        System.arraycopy(msg, 0, p, 0, len);
        p[len] = (byte) 0x80;

        long bits = (long) len * 8;
        for (int i = 0; i < 8; i++)
            p[p.length - 1 - i] = (byte) (bits >>> (8 * i));

        int[] W = new int[64];

        for (int j = 0; j < 16; j++) {
            int k = j * 4;
            W[j] = ((p[k] & 0xff) << 24) |
                   ((p[k + 1] & 0xff) << 16) |
                   ((p[k + 2] & 0xff) << 8) |
                   (p[k + 3] & 0xff);
        }

        int a = H[0], b = H[1], c = H[2], d = H[3];
        int e = H[4], f = H[5], g = H[6], h = H[7];

        int t1 = h + (r(e,6) ^ r(e,11) ^ r(e,25)) + ((e & f) ^ (~e & g)) + K[0] + W[0];
        int t2 = (r(a,2) ^ r(a,13) ^ r(a,22)) + ((a & b) ^ (a & c) ^ (b & c));

        h = g;
        g = f;
        f = e;
        e = d + t1;
        d = c;
        c = b;
        b = a;
        a = t1 + t2;

        H[0] += a; H[1] += b; H[2] += c; H[3] += d;
        H[4] += e; H[5] += f; H[6] += g; H[7] += h;

        byte[] out = new byte[32];

        for (int i = 0; i < 8; i++) {
            out[i * 4]     = (byte) (H[i] >>> 24);
            out[i * 4 + 1] = (byte) (H[i] >>> 16);
            out[i * 4 + 2] = (byte) (H[i] >>> 8);
            out[i * 4 + 3] = (byte) (H[i]);
        }

        return out;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter message: ");
        String input = sc.nextLine();

        byte[] hash = oneRoundSHA(input.getBytes(StandardCharsets.UTF_8));

        StringBuilder sb = new StringBuilder();
        for (byte b : hash)
            sb.append(String.format("%02x", b));

        System.out.println("One Round SHA-256: " + sb);
    }
}