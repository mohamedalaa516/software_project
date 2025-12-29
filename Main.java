import java.util.*;
//implement
enum UserStatus {
    ACTIVE, LOCKED
}

class User {
    String email;
    String passwordHash;
    UserStatus status = UserStatus.ACTIVE;
    int failedAttempts = 0;
    String resetToken;
    Date tokenExpiry;

    User(String email, String passwordHash) {
        this.email = email;
        this.passwordHash = passwordHash;
    }
}

class AuthenticationService {

    User user;
    final int MAX_ATTEMPTS = 5;
    final long TOKEN_TIME = 3600000;

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
        user.tokenExpiry = new Date(System.currentTimeMillis() + TOKEN_TIME);

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