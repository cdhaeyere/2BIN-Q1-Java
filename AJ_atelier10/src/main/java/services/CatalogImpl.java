package services;

import domain.CompactDisc;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CatalogImpl implements Catalog {

  private List<CompactDisc> cds = new ArrayList<>();

  @Override
  public List<CompactDisc> findCds(String title, String artist) {
    if (!title.isBlank()) {
      return cds.stream().filter(cd -> cd.getTitle().equalsIgnoreCase(title)).toList();
    }
    if (!artist.isBlank()) {
      return cds.stream().filter(cd -> cd.getArtist().equalsIgnoreCase(artist)).toList();
    }
    return null;
  }

  @Override
  public void initCatalog(CompactDisc... defaultItems) {
    if (defaultItems.length == 0) {
      cds = new ArrayList<>();
    } else {
      cds = Arrays.asList(defaultItems);
    }
  }
}
