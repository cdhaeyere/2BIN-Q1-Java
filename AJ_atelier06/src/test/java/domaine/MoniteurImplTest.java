package domaine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class MoniteurImplTest {

    private Sport sportCompetent;
    private Moniteur moniteur;
    private Stage stageValide;

    @BeforeEach
    void setUp() {
        moniteur = new MoniteurImpl("Lagaffe");
        sportCompetent = Mockito.mock(Sport.class);
        Mockito.when(sportCompetent.contientMoniteur(moniteur)).thenReturn(true);
        stageValide = Mockito.mock(Stage.class);
        Mockito.when(stageValide.getSport()).thenReturn(sportCompetent);
        Mockito.when(stageValide.getMoniteur()).thenReturn(null);
        Mockito.when(stageValide.getNumeroDeSemaine()).thenReturn(8);
    }

    private void amenerALEtat(int etat, Moniteur moniteur) {
        for (int i = 1; i <= etat; i++) {
            Stage stageAjoute = Mockito.mock(Stage.class);
            Mockito.when(stageAjoute.getSport()).thenReturn(sportCompetent);
            Mockito.when(stageAjoute.getMoniteur()).thenReturn(null);
            Mockito.when(stageAjoute.getNumeroDeSemaine()).thenReturn(i);
            moniteur.ajouterStage(stageAjoute);
        }
    }

    @Test
    void testMoniteurTC1() {
        assertAll(() -> assertTrue(moniteur.ajouterStage(stageValide)),
                () -> assertTrue(moniteur.contientStage(stageValide)),
                () -> assertEquals(1, moniteur.nombreDeStages()),
                () -> Mockito.verify(stageValide).enregistrerMoniteur(moniteur)
        );
    }

    @Test
    void testMoniteurTC2() {
        amenerALEtat(1, moniteur);
        assertAll(() -> assertTrue(moniteur.ajouterStage(stageValide)),
                () -> assertTrue(moniteur.contientStage(stageValide)),
                () -> assertEquals(2, moniteur.nombreDeStages()),
                () -> Mockito.verify(stageValide).enregistrerMoniteur(moniteur)
        );
    }

    @Test
    void testMoniteurTC3() {
        amenerALEtat(2, moniteur);
        assertAll(() -> assertTrue(moniteur.ajouterStage(stageValide)),
                () -> assertTrue(moniteur.contientStage(stageValide)),
                () -> assertEquals(3, moniteur.nombreDeStages()),
                () -> Mockito.verify(stageValide).enregistrerMoniteur(moniteur)
        );
    }

    @Test
    void testMoniteurTC4() {
        amenerALEtat(3, moniteur);
        assertAll(() -> assertTrue(moniteur.ajouterStage(stageValide)),
                () -> assertTrue(moniteur.contientStage(stageValide)),
                () -> assertEquals(4, moniteur.nombreDeStages()),
                () -> Mockito.verify(stageValide).enregistrerMoniteur(moniteur)
        );
    }


    @Test
    void testMoniteurTC5() {
        amenerALEtat(3, moniteur);
        moniteur.ajouterStage(stageValide);
        assertAll(() -> assertFalse(moniteur.ajouterStage(stageValide)),
                () -> assertEquals(4, moniteur.nombreDeStages()),
                //Vérification que la méthode enregistrerMoniteur a été appelée
                // une seule fois (pour le premier ajout) et non 2
                () -> Mockito.verify(stageValide).enregistrerMoniteur(moniteur)
        );
    }

    @Test
    void testMoniteurTC6() {
        amenerALEtat(4, moniteur);
        Stage stageMemeSemaine = Mockito.mock(Stage.class);
        Mockito.when(stageMemeSemaine.getSport()).thenReturn(sportCompetent);
        Mockito.when(stageMemeSemaine.getMoniteur()).thenReturn(null);
        Mockito.when(stageMemeSemaine.getNumeroDeSemaine()).thenReturn(1);
        assertAll(() -> assertFalse(moniteur.ajouterStage(stageMemeSemaine)),
                () -> assertFalse(moniteur.contientStage(stageMemeSemaine)),
                () -> assertEquals(4, moniteur.nombreDeStages()),
                () -> Mockito.verify(stageMemeSemaine, Mockito.never()).enregistrerMoniteur(moniteur)
        );
    }

    @Test
    void testMoniteurTC7() {
        amenerALEtat(4, moniteur);
        Moniteur autreMoniteur = new MoniteurImpl("Snake");
        Stage stageAutreMoniteur = Mockito.mock(Stage.class);
        Mockito.when(stageAutreMoniteur.getSport()).thenReturn(sportCompetent);
        Mockito.when(stageAutreMoniteur.getMoniteur()).thenReturn(autreMoniteur);
        Mockito.when(stageAutreMoniteur.getNumeroDeSemaine()).thenReturn(8);
        assertAll(() -> assertFalse(moniteur.ajouterStage(stageAutreMoniteur)),
                () -> assertFalse(moniteur.contientStage(stageAutreMoniteur)),
                () -> assertEquals(4, moniteur.nombreDeStages()),
                () -> Mockito.verify(stageAutreMoniteur, Mockito.never()).enregistrerMoniteur(moniteur)
        );
    }

    @Test
    void testMoniteurTC8() {
        amenerALEtat(4, moniteur);
        Stage stageBonMoniteur = Mockito.mock(Stage.class);
        Mockito.when(stageBonMoniteur.getSport()).thenReturn(sportCompetent);
        Mockito.when(stageBonMoniteur.getMoniteur()).thenReturn(moniteur);
        Mockito.when(stageBonMoniteur.getNumeroDeSemaine()).thenReturn(8);
        assertAll(() -> assertTrue(moniteur.ajouterStage(stageBonMoniteur)),
                () -> assertTrue(moniteur.contientStage(stageBonMoniteur)),
                () -> assertEquals(5, moniteur.nombreDeStages()),
                () -> Mockito.verify(stageBonMoniteur, Mockito.never()).enregistrerMoniteur(moniteur)
        );
    }

    @Test
    void testMoniteurTC9() {
        amenerALEtat(4, moniteur);
        Sport sportHorsCompetence = Mockito.mock(Sport.class);
        Mockito.when(sportHorsCompetence.contientMoniteur(moniteur)).thenReturn(false);
        Stage stageMauvaisSport = Mockito.mock(Stage.class);
        Mockito.when(stageMauvaisSport.getSport()).thenReturn(sportHorsCompetence);
        Mockito.when(stageMauvaisSport.getMoniteur()).thenReturn(null);
        Mockito.when(stageMauvaisSport.getNumeroDeSemaine()).thenReturn(8);
        assertAll(() -> assertFalse(moniteur.ajouterStage(stageMauvaisSport)),
                () -> assertFalse(moniteur.contientStage(stageMauvaisSport)),
                () -> assertEquals(4, moniteur.nombreDeStages()),
                () -> Mockito.verify(stageMauvaisSport, Mockito.never()).enregistrerMoniteur(moniteur)
        );
    }


}