package ttbank.example.ttbank;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtils {
    // Method to hash a password
    public static String hashPassword(String plainTextPassword){
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }

    // Method to check the password against the hashed version
    public static boolean checkPassword(String plainTextPassword, String hashedPassword) {
        return BCrypt.checkpw(plainTextPassword, hashedPassword);
    }

}
