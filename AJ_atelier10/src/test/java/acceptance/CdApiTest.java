package acceptance;

import static org.junit.jupiter.api.Assertions.*;

import domain.CompactDisc;
import domain.DomainFactory;
import domain.DomainFactoryImpl;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import main.ApiServer;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.Catalog;
import services.CatalogImpl;

class CdApiTest {

  private Catalog defaultCatalog;
  private DomainFactory factory = new DomainFactoryImpl();
  @BeforeEach
  void setUp() {
    defaultCatalog = new CatalogImpl();
    CompactDisc cd1 = factory.createCompactDisc("Young Mystic", "Bob Marley", 3, 12, "666-777");
    CompactDisc cd2 = factory.createCompactDisc("Time Out", "Dave Brubeck Quartet", 10, 111.1, "123-456");
    CompactDisc cd3 = factory.createCompactDisc("Time Out", "Dave Brubeck Quartet", 1, 98, "123-457");
    CompactDisc cd4 = factory.createCompactDisc("Time Out", "Dave Brubeck Quartet", 150, 28.5, "123-458");
    defaultCatalog.initCatalog(cd1, cd2, cd3, cd4);

    ApiServer.setApplicationBinder((new AbstractBinder() {
      @Override
      protected void configure() {
        bind(defaultCatalog).to(Catalog.class);
      }
    }));

    ApiServer.start();
  }

  @AfterEach
  void tearDown() {
    ApiServer.stop();
  }

  @Test
  void findCdInStockFromTitle() {

    String expectedResponse = "title:Young Mystic, artist:Bob Marley, stock:3, price:12.0€, id:666-777";
    String actualResponse = getRequest("cds", "title=young mystic");
    assertEquals(expectedResponse, actualResponse);
  }

  @Test
  void cdNotFoundInCatalog() {
    String actualResponse = getRequest("cds", "title=there once was a song");
    assertEquals("No cds", actualResponse);
  }

  @Test
  void multipleCdsFoundInCatalog() {
    String expectedResponse = "title:Time Out, artist:Dave Brubeck Quartet, stock:10, price:111.1€, id:123-456";
    expectedResponse += "\ntitle:Time Out, artist:Dave Brubeck Quartet, stock:1, price:98.0€, id:123-457";
    expectedResponse += "\ntitle:Time Out, artist:Dave Brubeck Quartet, stock:150, price:28.5€, id:123-458";
    String actualResponse = getRequest("cds", "title=time out");
    assertEquals(expectedResponse, actualResponse);
  }


  private String getRequest(String path, String queryParam1) {
    Client c = ClientBuilder.newClient();
    WebTarget target = c.target("http://localhost:9998/");
    String key = queryParam1.split("=")[0];
    String value = queryParam1.split("=")[1];
    String responseBody = target.path(path).queryParam(key, value)
        .request().get(String.class);
    return responseBody;
  }


}