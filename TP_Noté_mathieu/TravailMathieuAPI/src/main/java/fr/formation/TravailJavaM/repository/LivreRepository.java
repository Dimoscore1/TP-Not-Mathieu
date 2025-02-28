package fr.formation.TravailJavaM.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.formation.TravailJavaM.modele.Livre;

public interface LivreRepository extends JpaRepository<Livre, String> {

    List<Livre> findByIsbn(String isbn);

    List<Livre> findByTitreContainingIgnoreCase(String titre);

    List<Livre> findByAuteurContainingIgnoreCase(String auteur);
}
