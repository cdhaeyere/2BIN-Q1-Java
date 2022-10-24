package domaine;

import exceptions.DateDejaPresenteException;
import exceptions.PrixNonDisponibleException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ProduitTest {

    private Prix prixAucune;
    private Prix prixPub;
    private Prix prixSolde;

    private Produit produit1;
    private Produit produit2;

    @BeforeEach
    void setUp() {
        prixAucune = new Prix();
        prixPub = new Prix(TypePromo.PUB, 0.1);
        prixSolde = new Prix(TypePromo.SOLDE, 0.2);

        prixAucune.definirPrix(1, 20);
        prixAucune.definirPrix(10, 10);

        prixPub.definirPrix(3, 15);

        produit1 = new Produit("Produit1", "Okay", "Rayon1");
        produit2 = new Produit("Produit2", "365", "Rayon2");

        produit1.ajouterPrix(LocalDate.of(2002, 9, 5), prixAucune);
        produit1.ajouterPrix(LocalDate.of(2002, 9, 4), prixPub);
        produit1.ajouterPrix(LocalDate.of(2002, 9, 3), prixSolde);
    }

    @Test
    @DisplayName("Test constructeur avec parametre null")
    void testConstructeurNull() {
        assertThrows(IllegalArgumentException.class, () -> new Produit(null, "1", "1"));
        assertThrows(IllegalArgumentException.class, () -> new Produit("1", null, "1"));
        assertThrows(IllegalArgumentException.class, () -> new Produit("1", "1", null));
    }

    @Test
    @DisplayName("Test constructeur avec parametre vide")
    void testConstructeurVide() {
        assertThrows(IllegalArgumentException.class, () -> new Produit("", "1", "1"));
        assertThrows(IllegalArgumentException.class, () -> new Produit("1", "", "1"));
        assertThrows(IllegalArgumentException.class, () -> new Produit("1", "1", ""));
    }

    @Test
    @DisplayName("Test constructeur avec parametre valide")
    void testConstructeurValide() {
        assertEquals("Produit1", produit1.getNom());
        assertEquals("Okay", produit1.getMarque());
        assertEquals("Rayon1", produit1.getRayon());
    }

    @Test
    @DisplayName("Test de la méthode ajouterPrix avec parametre null")
    void testAjouterPrix() {
        assertThrows(IllegalArgumentException.class, () -> produit1.ajouterPrix(null, new Prix()));
        assertThrows(IllegalArgumentException.class, () -> produit1.ajouterPrix(LocalDate.of(2001, 9, 5), null));
    }

    @Test
    @DisplayName("Test de la méthode ajouterPrix si date deja présente")
    void testAjouterPrixDateDejaPresente() {
        assertThrows(DateDejaPresenteException.class, () -> produit1.ajouterPrix(LocalDate.of(2002, 9, 5), new Prix()));
    }

    @Test
    @DisplayName("Test de la méthode ajouterPrix avec parametre valide")
    void testAjouterPrixValide() {
        produit1.ajouterPrix(LocalDate.of(2002, 9, 6), prixAucune);
        assertEquals(20, produit1.getPrix(LocalDate.of(2002, 9, 6)).getPrix(1));
    }

    @Test
    @DisplayName("Test de la méthode getPrix avec une date antérieure")
    void testGetPrixDateAnterieure() {
        assertThrows(PrixNonDisponibleException.class, () -> produit1.getPrix(LocalDate.of(2001, 9, 2)));
    }

    @Test
    @DisplayName("Test de la méthode getPrix avec un produit qui n'en a pas")
    void testGetPrixProduitSansPrix() {
        assertThrows(PrixNonDisponibleException.class, () -> produit2.getPrix(LocalDate.of(2001, 9, 2)));
    }

    @Test
    @DisplayName("Test de la méthode getPrix avec une date comprise entre deux prix, donnant le prix de la date antérieure")
    void testGetPrixDateComprise() {
        assertEquals(prixPub, produit1.getPrix(LocalDate.of(2002, 9, 4)));
    }

    @Test
    @DisplayName("Test de la méthode equals avec deux produits avec les memes attributs")
    void testEqualsMemeProduit() {
        Produit produit3 = new Produit("Produit1", "Okay", "Rayon1");
        assertEquals(produit1, produit3);
    }

    @Test
    @DisplayName("Test de la méthode equals avec deux produits avec des noms differents")
    void testEqualsNomDifferent() {
        Produit produit3 = new Produit("Produit3", "Okay", "Rayon1");
        assertNotEquals(produit1, produit3);
    }

    @Test
    @DisplayName("Test de la méthode equals avec deux produits avec des marques differentes")
    void testEqualsMarqueDifferent() {
        Produit produit3 = new Produit("Produit1", "Okay2", "Rayon1");
        assertNotEquals(produit1, produit3);
    }

    @Test
    @DisplayName("Test de la méthode equals avec deux produits avec des rayons differents")
    void testEqualsRayonDifferent() {
        Produit produit3 = new Produit("Produit1", "Okay", "Rayon2");
        assertNotEquals(produit1, produit3);
    }

    @Test
    @DisplayName("Test de la méthode hashCode avec deux objets ayant les memes attributs")
    void testHashCodeMemeProduit() {
        Produit produit3 = new Produit("Produit1", "Okay", "Rayon1");
        assertEquals(produit1.hashCode(), produit3.hashCode());
    }
}