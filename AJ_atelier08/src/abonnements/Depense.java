package abonnements;

/**
 * Ne pas modifier
 */
public class Depense {

    private final double montant;
    private final String raison;

    public Depense(double montant, String raison) {
        this.montant = montant;
        this.raison = raison;
    }

    public double getMontant() {
        return montant;
    }

    public String getRaison() {
        return raison;
    }
}
