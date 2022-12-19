package domaine;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TrajetTest {

    @Test
    @DisplayName("Test méthode peutAjouter avec trajet null")
    void testPeutAjouterNull() {
        Trajet trajet = new Trajet("1", LocalDate.now(), "Montreal", "Quebec");
        assertFalse(trajet.peutAjouter(null));
    }

    @Test
    @DisplayName("Test méthode peutAjouter avec une caisse dont la ville de départ est différente")
    void testPeutAjouterVilleDepart() {
        Trajet trajet = new Trajet("1", LocalDate.now(), "Montreal", "Quebec");
        Caisse caisse = new Caisse("1", LocalDate.now(), "Sherbrooke", "Quebec", 100);
        assertFalse(trajet.peutAjouter(caisse));
    }

    @Test
    @DisplayName("Test méthode peutAjouter avec une caisse valide")
    void testPeutAjouter() {
        Trajet trajet = new Trajet("1", LocalDate.now(), "Montreal", "Quebec");
        Caisse caisse = new Caisse("1", LocalDate.now(), "Montreal", "Quebec", 100);
        assertTrue(trajet.peutAjouter(caisse));
    }
}
