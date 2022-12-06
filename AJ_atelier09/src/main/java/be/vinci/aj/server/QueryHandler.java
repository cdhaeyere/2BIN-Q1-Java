package be.vinci.aj.server;

import be.vinci.aj.domaine.Query;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;

public class QueryHandler extends Thread {

    private Query queryImpl;

    public QueryHandler(Query queryImpl) {
        this.queryImpl = queryImpl;
    }

    @Override
    public void run() {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            try (CloseableHttpResponse response = httpClient.execute(new HttpGet(queryImpl.getUrl()))) {
                System.out.println(response.getCode());
                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity);
                System.out.println(result);
                EntityUtils.consume(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
