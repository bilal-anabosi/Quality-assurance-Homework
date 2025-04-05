//Disable is in this file by the way 
package main.najah.test;

import main.najah.code.RecipeException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("RecipeException Test")
public class RecipeExceptionTest {

    @Test
    @DisplayName("Test RecipeException message storage and retrieval")
    void testRecipeExceptionMessage() {
        String message = "Custom error message";
        RecipeException exception = new RecipeException(message);

        // Ensure message is correctly stored and returned
        assertEquals(message, exception.getMessage());
    }

    @Test
    @DisplayName("Test RecipeException is instance of Exception")
    void testInstanceOfException() {
        RecipeException exception = new RecipeException("error");
        assertTrue(exception instanceof Exception);
    }

    @Test
    @DisplayName("Test RecipeException thrown in try-catch block")
    void testThrowAndCatchRecipeException() {
        try {
            throw new RecipeException("Something went wrong");
        } catch (RecipeException e) {
            assertEquals("Something went wrong", e.getMessage());
        }
    }
//THIS IF THE DISABLE SECTION AS REQUIRED
    
//    @Test
//    @Disabled("Fix: The expected message is wrong. Change 'Correct message' to match the actual exception message.")
//    @DisplayName("Intentionally failing test - wrong expected message")
//    void testFailingExceptionMessage() {
//        RecipeException exception = new RecipeException("Actual message");
//        assertEquals("Correct message", exception.getMessage()); // Will fail
//    }
}
