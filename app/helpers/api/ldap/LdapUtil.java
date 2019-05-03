package helpers.api.ldap;

import controllers.ldap.Ldap;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.directory.ldap.client.api.DefaultLdapConnectionFactory;
import org.apache.directory.ldap.client.api.LdapConnectionConfig;
import org.apache.directory.ldap.client.api.LdapConnectionPool;
import org.apache.directory.ldap.client.api.ValidatingPoolableLdapConnectionFactory;
import play.Logger;
import play.Play;

/**
 * @author jtremeaux
 */
public class LdapUtil {
    /**
     * Returns the LDAP connection pool.
     *
     * @return The connection pool
     */
    public static LdapConnectionPool createConnectionPool() {
        LdapConnectionConfig config = new LdapConnectionConfig();
        config.setLdapHost(getLdapHost());
        config.setLdapPort(getLdapPort());
        config.setName(getLdapAdminDn());
        config.setCredentials(getLdapAdminPassword());
        config.setTimeout(getLdapTimeout());

        DefaultLdapConnectionFactory factory = new DefaultLdapConnectionFactory(config);
        GenericObjectPool.Config poolConfig = new GenericObjectPool.Config();
        poolConfig.whenExhaustedAction = GenericObjectPool.WHEN_EXHAUSTED_GROW;
        poolConfig.maxWait = 500;
        LdapConnectionPool pool = new LdapConnectionPool(new ValidatingPoolableLdapConnectionFactory(factory), poolConfig);
        Logger.info("Connected to LDAP at " + config.getLdapHost() + ":" + config.getLdapPort());
        return pool;
    }

    public static boolean isMock() {
        return Boolean.parseBoolean(Play.configuration.getProperty("ldap.mock", "false"));
    }

    private static String getLdapHost() {
        return Play.configuration.getProperty("ldap.host");
    }

    private static Integer getLdapPort() {
        return Integer.valueOf(Play.configuration.getProperty("ldap.port", "389"));
    }

    private static String getLdapAdminDn() {
        return Play.configuration.getProperty("ldap.adminDn");
    }

    private static String getLdapAdminPassword() {
        return Play.configuration.getProperty("ldap.adminPassword");
    }

    private static Long getLdapTimeout() {
        return Long.valueOf(Play.configuration.getProperty("ldap.timeout", "10000"));
    }

    public static String getLdapBaseDn() {
        return Play.configuration.getProperty("ldap.baseDn");
    }

    public static void withLdapConnection(Runnable r) {
        try {
            Ldap.checkLdapConnection();
            r.run();
        } catch (Exception e) {
            Logger.error(e, "Exception in LDAP runner");
        } finally {
            try {
                Ldap.withLdapFinally();
            } catch (Exception e) {
                // NOP
            }
        }
    }
}
