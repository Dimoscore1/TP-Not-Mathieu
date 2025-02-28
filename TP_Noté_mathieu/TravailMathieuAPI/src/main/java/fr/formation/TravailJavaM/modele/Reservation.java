package fr.formation.TravailJavaM.modele;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "reservations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "reservation_id")
    private String id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Utilisateur utilisateur;

    @ManyToOne
    @JoinColumn(name = "livre_id", nullable = false)
    private Livre livre;

    @Column(nullable = false)
    private LocalDate reservationDate;

    @Column(nullable = false)
    private LocalDate dueDate;

    @Column(nullable = false)
    private boolean isEnded;

    @PrePersist
    public void setDueDate() {
        if (this.dueDate == null) {
            this.dueDate = this.reservationDate.plusMonths(4);
        }
    }

    public void endReservation() {
        this.isEnded = true;
    }
}
