package fr.formation.TravailJavaM.api;

import fr.formation.TravailJavaM.modele.Livre;
import fr.formation.TravailJavaM.modele.Reservation;
import fr.formation.TravailJavaM.modele.Utilisateur;
import fr.formation.TravailJavaM.repository.LivreRepository;
import fr.formation.TravailJavaM.repository.ReservationRepository;
import fr.formation.TravailJavaM.repository.UtilisateurRepository;
import fr.formation.TravailJavaM.service.ReservationService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    private UtilisateurRepository utilisateurRepository;

    @Mock
    private LivreRepository livreRepository;

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationService reservationService;

    private Utilisateur utilisateur;
    private Livre livre;
    private Reservation activeReservation1;
    private Reservation activeReservation2;

    @BeforeEach
    void setUp() {
        utilisateur = new Utilisateur();
        utilisateur.setId("user756");

        livre = new Livre();
        livre.setId("livre756");

        activeReservation1 = new Reservation();
        activeReservation1.setUtilisateur(utilisateur);
        activeReservation1.setEnded(false);

        activeReservation2 = new Reservation();
        activeReservation2.setUtilisateur(utilisateur);
        activeReservation2.setEnded(false);
    }

    @Test
    void createReservationWithLessThanThreeReservations() {
        // GIVEN :
        when(utilisateurRepository.findById("user756")).thenReturn(Optional.of(utilisateur));
        when(livreRepository.findById("livre756")).thenReturn(Optional.of(livre));

        List<Reservation> activeReservations = Arrays.asList(activeReservation1, activeReservation2);
        when(reservationRepository.findByUtilisateurId("user756")).thenReturn(activeReservations);

        when(reservationRepository.save(any(Reservation.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // WHEN :
        Reservation newReservation = reservationService.createReservation("user756", "livre756");

        // THEN
        assertEquals(utilisateur, newReservation.getUtilisateur());
        assertEquals(livre, newReservation.getLivre());
        assertFalse(newReservation.isEnded());
        assertEquals(LocalDate.now().plusMonths(4), newReservation.getDueDate());
        verify(reservationRepository, times(1)).save(any(Reservation.class));
    }

    @Test
    void createReservationWithThreeOrMoreReservations_ShouldThrowException() {
        // GIVEN
        Reservation activeReservation3 = new Reservation();
        activeReservation3.setUtilisateur(utilisateur);
        activeReservation3.setEnded(false);

        List<Reservation> activeReservations = Arrays.asList(activeReservation1, activeReservation2, activeReservation3);
        when(utilisateurRepository.findById("user756")).thenReturn(Optional.of(utilisateur));
        when(livreRepository.findById("livre756")).thenReturn(Optional.of(livre));
        when(reservationRepository.findByUtilisateurId("user756")).thenReturn(activeReservations);

        // WHEN :
        Exception exception = assertThrows(RuntimeException.class, () -> {
            reservationService.createReservation("user756", "livre756");
        });

        assertEquals("L'utilisateur a déjà 3 réservations actives", exception.getMessage());

    }

    @Test
    void createReservationWithNonExistingUser_ShouldThrowException() {
        when(utilisateurRepository.findById("user756")).thenReturn(Optional.empty());
        // là aucun utilisateur n'est
        // renvoyé

        Exception exception = assertThrows(RuntimeException.class, () -> {
            reservationService.createReservation("user756", "livre756");
        });

        assertEquals("L'Utilisateur est non trouvé", exception.getMessage());
    }

    @Test
    void createReservationWithNonExistingLivre_ShouldThrowException() {
        when(utilisateurRepository.findById("user756")).thenReturn(Optional.of(utilisateur));
        when(livreRepository.findById("livre756")).thenReturn(Optional.empty());
        // A ce moment aucun livre n'est renvoyé
        Exception exception = assertThrows(RuntimeException.class, () -> {
            reservationService.createReservation("user756", "livre756");
        });

        assertEquals("Le livre est non trouvé", exception.getMessage());
    }

    @Test
    void getAllReservations() {
        // GIVEN
        Utilisateur utilisateur = new Utilisateur();

        utilisateur = new Utilisateur();
        utilisateur.setId("user756");

        Reservation reservation = new Reservation();
        reservation = new Reservation();
        reservation.setUtilisateur(utilisateur);
        reservation.setEnded(false);

        Reservation reservation2 = new Reservation();
        reservation2 = new Reservation();
        reservation2.setUtilisateur(utilisateur);
        reservation2.setEnded(false);

        // WHEN
        when(reservationRepository.findAll()).thenReturn(List.of(reservation, reservation2));

        // THEN
        reservationService.getAllReservations();

        assertEquals(2, reservationRepository.findAll().size());
    }
}
