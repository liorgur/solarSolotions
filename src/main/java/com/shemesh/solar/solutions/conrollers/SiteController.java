package com.shemesh.solar.solutions.conrollers;

import com.shemesh.solar.solutions.models.Requests.CreateNewSiteRequest;
import com.shemesh.solar.solutions.models.Requests.UpdateSiteRequest;
import com.shemesh.solar.solutions.models.Responses.NewSiteResponse;
import com.shemesh.solar.solutions.models.Responses.SitesResponse;
import com.shemesh.solar.solutions.services.SiteService;
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
@CrossOrigin(origins = "*", maxAge = 3600)   //使用于前端的跨域
@RequestMapping("api/v1/Sites")
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

    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json")
    @ApiOperation(value = "Update New Site")
    @ResponseBody
    //@RolesAllowed()
    public ResponseEntity UpdateSite(@RequestBody UpdateSiteRequest updateSiteRequest) {

        log.info("Update Site");

        try {
            siteService.UpdateSite(updateSiteRequest);
            return ResponseEntity.ok()
                    .body("Update Site successfully");
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("Update Site Error " + e.getMessage());

        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Update Site Failed");
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/", method = RequestMethod.GET , produces = "application/json")
    @ApiOperation(value = "Get all Sites")
    @ResponseBody
    //@RolesAllowed()
    public ResponseEntity GetSites(@RequestParam(value = "id", required = false) Integer id) {
        log.info("Get All Sites");
        try {
            SitesResponse newSiteResponse = siteService.GetSites(id);
            return ResponseEntity.ok()
                    .body(newSiteResponse);
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("Get All  Ste Error " + e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Get All Site Failed");
    }


}