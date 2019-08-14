package com.xmed.resources;

import com.xmed.Objects.Requests.FinishedTestsRequest;
import com.xmed.Objects.Requests.StartedTestsRequest;
import com.xmed.Objects.Responses.FinishedTestsResponse;
import com.xmed.Objects.Responses.StartedTestsResponse;
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
@Api(value = "testSummery")
@RestController
@RequestMapping("api/v1/testSummery")
public class TestsSummeryResource {

    @Autowired
    TestsSummeryService summeryService;

    @PostMapping(path = "/finished", produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
    @ResponseBody
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



