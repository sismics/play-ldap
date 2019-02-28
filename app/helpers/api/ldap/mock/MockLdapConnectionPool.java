package helpers.api.ldap.mock;

import org.apache.directory.ldap.client.api.LdapConnectionPool;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author jtremeaux
 */
public class MockLdapConnectionPool {
    /**
     * Create a mock of LdapConnectionPool.
     *
     * @return The mock
     */
    public static LdapConnectionPool create() {
        LdapConnectionPool ldapConnectionPool = mock(LdapConnectionPool.class);

        try {
            when(ldapConnectionPool.getConnection()).thenAnswer(i -> MockLdapConnection.create());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return ldapConnectionPool;
    }
}
