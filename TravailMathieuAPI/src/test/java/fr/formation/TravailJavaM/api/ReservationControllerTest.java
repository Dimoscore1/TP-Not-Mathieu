package fr.formation.TravailJavaM.api;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import fr.formation.TravailJavaM.repository.ReservationRepository;
import fr.formation.TravailJavaM.service.ReservationService;
import fr.formation.TravailJavaM.service.EmailServiceMock;

public class ReservationControllerTest {

    @Mock
    private ReservationService reservationService;

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private EmailServiceMock emailServiceMock;

    @InjectMocks
    private LivreController livreController;

}