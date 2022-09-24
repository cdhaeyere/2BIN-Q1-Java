import java.util.ArrayList;

public class PizzaComposee extends Pizza {

    public static final double REMISE = 15.0;

    public PizzaComposee(String titre, String description, ArrayList<Ingredient> ingredients) {
        super(titre, description, ingredients);
    }

    @Override
    public boolean ajouter(Ingredient ingredient) {
        if (ingredient == null) throw new IllegalArgumentException("L'ingredient ne peut pas être null");
        throw new UnsupportedOperationException("Les ingrédients d'une pizza composée ne peuvent pas être modifiés");
    }

    @Override
    public boolean supprimer(Ingredient ingredient) {
        if (ingredient == null) throw new IllegalArgumentException("L'ingredient ne peut pas être null");
        throw new UnsupportedOperationException("Les ingrédients d'une pizza composée ne peuvent pas être modifiés");
    }

    @Override
    public double calculerPrix() {
        return Math.ceil(super.calculerPrix() - (super.calculerPrix() * REMISE / 100));
    }
}
