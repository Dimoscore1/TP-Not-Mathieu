package fr.formation.TravailJavaM.api;

import fr.formation.TravailJavaM.exception.InvalidIsbnCharacterException;
import fr.formation.TravailJavaM.exception.InvalidIsbnLengthException;
import fr.formation.TravailJavaM.modele.Livre;
import fr.formation.TravailJavaM.modele.LivreFormat;
import fr.formation.TravailJavaM.repository.LivreRepository;
import fr.formation.TravailJavaM.service.LivreService;
import fr.formation.TravailJavaM.service.IsbnValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/livres")
public class LivreController {

        @Autowired

        private final LivreRepository livreRepository;
        private final LivreService livreService;
        private IsbnValidator isbnValidator;

        public LivreController(LivreRepository livreRepository, LivreService livreService) {
                this.livreRepository = livreRepository;
                this.livreService = livreService;
        }

        @GetMapping
        public List<Livre> getAllLivres() {
                return livreRepository.findAll();
        }

        @GetMapping("/{id}")
        public ResponseEntity<Livre> getLivreById(@PathVariable String id) {
                return livreRepository.findById(id)
                                .map(ResponseEntity::ok)
                                .orElse(ResponseEntity.notFound().build());
        }

        // On filtre les Recherches
        @GetMapping("/search/isbn/{isbn}")
        public List<Livre> getLivresByIsbn(@PathVariable String isbn) {
                return livreService.getLivresByIsbn(isbn);
        }

        @GetMapping("/search/titre/{titre}")
        public List<Livre> getLivresByTitre(@PathVariable String titre) {
                return livreService.getLivresByTitre(titre);
        }

        @GetMapping("/search/auteur/{auteur}")
        public List<Livre> getLivresByAuteur(@PathVariable String auteur) {
                return livreService.getLivresByAuteur(auteur);
        }
        @PostMapping
        public ResponseEntity<Livre> createLivre(@RequestBody Livre livre) {
                try {
                        if (!isbnValidator.validateIsbn(livre.getIsbn())) {
                                return ResponseEntity.badRequest().body(null);
                        }
                } catch (InvalidIsbnLengthException | InvalidIsbnCharacterException e) {
                        return ResponseEntity.badRequest().body(null);
                }
                livre.setTitre(mockIfNullWebService(livre.getTitre()));
                livre.setAuteur(mockIfNullWebService(livre.getAuteur()));
                livre.setEditeur(mockIfNullWebService(livre.getEditeur()));
                livre.setFormat(mockFormatIfNullWebService(livre.getFormat()));

                return ResponseEntity.ok(livreRepository.save(livre));
        }

        @PutMapping("/{id}")
        public ResponseEntity<Livre> updateLivre(@PathVariable String id, @RequestBody Livre updatedLivre) {
                Optional<Livre> existingLivre = livreRepository.findById(id);

                if (existingLivre.isPresent()) {
                        Livre livre = existingLivre.get();
                        livre.setIsbn(updatedLivre.getIsbn());
                        livre.setTitre(updatedLivre.getTitre());
                        livre.setAuteur(updatedLivre.getAuteur());
                        livre.setEditeur(updatedLivre.getEditeur());
                        livre.setAvailable(updatedLivre.isAvailable());
                        livre.setFormat(updatedLivre.getFormat());

                        return ResponseEntity.ok(livreRepository.save(livre));
                } else {
                        return ResponseEntity.notFound().build();
                }
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteLivre(@PathVariable String id) {
                if (livreRepository.existsById(id)) {
                        livreRepository.deleteById(id);
                        return ResponseEntity.noContent().build();
                } else {
                        return ResponseEntity.notFound().build();
                }
        }

        private String mockIfNullWebService(String value) {
                return (value == null || value.trim().isEmpty()) ? "test" : value;
        }

        private static final LivreFormat BROCHE = LivreFormat.BROCHE;

        private LivreFormat mockFormatIfNullWebService(LivreFormat value) {
                return (value == null) ? BROCHE : value;
        }
}
