package helpers.api.ldap;

import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.directory.ldap.client.api.DefaultLdapConnectionFactory;
import org.apache.directory.ldap.client.api.LdapConnectionConfig;
import org.apache.directory.ldap.client.api.LdapConnectionPool;
import org.apache.directory.ldap.client.api.ValidatingPoolableLdapConnectionFactory;
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

        DefaultLdapConnectionFactory factory = new DefaultLdapConnectionFactory(config);
        GenericObjectPool.Config poolConfig = new GenericObjectPool.Config();
        poolConfig.whenExhaustedAction = GenericObjectPool.WHEN_EXHAUSTED_GROW;
        poolConfig.maxWait = 500;
        return new LdapConnectionPool(new ValidatingPoolableLdapConnectionFactory(factory), poolConfig);
    }

    public static boolean isMock() {
        return Boolean.parseBoolean(Play.configuration.getProperty("ldap.mock", "false"));
    }

    private static String getLdapHost() {
        return Play.configuration.getProperty("ldap.host");
    }

    private static Integer getLdapPort() {
        return Integer.valueOf(Play.configuration.getProperty("ldap.port"));
    }

    private static String getLdapAdminDn() {
        return Play.configuration.getProperty("ldap.adminDn");
    }

    private static String getLdapAdminPassword() {
        return Play.configuration.getProperty("ldap.adminPassword");
    }

    public static String getLdapBaseDn() {
        return Play.configuration.getProperty("ldap.baseDn");
    }
}
