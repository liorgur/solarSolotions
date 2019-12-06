package com.shemesh.conrollers;

import com.shemesh.models.Requests.CreateNewSiteRequest;
import com.shemesh.models.Requests.SendDataRequest;
import com.shemesh.models.Requests.UpdateSiteRequest;
import com.shemesh.models.Responses.NewSiteResponse;
import com.shemesh.models.Responses.SitesResponse;
import com.shemesh.services.DataService;
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
@Api(value = "Data")
@RestController
@RequestMapping("api/v1/data")
public class DataController {

    @Autowired
    private DataService dataService;

    @RequestMapping(value = "/add", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "Add new Data")
    //@RolesAllowed()
    public ResponseEntity CreateNewSite(@RequestParam("ip") String ip,
                                        @RequestParam(value = "tmp", required = false) Integer tmp,
                                        @RequestParam(value = "volt", required = false) Integer volt,
                                        @RequestParam(value = "humidity", required = false) Integer humidity,
                                        @RequestParam(value = "light", required = false) Integer light) {

        SendDataRequest request = new SendDataRequest(ip, tmp, humidity, volt, light);

        log.info("Add new Data");

        try {
            dataService.SendData(request);
            return ResponseEntity.ok()
                    .body("Data inserted");
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("Data insert Error " + e.getMessage());

        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Data insert Failed");
    }


}