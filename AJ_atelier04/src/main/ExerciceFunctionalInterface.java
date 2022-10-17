package main;

import domaine.Employe;
import domaine.Genre;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

public class ExerciceFunctionalInterface {
    public static List<Employe> employes;
    public static void main(String[] args) {
        employes = new ArrayList<>();

        employes.add(new Employe(Genre.HOMME, 185, "Bob"));
        employes.add(new Employe(Genre.FEMME, 225, "Alice"));
        employes.add(new Employe(Genre.HOMME, 155, "John"));
        employes.add(new Employe(Genre.FEMME, 165, "Carole"));
        employes.add(new Employe(Genre.HOMME, 185, "Alex"));
        employes.add(new Employe(Genre.HOMME, 185, "Bart"));

        exMap();

        exComparator();

        exForEach();

    }

    /**
     * Replacer l'instatiation de la classe EmployeComparator par un lambda
     */
    private static void exComparator() {
        employes.sort((e1, e2) -> {
            if (e1.getTaille() == e2.getTaille()){
                return e1.getNom().compareTo(e2.getNom());
            }
            return e2.getTaille() - e1.getTaille();
        });
        System.out.println("Employés triés:");
        System.out.println(employes);
    }

    /**
     * Trouver le type du paramètre de la méthode map.
     * Ensuite créer une classe implémentant la functional interface correspondante pour
     * remplacer le lambda en paramètre par une instance de celle-ci.
     */
    private static void exMap() {
        Stream<String> listeNom = employes.stream()
                .filter(e -> e.getGenre() == Genre.HOMME)
                .sorted(Comparator.comparingInt(Employe::getTaille)
                        .reversed())
                .map(new FunctionExMap()); // String
        System.out.println(listeNom);
    }

    private static class FunctionExMap implements Function<Employe, String> {
        @Override
        public String apply(Employe employe) {
            return employe.getNom();
        }
    }


    /**
     * Trouver le type du paramètre de la méthode foreach.
     * Ensuite créer une classe implémentant la functional interface correspondante pour
     * remplacer le lambda en paramètre par une instance de celle-ci.
     */
    private static void exForEach(){
        employes.forEach(new ConsumerForEach()); // Employe
    }

    private static class ConsumerForEach implements Consumer<Employe> {
        @Override
        public void accept(Employe employe) {
            System.out.println(employe);
        }
    }
}
