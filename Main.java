import java.util.*;
import java.time.Instant;

enum UserStatus {
    ACTIVE, LOCKED
}

class User {
    String email;
    String passwordHash;
    UserStatus status = UserStatus.ACTIVE;
    int failedAttempts = 0;
    String resetToken;
    Instant tokenExpiry;

    User(String email, String passwordHash) {
        this.email = email;
        this.passwordHash = passwordHash;
    }
}

class AuthenticationService {

    User user;
    final int MAX_ATTEMPTS = 5;
    final long TOKEN_TIME = 3600000; // 1 hour

    AuthenticationService(User user) {
        this.user = user;
    }

    String resetPassword(String email) {
        if (email == null || email.isEmpty())
            throw new IllegalArgumentException("Invalid email");

        if (!email.equals(user.email))
            return "If account exists, reset link sent";

        if (user.status == UserStatus.LOCKED)
            throw new RuntimeException("Account locked");

        user.resetToken = UUID.randomUUID().toString();
        user.tokenExpiry = Instant.now().plusMillis(TOKEN_TIME);

        return "If account exists, reset link sent";
    }

    User login(String email, String password) {
        if (!email.equals(user.email))
            throw new RuntimeException("Invalid credentials");

        if (!hash(password).equals(user.passwordHash)) {
            user.failedAttempts++;
            if (user.failedAttempts >= MAX_ATTEMPTS)
                user.status = UserStatus.LOCKED;
            throw new RuntimeException("Invalid credentials");
        }

        user.failedAttempts = 0;
        return user;
    }

    String hash(String password) {
        return "H_" + password.hashCode();
    }
}

public class Main {
    public static void main(String[] args) {
        User u = new User("user@example.com", "H_password".hashCode() + "");
        AuthenticationService auth = new AuthenticationService(u);

        System.out.println("---- Test 1: Successful login ----");
        try {
            u.passwordHash = auth.hash("password");
            auth.login("user@example.com", "password");
            System.out.println("Login successful, status: " + u.status);
        } catch (Exception e) {
            System.out.println("Login failed: " + e.getMessage());
        }

        System.out.println("\n---- Test 2: Failed login attempts ----");
        for (int i = 1; i <= 5; i++) {
            try {
                auth.login("user@example.com", "wrong");
            } catch (Exception e) {
                System.out.println("Attempt " + i + ": " + e.getMessage());
            }
        }
        System.out.println("User status after failed attempts: " + u.status);
        System.out.println("Failed attempts: " + u.failedAttempts);

        System.out.println("\n---- Test 3: Reset password ----");
        String result = auth.resetPassword("user@example.com");
        System.out.println("Reset result: " + result);
        System.out.println("Reset token: " + u.resetToken);
        System.out.println("Token expiry: " + u.tokenExpiry);
    }
}
