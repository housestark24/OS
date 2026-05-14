import java.math.*;
public class Diffie {
    public static void main(String[] args) {
        BigInteger p=BigInteger.valueOf(23);
        BigInteger g=BigInteger.valueOf(5);

        BigInteger a=BigInteger.valueOf(6);
        BigInteger b=BigInteger.valueOf(15);

        BigInteger A=g.modPow(a,p);
        BigInteger B=g.modPow(b,p);

        BigInteger key1=B.modPow(a,p);
        BigInteger key2=A.modPow(b,p);

        System.out.println("Shared Key: "+key1);
    }
}