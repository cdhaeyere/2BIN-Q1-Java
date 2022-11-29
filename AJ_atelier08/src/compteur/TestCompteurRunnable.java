package compteur;

import java.util.ArrayList;
import java.util.List;

public class TestCompteurRunnable {

	public static void main(String[] args) {
		List<CompteurRunnable> compteurs = new ArrayList<>();
		compteurs.add(new CompteurRunnable("Bolt", 10));
		compteurs.add(new CompteurRunnable("Jakson", 10));
		compteurs.add(new CompteurRunnable("Robert", 10));
		compteurs.add(new CompteurRunnable("Stéphanie", 10));
		for (CompteurRunnable compteurRunnable : compteurs) {
			Thread t = new Thread(compteurRunnable);
			t.start();
		}
	}
}
