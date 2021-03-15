package com.shemesh.solar.solutions.conrollers;

import com.shemesh.solar.solutions.services.SecurityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

/**
 * Handler for requests to Lambda function.
 */
@Slf4j
@Api(value = "Security")
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("api/v1/security")
public class SecurityController {

    @Autowired
    private SecurityService securityService;

    @RequestMapping(value = "/validate_pass", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "validate pass")
    @ResponseBody
    //@RolesAllowed()
    public ResponseEntity ValidatePass(String pass) {
        log.info("Get Pass");

        try {
            boolean isValidated = securityService.ValidatePass(pass);
            if (isValidated)
                return ResponseEntity.ok().body(isValidated);
            else
                return (ResponseEntity) ResponseEntity.status(HttpStatus.FORBIDDEN);
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("Get Pass  " + e.getMessage());

        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Get Pass Failed");
    }

}