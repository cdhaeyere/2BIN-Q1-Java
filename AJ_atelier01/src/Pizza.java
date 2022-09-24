import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public abstract class Pizza implements Iterable<Ingredient> {

    public double PRIX_BASE = 5.0;

    private String titre;
    private String description;
    private ArrayList<Ingredient> ingredients;

    public Pizza(String titre, String description) {
        this(titre, description, new ArrayList<>());
    }

    public Pizza(String titre, String description, ArrayList<Ingredient> ingredients) {
        if (titre.equals("")) throw new IllegalArgumentException("Le titre ne peut pas être vide");
        if (description.equals("")) throw new IllegalArgumentException("La description ne peut pas être vide");
        ArrayList<Ingredient> ingredientsV = new ArrayList<>();
        for (Ingredient ingredient : ingredients) {
            if (!ingredientsV.contains(ingredient)) {
                ingredientsV.add(ingredient);
            } else {
                throw new IllegalArgumentException("Il ne peut pas y avoir deux fois le même ingrédient dans une pizza");
            }
        }

        this.titre = titre;
        this.description = description;
        this.ingredients = ingredientsV;
    }

    public String getTitre() {
        return titre;
    }

    public String getDescription() {
        return description;
    }

    public boolean ajouter(Ingredient ingredient) {
        if (ingredient == null) throw new IllegalArgumentException("L'ingredient ne peut pas être null");
        if (!ingredients.contains(ingredient)) {
            ingredients.add(ingredient);
            return true;
        } else {
            return false;
        }
    }

    public boolean supprimer(Ingredient ingredient) {
        if (ingredient == null) throw new IllegalArgumentException("L'ingredient ne peut être null");
        if (ingredients.contains(ingredient)) {
            ingredients.remove(ingredient);
            return true;
        } else {
            return false;
        }
    }

    public double calculerPrix() {
        double prixIngredients = 0.0;
        for (Ingredient ingredient : ingredients) {
            prixIngredients += ingredient.getPrix();
        }
        return PRIX_BASE + prixIngredients;
    }

    @Override
    public Iterator<Ingredient> iterator() {
        return ingredients.iterator();
    }

    @Override
    public String toString() {
        String infos = titre + "\n" + description + "\nIngrédients : ";
        for (Ingredient ingredient : ingredients){
            infos +="\n" + ingredient.getNom();
        }
        infos +="\nprix : " + calculerPrix() + " euros";
        return infos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pizza)) return false;
        Pizza that = (Pizza) o;
        return titre.equals(that.titre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(titre);
    }
}
