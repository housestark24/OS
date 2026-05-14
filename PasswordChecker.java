public class PasswordChecker {

    public static void main(String[] args) {

        String p = args[0];
        int score = 0;

        if (p.length() >= 8) score++;

        if (p.matches(".*[A-Z].*")) score++;

        if (p.matches(".*[a-z].*")) score++;

        if (p.matches(".*\\d.*")) score++;

        if (p.matches(".*[^A-Za-z0-9].*")) score++;

        String strength =
                (score <= 2) ? "Weak" :
                (score <= 4) ? "Medium" :
                "Strong";

        System.out.println("Password : " + p);
        System.out.println("Strength : " + strength);
    }
}