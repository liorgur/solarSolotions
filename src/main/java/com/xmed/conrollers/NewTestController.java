package com.xmed.resources;

import com.xmed.models.Requests.CreateNewTestRequest;
import com.xmed.models.Responses.NewTestResponse;

import com.xmed.services.NewTestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;

/**
 * Handler for requests to Lambda function.
 */
@Slf4j
@Api(value = "NewTest")
@RestController
@RequestMapping("api/v1/newTest")
public class NewTestResource {

    @Autowired
    private NewTestService newTestService;

    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json")
    @ApiOperation(value = "Create New Test")
    @ResponseBody
    //@RolesAllowed()
    public ResponseEntity CreateNewTest(@RequestBody CreateNewTestRequest newTestRequest) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
        }


        try {
            NewTestResponse newTestResponse = newTestService.CreateNewTest(newTestRequest);
            return ResponseEntity.ok() //todo created ?
                    .body(newTestResponse);
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("Create New Test Error " + e.getMessage());

        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Create New Test Failed");
    }


}