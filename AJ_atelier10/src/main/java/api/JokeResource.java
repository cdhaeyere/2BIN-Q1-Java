package api;


import domain.DomainFactory;
import domain.Joke;
import jakarta.inject.Inject;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;
import services.JokeLibrary;

@Path("/jokes")
public class JokeResource {


  @Inject
  private DomainFactory factory;
  @Inject
  private JokeLibrary jokeLibrary;

  @Inject
  AuthorizeApiOperations authorize;


  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String getJokes() {

    if (!authorize.checkIfOperationIsAuthorized()) {
      return AuthorizeApiOperationsImpl.AFTER_HOURS_MESSAGE;
    }

    List<Joke> jokes = jokeLibrary.getJokes();

    if (jokes.isEmpty()) {
      return "No jokes";
    }

    String jokesSerialized = jokes.stream().map(Joke::toString)
        .collect(
            Collectors.joining("\n"));
    return jokesSerialized;
  }

  @GET
  @Path("/random")
  @Produces(MediaType.TEXT_PLAIN)
  public String randomJokes() {
    if (!authorize.checkIfOperationIsAuthorized()) {
      return AuthorizeApiOperationsImpl.AFTER_HOURS_MESSAGE;
    }

    Joke randomJoke = jokeLibrary.getRandomJoke();
    if (randomJoke == null) {
      return "No jokes";
    }

    return randomJoke.toString();
  }

  @GET
  @Path("/add")
  @Produces(MediaType.TEXT_PLAIN)
  public String createJoke(@DefaultValue("") @QueryParam("category") String category,
      @DefaultValue("") @QueryParam("title") String title,
      @DefaultValue("") @QueryParam("content") String content,
      @DefaultValue("") @QueryParam("author") String author) {

    if (!authorize.checkIfOperationIsAuthorized()) {
      return AuthorizeApiOperationsImpl.AFTER_HOURS_MESSAGE;
    }

    Joke newJoke = factory.createJoke(category, title, content, author);

    Joke addedJoke = jokeLibrary.addJoke(newJoke);

    if (addedJoke == null) {
      return "Bad request";
    }

    String jokesSerialized = "Joke added with id : " + addedJoke.getId();

    return jokesSerialized;
  }


}


