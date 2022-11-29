package deadlock_magic;

public class MagicTheGathering {
    public static void main(String[] args) throws InterruptedException{

        Grimoire codex = new Grimoire();

        Thread incantation1 = new Thread(){
            public void run(){
                    codex.calmeInfini("Gandalf");
            }
        };

        Thread incantation2 = new Thread(){
            public void run(){
                codex.demonDeMinuit("Saroumane");
            }
        };

        Thread incantation3 = new Thread(){
            public void run(){
                codex.pluieBienfaisante("Garcimore");
            }
        };

        incantation1.start();
        incantation2.start();
        incantation3.start();

        incantation1.join();
        incantation2.join();
        incantation3.join();


        System.out.println("Toutes les incantations ont réussies !!");
    }
}
