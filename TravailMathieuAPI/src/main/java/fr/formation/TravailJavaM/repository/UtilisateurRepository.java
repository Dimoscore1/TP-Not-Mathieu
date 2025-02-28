package fr.formation.TravailJavaM.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.formation.TravailJavaM.modele.Utilisateur;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, String> {

}
