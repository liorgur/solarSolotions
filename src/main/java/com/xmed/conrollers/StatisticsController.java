package com.xmed.resources;

import com.xmed.models.Requests.FinishedTestsRequest;
import com.xmed.models.Requests.StartedTestsRequest;
import com.xmed.models.Requests.TestsSummeryRequest;
import com.xmed.models.Requests.UserStatisticsRequest;
import com.xmed.models.Responses.FinishedTestsResponse;
import com.xmed.models.Responses.StartedTestsResponse;
import com.xmed.models.Responses.TestsSummeryResponse;
import com.xmed.models.Responses.UserStatisticsResponse;
import com.xmed.services.StatisticsService;
import com.xmed.services.TestsSummeryService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.MediaType;
import java.sql.SQLException;

/**
 * Handler for requests to Lambda function.
 */
@Slf4j
@Api(value = "statistics")
@RestController
@RequestMapping("api/v1/statistics")
public class StatisticsResource {

    @Autowired
    private TestsSummeryService summeryService;

    @Autowired
    private StatisticsService statisticsService;

    @PostMapping(path = "/testsSummery", produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
    @ResponseBody
    public ResponseEntity GetTestSummery(TestsSummeryRequest request) {

        try {
            TestsSummeryResponse testsSummeryResponse = summeryService.GetTestSummery(request);

            return ResponseEntity.ok()
                    .body(testsSummeryResponse);

        } catch (
                SQLException e) {
            e.printStackTrace();
            log.error("Get Started Error " + e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Get Started Failed");
    }

    @PostMapping(path = "/", produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
    @ResponseBody
    public ResponseEntity getStatistics(UserStatisticsRequest request) {

        try {
            UserStatisticsResponse response = statisticsService.getUserStatistics(request);

            return ResponseEntity.ok()
                    .body(response);

        } catch (SQLException e) {
            e.printStackTrace();
            log.error("Get Started Error " + e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Get Started Failed");
    }


    @PostMapping(path = "/finished", produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
    @ResponseBody
    @Deprecated
    public ResponseEntity GetFinishedTests(FinishedTestsRequest request) {

        try {
            FinishedTestsResponse finishedTestsResponse = summeryService.GetFinishedTestSummery(request);

            return ResponseEntity.ok()
                    .body(finishedTestsResponse);

        } catch (
                SQLException e) {
            e.printStackTrace();
            log.error("Get Finished Tests Error " + e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Get Finished Tests Failed");
    }


    @PostMapping(path = "/started", produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
    @ResponseBody
    @Deprecated
    public ResponseEntity GetStartedTests(StartedTestsRequest request) {

        try {
            StartedTestsResponse startedTestsResponse = summeryService.GetStartedTestSummery(request);

            return ResponseEntity.ok()
                    .body(startedTestsResponse);

        } catch (
                SQLException e) {
            e.printStackTrace();
            log.error("Get Started Error " + e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Get Started Failed");
    }
}



