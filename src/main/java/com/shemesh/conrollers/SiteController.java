package com.shemesh.conrollers;

import com.shemesh.models.Requests.CreateNewSiteRequest;
import com.shemesh.models.Requests.CreateNewTestRequest;
import com.shemesh.models.Responses.NewSiteResponse;
import com.shemesh.models.Responses.NewTestResponse;
import com.shemesh.services.SiteService;
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
@Api(value = "NewTest")
@RestController
@RequestMapping("api/v1/Site")
public class SiteController {

    @Autowired
    private SiteService siteService;

    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json")
    @ApiOperation(value = "Create New Site")
    @ResponseBody
    //@RolesAllowed()
    public ResponseEntity CreateNewSite(@RequestBody CreateNewSiteRequest newSiteRequest) {

        log.info("Create New Site");

        try {
            NewSiteResponse newSiteResponse = siteService.CreateNewSite(newSiteRequest);
            return ResponseEntity.ok()
                    .body(newSiteResponse);
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("Create New Site Error " + e.getMessage());

        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Create New Site Failed");
    }


}