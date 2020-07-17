package com.thinkmicroservices.ri.spring.sms.outbound.controller;

import com.thinkmicroservices.ri.spring.sms.outbound.model.MmsRequest;
import com.thinkmicroservices.ri.spring.sms.outbound.model.SmsRequest;
import com.thinkmicroservices.ri.spring.sms.outbound.service.OutboundSmsException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.thinkmicroservices.ri.spring.sms.outbound.service.OutboundSmsService;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author cwoodward
 */
@RestController
@Slf4j
public class OutboundSmsController {

     

    @Autowired
    private OutboundSmsService smsService;

    /**
     * 
     * @param request
     * @return 
     */
    @ApiOperation(value = "Submit a SMS request")
    @RequestMapping(value = "/sendSMS", method = {RequestMethod.POST}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = "application/json")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully sent SMS message"),
        @ApiResponse(code = 500, message = "An error ocurred sending the SMS message.")
    })
    @ResponseBody
    public ResponseEntity<?> sendSMSMessage(@RequestBody SmsRequest request) {
        log.debug("Submit a SMS message");

        try {
            smsService.sendSmsMessage(request.getSourceNumber(), request.getDestinationNumber(), request.getMessage());
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } catch (OutboundSmsException ex) {
            log.error("error sending SMS message", ex);
            return new ResponseEntity<>("Unable to send SMS message", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * 
     * @param request
     * @return 
     */
    @ApiOperation(value = "Submit a MMS request")
    @RequestMapping(value = "/sendMMS", method = {RequestMethod.POST})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully sent MMS message"),
        @ApiResponse(code = 500, message = "An error ocurred sending the MMS message.")
    })
    @ResponseBody
    public ResponseEntity<?> sendMMSMessage(@RequestBody MmsRequest request) {
        log.debug("Submit a MMS request");

        try {

            smsService.sendMmsMessage(request.getSourceNumber(), request.getDestinationNumber(), request.getMessage(), request.getAttachmentList());
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } catch (OutboundSmsException ex) {
            log.error("error sending MMS message", ex);
            return new ResponseEntity<>("Unable to send SMS message", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
