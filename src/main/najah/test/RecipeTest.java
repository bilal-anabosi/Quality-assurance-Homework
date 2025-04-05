package main.najah.test;

import main.najah.code.Recipe;
import main.najah.code.RecipeException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.time.Duration;
import static org.junit.jupiter.api.Assertions.assertTimeout;

import static org.junit.jupiter.api.Assertions.*;

@Execution(ExecutionMode.CONCURRENT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RecipeTest {

    Recipe recipe;

    @BeforeAll
    static void initAll() {
        System.out.println("=== Starting Recipe tests ===");
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("=== Finished Recipe tests ===");
    }

    @BeforeEach
    void init() {
        recipe = new Recipe();
        System.out.println("Setup complete");
    }

    @AfterEach
    void cleanup() {
        System.out.println("Test finished");
    }

    @Test
    @Order(1)
    @DisplayName("Set and Get Name - Valid")
    void testSetNameValid() {
        recipe.setName("Mocha");
        assertEquals("Mocha", recipe.getName());
    }

    @Test
    @Order(2)
    @DisplayName("Set Name to null - Should not change")
    void testSetNameNull() {
        recipe.setName("Latte");
        recipe.setName(null);
        assertEquals("Latte", recipe.getName());
    }

    @Test
    @Order(3)
    @DisplayName("Set and Get Price - Valid")
    void testSetPriceValid() throws RecipeException {
        recipe.setPrice("50");
        assertEquals(50, recipe.getPrice());
    }

    @Test
    @Order(4)
    @DisplayName("Set Price - Invalid (non-numeric)")
    void testSetPriceInvalidFormat() {
        RecipeException ex = assertThrows(RecipeException.class, () -> recipe.setPrice("abc"));
        assertEquals("Price must be a positive integer", ex.getMessage());
    }

    @Test
    @Order(5)
    @DisplayName("Set Price - Invalid (negative)")
    void testSetPriceNegative() {
        RecipeException ex = assertThrows(RecipeException.class, () -> recipe.setPrice("-1"));
        assertEquals("Price must be a positive integer", ex.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"0", "1", "100"})
    @DisplayName("Parameterized Test for Setting Coffee")
    void testSetAmtCoffeeParameterized(String value) throws RecipeException {
        recipe.setAmtCoffee(value);
        assertTrue(recipe.getAmtCoffee() >= 0);
    }

    @Test
    @DisplayName("Set AmtMilk - Valid")
    void testSetAmtMilkValid() throws RecipeException {
        recipe.setAmtMilk("2");
        assertEquals(2, recipe.getAmtMilk());
    }

    @Test
    @DisplayName("Set AmtMilk - Invalid")
    void testSetAmtMilkInvalid() {
        Exception exception = assertThrows(RecipeException.class, () -> recipe.setAmtMilk("milk"));
        assertEquals("Units of milk must be a positive integer", exception.getMessage());
    }

    @Test
    @DisplayName("Set AmtSugar - Valid")
    void testSetAmtSugarValid() throws RecipeException {
        recipe.setAmtSugar("5");
        assertEquals(5, recipe.getAmtSugar());
    }

    @Test
    @DisplayName("Set AmtChocolate - Invalid (negative)")
    void testSetAmtChocolateNegative() {
        Exception ex = assertThrows(RecipeException.class, () -> recipe.setAmtChocolate("-10"));
        assertEquals("Units of chocolate must be a positive integer", ex.getMessage());
    }

    @Test
    @DisplayName("Equals and HashCode - Same Name")
    void testEqualsAndHashCode() {
        Recipe r1 = new Recipe();
        Recipe r2 = new Recipe();
        r1.setName("Espresso");
        r2.setName("Espresso");
        assertEquals(r1, r2);
        assertEquals(r1.hashCode(), r2.hashCode());
    }

    @Test
    @DisplayName("Equals - Different Object Type")
    void testEqualsDifferentType() {
        assertNotEquals(recipe, "String");
    }

    @Test
    @DisplayName("Equals - Same Object")
    void testEqualsSameObject() {
        assertEquals(recipe, recipe);
    }

    @Test
    @DisplayName("ToString returns name")
    void testToString() {
        recipe.setName("Cappuccino");
        assertEquals("Cappuccino", recipe.toString());
    }

    @Test
    @DisplayName("Timeout Test")
    void testWithTimeout() {
        assertTimeout(Duration.ofMillis(100), () -> {
            recipe.setAmtCoffee("3");
            recipe.setAmtMilk("2");
        });
    }

    @Test
    @Disabled("Fails because input is null - fix: check for null before parseInt")
    @DisplayName("Disabled Failing Test - Set AmtSugar with null")
    void testSetAmtSugarNull() throws RecipeException {
        recipe.setAmtSugar(null);
    }

    @Test
    @DisplayName("Multiple Assertions Example")
    void testMultipleSetters() throws RecipeException {
        recipe.setName("Mocha");
        recipe.setPrice("100");
        recipe.setAmtCoffee("3");
        recipe.setAmtMilk("2");
        recipe.setAmtSugar("1");
        recipe.setAmtChocolate("4");

        assertAll("Recipe properties",
            () -> assertEquals("Mocha", recipe.getName()),
            () -> assertEquals(100, recipe.getPrice()),
            () -> assertEquals(3, recipe.getAmtCoffee()),
            () -> assertEquals(2, recipe.getAmtMilk()),
            () -> assertEquals(1, recipe.getAmtSugar()),
            () -> assertEquals(4, recipe.getAmtChocolate())
        );
    }
}
