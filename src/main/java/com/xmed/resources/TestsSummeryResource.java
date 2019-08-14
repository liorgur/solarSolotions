package com.xmed.resources;

import com.xmed.Objects.Requests.GetFinishedTestsRequest;
import com.xmed.Objects.Responses.FinishedTestResponse;
import com.xmed.services.TestSummeryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.MediaType;

/**
 * Handler for requests to Lambda function.
 */
@RestController
@RequestMapping("api/v1/testSummery")
public class TestsSummeryResource {

    @Autowired
    TestSummeryService summeryService;

    @PostMapping(path = "/finished", produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
    @ResponseBody
    public ResponseEntity<FinishedTestResponse> GetFinishedTestsTable(GetFinishedTestsRequest request) {

        FinishedTestResponse finishedTestResponse = summeryService.GetFinishedTestSummery(request);

        return ResponseEntity.ok()
                .body(finishedTestResponse);
    }



}
