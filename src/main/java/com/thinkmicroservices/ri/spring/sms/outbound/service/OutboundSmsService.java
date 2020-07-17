
package com.thinkmicroservices.ri.spring.sms.outbound.service;

import java.util.List;

/**
 *
 * @author cwoodward
 */
public interface OutboundSmsService {
    
    /**
     * 
     * @param sourceNumber
     * @param destinationNumber
     * @param message
     * @throws OutboundSmsException 
     */
    public void sendSmsMessage(String sourceNumber,String destinationNumber, String message) throws OutboundSmsException;
    
    /**
     * 
     * @param sourceNumber
     * @param destinationNumber
     * @param message
     * @param imageRef
     * @throws OutboundSmsException 
     */
    public void sendMmsMessage(String sourceNumber,String destinationNumber, String message,List<String> imageRef) throws OutboundSmsException;
}
