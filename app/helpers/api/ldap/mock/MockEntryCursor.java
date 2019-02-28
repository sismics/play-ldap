package helpers.api.ldap.mock;

import org.apache.directory.api.ldap.model.cursor.EntryCursor;
import org.apache.directory.api.ldap.model.entry.Entry;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author jtremeaux
 */
public class MockEntryCursor {
    /**
     * Create a mock of EntryCursor.
     *
     * @return The mock
     */
    public static EntryCursor create() {
        EntryCursor entryCursor = mock(EntryCursor.class);

        try {
            List<Entry> entryList = MockLdap.getEntryList();
            when(entryCursor.iterator()).thenAnswer(i -> entryList.iterator());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return entryCursor;
    }
}
