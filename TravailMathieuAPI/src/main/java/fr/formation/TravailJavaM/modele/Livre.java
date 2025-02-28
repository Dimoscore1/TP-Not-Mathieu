package fr.formation.TravailJavaM.modele;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "livres")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Livre {

    @Id
    @UuidGenerator
    @Column(name = "livre_id")
    private String id;

    @Column(nullable = false, unique = true)
    private String isbn;

    @Column(nullable = true)
    private String titre;

    @Column(nullable = true)
    private String auteur;

    @Column(nullable = true)
    private String editeur;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LivreFormat format;

    @Column(nullable = false)
    private boolean isAvailable;
}
