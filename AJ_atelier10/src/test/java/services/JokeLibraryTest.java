package services;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import domain.DomainFactory;
import domain.DomainFactoryImpl;
import domain.Joke;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JokeLibraryTest {

  private static DomainFactory factory = new DomainFactoryImpl();
  private static JokeLibrary jokeLibrary = new JokeLibraryImpl();
  private Joke joke1 = factory.createJoke("Christmas", "Santa Claus",
      "Santa Clause is lost... Luckily, he's got a smartphone. Bad luck, there is no network... So he builds a network tree and the legend began!",
      "Christmas Lord");
  private Joke joke2 = factory.createJoke("Bun", "Angry mussel",
      "During a quarrel, a mussel says to another : \"Don't be shellfish !\"'", "Fruit Master");

  private Joke joke3 = factory.createJoke("Bun", "Strong mussel",
      "A mussel says to another : \"I have no mussel, can you train me to be fit ?\"'",
      "Fruit Master");

  @Test
  void getJokesReturnOne() {
    jokeLibrary.initJokeLibrary(joke1);

    var jokes = jokeLibrary.getJokes();

    assertAll(() -> assertEquals(1, jokes.size()),
        () -> assertEquals(joke1, jokes.get(0)));
  }

  @Test
  void getJokesReturnMultiple() {
    jokeLibrary.initJokeLibrary(joke1, joke2);

    var jokes = jokeLibrary.getJokes();

    assertAll(() -> assertEquals(2, jokes.size()),
        () -> assertEquals(joke1, jokes.get(0)),
        () -> assertEquals(joke2, jokes.get(1)));
  }

  @Test
  void getJokesWhenNoJokes() {
    jokeLibrary = new JokeLibraryImpl();

    var jokes = jokeLibrary.getJokes();

    assertEquals(0, jokes.size());
  }

  @Test
  void createJoke() {
    jokeLibrary = new JokeLibraryImpl();

    var category = "Football";
    var title = "Birthday card";
    var content = "Why was the footballer upset on his birthday? He got a red card !";
    var author = "Maraking";

    Joke newJoke = factory.createJoke(category, title, content, author);

    Joke addedJoke = jokeLibrary.addJoke(newJoke);

    assertEquals(newJoke, addedJoke);
  }

  @Test
  void jokeNotCreatedBadRequest() {
    jokeLibrary = new JokeLibraryImpl();

    var category = "Football";
    var title = "Birthday card";
    var content = "";
    var author = "Maraking";

    Joke newJoke = factory.createJoke(category, title, content, author);

    assertNull(jokeLibrary.addJoke(newJoke));
  }

  @Test
  void getRandomJokeIsNotEmpty() {
    jokeLibrary.initJokeLibrary(joke1, joke2);

    var jokes = jokeLibrary.getJokes();

    var randomJoke = jokeLibrary.getRandomJoke();

    assertTrue(jokes.contains(randomJoke));

  }

  @Test
  void getRandomJokeIsRandom() {
    jokeLibrary.initJokeLibrary(joke1, joke2);

    var jokes = jokeLibrary.getJokes();

    Set<Joke> jokeCount = new HashSet<>();

    for (int i = 0; i < 10; i++) {
      jokeCount.add(jokeLibrary.getRandomJoke());
    }

    System.out.println("Count of different jokes : " + jokeCount.size());

    assertNotEquals(1, jokeCount.size());

  }


}