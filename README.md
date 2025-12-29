# Authentication Service

A Java-based authentication system with user login, password reset, and account lockout functionality.

## Project Structure

```
.
├── Main.java          # User and AuthenticationService classes
├── Test.java          # JUnit test cases
└── README.md
```

## Classes

### User
Represents a user with the following properties:
- `email` - User's email address
- `studentID` - Student identifier
- `passwordHash` - Hashed password
- `status` - User status (ACTIVE or LOCKED)
- `failedAttempts` - Count of failed login attempts
- `resetToken` - Password reset token
- `tokenExpiry` - Token expiration time

### AuthenticationService
Handles authentication operations:
- `login(email, password)` - Authenticate user
- `resetPassword(email)` - Generate password reset token
- `hash(password)` - Hash a password

## Running the Project

### Option 1: Terminal (Command Line)

#### Prerequisites
Download JUnit libraries:
```bash
# Download JUnit 4
wget https://repo1.maven.org/maven2/junit/junit/4.13.2/junit-4.13.2.jar
wget https://repo1.maven.org/maven2/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar
```

#### Compile
```bash
javac Main.java Test.java
```

#### Run Tests
```bash
java -cp .:junit-4.13.2.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore Test
```

### Option 2: IDE (Eclipse/IntelliJ)

1. Import the project into your IDE
2. Right-click on `Test.java`
3. Select **"Run As"** → **"JUnit Test"**
4. View results in the Test Results panel

### Option 3: Maven

#### Prerequisites
Ensure you have Maven installed.

#### Create pom.xml (if not exists)
```xml
<project>
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.auth</groupId>
    <artifactId>authentication-service</artifactId>
    <version>1.0.0</version>
    
    <dependencies>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.13.2</version>
            <scope>test</scope>
        </dependency>
    </dependencies>
</project>
```

#### Run Tests
```bash
mvn test
```

### Option 4: Gradle

#### Create build.gradle (if not exists)
```gradle
plugins {
    id 'java'
}

dependencies {
    testImplementation 'junit:junit:4.13.2'
}
```

#### Run Tests
```bash
gradle test
```

## Test Cases

### testSuccessfulLogin()
Verifies that a user with correct credentials can successfully log in and remains in ACTIVE status.

### testFailedLoginAttempts()
Verifies that after 5 failed login attempts, the user account is locked with LOCKED status.

## Expected Output

```
JUnit version 4.13.2
..
Time: 0.123

OK (2 tests)
```

## Security Notes

⚠️ **Warning**: This implementation is for educational purposes only. The hashing mechanism is not cryptographically secure and should not be used in production. For production use:

- Use `BCrypt` or `Argon2` for password hashing
- Implement proper token validation
- Add rate limiting and logging
- Use secure random token generation

## License

MIT License
