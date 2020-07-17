
package com.thinkmicroservices.ri.spring.sms.outbound.service.twilio;

import com.thinkmicroservices.ri.spring.sms.outbound.i18n.I18NResourceBundle;
import com.thinkmicroservices.ri.spring.sms.outbound.service.OutboundSmsException;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.thinkmicroservices.ri.spring.sms.outbound.service.OutboundSmsService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author cwoodward
 */
@Service
@Slf4j
public class TwilioOutboundSmsServiceImpl implements OutboundSmsService {

 

    @Autowired
    TwilioConfigurationProperties twilioConfig;

    @Autowired
    private MeterRegistry meterRegistry;
    
    private Counter outboundSMSPlainCounter;
    private Counter outboundSMSAttachmentCounter;
    /**
     *
     */
    private void initTwilio() {
        log.debug("account sid= {}, auth id=  {}", twilioConfig.getTwilio().getAccountSID(), twilioConfig.getTwilio().authID);
        Twilio.init(twilioConfig.getTwilio().getAccountSID(), twilioConfig.getTwilio().authID);
    }

    /**
     *
     * @param sourceNumber
     * @param destinationNumber
     * @param message
     * @throws OutboundSmsException
     */
    @Override
    public void sendSmsMessage(String sourceNumber, String destinationNumber, String message) throws OutboundSmsException {
        log.debug("sending message '{}' from {} to {}", message, sourceNumber, destinationNumber);
        initTwilio();
        try {
            Message m = Message.creator(
                    new PhoneNumber(destinationNumber),
                    new PhoneNumber(sourceNumber),
                    message).create();

            log.debug("message: {}", m);
            this.outboundSMSPlainCounter.increment();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new OutboundSmsException(I18NResourceBundle.translateForLocale("error.outbound.sms.send.sms.error"),ex);

        }
    }

    /**
     *
     * @param sourceNumber
     * @param destinationNumber
     * @param message
     * @param imageRefList
     * @throws OutboundSmsException
     */
    @Override
    public void sendMmsMessage(String sourceNumber, String destinationNumber, String message, List<String> imageRefList) throws OutboundSmsException {
        try {

            List<URI> uriList = imageRefList.stream()
                    .map(s -> URI.create(String.valueOf(s)))
                    .collect(Collectors.toList());

            log.debug("sending message '{}' from {} to {}", message, sourceNumber, destinationNumber);
            initTwilio();

            Message m = Message.creator(
                    new PhoneNumber(destinationNumber),
                    new PhoneNumber(sourceNumber),
                    message)
                    .setMediaUrl(uriList)
                    .create();
            log.debug("message: {}", m);
            this.outboundSMSAttachmentCounter.increment();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new OutboundSmsException(I18NResourceBundle.translateForLocale("error.outbound.sms.send.sms.error"),ex);
        }
    }
    
    
    @PostConstruct
    private void initializeMetrics() {
        outboundSMSPlainCounter = Counter.builder("outbound.sms.plain")
                .tag("type", "created")
                .description("The number of outbound plain SMS messages sent.")
                .register(meterRegistry);
        
        outboundSMSAttachmentCounter = Counter.builder("outbound.sms.attachment")
                .tag("type", "created")
                .description("The number of outbound plain SMS messages sent.")
                .register(meterRegistry);
        
         
         
          

    }

}
