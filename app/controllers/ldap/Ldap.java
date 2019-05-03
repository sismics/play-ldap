package controllers.ldap;

import helpers.api.ldap.LdapApi;
import helpers.api.ldap.LdapContext;
import org.apache.directory.api.ldap.model.exception.LdapException;
import org.apache.directory.ldap.client.api.LdapConnection;
import play.Logger;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.Finally;
import play.mvc.Http;

/**
 * This interceptor initializes a LDAP ldapConnection and sets it into the LDAPContext.
 *
 *  @author jtremeaux
 */
public class Ldap extends Controller {

    @Before()
    public static void checkLdapConnection() {
        Http.Request request = Http.Request.current.get();
        LdapConnection ldapConnection = null;
        if (request != null) {
            ldapConnection = (LdapConnection) request.args.get("ldapConnection");
        }
        if (ldapConnection == null) {
            try {
                ldapConnection = LdapApi.get().getPool().getConnection();
                if (request != null) {
                    request.args.put("ldapConnection", ldapConnection);
                }
            } catch (LdapException e) {
                Logger.error(e, "Error getting Ldap connection");
                error(e.getMessage());
            }
        }
        LdapContext.get().setLdapConnection(ldapConnection);
    }

    @Finally
    public static void withLdapFinally() {
        LdapContext ldapContext = LdapContext.get();
        if (ldapContext != null) {
            LdapConnection connection = ldapContext.getLdapConnection();
            if (connection != null) {
                try {
                    LdapApi.get().getPool().releaseConnection(connection);
                } catch (Exception e) {
                    Logger.error(e, "Error releasing LDAP connection");
                }
            }
        }
        LdapContext.reset();
    }

}
