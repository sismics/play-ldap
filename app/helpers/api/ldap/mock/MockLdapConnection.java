package helpers.api.ldap.mock;

import org.apache.directory.api.ldap.model.filter.ExprNode;
import org.apache.directory.api.ldap.model.message.SearchRequest;
import org.apache.directory.api.ldap.model.message.SearchScope;
import org.apache.directory.ldap.client.api.LdapConnection;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author jtremeaux
 */
public class MockLdapConnection {
    /**
     * Create a mock of LdapConnection.
     *
     * @return The mock
     */
    public static LdapConnection create() {
        LdapConnection ldapConnection = mock(LdapConnection.class);

        try {
            when(ldapConnection.search(any(String.class), any(String.class), any(SearchScope.class), any(String[].class))).thenAnswer(i -> MockEntryCursor.create());
            when(ldapConnection.search(any(SearchRequest.class))).thenAnswer(i -> {
                SearchRequest searchRequest = i.getArgument(0);
                ExprNode filter1 = searchRequest.getFilter();
                String filter = filter1.toString();
                Matcher matcher = Pattern.compile("\\(uid=(.+?)\\)").matcher(filter);
                String uid = null;
                if (matcher.find()) {
                    uid = matcher.group(1);
                }
                return MockSearchCursor.create(uid);
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return ldapConnection;
    }
}
