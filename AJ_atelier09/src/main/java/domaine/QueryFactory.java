package domaine;

public interface QueryFactory {
    default Query getQuery() {
        return new QueryImpl(null, null);
    }
}
