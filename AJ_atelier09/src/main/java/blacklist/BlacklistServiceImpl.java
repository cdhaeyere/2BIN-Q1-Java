package blacklist;

import domaine.Query;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.Set;

public class BlacklistServiceImpl implements BlacklistService {

    private Set<String> blacklist;

    public BlacklistServiceImpl() {
        try (FileInputStream fs = new FileInputStream("blacklist.properties")) {
            Properties props = new Properties();
            props.load(fs);
            blacklist = Set.of(props.getProperty("blacklistedDomains").split(";"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean check(Query query) {
        return blacklist.stream().noneMatch(d -> query.getUrl().contains(d));
    }
}
