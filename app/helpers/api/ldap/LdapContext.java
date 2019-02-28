package helpers.api.ldap;

import org.apache.directory.ldap.client.api.LdapConnection;

/**
 * LDAP thread-local context.
 *
 * @author jtremeaux
 */
public class LdapContext {
    private static ThreadLocal<LdapContext> local = new ThreadLocal<>();

    private LdapConnection connection;

    private LdapContext() {
    }

    public static LdapContext get() {
        if (local.get() == null) {
            LdapContext context = new LdapContext();
            local.set(context);
        }
        return local.get();
    }

    public static void reset() {
        if (local.get() != null) {
            local.set(null);
        }
    }

    public LdapConnection getLdapConnection() {
        if (connection == null) {
            throw new RuntimeException("Connection not set in the LDAP context. Did you annotate your controller with @With(Ldap.class)?");
        }
        return connection;
    }

    public void setLdapConnection(LdapConnection connection) {
        this.connection = connection;
    }
}
