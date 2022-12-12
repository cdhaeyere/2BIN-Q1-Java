package domain;

import java.util.UUID;

public class Joke {

  private String id;
  private String category;
  private String title;
  private String content;
  private String author;

  public Joke(String category, String title, String content, String author) {
    this.id = UUID.randomUUID().toString();
    this.category = category;
    this.title = title;
    this.content = content;
    this.author = author;
  }

  public String getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public String getAuthor() {
    return author;
  }

  public String getCategory() {
    return category;
  }

  public String getContent() {
    return content;
  }

  @Override
  public String toString() {
    return "id:" + id +
        ", category:" + category +
        ", title:" + title +
        ", content:" + content +
        ", author:" + author;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Joke joke = (Joke) o;

    return id.equals(joke.id);
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }
}
