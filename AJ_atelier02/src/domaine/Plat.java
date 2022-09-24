package domaine;

import domaine.Instruction;
import util.Util;

import java.time.Duration;
import java.util.*;

public class Plat {

    private String                   nom;
    private int                      nbPersonnes;
    private Difficulte               niveauDeDifficulte;
    private Cout                     cout;
    private Duration                 dureeEnMinutes;
    private List<Instruction>        recette;
    private Set<IngredientQuantifie> ingredients;

    public Plat(String nom, int nbPersonnes, Difficulte niveauDeDifficulte, Cout cout) {
        Util.checkString(nom);
        Util.checkStrictlyPositive(nbPersonnes);
        Util.checkObject(niveauDeDifficulte);
        Util.checkObject(cout);
        this.nom = nom;
        this.nbPersonnes = nbPersonnes;
        this.niveauDeDifficulte = niveauDeDifficulte;
        this.cout = cout;
        this.dureeEnMinutes = Duration.ZERO;
        recette = new ArrayList<>();
        ingredients = new HashSet<>();
    }

    public void insererInstruction(int position, Instruction instruction) {
        Util.checkObject(instruction);
        if (position <= 0 || position > recette.size() + 1) {
            throw new IllegalArgumentException("La position doit être comprise entre 1 et " + recette.size());
        }
        recette.add(position-1, instruction);
        dureeEnMinutes = dureeEnMinutes.plus(instruction.getDureeEnMinutes());
    }

    public void ajouterInstruction(Instruction instruction) {
        Util.checkObject(instruction);
        recette.add(instruction);
        dureeEnMinutes = dureeEnMinutes.plus(instruction.getDureeEnMinutes());
    }

    public Instruction remplacerInstruction(int position, Instruction instruction) {
        Util.checkObject(instruction);
        if (position <= 0 || position > recette.size()) {
            throw new IllegalArgumentException("La position doit être comprise entre 1 et " + recette.size());
        }
        Instruction ancienneInstruction = recette.get(position-1);
        dureeEnMinutes = dureeEnMinutes.minus(ancienneInstruction.getDureeEnMinutes());
        recette.set(position-1, instruction);
        dureeEnMinutes = dureeEnMinutes.plus(instruction.getDureeEnMinutes());
        return ancienneInstruction;
    }

    public Instruction supprimerInstruction(int position) {
        if (position <= 0 || position > recette.size()) {
            throw new IllegalArgumentException("La position doit être comprise entre 0 et " + recette.size());
        }
        Instruction instruction = recette.remove(position-1);
        dureeEnMinutes = dureeEnMinutes.minus(instruction.getDureeEnMinutes());
        return instruction;
    }

    public Iterator<Instruction> instructions() {
        return Collections.unmodifiableList(recette).iterator();
    }

    public void ajouterIngredient(Ingredient ingredient, int quantite, Unite unite) {
        Util.checkObject(ingredient);
        Util.checkStrictlyPositive(quantite);
        Util.checkObject(unite);
        ingredients.add(new IngredientQuantifie(ingredient, quantite, unite));
    }

    public void ajouterIngredient(Ingredient ingredient, int quantite) {
        ajouterIngredient(ingredient, quantite, Unite.NEANT);
    }

    public void modifierIngredient(Ingredient ingredient, int quantite, Unite unite) {
        Util.checkObject(ingredient);
        Util.checkStrictlyPositive(quantite);
        Util.checkObject(unite);
        for (IngredientQuantifie ingredientQuantifie : ingredients) {
            if (ingredientQuantifie.getIngredient().equals(ingredient)) {
                ingredientQuantifie.setQuantite(quantite);
                ingredientQuantifie.setUnite(unite);
            }
        }
    }

    public void supprimerIngredient(Ingredient ingredient) {
        Util.checkObject(ingredient);
        ingredients.removeIf(ingredientQuantifie -> ingredientQuantifie.getIngredient().equals(ingredient));
    }

    public IngredientQuantifie trouverIngredientQuantifie(Ingredient ingredient) {
        Util.checkObject(ingredient);
        for (IngredientQuantifie ingredientQuantifie : ingredients) {
            if (ingredientQuantifie.getIngredient().equals(ingredient)) {
                return ingredientQuantifie;
            }
        }
        return null;
    }

    public SortedSet<Ingredient> ingredients() {
        SortedSet<Ingredient> ingredientsTries = new TreeSet<>(new IngredientComparator());
        for (IngredientQuantifie ingredientQuantifie : ingredients) {
            ingredientsTries.add(ingredientQuantifie.getIngredient());
        }
        return ingredientsTries;
    }

    private class IngredientComparator implements Comparator<Ingredient> {
        @Override
        public int compare(Ingredient ingredient1, Ingredient ingredient2) {
            return ingredient1.getNom().compareTo(ingredient2.getNom());
        }
    }

    @Override
    public String toString() {
        String hms = String.format("%d h %02d m", dureeEnMinutes.toHours(), dureeEnMinutes.toMinutes()%60);
        String res = this.nom + "\n\n";
        res += "Pour " + this.nbPersonnes + " personnes\n";
        res += "Difficulté : " + this.niveauDeDifficulte + "\n";
        res += "Coût : " + this.cout + "\n";
        res += "Durée : " + hms + " \n\n";
        res += "Ingrédients :\n";
        for (IngredientQuantifie ing : this.ingredients) {
            res += ing + "\n";
        }
        int i = 1;
        res += "\n";
        for (Instruction instruction : this.recette) {
            res += i++ + ". " + instruction + "\n";
        }
        return res;
    }


    public enum Difficulte {
        X,
        XX,
        XXX,
        XXXX,
        XXXXX;

        @Override
        public String toString() {
            return name().replace("X", "*");
        }
    }

    public enum Cout {
        $,
        $$,
        $$$,
        $$$$,
        $$$$$;

        @Override
        public String toString() {
            return name().replace("$", "€");
        }
    }
}
