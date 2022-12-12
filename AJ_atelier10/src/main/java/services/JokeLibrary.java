package services;

import domain.Joke;
import java.util.List;

public interface JokeLibrary {

  void initJokeLibrary(Joke... defaultItems);

  Joke getRandomJoke();

  List<Joke> getJokes();

  void add(Joke joke);

  Joke addJoke(Joke joke);
}
