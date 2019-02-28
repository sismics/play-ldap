package helpers.api.ldap.mock;

import org.apache.directory.api.ldap.model.cursor.SearchCursor;
import org.apache.directory.api.ldap.model.entry.Entry;
import org.apache.directory.api.ldap.model.exception.LdapInvalidAttributeValueException;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author jtremeaux
 */
public class MockSearchCursor {
    /**
     * Create a mock of SearchCursor.
     *
     * @return The mock
     */
    public static SearchCursor create(String uid) {
        SearchCursor searchCursor = mock(SearchCursor.class);

        try {
            List<Entry> entryList = MockLdap.getEntryList();
            if (uid != null) {
                entryList = entryList.stream().filter(e -> {
                    try {
                        return e.get("uid").getString().equals(uid);
                    } catch (LdapInvalidAttributeValueException e1) {
                        throw new RuntimeException(e1);
                    }
                }).collect(Collectors.toList());
            }
            Iterator<Entry> it = entryList.iterator();
            when(searchCursor.next()).thenAnswer(i -> it.hasNext());
            when(searchCursor.getEntry()).thenAnswer(i -> it.next());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return searchCursor;
    }
}
