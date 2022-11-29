package abonnements;

import jdk.nashorn.internal.runtime.regexp.joni.exception.InternalException;

import java.util.ArrayList;
import java.util.List;


public class Compte {

    private double solde;
    private List<Depense> historique;

    public Compte(double solde) {
        this.solde = solde;
        historique = new ArrayList<>();
    }

    public boolean depenser(Depense depense) {
        synchronized (this) {
            if (solde<depense.getMontant())
                return false;

            solde = getSolde() - depense.getMontant();
            historique.add(depense);
            return true;
        }
    }

    public double getSolde() {

        try {
            Thread.sleep(30);
        } catch (InterruptedException e) {
            throw new InternalException("Who killed me???");
        }

        return solde;
    }

    public void verifier() {
        double total = 0;
        for(Depense d : historique) {
            System.out.println(d.getRaison() + " : -" + d.getMontant());
            total += d.getMontant();
        }
        System.out.println("==================================");
        System.out.println("Total dépensé : " + total);
        System.out.println("Solde restant (doit être positif) : " + solde);
    }
}
