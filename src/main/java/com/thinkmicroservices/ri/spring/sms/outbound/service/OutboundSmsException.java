package com.thinkmicroservices.ri.spring.sms.outbound.service;

/**
 *
 * @author cwoodward
 */
public class OutboundSmsException extends Throwable {

    /**
     *
     * @param msg
     */
    public OutboundSmsException(String msg) {
        super(msg);
    }

    /**
     *
     * @param t
     */
    public OutboundSmsException(Throwable t) {
        super(t);
    }

    /**
     *
     * @param msg
     * @param t
     */
    public OutboundSmsException(String msg, Throwable t) {
        super(msg, t);
    }
}
