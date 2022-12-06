package be.vinci.aj.server;

import be.vinci.aj.domaine.Query;

import java.util.Arrays;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

public class BlacklistService {

    private Set<String> blacklist;

    public BlacklistService() {
        Properties properties = new Properties();
        try {
            properties.load(getClass().getResourceAsStream("/blacklist.properties"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        blacklist = Arrays.stream(properties.getProperty("blacklist").split(",")).map(String::trim).collect(Collectors.toSet());
    }

    public boolean check(Query query) {
        return blacklist.stream().anyMatch(query.getUrl()::contains);
    }
}
