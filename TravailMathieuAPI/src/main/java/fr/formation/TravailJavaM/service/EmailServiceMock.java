package fr.formation.TravailJavaM.service;

import fr.formation.TravailJavaM.modele.Reservation;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceMock {

    public void sendMockEmail(Reservation booking) {
        System.out.println("Envoi d'un mail à : " + booking.getUtilisateur().getNom() +
                " pour livre : " + booking.getLivre().getTitre() +
                " (Date limite max : " + booking.getDueDate() + ")");
    }
}
