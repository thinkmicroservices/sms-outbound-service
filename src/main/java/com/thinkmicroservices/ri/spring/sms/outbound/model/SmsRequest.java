
package com.thinkmicroservices.ri.spring.sms.outbound.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 *
 * @author cwoodward
 */
@Data
@ApiModel(description = "All details for a SMS request. ")
public class SmsRequest {
    
    @ApiModelProperty( position=0, name="sourceNumber", required=true, value="Phone number of the SMS sender.", example="xxxxxxxxxx")
    @NotBlank
    private String sourceNumber;
    @NotBlank
    @ApiModelProperty( position=1, name="destinationNumber", required=true, value="Phone number of the SMS receiver", example="yyyyyyyyyy")
    private String destinationNumber;
    
    @NotBlank
    @Size(min = 1, max = 160)
    @ApiModelProperty( position=2, name="message", required=true,value="The message to be sent.", example="this is an outbound message")
    private String message;
    
    
    
     
}
