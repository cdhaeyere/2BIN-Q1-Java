package be.vinci.aj.main;

import be.vinci.aj.domaine.QueryFactory;
import be.vinci.aj.domaine.QueryFactoryImpl;
import be.vinci.aj.server.ProxyServer;

public class Main {

    public static void main(String[] args) {
        QueryFactory queryFactory = new QueryFactoryImpl();
        ProxyServer proxyServer = new ProxyServer(queryFactory);
        proxyServer.startServer();
    }
}
