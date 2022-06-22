package com.tinie.User.controllers;

import com.tinie.User.repositories.UserDetailsRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
     * @param phoneNumber Phone number of User
     * @return A {@link Response} whose payload is an {@link Map}.
     * */
    @GetMapping("get-user-detail")
    @ApiOperation(value = "Read User details from database")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "WRITE SUCCESSFUL"),
            @ApiResponse(code = 400, message = "INCORRECT REQUEST"),
            @ApiResponse(code = 404, message = "USER NOT FOUND")
    })
    public ResponseEntity<?> getUserDetails(HttpServletRequest httpServletRequest,
                                            @RequestParam("phonenumber") long phoneNumber) {

        if(phoneNumber < 1){
            return ResponseEntity.badRequest().body(
                    Map.of("phonenumber", phoneNumber,
                            "action", "",
                            "readstatus", "NOK")
            );
        }

        var dbResponse = userDetailsRepository.findByPhoneNumber(phoneNumber);

        if (dbResponse.isEmpty())
            return new ResponseEntity<>(Map.of(
                    "phonenumber", phoneNumber,
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
