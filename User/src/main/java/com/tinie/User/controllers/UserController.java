package com.tinie.User.controllers;

import com.tinie.User.repositories.UserDetailsRepository;
import com.tinie.User.requests.GetUserRequest;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("api/v1")
public class UserController {

    @Autowired
    UserDetailsRepository userDetailsRepository;

    /**
     * Get a user's details from the database given their phone number
     * @param httpServletRequest An object of type {@link HttpServletRequest} containing all the information about the request.
     * @param requestBody {@link GetUserRequest} containing phone number
     * @return A {@link Response} whose payload is an {@link GetUserRequest}.
     * */
    @GetMapping("get-user-detail")
    @ApiOperation(value = "Read User details from database")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "WRITE SUCCESSFUL"),
            @ApiResponse(code = 400, message = "INCORRECT REQUEST"),
            @ApiResponse(code = 404, message = "USER NOT FOUND")
    })
    public ResponseEntity<?> getUserDetails(HttpServletRequest httpServletRequest,
                                            BindingResult bindingResult,
                                            @RequestBody GetUserRequest requestBody) {

        if(bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body(
                    Map.of("phonenumber", requestBody.getPhonenumber(),
                            "action", "",
                            "readstatus", "NOK")
            );
        }

        var dbResponse = userDetailsRepository.findByPhoneNumber(requestBody.getPhonenumber());

        if (dbResponse.isEmpty())
            return new ResponseEntity<>(Map.of(
                    "phonenumber", requestBody.getPhonenumber(),
                    "action", "Register",
                    "readstatus", "NOK"
            ), HttpStatus.NOT_FOUND);
        else {
            var user = dbResponse.get();
            return ResponseEntity.ok(
                    Map.of(
                            "phonenumber", user.getPhoneNumber(),
                            "username", user.getUsername(),
                            "action", "Login",
                            "readstatus", "OK"
                    )
            );
        }
    }
}
