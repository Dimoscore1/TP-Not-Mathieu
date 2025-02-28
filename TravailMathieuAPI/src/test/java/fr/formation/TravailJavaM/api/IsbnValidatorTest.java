package fr.formation.TravailJavaM.api;

import fr.formation.TravailJavaM.exception.InvalidIsbnCharacterException;
import fr.formation.TravailJavaM.exception.InvalidIsbnLengthException;
import fr.formation.TravailJavaM.service.IsbnValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IsbnValidatorTest {

    @Test
    public void whenIsbnIsValid_shouldReturnTrue() {

        IsbnValidator validator = new IsbnValidator();
        // WHEN
        boolean result = validator.validateIsbn("2253009687");
        // THEN
        assertTrue(result);
    }

    @Test
    public void whenIsbnIsInvalid_shouldReturnFalse() {

        IsbnValidator validator = new IsbnValidator();
        // WHEN
        boolean result = validator.validateIsbn("2253008547");
        // THEN
        assertFalse(result);
    }

    @Test
    public void whenIsbnIs9Chars_shouldThrowInvalidLengthException() {

        IsbnValidator validator = new IsbnValidator();
        // WHEN
        assertThrows(InvalidIsbnLengthException.class, () -> validator.validateIsbn("276541004"));
    }

    @Test
    public void whenIsbnIsValidAndEndsWithX_shouldReturnTrue() {

        IsbnValidator validator = new IsbnValidator();
        // WHEN
        boolean result = validator.validateIsbn("013162959X");
        // THEN
        assertTrue(result);
    }

    @Test
    public void whenIsbnHasInvalidChar_shouldThrowException() {

        IsbnValidator validator = new IsbnValidator();
        // WHEN
        assertThrows(InvalidIsbnCharacterException.class, () -> validator.validateIsbn("01av62959X"));
    }

}
