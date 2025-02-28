package fr.formation.TravailJavaM.api;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import fr.formation.TravailJavaM.modele.Livre;
import fr.formation.TravailJavaM.modele.LivreFormat;
import fr.formation.TravailJavaM.repository.LivreRepository;
import fr.formation.TravailJavaM.service.LivreService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class LivreControllerTest {

    private static final LivreFormat POCHE = LivreFormat.POCHE;

    @Mock
    private LivreRepository livreRepository;

    @Mock
    private LivreService livreService;

    @InjectMocks
    private LivreController livreController;

    private Livre livreTest1;
    private Livre livreTest2;
    private Livre livreTest3;
    private List<Livre> livres;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        livreTest1 = new Livre("1", "2210770823", "J'accuse", "Emile Zola", "Littérature et histoire",
                POCHE, true);
        livreTest2 = new Livre("2", "2038717109", "Germinal", "Emile Zola", "Hatier", POCHE, true);
        livreTest3 = new Livre("3", "2547584254", "Un livre invalide", "Un auteur invalide", "Un éditeur invalide", POCHE,
                false);

        livres = Arrays.asList(livreTest1, livreTest2);
    }

    @Test
    void testGetAllLivres() {
        when(livreRepository.findAll()).thenReturn(livres);
        List<Livre> result = livreController.getAllLivres();
        assertEquals(2, result.size());
    }

    @Test
    void testGetLivreById() {
        when(livreRepository.findById("1")).thenReturn(Optional.of(livreTest1));
        assertEquals(livreTest1, livreController.getLivreById("1").getBody());
    }

    @Test
    void testGetLivreByIsbn() {
        when(livreService.getLivresByIsbn("2210770823")).thenReturn(livres);
        assertEquals(livres, livreController.getLivresByIsbn("2210770823"));
    }

    @Test
    void getLivresByTitre() {
        List<Livre> expectedLivres = List.of(livreTest1);
        when(livreService.getLivresByTitre("J'accuse")).thenReturn(expectedLivres);

        List<Livre> result = livreController.getLivresByTitre("J'accuse");

        assertEquals(expectedLivres, result);
        assertEquals(1, result.size());
        assertTrue(result.contains(livreTest1));
        assertFalse(result.contains(livreTest2));
    }

    @Test
    void getLivresByAuteur() {
        List<Livre> expectedLivres = List.of(livreTest1, livreTest2);
        when(livreService.getLivresByAuteur("Emile Zola")).thenReturn(expectedLivres);

        List<Livre> result = livreService.getLivresByAuteur("Emile Zola");

        assertTrue(result.contains(livreTest1));
        assertTrue(result.contains(livreTest2));
        assertFalse(result.contains(livreTest3));
    }

    @Test
    void createLivreWithCompleteData() {
        when(livreService.createLivre(livreTest1)).thenReturn(livreTest1);

        Livre result = livreService.createLivre(livreTest1);

        assertEquals(livreTest1, livreController.createLivre(livreTest1));
        assertNotNull(result);
        assertEquals(livreTest1, result);
        verify(livreService, times(2)).createLivre(any(Livre.class));
    }

    @Test
    void createLivreWithIncompleteData() {
        livreTest2.setIsbn("INVALID_ISBN");
        when(livreService.createLivre(livreTest2)).thenReturn(null);

        Livre result = livreController.createLivre(livreTest2);

        assertNull(result);
        verify(livreService, times(1)).createLivre(any(Livre.class));
    }

    @Test
    void createLivreWithNullFields() {
        livreTest3.setTitre(null);
        livreTest3.setAuteur(null);
        livreTest3.setEditeur(null);
        livreTest3.setFormat(null);

        Livre expectedLivre = new Livre("3", "2458652256", "testtitre", "testauteur", "testediteur", LivreFormat.BROCHE, true);

        when(livreService.createLivre(any(Livre.class))).thenReturn(expectedLivre);

        Livre result = livreController.createLivre(livreTest3);

        assertNotNull(result);
        assertEquals("testtitre", result.getTitre());
        assertEquals("testauteur", result.getAuteur());
        assertEquals("testediteur", result.getEditeur());
        assertEquals(LivreFormat.BROCHE, result.getFormat());
        verify(livreService, times(1)).createLivre(any(Livre.class));
    }
}
