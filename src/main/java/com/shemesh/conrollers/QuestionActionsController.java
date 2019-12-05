package com.shemesh.conrollers;

import com.shemesh.models.Requests.QuestionActionsRequest;
import com.shemesh.services.QuestionActionService;
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
public class QuestionActionsController {

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
    public Response commentAnswer(QuestionActionsRequest request) {

        try {
            request.setUserId(1); //todo add userId
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

    @PostMapping(path = "/hide", produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
    @ApiOperation(value = "hideQuestion")
    @ResponseBody
    public Response hideQuestion(QuestionActionsRequest request) {

        try {
            request.setUserId(1); //todo add userId
            questionActionService.hideQuestion(request);
            return Response
                    .status(Response.Status.OK)
                    .entity("Hide Question Done")
                    .build();
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("Hide Answer Error " + ex.getMessage());
        }

        return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Hide Question Failed")
                .build();
    }


    @PostMapping(path = "/mark", produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
    @ApiOperation(value = "markQuestion")
    @ResponseBody
    public Response markQuestion(QuestionActionsRequest request) {

        try {
            request.setUserId(1); //todo add userId
            questionActionService.markQuestion(request);
            return Response
                    .status(Response.Status.OK)
                    .entity("Mark Question Done")
                    .build();
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("Mark Answer Error " + ex.getMessage());
        }

        return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Mark Question Failed")
                .build();
    }

    @PostMapping(path = "/feedback", produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
    @ApiOperation(value = "feedbackQuestion")
    @ResponseBody
    public Response feedbackQuestion(QuestionActionsRequest request) {

        try {
            request.setUserId(1); //todo add userId
            questionActionService.feedbackQuestion(request);
            return Response
                    .status(Response.Status.OK)
                    .entity("feedback Question Done")
                    .build();
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("feedback Answer Error " + ex.getMessage());
        }

        return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("feedback Question Failed")
                .build();
    }
}

