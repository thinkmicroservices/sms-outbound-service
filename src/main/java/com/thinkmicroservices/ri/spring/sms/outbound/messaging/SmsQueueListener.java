package com.thinkmicroservices.ri.spring.sms.outbound.messaging;

import com.thinkmicroservices.ri.spring.sms.outbound.model.MmsRequest;
import com.thinkmicroservices.ri.spring.sms.outbound.model.SmsRequest;
import com.thinkmicroservices.ri.spring.sms.outbound.service.OutboundSmsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import com.thinkmicroservices.ri.spring.sms.outbound.service.OutboundSmsService;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author cwoodward
 */
@EnableBinding(Sink.class)
@Slf4j
public class SmsQueueListener {

    @Autowired
    private OutboundSmsService smsOutboundService;

    @StreamListener(target = Sink.INPUT, condition = "headers['type']=='SMS'")
    public void processSmsMessage(SmsRequest smsMessage) {
        log.debug("incoming SMS message from Queue=>: " + smsMessage);

        try {
            smsOutboundService.sendSmsMessage(smsMessage.getSourceNumber(), smsMessage.getDestinationNumber(), smsMessage.getMessage());

        } catch (OutboundSmsException ex) {
            log.error("error sending sms message", ex);
        }
    }

    /**
     *
     * @param mmsMessage
     */
    @StreamListener(target = Sink.INPUT, condition = "headers['type']=='MMS'")
    public void processMmsMessage(MmsRequest mmsMessage) {
        log.debug("incoming MMS message from Queue=>: " + mmsMessage);

        try {
            smsOutboundService.sendMmsMessage(mmsMessage.getSourceNumber(), mmsMessage.getDestinationNumber(), mmsMessage.getMessage(), mmsMessage.getAttachmentList());

        } catch (OutboundSmsException ex) {
            log.error("error sending sms message", ex);
        }
    }
}
