package blacklist;

import domaine.Query;

public interface BlacklistService {

    boolean check(Query query);
}
