package domaine;

import exceptions.QuantiteNonAutoriseeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class PrixTest {

    private Prix prixAucune;
    private Prix prixPub;
    private Prix prixSolde;

    @BeforeEach
    void setUp() {
        prixAucune = new Prix();
        prixPub = new Prix(TypePromo.PUB, 0.1);
        prixSolde = new Prix(TypePromo.SOLDE, 0.2);

        prixAucune.definirPrix(1, 20);
        prixAucune.definirPrix(10, 10);

        prixPub.definirPrix(3, 15);
    }

    @Test
    @DisplayName("Test du constructeur de Prix")
    void testConstructeur() {
        assertThrows(IllegalArgumentException.class, () -> new Prix(null, 0.1));
        assertThrows(IllegalArgumentException.class, () -> new Prix(TypePromo.PUB, -0.1));
    }

    @Test
    @DisplayName("Test de la méthode getValeurPromo, retourne la bonne valeur")
    void testGetValeurPromo() {
        assertEquals(0, prixAucune.getValeurPromo());
        assertEquals(0.1, prixPub.getValeurPromo());
        assertEquals(0.2, prixSolde.getValeurPromo());
    }

    @Test
    @DisplayName("Test du getter de typePromo avec le type null")
    void testGetTypePromoNull() {
        assertNull(prixAucune.getTypePromo());
    }

    @Test
    @DisplayName("Test du getter de typePromo avec les bonnes valeurs")
    void testGetTypePromo() {
        assertEquals(TypePromo.SOLDE, prixSolde.getTypePromo());
        assertEquals(TypePromo.PUB, prixPub.getTypePromo());
    }

    @ParameterizedTest
    @DisplayName("Test de la méthode définir prix avec quantité négative ou nulle")
    @ValueSource(ints = {-1, 0})
    void testDefinirPrixQuantité(int quantite) {
        assertThrows(IllegalArgumentException.class, () -> prixAucune.definirPrix(quantite,  10));
    }

    @ParameterizedTest
    @DisplayName("Test de la méthode définir prix avec valeur négative ou nulle")
    @ValueSource(ints = {-1, 0})
    void testDefinirPrixValeur(int valeur) {
        assertThrows(IllegalArgumentException.class, () -> prixAucune.definirPrix(1, valeur));
    }

    @ParameterizedTest
    @DisplayName("Test du getter de prix avec une quantité négative ou nulle")
    @ValueSource(ints = {-1, 0})
    void testGetPrixQuantité(int quantite) {
        assertThrows(IllegalArgumentException.class, () -> prixAucune.getPrix(quantite));
    }

    @ParameterizedTest
    @DisplayName("Test du getter de prix avec des quantités définies, = 20")
    @ValueSource(ints = {1, 5})
    void testGetPrix(int quantite) {
        assertEquals(20, prixAucune.getPrix(quantite));
    }

    @ParameterizedTest
    @DisplayName("Test du getter de prix avec des quantités définies, = 10")
    @ValueSource(ints = {10, 15, 20, 25})
    void testGetPrix2(int quantite) {
        assertEquals(10, prixAucune.getPrix(quantite));
    }

    @Test
    @DisplayName("Test du getter de prix avec des quantitées qui doivent échouées")
    void testGetPrix() {
        assertThrows(QuantiteNonAutoriseeException.class, () -> prixPub.getPrix(2));
        assertThrows(QuantiteNonAutoriseeException.class, () -> prixSolde.getPrix(1));
    }
}