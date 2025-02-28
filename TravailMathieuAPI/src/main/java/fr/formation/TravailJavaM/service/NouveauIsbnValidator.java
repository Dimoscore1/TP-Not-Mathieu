package fr.formation.TravailJavaM.service;

import org.springframework.stereotype.Component;

import fr.formation.TravailJavaM.exception.InvalidIsbnCharacterException;
import fr.formation.TravailJavaM.exception.InvalidIsbnLengthException;

@Component
public class NouveauIsbnValidator {

    public boolean validateNewIsbn(String isbn) {

        if (isbn.length() != 13) {
            throw new InvalidIsbnLengthException();
        }

        System.err.println(isbn);
        int total = 0;
        for (int i = 0; i < 13; i++) {
            if (!Character.isDigit(isbn.charAt(i))) {
                throw new InvalidIsbnCharacterException();
            }
            total += Character.getNumericValue(isbn.charAt(i)) * (i % 2 == 0 ? 1 : 3);
        }
        return total % 10 == 0;

    }

}