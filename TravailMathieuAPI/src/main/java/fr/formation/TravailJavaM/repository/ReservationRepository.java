package fr.formation.TravailJavaM.repository;

import fr.formation.TravailJavaM.modele.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, String> {

    List<Reservation> findByDueDateBeforeAndIsEndedFalse(LocalDate today);

    List<Reservation> findByUtilisateurId(String userId);
}
