package abonnements;

import java.util.ArrayList;

/**
 * Ne pas modifier
 */
public class TestAbonnements {

    public static void main(String[] args) {
        Compte compte = new Compte(800.0);
        ArrayList<Abonnement> abonnements = new ArrayList<>();
        abonnements.add(new Abonnement("MeTube Premium", 15.99, compte));
        abonnements.add(new Abonnement("Stopify Premium", 12.30, compte));
        abonnements.add(new Abonnement("Netflims", 19.99, compte));
        abonnements.add(new Abonnement("Nile Prime", 22.50, compte));
        abonnements.add(new Abonnement("7Gag Pro+", 35.83, compte));

        for (Abonnement abonnement : abonnements) {
            abonnement.start();
        }

        for (Abonnement abonnement : abonnements) {
            try {
                abonnement.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        compte.verifier();
    }
}
