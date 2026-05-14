import java.math.BigInteger;
import java.util.Scanner;

public class RSA {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        BigInteger p = new BigInteger("17");
        BigInteger q = new BigInteger("11");

        BigInteger n = p.multiply(q);

        BigInteger phi =
                (p.subtract(BigInteger.ONE))
                .multiply(q.subtract(BigInteger.ONE));

        BigInteger e = new BigInteger("7");

        BigInteger d = e.modInverse(phi);

        System.out.println("Public Key (e, n):");
        System.out.println("(" + e + ", " + n + ")");

        System.out.println("Private Key (d, n):");
        System.out.println("(" + d + ", " + n + ")");
        System.out.print("Enter message number (< n): ");
        BigInteger m = sc.nextBigInteger();
        BigInteger cipher = m.modPow(e, n);

        System.out.println("\nEncrypted Message: " + cipher);
        BigInteger decrypted = cipher.modPow(d, n);

        System.out.println("Decrypted Message: " + decrypted);
    }
}