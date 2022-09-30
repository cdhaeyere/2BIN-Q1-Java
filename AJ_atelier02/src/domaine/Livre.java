package domaine;

import util.Util;

import java.util.*;

public class Livre {

    private TreeMap<Plat.Type, SortedSet<Plat>> plats = new TreeMap<>();

    public boolean ajouterPlat(Plat plat) {
        Util.checkObject(plat);
        if (plats.containsKey(plat.getType())) {
            return plats.get(plat.getType()).add(plat);
        } else {
            SortedSet<Plat> set = new TreeSet<>(Comparator.comparing(Plat::getNom));
            set.add(plat);
            plats.put(plat.getType(), set);
            return true;
        }
    }

    public boolean supprimerPlat(Plat plat) {
        Util.checkObject(plat);
        if (!plats.get(plat.getType()).contains(plat)) {
            return false;
        }
        plats.get(plat.getType()).remove(plat);
        if (plats.get(plat.getType()).isEmpty()) {
            plats.remove(plat.getType());
        }
        return true;
    }

    public SortedSet<Plat> getPlatsParType(Plat.Type type) {
        Util.checkObject(type);
        return Collections.unmodifiableSortedSet(plats.get(type));
    }

    public boolean contientPlat(Plat plat) {
        Util.checkObject(plat);
        return plats.get(plat.getType()).contains(plat);
    }

    public Set<Plat> tousLesPlats() {
        SortedSet<Plat> setPlats = new TreeSet<>(new PlatsComparator());
        for (Plat.Type type : Plat.Type.values()) {
            setPlats.addAll(plats.get(type));
        }
        return setPlats;
    }

    public class PlatsComparator implements Comparator<Plat> {

        @Override
        public int compare(Plat o1, Plat o2) {
            if (o1.getType().compareTo(o2.getType()) == 0) {
                if (o1.getNom().compareTo(o2.getNom()) == 0) {
                    return o1.getNbPersonnes() - o2.getNbPersonnes();
                } else {
                    return o1.getNom().compareTo(o2.getNom());
                }
            }
            return o1.getType().compareTo(o2.getType());
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Plat.Type type : plats.keySet()) {
            sb.append(type.getNom()).append("\n");
            for (Plat plat : plats.get(type)) {
                sb.append("\t").append(plat.getNom()).append("\n");
            }
        }
        return sb.toString();
    }
}
