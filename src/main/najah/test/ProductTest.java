package main.najah.test;

import main.najah.code.Product;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;

@DisplayName("Product Tests")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Execution(ExecutionMode.CONCURRENT)
public class ProductTest {

    Product p;

    @BeforeAll
    static void setupAll() {
        System.out.println("Starting Product Tests");
    }

    @BeforeEach
    void setUp() {
        p = new Product("TestProduct", 100);
        System.out.println("Product test setup complete");
    }

    @Test
    @Order(1)
    @DisplayName("Test product creation with valid price")
    void testValidProductCreation() {
        assertAll("Valid product fields",
            () -> assertEquals("TestProduct", p.getName()),
            () -> assertEquals(100, p.getPrice()),
            () -> assertEquals(0, p.getDiscount())
        );
    }

    @Test
    @Order(2)
    @DisplayName("Test product creation with zero price")
    void testZeroPrice() {
        Product product = new Product("Freebie", 0);
        assertEquals(0, product.getPrice());
    }

    @Test
    @Order(3)
    @DisplayName("Test product creation with negative price (should throw exception)")
    void testNegativePrice() {
        assertThrows(IllegalArgumentException.class, () -> new Product("InvalidProduct", -10));
    }

    @ParameterizedTest
    @ValueSource(doubles = {0, 10, 25, 50})
    @DisplayName("Test valid discount boundaries and values")
    void testValidDiscount(double discount) {
        p.applyDiscount(discount);
        assertEquals(discount, p.getDiscount());
    }

    @Test
    @Order(4)
    @DisplayName("Test lower bound invalid discount")
    void testDiscountNegative() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> p.applyDiscount(-1));
        assertEquals("Invalid discount", e.getMessage());
    }

    @Test
    @Order(5)
    @DisplayName("Test upper bound invalid discount")
    void testDiscountAboveFifty() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> p.applyDiscount(51));
        assertEquals("Invalid discount", e.getMessage());
    }

    @Test
    @Order(6)
    @DisplayName("Test final price calculation after 20% discount")
    void testFinalPrice() {
        p.applyDiscount(20);
        assertEquals(80.0, p.getFinalPrice(), 0.01);
    }

    @Test
    @Order(7)
    @DisplayName("Test get methods with multiple assertions")
    void testGetters() {
        assertAll("Product getters",
            () -> assertEquals("TestProduct", p.getName()),
            () -> assertEquals(100.0, p.getPrice()),
            () -> assertEquals(0.0, p.getDiscount())
        );
    }

    @Test
    @Order(8)
    @DisplayName("Test discount application multiple times")
    void testMultipleDiscountApplications() {
        p.applyDiscount(10);
        assertEquals(90.0, p.getFinalPrice(), 0.01);
        p.applyDiscount(25);
        assertEquals(75.0, p.getFinalPrice(), 0.01);
    }

    @Test
    @Disabled("Fix: handle incorrect expectation")
    @DisplayName("Intentionally failing test - wrong expected value")
    void testFailingFinalPrice() {
        p.applyDiscount(50);
        assertEquals(60, p.getFinalPrice()); // Should be 50
    }

    @Test
    @Order(9)
    @DisplayName("Timeout test to check performance")
    void testPerformance() {
        assertTimeout(Duration.ofMillis(50), () -> {
            Product fast = new Product("Speedy", 100);
            fast.applyDiscount(10);
            assertEquals(90.0, fast.getFinalPrice(), 0.01);
        });
    }

    @AfterEach
    void teardown() {
        System.out.println("Product test execution complete");
    }

    @AfterAll
    static void teardownAll() {
        System.out.println("All Product Tests Completed");
    }
}
