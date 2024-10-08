package github.tanishqtrivedi27.authService.utils;

public class ValidationUtil {
    public static Boolean validateEmail(String email) {
        return true;
    }

    public static Boolean validatePassword(String password) {
        return password.length() >= 8 && password.length() <= 16;
    }
}
