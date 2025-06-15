package Utils;

public class InputValidator {
    public static String validateStudentFields(String username, String fname, String lname, String email, String password) {
        if (!username.matches("^[a-zA-Z0-9_]{4,}$")) {
            return "Username must be at least 4 characters and contain only letters, numbers, or underscores.";
        }
        if (!fname.matches("^[a-zA-Z ]{2,}$")) {
            return "First name must be at least 2 letters and contain only letters and spaces.";
        }
        if (!lname.matches("^[a-zA-Z]{2,}$")) {
            return "Last name must be at least 2 letters and contain only letters.";
        }
        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.\\w{2,}$")) {
            return "Please enter a valid email address.";
        }
        if (password.length() < 6) {
            return "Password must be at least 6 characters long.";
        }
        return null; 
    }
}
