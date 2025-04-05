package main.najah.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import main.najah.code.Calculator;

@TestMethodOrder(OrderAnnotation.class)
@DisplayName("Calculator Tests")
public class CalculatorTest {
    Calculator calc;

    @BeforeAll
    static void setupAll() {
        System.out.println("Starting Calculator Tests");
    }

    @BeforeEach
    void setUp() {
        calc = new Calculator();
        System.out.println("Test case setup complete");
    }

    @Test
    @Order(1)
    @DisplayName("Test addition with multiple numbers")
    void testAddition() {
        assertEquals(10, calc.add(2, 3, 5));
        assertEquals(0, calc.add());
        assertEquals(-5, calc.add(-2, -3));
        assertEquals(100, calc.add(50, 50));
        assertEquals(-100, calc.add(-50, -50));
        assertEquals(5, calc.add(10, -5));
    }

    @Test
    @Order(2)
    @DisplayName("Test division with valid numbers")
    void testValidDivision() {
        assertEquals(2, calc.divide(10, 5));
        assertEquals(0, calc.divide(0, 5));
        assertEquals(-2, calc.divide(-10, 5));
        assertEquals(2, calc.divide(-10, -5));
    }

    @Test
    @Order(3)
    @DisplayName("Test division by zero (should throw exception)")
    void testDivisionByZero() {
        assertThrows(ArithmeticException.class, () -> calc.divide(5, 0));
    }

    @ParameterizedTest
    @Order(4)
    @DisplayName("Test factorial with multiple values")
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6})
    void testFactorial(int n) {
        int expected = switch (n) {
            case 0, 1 -> 1;
            case 2 -> 2;
            case 3 -> 6;
            case 4 -> 24;
            case 5 -> 120;
            case 6 -> 720;
            default -> throw new IllegalStateException("Unexpected value: " + n);
        };
        assertEquals(expected, calc.factorial(n));
    }

    @Test
    @Order(5)
    @DisplayName("Test factorial of negative number (should throw exception)")
    void testNegativeFactorial() {
        assertThrows(IllegalArgumentException.class, () -> calc.factorial(-3));
    }

    @Test
    @Order(6)
    @DisplayName("Test factorial execution time")
    @Timeout(1)
    void testFactorialTimeout() {
        assertDoesNotThrow(() -> calc.factorial(10));
    }

    @Test
    @Order(7)
    @Disabled("Fix: Handle edge cases where factorial might overflow")
    @DisplayName("Intentionally failing test")
    void testFailingFactorial() {
        assertEquals(3628800, calc.factorial(10)); // Fixed expected value
    }

    @AfterEach
    void teardown() {
        System.out.println("Test case execution complete");
    }

    @AfterAll
    static void teardownAll() {
        System.out.println("All Calculator Tests Completed");
    }
}