package helpers.api.ldap.mock;

import com.google.common.collect.Lists;
import org.apache.directory.api.ldap.model.entry.DefaultEntry;
import org.apache.directory.api.ldap.model.entry.Entry;

import java.util.List;

/**
 * @author jtremeaux
 */
public class MockLdap {
    public static List<Entry> entryList = Lists.newArrayList(
            getEntry("user1", "user1@test.com", "Firstname 1", "Lastname 1"),
            getEntry("user2", "user2@test.com", "Firstname 2", "Lastname 2"),
            getEntry("user3", "user3@test.com", "Firstname 3", "Lastname 3")
    );

    public static List<Entry> getEntryList() {
        return entryList;
    }

    public static DefaultEntry getEntry(String uid, String mail, String givenName, String sn) {
        try {
            DefaultEntry entry = new DefaultEntry("cn=" + uid + ",ou=people,dc=example,dc=com");
            entry.add("uid", uid);
            entry.add("mail", mail);
            entry.add("givenName", givenName);
            entry.add("sn", sn);
            entry.add("displayName", givenName + " " + sn);
            return entry;
        } catch (Exception e) {
            throw new RuntimeException("Error creating LDAP entry", e);
        }
    }
}
