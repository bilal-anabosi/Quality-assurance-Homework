package main.najah.test;

import main.najah.code.Recipe;
import main.najah.code.RecipeBook;
import main.najah.code.RecipeException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("RecipeBook Test Suite")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RecipeBookTest {

    RecipeBook recipeBook;
    Recipe sampleRecipe;

    @BeforeAll
    static void setupAll() {
        System.out.println("Starting RecipeBook tests...");
    }

    @BeforeEach
    void setUp() throws RecipeException {
        recipeBook = new RecipeBook();
        sampleRecipe = new Recipe();
        sampleRecipe.setName("Coffee");
        sampleRecipe.setAmtCoffee("3");
        sampleRecipe.setAmtMilk("2");
        sampleRecipe.setAmtSugar("1");
        sampleRecipe.setAmtChocolate("0");
        sampleRecipe.setPrice("50");
    }

    @Test
    @Order(1)
    @DisplayName("Test adding a new recipe successfully")
    void testAddRecipeSuccess() {
        assertTrue(recipeBook.addRecipe(sampleRecipe));
    }

    @Test
    @Order(2)
    @DisplayName("Test adding duplicate recipe fails")
    void testAddDuplicateRecipe() {
        assertTrue(recipeBook.addRecipe(sampleRecipe));
        assertFalse(recipeBook.addRecipe(sampleRecipe)); // duplicate
    }

    @Test
    @Order(3)
    @DisplayName("Test adding recipes up to the limit")
    void testAddUntilFull() throws RecipeException {
        for (int i = 0; i < 4; i++) {
            Recipe r = new Recipe();
            r.setName("Recipe" + i);
            r.setPrice("10");
            r.setAmtCoffee("1");
            r.setAmtMilk("1");
            r.setAmtSugar("1");
            r.setAmtChocolate("1");
            assertTrue(recipeBook.addRecipe(r));
        }
        // Should fail when full
        Recipe extra = new Recipe();
        extra.setName("Extra");
        extra.setPrice("10");
        extra.setAmtCoffee("1");
        extra.setAmtMilk("1");
        extra.setAmtSugar("1");
        extra.setAmtChocolate("1");
        assertFalse(recipeBook.addRecipe(extra));
    }

    @Test
    @Order(4)
    @DisplayName("Test getRecipes() returns correct size and structure")
    void testGetRecipes() {
        Recipe[] recipes = recipeBook.getRecipes();
        assertNotNull(recipes);
        assertEquals(4, recipes.length);
    }

    @Test
    @Order(5)
    @DisplayName("Test deleting an existing recipe")
    void testDeleteRecipeSuccess() {
        recipeBook.addRecipe(sampleRecipe);
        assertEquals("Coffee", recipeBook.deleteRecipe(0));
    }

    @Test
    @Order(6)
    @DisplayName("Test deleting a non-existing recipe returns null")
    void testDeleteRecipeInvalid() {
        assertNull(recipeBook.deleteRecipe(1));
    }

    @Test
    @Order(7)
    @DisplayName("Test editing an existing recipe")
    void testEditRecipeSuccess() throws RecipeException {
        recipeBook.addRecipe(sampleRecipe);
        Recipe newRecipe = new Recipe();
        newRecipe.setName("Latte");
        newRecipe.setPrice("60");
        newRecipe.setAmtCoffee("2");
        newRecipe.setAmtMilk("3");
        newRecipe.setAmtSugar("2");
        newRecipe.setAmtChocolate("1");

        String result = recipeBook.editRecipe(0, newRecipe);
        assertEquals("Coffee", result);
        assertEquals("", recipeBook.getRecipes()[0].getName()); // newRecipe name set to ""
    }

    @Test
    @Order(8)
    @DisplayName("Test editing a non-existing recipe returns null")
    void testEditRecipeInvalid() throws RecipeException {
        Recipe newRecipe = new Recipe();
        newRecipe.setName("Mocha");
        newRecipe.setPrice("70");
        newRecipe.setAmtCoffee("1");
        newRecipe.setAmtMilk("1");
        newRecipe.setAmtSugar("1");
        newRecipe.setAmtChocolate("1");

        assertNull(recipeBook.editRecipe(2, newRecipe));
    }

    @ParameterizedTest
    @Order(9)
    @ValueSource(ints = {0, 1, 2, 3})
    @DisplayName("Test deleteRecipe handles all valid indices")
    void testDeleteAcrossAllIndices(int index) throws RecipeException {
        Recipe r = new Recipe();
        r.setName("Test" + index);
        r.setPrice("10");
        r.setAmtCoffee("1");
        r.setAmtMilk("1");
        r.setAmtSugar("1");
        r.setAmtChocolate("1");

        recipeBook.addRecipe(r);
        assertNotNull(recipeBook.deleteRecipe(index));
    }

    @Test
    @Order(10)
    @DisplayName("Timeout test: ensure addRecipe is fast")
    void testTimeout() {
        assertTimeout(Duration.ofMillis(50), () -> {
            recipeBook.addRecipe(sampleRecipe);
        });
    }

    @Test
    @Order(11)
    @Disabled("Fix later: intentionally wrong expectation")
    @DisplayName("Failing test: recipe should not be null after add")
    void testFailingAdd() {
        recipeBook.addRecipe(sampleRecipe);
        assertNull(recipeBook.getRecipes()[0]); // Intentionally wrong
    }

    @AfterEach
    void afterEach() {
        System.out.println("Test complete.");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("All RecipeBook tests complete.");
    }
}
