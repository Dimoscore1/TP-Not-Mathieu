package fr.formation.TravailJavaM.service;

import fr.formation.TravailJavaM.modele.Utilisateur;
import fr.formation.TravailJavaM.repository.UtilisateurRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;

    public UtilisateurService(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurRepository.findAll();
    }

    public Optional<Utilisateur> getUtilisateurById(String id) {
        return utilisateurRepository.findById(id);
    }

    public Utilisateur createUtilisateur(Utilisateur utilisateur) {
        return utilisateurRepository.save(utilisateur);
    }

    public Optional<Utilisateur> updateUtilisateur(String id, Utilisateur updatedUtilisateur) {
        return utilisateurRepository.findById(id).map(existingUtilisateur -> {
            existingUtilisateur.setNom(updatedUtilisateur.getNom());
            existingUtilisateur.setPrenom(updatedUtilisateur.getPrenom());
            existingUtilisateur.setDateDeNaissance(updatedUtilisateur.getDateDeNaissance());
            existingUtilisateur.setCivilite(updatedUtilisateur.getCivilite());
            return utilisateurRepository.save(existingUtilisateur);
        });
    }

    public boolean deleteUtilisateur(String id) {
        if (utilisateurRepository.existsById(id)) {
            utilisateurRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
