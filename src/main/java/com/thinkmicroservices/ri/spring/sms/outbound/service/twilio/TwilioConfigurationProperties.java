
package com.thinkmicroservices.ri.spring.sms.outbound.service.twilio;

 
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 *
 * @author cwoodward
 */
@Component
@ConfigurationProperties
@PropertySource("classpath:twilio.yml")

public class TwilioConfigurationProperties {

    Twilio twilio;

    /**
     * 
     * @return 
     */
    public Twilio getTwilio() {
        return twilio;
    }

    /**
     * 
     * @param twilio 
     */
    public void setTwilio(Twilio twilio) {
        this.twilio = twilio;
    }

    /**
     * 
     */
    public static class Twilio {

        String authID;
        String accountSID;

        /**
         * 
         * @return 
         */
        public String getAuthID() {
            return authID;
        }

        /**
         * 
         * @param authID 
         */
        public void setAuthID(String authID) {
            this.authID = authID;
        }

        /**
         * 
         * @return 
         */
        public String getAccountSID() {
            return accountSID;
        }

        /**
         * 
         * @param accountSID 
         */
        public void setAccountSID(String accountSID) {
            this.accountSID = accountSID;
        }

    }
}
