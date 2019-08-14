package com.xmed.resources;

import com.xmed.Objects.Requests.QuestionActionsRequest;
import com.xmed.Utils.DbHelper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static com.xmed.Objects.Tables.ANSWERS_TABLE;

/**
 * Handler for requests to Lambda function.
 */
@RequestMapping("/Question")
@Component
//@Scope("request")
public class QuestionActionsService {

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PutMapping("")
    public ResponseEntity shareQuestion(QuestionActionsRequest request) {

        return ResponseEntity.ok()
                .body("");

    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PutMapping("comment")
    public Response handleRequest(QuestionActionsRequest request) {

        String commentQuestionQuery = commentQuestionQuery(request);
        try {
            DbHelper.executeQuery(commentQuestionQuery);
            return Response
                    .status(Response.Status.OK)
                    .entity("") //todo
                    .build();
        }
       catch (Exception ex){
           return Response
                   .status(Response.Status.INTERNAL_SERVER_ERROR)
                   .entity("") //todo
                   .build();
       }

    }

    //public Object handleRequest(final Object input) {
    //    Map<String, String> headers = new HashMap<>();
    //    headers.put("Content-Type", "application/json");
    //    headers.put("X-Custom-Header", "application/json");
    //
    //    QuestionActionsRequest request = new Gson().fromJson(input.toString(), QuestionActionsRequest.class);
    //
    //    if (request.getIsMark()) {
    //        DbHelper.executeQuery(markQuestionQuery(request));
    //        return new GatewayResponse("Mark question done", headers, 200);
    //    }
    //    if (request.getIsComment()) {
    //        DbHelper.executeQuery(commentQuestionQuery(request));
    //        return new GatewayResponse("comment question done", headers, 200);
    //    }
    //    if (request.getIsComment()) { //todo
    //        DbHelper.executeQuery(commentQuestionQuery(request));
    //        return new GatewayResponse("comment question done", headers, 200);
    //    }
    //
    //    return Response
    //            .status(Response.Status.OK)
    //            .entity("") //todo
    //            .build();
    //
    //}

    private String markQuestionQuery(QuestionActionsRequest request) {
        return " UPDATE " + ANSWERS_TABLE + " " +
                " SET is_marked = " + request.getIsMark() + " " +
                " WHERE user_id = " + request.getUserId() + " " +
                " AND question_id = " + request.getQuestionId();
    }

    private String commentQuestionQuery(QuestionActionsRequest request) {
        return " UPDATE " + ANSWERS_TABLE + " " +
                " SET comment = " + request.getComment() + " " +
                " WHERE user_id = " + request.getUserId() + " " +
                " AND question_id = " + request.getQuestionId();
    }

}
