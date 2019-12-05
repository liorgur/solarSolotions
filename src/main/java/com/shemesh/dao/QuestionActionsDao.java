package com.shemesh.dao;

import com.shemesh.models.Requests.QuestionActionsRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.shemesh.models.Objects.Tables.*;

/**
 * @author Dan Feldman
 */
@Component
@Slf4j

public class QuestionActionsDao {

    public String markQuestionQuery(QuestionActionsRequest request) {
        return " UPDATE " + ANSWERS_TABLE + " " +
                " SET is_marked = " + request.getIsMark() + " " +
                " WHERE user_id = " + request.getUserId() + " " +
                " AND question_id = " + request.getQuestionId();
    }

    public String commentQuestionQuery(QuestionActionsRequest request) {
        return " UPDATE " + ANSWERS_TABLE + " " +
                " SET comment = " + request.getComment() + " " +
                " WHERE user_id = " + request.getUserId() + " " +
                " AND question_id = " + request.getQuestionId();
    }

    public String hideQuestionQuery(QuestionActionsRequest request) {
        return " UPDATE " + ANSWERS_TABLE + " " +
                " SET hide = " + request.getIsMark() + " " +
                " WHERE user_id = " + request.getUserId() + " " +
                " AND question_id = " + request.getQuestionId();
    }

    public String feedbackQuestionQuery(QuestionActionsRequest request) {
        return "INSERT INTO " + FEEDBACK_QUESTION_TABLE + " " +
                " (question_id, user_id, feedback) " +
                " VALUES (" + request.getQuestionId() + "," + request.getUserId() +  "," +  request.getFeedback() + ")";
    }
}
