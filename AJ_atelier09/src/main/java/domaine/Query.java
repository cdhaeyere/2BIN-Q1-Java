package domaine;

public interface Query {
    String getUrl();

    void setString(String url);

    QueryMethod getMethod();

    void setMethod(QueryMethod method);

    public enum QueryMethod {
        GET, POST
    }
}
