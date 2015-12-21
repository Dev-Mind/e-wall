package fr.emse.ewall.service.security;

import org.assertj.core.api.Assertions;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.validation.Cas20ProxyTicketValidator;
import org.junit.Test;

/**
 * @author Dev-Mind <guillaume@dev-mind.fr>
 * @since 17/12/15.
 */
public class TestCas {

    @Test
    public void test(){
        AttributePrincipal principal = null;
        String casServerUrl = "https://cas.emse.fr";
        Cas20ProxyTicketValidator sv = new Cas20ProxyTicketValidator(casServerUrl);
        sv.setAcceptAnyProxy(true);
//        try {
//            // there is no need, that the legacy application is accessible
//            // through this URL. But for validation purpose, even a non-web-app
//            // needs a valid looking URL as identifier.
////            String legacyServerServiceUrl = "http://otherserver/legacy/service";
////            Assertion a = sv.validate(ticket, legacyServerServiceUrl);
////            principal = a.getPrincipal();
////            System.out.println("user name:" + principal.getName());
//            //sv.se
//        } catch (TicketValidationException e) {
//            e.printStackTrace(); // bad style, but only for demonstration purpose.
//        }
        Assertions.assertThat(principal!=null);
    }
}
