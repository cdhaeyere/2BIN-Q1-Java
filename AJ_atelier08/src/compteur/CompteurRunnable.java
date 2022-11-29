package compteur;

import java.util.concurrent.atomic.AtomicInteger;

public class CompteurRunnable implements Runnable {

    private final String nom;
    private final int    max;
    private static final AtomicInteger ordreArrivee = new AtomicInteger(1);

    @Override
    public void run() {
        for (int i = 1; i <= max; i++) {
            System.out.println(nom + " : " + i);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(nom + " a fini de compter et est arrivé en " + ordreArrivee.getAndIncrement() + "e position.");
    }

    public CompteurRunnable(String nom, int max) {
        this.nom = nom;
        this.max = max;
    }

    public String getNom() {
        return nom;
    }

}
