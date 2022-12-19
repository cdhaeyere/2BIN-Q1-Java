package domaine;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class CamionTest {

    @Test
    @DisplayName("Test ajouterTest avec un trajet qui comportent plus de caisses que le camion peut en transporter")
    void testAjouterTrajet() {
        Camion camion = Mockito.mock(Camion.class);
        Trajet trajet = Mockito.mock(Trajet.class);
        Mockito.when(camion.ajouterTrajet(trajet)).thenReturn(false);
    }
}
