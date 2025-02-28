package fr.formation.TravailJavaM.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import fr.formation.TravailJavaM.modele.Livre;
import fr.formation.TravailJavaM.repository.LivreRepository;

@Service
public class LivreService {

    private final LivreRepository livreRepository;

    public LivreService(LivreRepository livreRepository) {
        this.livreRepository = livreRepository;
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
}
