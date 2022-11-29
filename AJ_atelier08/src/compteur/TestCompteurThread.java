package compteur;

public class TestCompteurThread {

	public static void main(String[] args) {
		CompteurThread[] compteurs = { new CompteurThread("Bolt", 10), new CompteurThread("Jakson", 10), new CompteurThread("Robert", 10), new CompteurThread("Stéphanie", 10) };

		for (CompteurThread compteur : compteurs) {
			compteur.start();
		}

		for (CompteurThread compteur : compteurs) {
			try {
				compteur.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		System.out.println("Le(la) gagnant(e) est " + CompteurThread.getGagnant().getNom());
	}
}
