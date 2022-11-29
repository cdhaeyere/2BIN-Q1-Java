package deadlock_magic;

public class Grimoire {
    public enum Ingredient {
        BAVE_DE_CRAPEAU, PIERRE_PHILOSOPHALE, CUISSE_DE_GRENOUILLE, POIL_DE_TROLL, SILICIUM, LARME_DE_FEE
    }

    public void mediter(){
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void calmeInfini(String nomMagicien){
        synchronized (Ingredient.LARME_DE_FEE){
            System.out.println(Ingredient.LARME_DE_FEE + " pris par " + nomMagicien +". Je vais méditer");
            mediter();
            synchronized (Ingredient.POIL_DE_TROLL){
                System.out.println(Ingredient.POIL_DE_TROLL + " pris par " + nomMagicien +". Je vais méditer");
                mediter();
                synchronized (Ingredient.CUISSE_DE_GRENOUILLE){
                    System.out.println(Ingredient.CUISSE_DE_GRENOUILLE + " pris par " + nomMagicien +". Je vais méditer");
                    mediter();
                    System.out.println("Calme infini lancé !");
                }
            }
        }
    }

    public void demonDeMinuit(String nomMagicien){
        synchronized (Ingredient.LARME_DE_FEE){
            System.out.println(Ingredient.LARME_DE_FEE + " pris par " + nomMagicien +". Je vais méditer");
            mediter();
            synchronized (Ingredient.POIL_DE_TROLL){
                System.out.println(Ingredient.POIL_DE_TROLL + " pris par " + nomMagicien +". Je vais méditer");
                mediter();
                synchronized (Ingredient.PIERRE_PHILOSOPHALE){
                    System.out.println(Ingredient.PIERRE_PHILOSOPHALE + " pris par " + nomMagicien +". Je vais méditer");
                    mediter();
                    System.out.println("Démons de minuit lancé !");
                }
            }
        }
    }


    public void pluieBienfaisante(String nomMagicien){
        synchronized (Ingredient.SILICIUM){
            System.out.println(Ingredient.SILICIUM + " pris par " + nomMagicien +". Je vais méditer");
            mediter();
            synchronized (Ingredient.CUISSE_DE_GRENOUILLE){
                System.out.println(Ingredient.CUISSE_DE_GRENOUILLE + " pris par " + nomMagicien +". Je vais méditer");
                mediter();
                synchronized (Ingredient.PIERRE_PHILOSOPHALE){
                    System.out.println(Ingredient.PIERRE_PHILOSOPHALE + " pris par " + nomMagicien +". Je vais méditer");
                    mediter();
                    System.out.println("Pluie bienfaisante lancé !");
                }
            }
        }
    }
}
