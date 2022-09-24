import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public class Client implements Iterable<Commande> {

    private static int numeroSuivant = 1;

    private int numero;
    private String nom;
    private String prenom;
    private String telephone;
    private Commande commandeEnCours;
    private ArrayList<Commande> historiqueCommandes;

    public Client(String nom, String prenom, String telephone) {
        if (nom.equals("")) throw new IllegalArgumentException("Le nom du client ne peut pas être vide");
        if (prenom.equals("")) throw new IllegalArgumentException("Le prenom du client ne peut être vide");
        if (telephone.equals("")) throw new IllegalArgumentException("Le telephone du client ne peut être vide");
        this.numero = numeroSuivant++;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.historiqueCommandes = new ArrayList<>();
    }

    public int getNumero() {
        return numero;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getTelephone() {
        return telephone;
    }

    public Commande getCommandeEnCours() {
        return commandeEnCours;
    }

    public boolean enregistrer(Commande commande) {
        if (commande == null) throw new IllegalArgumentException("La commande ne peut pas être null");
        if (historiqueCommandes.contains(commande)) return false;
        if (commandeEnCours == null && commande.getClient() == this) {
            commandeEnCours = commande;
            return true;
        }
        return false;
    }

    public boolean cloturerCommandeEnCours() {
        if (commandeEnCours != null) {
            historiqueCommandes.add(commandeEnCours);
            commandeEnCours = null;
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client)) return false;
        Client client = (Client) o;
        return numero == client.numero;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numero);
    }

    @Override
    public String toString() {
        return "client n°" + numero + " (" + prenom + " " + nom + ", telephone : " + telephone + ")";
    }

    @Override
    public Iterator<Commande> iterator() {
        return historiqueCommandes.iterator();
    }
}
