package domain;

public class DomainFactoryImpl implements DomainFactory {

  @Override
  public CompactDisc createCompactDisc(String title, String artist, int stock, double price,
      String id) {
    return new CompactDiscImpl(title, artist, stock, price, id);
  }

  @Override
  public Joke createJoke(String category, String title, String content, String author) {
    return new Joke(category, title, content, author);
  }
}
