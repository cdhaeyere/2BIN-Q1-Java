package services;

import domain.Joke;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class JokeLibraryImpl implements JokeLibrary {

  private List<Joke> jokes = new ArrayList<>();

  @Override
  public void initJokeLibrary(Joke... defaultItems) {
    this.jokes = new ArrayList<>(Arrays.asList(defaultItems));
  }

  @Override
  public Joke getRandomJoke() {
    if (jokes.isEmpty()) {
      return null;
    }
    Random random = new Random();
    var randomIndex = random.nextInt(jokes.size());
    return jokes.get(randomIndex);
  }

  @Override
  public List<Joke> getJokes() {
    return jokes;
  }

  @Override
  public void add(Joke joke) {
    jokes.add(joke);
  }

  @Override
  public Joke addJoke(Joke joke) {
    if (joke.getId() == null || joke.getId().isBlank()) {
      return null;
    }
    if (joke.getTitle() == null || joke.getTitle().isBlank()) {
      return null;
    }
    if (joke.getContent() == null || joke.getContent().isBlank()) {
      return null;
    }
    if (joke.getAuthor() == null || joke.getAuthor().isBlank()) {
      return null;
    }
    jokes.add(joke);
    return joke;
  }


}
