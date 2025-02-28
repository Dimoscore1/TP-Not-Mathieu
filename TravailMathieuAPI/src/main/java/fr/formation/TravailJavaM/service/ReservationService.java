package fr.formation.TravailJavaM.service;

import fr.formation.TravailJavaM.modele.Reservation;
import fr.formation.TravailJavaM.modele.Utilisateur;
import fr.formation.TravailJavaM.modele.Livre;
import fr.formation.TravailJavaM.repository.ReservationRepository;
import fr.formation.TravailJavaM.repository.UtilisateurRepository;
import fr.formation.TravailJavaM.repository.LivreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private LivreRepository livreRepository;

    public Reservation createReservation(String userId, String livreId) {
        Utilisateur utilisateur = utilisateurRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("L'Utilisateur est non trouvé"));

        Livre livre = livreRepository.findById(livreId)
                .orElseThrow(() -> new RuntimeException("Le livre est non trouvé"));

        // Il faut Vérifier que l'utilisateur n'a pas plus de 3 réservations en cours
        long activeReservations = reservationRepository.findByUtilisateurId(userId)
                .stream()
                .filter(b -> !b.isEnded())
                .count();

        if (activeReservations >= 3) {
            throw new RuntimeException("L'utilisateur a déjà 3 réservations actives");
        }

        Reservation reservation = new Reservation();
        reservation.setUtilisateur(utilisateur);
        reservation.setLivre(livre);
        reservation.setReservationDate(LocalDate.now());
        reservation.setDueDate(LocalDate.now().plusMonths(4));
        reservation.setEnded(false);

        return reservationRepository.save(reservation);
    }

    public List<Reservation> getUserReservations(String userId) {
        return reservationRepository.findByUtilisateurId(userId);
    }
    public void deleteReservation(String reservationId) {
        reservationRepository.deleteById(reservationId);
    }

    public void endReservation(String reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("La réservation est non trouvée"));

        reservation.setEnded(true);
        reservationRepository.save(reservation);
    }
    public void getAllReservations() {
        reservationRepository.findAll();
    }

}
