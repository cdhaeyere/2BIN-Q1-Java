package domain;

public class RequestFactory {

    public static Request createRequest(String method, String URL) {
        Request request = new RequestImpl();
        request.setMethod(method);
        request.setURL(URL);
        return request;
    }

}
