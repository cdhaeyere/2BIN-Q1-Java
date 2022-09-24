import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Objects;

public class PizzaComposable extends Pizza {

    private Client createur;
    private LocalDateTime date;

    public PizzaComposable(Client createur) {
        super("Pizza composable du client " + createur.getNumero(), "Pizza de " + createur.getNom() + " " + createur.getPrenom());
        if (createur == null) throw new IllegalArgumentException("Le createur ne peut pas être null");
        this.createur = createur;
        this.date = LocalDateTime.now();
    }

    public Client getCreateur() {
        return createur;
    }

    public LocalDateTime getDate() {
        return date;
    }

    @Override
    public String toString() {
        DateTimeFormatter formater = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
        return super.toString() + "\nPizza créée le " + formater.format(date);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pizza)) return false;
        Pizza that = (Pizza) o;
        return this.getTitre().equals(that.getTitre());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getTitre(), this.date);
    }
}
