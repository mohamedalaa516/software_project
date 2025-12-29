import java.util.*;
import java.time.Instant;

enum UserStatus { ACTIVE, LOCKED }

class User {
    String email;
    String studentID;
    String passwordHash;
    UserStatus status = UserStatus.ACTIVE;
    int failedAttempts = 0;
    String resetToken;
    Instant tokenExpiry;

    User(String email, String passwordHash, String studentID) {
        this.email = email;
        this.passwordHash = passwordHash;
        this.studentID = studentID;
    }
}

class AuthenticationService {
    User user;
    final int MAX_ATTEMPTS = 5;
    final long TOKEN_TIME = 3600000;

    AuthenticationService(User user) { this.user = user; }

    User login(String email, String password) {
        if (!email.equals(user.email) || !hash(password).equals(user.passwordHash)) {
            user.failedAttempts++;
            if(user.failedAttempts >= MAX_ATTEMPTS)
                user.status = UserStatus.LOCKED;
            throw new RuntimeException("Invalid credentials");
        }
        user.failedAttempts = 0;
        return user;
    }

    String resetPassword(String email) {
        if (!email.equals(user.email)) return "If account exists, reset link sent";
        user.resetToken = UUID.randomUUID().toString();
        user.tokenExpiry = Instant.now().plusMillis(TOKEN_TIME);
        return "If account exists, reset link sent";
    }

    String hash(String password) {
        return "H_" + password.hashCode();
    }
}
