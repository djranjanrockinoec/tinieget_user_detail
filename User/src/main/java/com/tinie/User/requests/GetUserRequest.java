package com.tinie.User.requests;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class GetUserRequest {
    @ApiModelProperty(required = true, value = "The User's phone number")
    @Min(1)
    private long phonenumber;
}
