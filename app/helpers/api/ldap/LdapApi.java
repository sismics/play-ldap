package helpers.api.ldap;

import helpers.api.ldap.mock.MockLdapConnectionPool;
import org.apache.directory.ldap.client.api.LdapConnectionPool;

/**
 * @author jtremeaux
 */
public class LdapApi {
    private static LdapApi ldapApi;

    private LdapConnectionPool pool;

    public static LdapApi get() {
        if (ldapApi == null) {
            ldapApi = new LdapApi();
        }
        return ldapApi;
    }

    public LdapApi() {
        pool = getLdapConnectionPool();
    }

    private LdapConnectionPool getLdapConnectionPool() {
        if (LdapUtil.isMock()) {
            return MockLdapConnectionPool.create();
        } else {
            return LdapUtil.createConnectionPool();
        }
    }

    public LdapConnectionPool getPool() {
        return pool;
    }
}
