package main;

import api.AuthorizeApiOperations;
import api.AuthorizeApiOperationsImpl;
import api.CdResource;
import api.JokeResource;
import domain.DomainFactory;
import domain.DomainFactoryImpl;
import jakarta.inject.Singleton;
import jakarta.ws.rs.core.UriBuilder;
import java.net.URI;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import services.Catalog;
import services.CatalogImpl;
import services.JokeLibrary;
import services.JokeLibraryImpl;
import utils.TimeProvider;
import utils.TimeProviderImpl;

public class ApiServer {

  private static HttpServer server;

  private static AbstractBinder applicationBinder = new AbstractBinder() {
    @Override
    protected void configure() {
      bind(CatalogImpl.class).to(Catalog.class).in(Singleton.class).ranked(1);
      bind(JokeLibraryImpl.class).to(JokeLibrary.class).in(Singleton.class).ranked(1);
      bind(DomainFactoryImpl.class).to(DomainFactory.class).in(Singleton.class).ranked(1);
      bind(AuthorizeApiOperationsImpl.class).to(AuthorizeApiOperations.class).in(Singleton.class)
          .ranked(1);
      bind(TimeProviderImpl.class).to(TimeProvider.class).in(Singleton.class).ranked(1);
    }
  };

  private static AbstractBinder additionalApplicationBinder;

  public static void main(String[] args) {
    start();
  }

  public static void start() {
    URI baseUri = UriBuilder.fromUri("http://localhost/").port(9998).build();
    ResourceConfig config = new ResourceConfig();
    config.registerClasses(CdResource.class, JokeResource.class);

    config.register(applicationBinder);
    if (additionalApplicationBinder != null) {
      config.register(additionalApplicationBinder);
    }

    server = GrizzlyHttpServerFactory.createHttpServer(baseUri, config);
    System.out.println("API server started on " + baseUri.getHost() + ":" + baseUri.getPort());
  }

  public static void stop() {
    setAdditionalApplicationBinder(null);
    server.shutdownNow();
  }

  public static void setApplicationBinder(
      AbstractBinder applicationBinder) {
    ApiServer.applicationBinder = applicationBinder;
  }

  public static void setAdditionalApplicationBinder(
      AbstractBinder additionalApplicationBinder) {
    ApiServer.additionalApplicationBinder = additionalApplicationBinder;
  }
}


