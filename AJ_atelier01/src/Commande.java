import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Iterator;

public class Commande implements Iterable<LigneDeCommande> {

    private static int           numeroSuivant = 1;
    private int           numero;
    private Client        client;
    private LocalDateTime date;
    private ArrayList<LigneDeCommande>     lignesCommande;

    public Commande(Client client) {
        if (client == null) throw new IllegalArgumentException("Le client ne peut pas être null");
        if (client.getCommandeEnCours() != null) throw new IllegalArgumentException("impossible de créer une commande pour un client ayant encore une commande en cours");
        this.numero = numeroSuivant;
        numeroSuivant = numero + 1;
        this.client = client;
        client.enregistrer(this);
        this.date = LocalDateTime.now();
        this.lignesCommande = new ArrayList<>();
    }

    public int getNumero() {
        return numero;
    }

    public Client getClient() {
        return client;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public boolean ajouter(Pizza pizza, int quantite) {
        if (pizza == null) throw new IllegalArgumentException("La pizza ne peut pas être null");
        if (quantite <= 0) throw new IllegalArgumentException("La quantité ne peut être inférieure ou égale à 0");
        if (this == client.getCommandeEnCours()) {
            for (LigneDeCommande ligneDeCommande : lignesCommande) {
                if (ligneDeCommande.getPizza().getTitre() == pizza.getTitre()) {
                    lignesCommande.get(lignesCommande.indexOf(ligneDeCommande)).setQuantite(lignesCommande.get(lignesCommande.indexOf(ligneDeCommande)).getQuantite() + quantite);
                    return true;
                }
            }
            lignesCommande.add(new LigneDeCommande(pizza, quantite));
            return true;
        }
        return false;
    }

    public boolean ajouter(Pizza pizza) {
        return ajouter(pizza, 1);
    }

    public boolean retirer(Pizza pizza, int quantite) {
        if (pizza == null) throw new IllegalArgumentException("La pizza ne peut pas être null");
        if (quantite <= 0) throw new IllegalArgumentException("La quantité ne peut être inférieure ou égale à 0");
        if (this == client.getCommandeEnCours()) {
            for (LigneDeCommande ligneDeCommande : lignesCommande) {
                if (ligneDeCommande.getPizza().getTitre() == pizza.getTitre()) {
                    if (ligneDeCommande.getQuantite() - quantite > 0) {
                        lignesCommande.get(lignesCommande.indexOf(ligneDeCommande)).setQuantite(lignesCommande.get(lignesCommande.indexOf(ligneDeCommande)).getQuantite() - quantite);
                        return true;
                    } else {
                        lignesCommande.remove(ligneDeCommande);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean retirer(Pizza pizza) {
        return retirer(pizza, 1);
    }

    public boolean supprimer(Pizza pizza) {
        if (pizza == null) throw new IllegalArgumentException("La pizza ne peut pas être null");
        if (this == client.getCommandeEnCours()) {
            for (LigneDeCommande ligneDeCommande : lignesCommande) {
                if (ligneDeCommande.getPizza().getTitre() == pizza.getTitre()) {
                    lignesCommande.remove(ligneDeCommande);
                    return true;
                }
            }
        }
        return false;
    }

    public double calculerMontantTotal() {
        double montantTotal = 0;
        for (LigneDeCommande ligne : lignesCommande) {
            montantTotal += ligne.calculerPrixTotal();
        }
        return montantTotal;
    }

    public String detailler() {
        StringBuilder sb = new StringBuilder();
        for (LigneDeCommande ligneDeCommande : lignesCommande) {
            sb.append(ligneDeCommande.getQuantite()).append(" ").append(ligneDeCommande.getPizza().getTitre()).append(" ").append("à").append(" ").append(ligneDeCommande.getPrixUnitaire()).append("\n");
        }
        return sb.toString();
    }

    public Iterator<LigneDeCommande> iterator() {
        return lignesCommande.iterator();
    }

    public String toString() {
        DateTimeFormatter formater = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
        String encours = "";
        if (client.getCommandeEnCours() == this)
            encours = " (en cours)";
        return "Commande n° " + numero + encours + " du " + client + "\ndate : " + formater.format(date);
    }
}
