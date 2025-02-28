package fr.formation.TravailJavaM.api;

import fr.formation.TravailJavaM.modele.Reservation;
import fr.formation.TravailJavaM.repository.ReservationRepository;
import fr.formation.TravailJavaM.service.ReservationService;
import fr.formation.TravailJavaM.service.EmailServiceMock;
import fr.formation.TravailJavaM.service.UtilisateurService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    private final ReservationRepository reservationRepository;

    private final EmailServiceMock emailServiceMock;

    public ReservationController(ReservationService reservationService, ReservationRepository reservationRepository,
            UtilisateurService utilisateurService, EmailServiceMock emailServiceMock) {
        this.reservationService = reservationService;
        this.reservationRepository = reservationRepository;
        this.emailServiceMock = emailServiceMock;
    }

    @PostMapping("/reminders")
    public ResponseEntity<String> sendOverdueReminders() {
        LocalDate today = LocalDate.now();

        List<Reservation> expiredReservations = reservationRepository.findByDueDateBeforeAndIsEndedFalse(today);
        if (expiredReservations.isEmpty()) {
            return ResponseEntity.ok("Aucune réservation est en retard.");
        }

        expiredReservations.forEach(reservation -> emailServiceMock.sendMockEmail(reservation));

        return ResponseEntity.ok("un rappels est envoyés à " + expiredReservations.size() + " utilisateur(s).");
    }

    @PostMapping("/create/{userId}/{livreId}")
    public ResponseEntity<Reservation> createReservation(@PathVariable String userId, @PathVariable String livreId) {
        return ResponseEntity.ok(reservationService.createReservation(userId, livreId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Reservation>> getUserReservations(@PathVariable String userId) {
        return ResponseEntity.ok(reservationService.getUserReservations(userId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Reservation>> getAllReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        return ResponseEntity.ok(reservations);
    }

    @DeleteMapping("/delete/{reservationId}")
    public ResponseEntity<String> deleteReservation(@PathVariable String reservationId) {
        reservationService.deleteReservation(reservationId);
        return ResponseEntity.ok("La réservation est supprimée avec succès !");
    }
    @PutMapping("/end/{reservationId}")
    public ResponseEntity<String> endReservation(@PathVariable String reservationId) {
        reservationService.endReservation(reservationId);
        return ResponseEntity.ok("La réservation est terminée avec succès !");
    }
}
