package com.xmed.resources;

import com.xmed.models.Requests.QuestionActionsRequest;
import com.xmed.services.QuestionActionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Handler for requests to Lambda function.
 */
@Slf4j
@Api(value = "QuestionActions")
@RestController
@RequestMapping("api/v1/questionActions")
public class QuestionActionsResource {

    @Autowired
    QuestionActionService questionActionService;

    @PostMapping(path = "/share", produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
    @ApiOperation(value = "shareAnswer")
    @ResponseBody
    public ResponseEntity shareQuestion(QuestionActionsRequest request) {

        //TODO
        return ResponseEntity.ok()
                .body("share done");

    }

    @PostMapping(path = "/comment", produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
    @ApiOperation(value = "commentAnswer")
    @ResponseBody
    public Response handleRequest(QuestionActionsRequest request) {

        try {
            questionActionService.commentQuestion(request);
            return Response
                    .status(Response.Status.OK)
                    .entity("Comment Question Done")
                    .build();
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("Send Answer Error " + ex.getMessage());
        }

        return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Comment Question Failed")
                .build();
    }

}

