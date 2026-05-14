import java.math.BigInteger;
import java.util.Scanner;

public class ElGamal {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        BigInteger p = new BigInteger("23"); // Prime number
        BigInteger g = new BigInteger("5");  // Generator

        BigInteger x = new BigInteger("6");

        BigInteger y = g.modPow(x, p);

        System.out.println("Public Key (p, g, y):");
        System.out.println(p + " " + g + " " + y);

        System.out.print("Enter message number (< p): ");
        BigInteger m = sc.nextBigInteger();

        BigInteger k = new BigInteger("15");

        BigInteger c1 = g.modPow(k, p);

        BigInteger c2 =
                (m.multiply(y.modPow(k, p))).mod(p);

        System.out.println("\nEncrypted:");
        System.out.println("C1 = " + c1);
        System.out.println("C2 = " + c2);
        BigInteger s = c1.modPow(x, p);

        BigInteger sInverse = s.modInverse(p);

        BigInteger decrypted =
                (c2.multiply(sInverse)).mod(p);

        System.out.println("\nDecrypted Message: " + decrypted);
    }
}