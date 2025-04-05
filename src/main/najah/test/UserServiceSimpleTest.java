package main.najah.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import main.najah.code.UserService;

@DisplayName("UserService Tests")
public class UserServiceSimpleTest {
    UserService userService;

    @BeforeAll
    static void setupAll() {
        System.out.println("Starting UserService Tests");
    }

    @BeforeEach
    void setUp() {
        userService = new UserService();
        System.out.println("UserService test setup complete");
    }

    @ParameterizedTest
    @DisplayName("Test valid email formats")
    @ValueSource(strings = {"user@example.com", "admin@test.org", "hello@world.net"})
    void testValidEmails(String email) {
        assertTrue(userService.isValidEmail(email));
    }

    @Test
    @DisplayName("Test invalid email formats")
    void testInvalidEmails() {
        assertFalse(userService.isValidEmail("invalid-email"));
        assertFalse(userService.isValidEmail("user@com"));
        assertFalse(userService.isValidEmail(null));
    }

    @Test
    @DisplayName("Test successful authentication")
    void testValidAuthentication() {
        assertTrue(userService.authenticate("admin", "1234"));
    }

    @Test
    @DisplayName("Test failed authentication")
    void testInvalidAuthentication() {
        assertFalse(userService.authenticate("admin", "wrong"));
        assertFalse(userService.authenticate("user", "1234"));
    }

    @Test
    @Timeout(1)
    @DisplayName("Test authentication execution time")
    void testAuthenticationTimeout() {
        assertDoesNotThrow(() -> userService.authenticate("admin", "1234"));
    }

    @Test
    @Disabled("Fix: Enhance email validation for stricter checks")
    @DisplayName("Intentionally failing test")
    void testFailingEmailValidation() {
        assertTrue(userService.isValidEmail("plainaddress")); // Wrong expected value
    }

    @AfterEach
    void teardown() {
        System.out.println("UserService test execution complete");
    }

    @AfterAll
    static void teardownAll() {
        System.out.println("All UserService Tests Completed");
    }
}
