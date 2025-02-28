package fr.formation.TravailJavaM.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.stereotype.Service;

import fr.formation.TravailJavaM.exception.InvalidIsbnCharacterException;
import fr.formation.TravailJavaM.exception.InvalidIsbnLengthException;
import fr.formation.TravailJavaM.modele.Livre;
import fr.formation.TravailJavaM.modele.LivreFormat;
import fr.formation.TravailJavaM.repository.LivreRepository;

@Service
public class LivreService {

    private final LivreRepository livreRepository;
    private final IsbnValidator isbnValidator;
    private final NouveauIsbnValidator nouveauIsbnValidator;

    public LivreService(LivreRepository livreRepository, IsbnValidator isbnValidator, NouveauIsbnValidator nouveauIsbnValidator) {
        this.livreRepository = livreRepository;
        this.isbnValidator = isbnValidator;
        this.nouveauIsbnValidator = nouveauIsbnValidator;
    }

    public Livre createLivre(@RequestBody Livre livre) {

        livre.setIsbn(deleteBareIsbn(livre.getIsbn()));

        if (livre.getIsbn() != null && livre.getIsbn().length() == 13) {
            try {
                if (!nouveauIsbnValidator.validateNewIsbn(livre.getIsbn())) {
                    return null;
                }
            } catch (InvalidIsbnLengthException | InvalidIsbnCharacterException e) {
                throw new IllegalArgumentException("Invalid ISBN");
            }
        } else {
            try {
                if (!isbnValidator.validateIsbn(livre.getIsbn())) {
                    return null;
                }
            } catch (InvalidIsbnLengthException | InvalidIsbnCharacterException e) {
                throw new IllegalArgumentException("Invalid ISBN");
            }
        }

        livre.setTitre(mockIfNullWebService(livre.getTitre()));
        livre.setAuteur(mockIfNullWebService(livre.getAuteur()));
        livre.setEditeur(mockIfNullWebService(livre.getEditeur()));
        livre.setFormat(mockFormatIfNullWebService(livre.getFormat()));

        return livreRepository.save(livre);
    }

    public List<Livre> getLivresByIsbn(String isbn) {
        return livreRepository.findAll().stream()
                .filter(livre -> livre.getIsbn().equalsIgnoreCase(isbn))
                .collect(Collectors.toList());
    }

    public List<Livre> getLivresByTitre(String titre) {
        return livreRepository.findAll().stream()
                .filter(livre -> livre.getTitre().equalsIgnoreCase(titre))
                .collect(Collectors.toList());
    }

    public List<Livre> getLivresByAuteur(String auteur) {
        return livreRepository.findAll().stream()
                .filter(livre -> livre.getAuteur().equalsIgnoreCase(auteur))
                .collect(Collectors.toList());
    }

    public String mockIfNullWebService(String value) {
        return (value == null || value.trim().isEmpty()) ? "test" : value;
    }

    private static final LivreFormat BROCHE = LivreFormat.BROCHE;

    public LivreFormat mockFormatIfNullWebService(LivreFormat value) {
        return (value == null) ? BROCHE : value;
    }

    public String deleteBareIsbn(String isbn) {
        return isbn.replaceAll("[^0-9X]", "");
    }
}