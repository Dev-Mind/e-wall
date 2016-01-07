package fr.emse.ewall.security;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

import java.util.List;
import java.util.jar.Attributes;

import fr.emse.ewall.repository.DataSourceTestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.NamingException;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.query.LdapQueryBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Dev-Mind <guillaume@dev-mind.fr>
 * @since 07/01/16.
 */
@ContextConfiguration(classes = LdapTestConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class LdapTest {

    @Autowired
    private LdapTemplate ldapTemplate;

    public void setLdapTemplate(LdapTemplate ldapTemplate) {
        this.ldapTemplate = ldapTemplate;
    }

    @Test
    public void getAllPersonNames() {

        LdapQuery query = query()
                //.attributes("cn")
                .where("objectClass").is("person")
                .and("uid").is("freedom");

        ldapTemplate.authenticate(query, " toto423!!!"); //==> javax.naming.AuthenticationException

//        Person dn =
//        ldapTemplate.bind();
        ldapTemplate.search(query, (AttributesMapper<String>) attrs -> (String)attrs.get("cn").get());

    }
}
//    serveur ldap : 193.49.174.194
//        port 386
//        basedn :  ou=people,dc=emse,dc=fr
//        user freedom
//        toto423!!!