package fr.formation.TravailJavaM.api;

import fr.formation.TravailJavaM.modele.Utilisateur;
import fr.formation.TravailJavaM.service.UtilisateurService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class UtilisateurControllerTest {

    @Mock
    private UtilisateurService utilisateurService;

    @InjectMocks
    private UtilisateurController utilisateurController;

    private Utilisateur utilisateur;
    private Utilisateur utilisateur2;

    @BeforeEach
    void setUp() {
        utilisateur = new Utilisateur();
        utilisateur.setId("1");
        utilisateur.setNom("Dupont");
        utilisateur.setPrenom("Jean");
        utilisateur.setDateDeNaissance(LocalDate.of(1990, 5, 15));
        utilisateur.setCivilite("M.");

        utilisateur2 = new Utilisateur();
        utilisateur2.setId("2");
        utilisateur2.setNom("Dupon2");
        utilisateur2.setPrenom("Jea2");
        utilisateur2.setDateDeNaissance(LocalDate.of(1990, 5, 15));
        utilisateur2.setCivilite("M.");
    }

    @Test
    void testGetAllUtilisateurs() {

        List<Utilisateur> utilisateurs = Arrays.asList(utilisateur, utilisateur2);
        when(utilisateurService.getAllUtilisateurs()).thenReturn(utilisateurs);

        List<Utilisateur> result = utilisateurService.getAllUtilisateurs();

        assertEquals(2, result.size());
        assertEquals("Dupont", result.get(0).getNom());
    }

    @Test
    void testGetUtilisateurById() {
        when(utilisateurService.getUtilisateurById("1")).thenReturn(java.util.Optional.of(utilisateur));

        assertEquals("Dupont", utilisateurService.getUtilisateurById("1").get().getNom());
    }

    @Test
    void createUtilisateurs() {
        when(utilisateurService.createUtilisateur(utilisateur)).thenReturn(utilisateur);

        assertEquals("Dupont", utilisateurService.createUtilisateur(utilisateur).getNom());
        assertEquals("Jean", utilisateurService.createUtilisateur(utilisateur).getPrenom());
        assertEquals("M.", utilisateurService.createUtilisateur(utilisateur).getCivilite());
    }

    @Test
    void updateUtilisateurs() {

        when(utilisateurService.updateUtilisateur("1", utilisateur2)).thenReturn(java.util.Optional.of(utilisateur2));

        assertEquals("Dupon2", utilisateurService.updateUtilisateur("1", utilisateur2).get().getNom());
        assertEquals("Jea2", utilisateurService.updateUtilisateur("1", utilisateur2).get().getPrenom());
        assertEquals("M.", utilisateurService.updateUtilisateur("1", utilisateur2).get().getCivilite());
    }

    @Test
    void deleteUtilisateur() {
        when(utilisateurService.deleteUtilisateur("1")).thenReturn(true);

        assertEquals(true, utilisateurService.deleteUtilisateur("1"));
    }

}
