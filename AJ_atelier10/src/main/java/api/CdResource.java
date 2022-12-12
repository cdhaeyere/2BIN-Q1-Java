package api;

import domain.CompactDisc;
import jakarta.inject.Inject;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;
import services.Catalog;

@Path("/cds")
public class CdResource {

  @Inject
  private Catalog catalog;

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String findCds(@DefaultValue("") @QueryParam("title") String title,
      @DefaultValue("") @QueryParam("artist") String artist) {
    System.out.println("find my CD ? " + title + " artist");

    List<CompactDisc> cdsFound = catalog.findCds(title, artist);
    if(cdsFound.isEmpty())
      return "No cds";
    String cdsFoundSerialized = cdsFound.stream().map(compactDisc -> compactDisc.toString())
        .collect(
            Collectors.joining("\n"));
    return cdsFoundSerialized;
  }

}
