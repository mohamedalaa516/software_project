@Test
void testSuccessfulLogin() {
    User u = new User("user@example.com", "H_password".hashCode()+"", "12345");
    AuthenticationService auth = new AuthenticationService(u);
    u.passwordHash = auth.hash("password");
    User logged = auth.login("user@example.com", "password");
    assertEquals(UserStatus.ACTIVE, logged.status);
}

@Test
void testFailedLoginAttempts() {
    User u = new User("user@example.com", "H_password".hashCode()+"", "12345");
    AuthenticationService auth = new AuthenticationService(u);
    u.passwordHash = auth.hash("password");
    for(int i=1;i<=5;i++) {
        try { auth.login("user@example.com","wrong"); } catch(Exception e) {}
    }
    assertEquals(UserStatus.LOCKED, u.status);
}
