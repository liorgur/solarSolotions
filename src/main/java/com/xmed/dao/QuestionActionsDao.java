package com.xmed.dao;

import com.xmed.models.Requests.QuestionActionsRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.xmed.models.Tables.ANSWERS_TABLE;

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
}
