package fr.formation.TravailJavaM.api;

import fr.formation.TravailJavaM.modele.Reservation;
import fr.formation.TravailJavaM.modele.Utilisateur;
import fr.formation.TravailJavaM.modele.Livre;
import fr.formation.TravailJavaM.repository.ReservationRepository;
import fr.formation.TravailJavaM.service.EmailServiceMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailServiceMockTest {

    @Mock
    private ReservationRepository bookingRepository;

    @Mock
    private EmailServiceMock emailServiceMock;

    @InjectMocks
    private ReservationController bookingController;

    private Reservation reservation;
    private Reservation reservation2;
    private Reservation reservation3;

    @BeforeEach
    void setUp() {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNom("Gascon");

        Livre livre = new Livre();
        livre.setTitre("Ready Player One");

        reservation = new Reservation();
        reservation.setUtilisateur(utilisateur);
        reservation.setLivre(livre);
        reservation.setDueDate(LocalDate.now().minusDays(1));

        reservation2 = new Reservation();
        reservation2.setUtilisateur(utilisateur);
        reservation2.setLivre(livre);
        reservation2.setDueDate(LocalDate.now().minusDays(1));

        reservation3 = new Reservation();
        reservation3.setUtilisateur(utilisateur);
        reservation3.setLivre(livre);
        reservation3.setDueDate(LocalDate.now().minusDays(1));
    }

    @Test
    void testSendOverdueReminders_AucuneReservationEnRetard() {
        when(bookingRepository.findByDueDateBeforeAndIsEndedFalse(any())).thenReturn(Collections.emptyList());

        ResponseEntity<String> response = bookingController.sendOverdueReminders();

        assertEquals("Aucune réservation est en retard.", response.getBody());

        verify(emailServiceMock, never()).sendMockEmail(any());
    }

    @Test
    void testSendOverdueReminders_ReservationsEnRetard() {
        when(bookingRepository.findByDueDateBeforeAndIsEndedFalse(any())).thenReturn(List.of(reservation));

        ResponseEntity<String> response = bookingController.sendOverdueReminders();

        assertEquals("un rappel est envoyés à 1 utilisateur(s).", response.getBody());

    }

    @Test
    void testSendOverdueReminders_ReservationsEnRetardToTwoUsers() {
        when(bookingRepository.findByDueDateBeforeAndIsEndedFalse(any()))
                .thenReturn(List.of(reservation, reservation2));

        ResponseEntity<String> response = bookingController.sendOverdueReminders();

        assertEquals("un rappel est envoyés à 2 utilisateur(s).", response.getBody());

    }
    @Test
    void testSendOverdueReminders_ReservationsEnRetardToThreeUsers() {
        when(bookingRepository.findByDueDateBeforeAndIsEndedFalse(any()))
                .thenReturn(List.of(reservation, reservation2, reservation3));

        ResponseEntity<String> response = bookingController.sendOverdueReminders();

        assertEquals("un rappel est envoyés à 3 utilisateur(s).", response.getBody());

    }

    @Test
    void testSendMockEmail() {
        emailServiceMock.sendMockEmail(reservation);

        verify(emailServiceMock, times(1)).sendMockEmail(reservation);
        verify(emailServiceMock, times(1)).sendMockEmail(reservation);
    }

}
