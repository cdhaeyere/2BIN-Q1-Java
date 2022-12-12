package acceptance;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import domain.DomainFactory;
import domain.DomainFactoryImpl;
import domain.Joke;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import java.util.HashMap;
import java.util.Map;
import main.ApiServer;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import services.JokeLibrary;
import services.JokeLibraryImpl;
import utils.TimeProvider;

public class JokeApiTest {

  private static DomainFactory factory = new DomainFactoryImpl();


  private Joke joke1;
  private Joke joke2;
  private Joke joke3;


  public JokeApiTest() {
    joke1 = factory.createJoke("Christmas", "Santa Claus",
        "Santa Clause is lost... Luckily, he's got a smartphone. Bad luck, there is no network... So he builds a network tree and the legend began!",
        "Christmas Lord");
    joke2 = factory.createJoke("Bun", "Angry mussel",
        "During a quarrel, a mussel says to another : \"Don't be shellfish !\"'", "Fruit Master");

    joke3 = factory.createJoke("Bun", "Strong mussel",
        "A mussel says to another : \"I have no mussel, can you train me to be fit ?\"'",
        "Fruit Master");
  }

  private static String AFTER_HOURS_MESSAGE = "At FREE WEB SERVICES we are doing our best to provide serious jokes. To protect your from addiction, we don’t provide any services at night. See you later in the day.";

  @AfterEach
  void tearDown() {
    ApiServer.stop();
  }

  @Test
  void singleJokeExists() {

    bootServerWithDependencies(joke1);

    String actualResponse = getRequest("jokes");

    assertAll(() -> assertEquals(joke1.toString(), actualResponse),
        () -> assertNotEquals("No jokes", actualResponse));
    ;
  }


  @Test
  void multipleJokesExist() {

    bootServerWithDependencies(joke1, joke2);

    String actualResponse = getRequest("jokes");

    assertAll(() -> assertEquals(joke1 + "\n" + joke2, actualResponse),
        () -> assertNotEquals("No jokes", actualResponse));
    ;
  }

  @Test
  void noJokeExists() {

    bootServerWithDependencies();

    String actualResponse = getRequest("jokes");
    assertEquals("No jokes", actualResponse);


  }


  @Test
  void createJoke() {
    var category = "Football";
    var title = "Birthday card";
    var content = "Why was the footballer upset on his birthday? He got a red card !";
    var author = "Maraking";

    Map<String, String> joke = new HashMap<>();
    joke.put("category", category);
    joke.put("title", title);
    joke.put("content", content);
    joke.put("author", author);

    ApiServer.start();

    String actualResponseOfCreate = getRequest("jokes/add", joke);
    String idOfNewJoke = actualResponseOfCreate.split(": ")[1];

    String actualResponseOfGet = getRequest("jokes");

    assertAll(() -> assertTrue(actualResponseOfCreate.contains("Joke added with")),
        () -> assertTrue(actualResponseOfGet.contains(idOfNewJoke)));
  }

  @Test
  void jokeNotCreatedBadRequest() {
    var category = "Football";
    var title = "Birthday card";
    var content = "Why was the footballer upset on his birthday? He got a red card !";
    var author = "Maraking";

    Map<String, String> joke = new HashMap<>();
    joke.put("category", category);
    joke.put("title", title);
    joke.put("author", author);

    ApiServer.start();

    String actualResponse = getRequest("jokes/add", joke);
    assertEquals("Bad request", actualResponse);
  }

  @Test
  void randomJokeExists() {

    bootServerWithDependencies(joke1, joke2);

    String actualResponse = getRequest("jokes/random");
    assertAll(() -> assertTrue(actualResponse.contains("id:")),
        () -> assertNotEquals("No jokes", actualResponse));
  }

  @Test
  void noRandomJokeExists() {

    bootServerWithDependencies();

    String actualResponse = getRequest("jokes/random");
    assertEquals("No jokes", actualResponse);
  }

  @Test
  void multipleJokesExistAtAuthorizedHours() {

    int hourOfApiCallToBeConsidered = 12;

    bootServerWithDependencies(hourOfApiCallToBeConsidered, joke1, joke2);

    String actualResponse = getRequest("jokes");

    assertAll(() -> assertEquals(joke1 + "\n" + joke2, actualResponse),
        () -> assertNotEquals("No jokes", actualResponse));
  }

  @Test
  void multipleJokesExistAtUnauthorizedHours() {

    int hourOfApiCallToBeConsidered = 1;

    bootServerWithDependencies(hourOfApiCallToBeConsidered, joke1, joke2);

    String actualResponse = getRequest("jokes");
    assertEquals(AFTER_HOURS_MESSAGE, actualResponse);
  }

  @Test
  void randomJokeAtAuthorizedHours() {

    int hourOfApiCallToBeConsidered = 12;

    bootServerWithDependencies(hourOfApiCallToBeConsidered, joke1, joke2);

    String actualResponse = getRequest("jokes/random");
    assertAll(() -> assertTrue(actualResponse.contains("id:")),
        () -> assertNotEquals("No jokes", actualResponse));
  }

  @Test
  void randomJokeAtUnauthorizedHours() {

    int hourOfApiCallToBeConsidered = 1;

    bootServerWithDependencies(hourOfApiCallToBeConsidered, joke1, joke2);

    String actualResponse = getRequest("jokes/random");
    assertEquals(AFTER_HOURS_MESSAGE, actualResponse);
  }

  @Test
  void createJokeAtAuthorizedHours() {

    var category = "Football";
    var title = "Birthday card";
    var content = "Why was the footballer upset on his birthday? He got a red card !";
    var author = "Maraking";

    Map<String, String> joke = new HashMap<>();
    joke.put("category", category);
    joke.put("title", title);
    joke.put("content", content);
    joke.put("author", author);

    int hourOfApiCallToBeConsidered = 12;
    bootServerWithDependencies(hourOfApiCallToBeConsidered);

    String actualResponseOfCreate = getRequest("jokes/add", joke);
    String idOfNewJoke = actualResponseOfCreate.split(": ")[1];

    String actualResponseOfGet = getRequest("jokes");

    assertAll(() -> assertTrue(actualResponseOfCreate.contains("Joke added with")),
        () -> assertTrue(actualResponseOfGet.contains(idOfNewJoke)));
  }

  @Test
  void jokeNotCreatedAtUnauthorizedHours() {

    var category = "Football";
    var title = "Birthday card";
    var content = "Why was the footballer upset on his birthday? He got a red card !";
    var author = "Maraking";

    Map<String, String> joke = new HashMap<>();
    joke.put("category", category);
    joke.put("title", title);
    joke.put("content", content);
    joke.put("author", author);

    int hourOfApiCallToBeConsidered = 1;
    bootServerWithDependencies(hourOfApiCallToBeConsidered);

    String actualResponse = getRequest("jokes/add", joke);
    assertEquals(AFTER_HOURS_MESSAGE, actualResponse);
  }


  private String getRequest(String path) {
    Client c = ClientBuilder.newClient();
    WebTarget target = c.target("http://localhost:9998/");

    String responseBody = target.path(path).request().get(String.class);
    return responseBody;
  }

  private String getRequest(String path, Map<String, String> queryParams) {
    Client c = ClientBuilder.newClient();
    WebTarget target = c.target("http://localhost:9998/");

    for (String key : queryParams.keySet()) {
      target = target.queryParam(key, queryParams.get(key));
    }

    String responseBody = target.path(path)
        .request().get(String.class);
    return responseBody;
  }

  private static void bootServerWithDependencies(Joke... defaultItems) {

    JokeLibrary defaultJokeLibrary = new JokeLibraryImpl();

    defaultJokeLibrary.initJokeLibrary(defaultItems);

    ApiServer.setAdditionalApplicationBinder(new AbstractBinder() {
      @Override
      protected void configure() {
        bind(defaultJokeLibrary).to(JokeLibrary.class).ranked(2);
      }
    });

    ApiServer.start();
  }

  private static void bootServerWithDependencies(
      int hourOfApiCallToBeConsidered, Joke... defaultItems) {

    TimeProvider timeProviderDuringTest = Mockito.mock(TimeProvider.class);
    Mockito.when(timeProviderDuringTest.getCurrentHour()).thenReturn(hourOfApiCallToBeConsidered);

    JokeLibrary defaultJokeLibrary = new JokeLibraryImpl();

    defaultJokeLibrary.initJokeLibrary(defaultItems);

    if (defaultItems.length == 0) {
      ApiServer.setAdditionalApplicationBinder(new AbstractBinder() {
        @Override
        protected void configure() {
          bind(timeProviderDuringTest).to(TimeProvider.class).ranked(2);
        }
      });
    } else {
      ApiServer.setAdditionalApplicationBinder(new AbstractBinder() {
        @Override
        protected void configure() {
          bind(defaultJokeLibrary).to(JokeLibrary.class).ranked(2);
          bind(timeProviderDuringTest).to(TimeProvider.class).ranked(2);
        }
      });
    }

    ApiServer.start();
  }


}
