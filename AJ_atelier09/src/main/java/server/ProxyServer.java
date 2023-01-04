package server;

import blacklist.BlacklistServiceImpl;
import domaine.Query;
import domaine.QueryFactory;

import java.util.Scanner;

public class ProxyServer {

    private Scanner scanner = new Scanner(System.in);

    private QueryFactory queryFactory;

    public ProxyServer(QueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    public void startServer() {
        BlacklistServiceImpl blacklistService = new BlacklistServiceImpl();
        while (true) {
            String url = scanner.nextLine();
            Query queryImpl = queryFactory.getQuery();
            queryImpl.setString(url);
            queryImpl.setMethod(Query.QueryMethod.GET);
            if (blacklistService.check(queryImpl)) {
                System.out.println("Blacklisted");
                return;
            } else {
                System.out.println("Not blacklisted");
            }
            QueryHandler queryHandler = new QueryHandler(queryImpl);
            queryHandler.start();
        }
    }
}
