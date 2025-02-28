package fr.formation.TravailJavaM.api;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import fr.formation.TravailJavaM.exception.InvalidIsbnCharacterException;
import fr.formation.TravailJavaM.exception.InvalidIsbnLengthException;
import fr.formation.TravailJavaM.modele.Livre;
import fr.formation.TravailJavaM.modele.LivreFormat;
import fr.formation.TravailJavaM.service.LivreService;
import fr.formation.TravailJavaM.service.NouveauIsbnValidator;

public class NewIsbnValidatorTest {

    @Mock
    LivreService bookService;

    private static final LivreFormat POCHE = LivreFormat.POCHE;

    @Test
    public void whenIsbnIsValid_shouldReturnTrue() {

        NouveauIsbnValidator nouveauValidator = new NouveauIsbnValidator();
        // WHEN
        boolean result = nouveauValidator.validateNewIsbn("9782091886077");
        // THEN
        assertTrue(result);
    }

    @Test
    public void whenIsbnIsValidWithBare_shouldReturnTrue() {
        // GIVEN : Création d'un livre avec un ISBN contenant des tirets
        Livre livreTest1 = new Livre();
        livreTest1.setId("1");
        livreTest1.setIsbn("978-2210770829");
        livreTest1.setTitre("J'accuse");
        livreTest1.setAuteur("Emile Zola");
        livreTest1.setEditeur("Littérature et histoire");
        livreTest1.setFormat(POCHE);
        livreTest1.setAvailable(true);

        NouveauIsbnValidator nouveauValidator = new NouveauIsbnValidator();

        LivreService bookService = new LivreService(null, null, nouveauValidator);

        // WHEN :
        livreTest1.setIsbn(bookService.deleteBareIsbn(livreTest1.getIsbn()));
        boolean result = nouveauValidator.validateNewIsbn(livreTest1.getIsbn());

        // THEN
        assertTrue(result);
        assertFalse(livreTest1.getIsbn().contains("-"));
    }

    @Test
    public void whenIsbnIsInvalid_shouldReturnFalse() {

        NouveauIsbnValidator newValidator = new NouveauIsbnValidator();
        // WHEN
        boolean result = newValidator.validateNewIsbn("9782091886047");
        // THEN
        assertFalse(result);
    }

    @Test
    public void whenIsbnIs12Chars_shouldThrowInvalidLengthException() {

        NouveauIsbnValidator newValidator = new NouveauIsbnValidator();
        // WHEN
        assertThrows(InvalidIsbnLengthException.class, () -> newValidator.validateNewIsbn("978209188607"));
    }

    @Test
    public void whenIsbnIsValidAndEndsWithX_shouldNotReturnTrue() {

        NouveauIsbnValidator newValidator = new NouveauIsbnValidator();
        // WHEN
        // Le dernier chiffre ne doit pas être un X en isbn 13 à la fin
        assertThrows(InvalidIsbnCharacterException.class, () -> newValidator.validateNewIsbn("978209188607X"));
    }

    @Test
    public void whenIsbnHasInvalidChar_shouldThrowException() {

        NouveauIsbnValidator newValidator = new NouveauIsbnValidator();
        // WHEN
        assertThrows(InvalidIsbnCharacterException.class, () -> newValidator.validateNewIsbn("978av9188607X"));
    }
}
