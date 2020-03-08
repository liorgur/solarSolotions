package com.shemesh.solar.solutions.conrollers;

import com.shemesh.solar.solutions.models.Requests.SendDataRequest;
import com.shemesh.solar.solutions.models.Responses.AlertResponse;
import com.shemesh.solar.solutions.models.Responses.DataResponse;
import com.shemesh.solar.solutions.services.AlertsService;
import com.shemesh.solar.solutions.services.DataService;
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
@Api(value = "Alerts")
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("api/v1/alerts")
public class AlertsController {

    @Autowired
    private AlertsService alertsService;

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "Get alerts")
    @ResponseBody
    //@RolesAllowed()
    public ResponseEntity GetAlerts(@RequestParam(value = "site_id", required = false) Integer site_id) {
        log.info("Get Alerts");

        try {
            AlertResponse response = alertsService.GetAlerts(site_id);
            return ResponseEntity.ok()
                    .body(response);
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("Get Alerts  " + e.getMessage());

        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Data insert Failed");
    }


    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/reset", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "Reset alert")
    public ResponseEntity ResetAlerts(@RequestParam("id") int id) {
        log.info("Reset Alerts");

        try {
             alertsService.resetAlert(id);
            return ResponseEntity.ok()
                    .body("Reset Alerts"); //todo
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("Reset Alerts " + e.getMessage());

        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Reset Alerts Failed");
    }

}