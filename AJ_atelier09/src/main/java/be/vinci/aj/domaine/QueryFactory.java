package be.vinci.aj.domaine;

public interface QueryFactory {
    default Query getQuery() {
        return new QueryImpl(null, null);
    }
}
