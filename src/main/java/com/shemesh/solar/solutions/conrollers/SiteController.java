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

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;

/**
 * Handler for requests to Lambda function.
 */
@Slf4j
@Api(value = "Sites")
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("api/v1/sites")
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

    @RequestMapping(value = "/switch/update", method = RequestMethod.GET , produces = "application/json")
    @ApiOperation(value = "Update switch status")
    @ResponseBody
    //@RolesAllowed()
    public ResponseEntity UpdateSwitchStatus(@RequestParam int site_id, @RequestParam int switch_id, @RequestParam boolean status) {

        log.info("Update Switch Status, Site id " + site_id + "switch " + switch_id + "status" + status);

        try {
            siteService.UpdateSwitchStatus(site_id, switch_id, status);
            return ResponseEntity.ok()
                    .body("Switch updated successfully");
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("Switch updated Error " + e.getMessage());

        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Update Site Failed");
    }

    @RequestMapping(value = "/pwm/update", method = RequestMethod.GET , produces = "application/json")
    @ApiOperation(value = "Update pwm")
    @ResponseBody
    //@RolesAllowed()
    public ResponseEntity UpdatePwm(@RequestParam int site_id, @RequestParam int pwm) {

        log.info("Update pwm " + site_id + "pwm " + pwm );

        try {
            siteService.UpdatePwm(site_id, pwm);
            return ResponseEntity.ok()
                    .body("pwm updated successfully");
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("pwm updated Error " + e.getMessage());

        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Update Site Failed");
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "", method = RequestMethod.GET , produces = "application/json")
    @ApiOperation(value = "Get all Sites")
    @ResponseBody
    //@RolesAllowed()
    public ResponseEntity GetSites() {
        log.info("Get All Sites");
        try {
            SitesResponse newSiteResponse = siteService.GetSites();
            return ResponseEntity.ok()
                    .body(newSiteResponse);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Get All Site Error " + e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Get All Site Failed");
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/ping", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "Ping Server")
    @ResponseBody
    public ResponseEntity PingServer(String ip) throws IOException {
        InetAddress address = InetAddress.getByName(ip);
        boolean reachable = address.isReachable(1000);
        if (reachable)
            return ResponseEntity.ok().build();
        return ResponseEntity.notFound().build();
    }

}