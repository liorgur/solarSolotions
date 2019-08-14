package com.xmed.resources;

import com.xmed.Objects.Requests.SendAnserRequest;
import com.xmed.services.SendAnswerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.core.MediaType;

/**
 * Handler for requests to Lambda function.
 */
@Slf4j
@RestController
@RequestMapping("api/v1/sendAnswer")
@Api(value = "sendAnswer.")
public class SendAnswer  {

    @Autowired
    SendAnswerService sendAnswerService;

    @PostMapping(path = "", produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
    @ApiOperation(value = "sendAnswer")
    @ResponseBody
    public ResponseEntity SendAnswer(SendAnserRequest request) {

        sendAnswerService.SendAnswer(request);
        return ResponseEntity.ok()
                .body("Send Answer Successfully");

    }

}
