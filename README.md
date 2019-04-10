# play-ldap plugin

This plugin adds [ldap](https://en.wikipedia.org/wiki/Lightweight_Directory_Access_Protocol) support to Play! Framework 1 applications.

# Features

# How to use

####  Add the dependency to your `dependencies.yml` file

```
require:
    - ldap -> ldap 1.3.0

repositories:
    - sismics:
        type:       http
        artifact:   "http://release.sismics.com/repo/play/[module]-[revision].zip"
        contains:
            - ldap -> *

```
####  Set configuration parameters

Add the following parameters to **application.conf**:

```
# LDAP configuration
# ~~~~~~~~~~~~~~~~~~~~
ldap.mock=false
ldap.host=ldap.example.com
ldap.port=389
ldap.adminDn=cn=user,ou=group,dc=example,dc=com
ldap.adminPassword=12345678
ldap.baseDn=ou=people,dc=example,dc=com
```
####  Connect to LDAP

Add the following annotation to inject an LDAP connection into your HTTP request:

```
@With({Ldap.class})
public class MyThings extends Controller {
    public static void getUsersFromLdap() {
        LdapConnection con = LdapContext.get().getLdapConnection();
        con.search(...);
    }

}
```

####  Mock the connection in dev

We recommand to mock LDAP in development mode and test profile.

Use the following configuration parameter:

```
ldap.mock=true
```

Play-ldap comes pre-initialized with some mock users.

You can add some test users in your fixtures:

```
        if (LdapUtil.isMock()) {
            MockLdap.entryList = Lists.newArrayList(
                new DefaultEntry("cn=test1,ou=people,dc=example,dc=com"),
                // ...
            );
```

# License

This software is released under the terms of the Apache License, Version 2.0. See `LICENSE` for more
information or see <https://opensource.org/licenses/Apache-2.0>.
