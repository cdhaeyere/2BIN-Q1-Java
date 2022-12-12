package domain;

public interface DomainFactory {

  CompactDisc createCompactDisc(String title, String artist, int stock, double price,
      String id);

  Joke createJoke(String category, String title, String content, String author);
}
