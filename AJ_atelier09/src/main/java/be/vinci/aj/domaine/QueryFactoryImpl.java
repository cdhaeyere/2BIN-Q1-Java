package be.vinci.aj.domaine;

public class QueryFactoryImpl implements QueryFactory {

    @Override
    public Query getQuery() {
        return new QueryImpl(null, null);
    }
}
