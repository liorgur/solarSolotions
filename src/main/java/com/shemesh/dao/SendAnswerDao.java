package com.shemesh.dao;

import com.shemesh.models.Requests.SendAnswerRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.shemesh.models.Objects.Tables.*;
import static com.shemesh.models.Objects.Tables.TEST_QUESTION_TABLE;

/**
 * @author Lior Gur
 */
@Slf4j
@Component
public class SendAnswerDao {

    public String updateAnswerTable(SendAnswerRequest request) {
        return " UPDATE " + ANSWERS_TABLE + " " +
                " SET is_correct = " + request.isCorrect() + " " +
                //setComment(request.getComment()) +
                //setMarked(request.getIsMarked()) + //todo remove
                " WHERE user_id = " + request.getUserId() + " " +
                " AND question_id = " + request.getQuestionId();
    }

    public String updateTestTable(SendAnswerRequest request) {
        return "UPDATE " + TEST_TABLE + " " +
                " SET last_solved_question_id = " + request.getQuestionId() + " " +
                setAnswered() +
                setDone(request.getIsDone()) +
                " WHERE test_id = " + request.getTestId() + " ";
    }

    public String updateTestQuestionTable(SendAnswerRequest request) {
        return " UPDATE " + TEST_QUESTION_TABLE + " " +
                " SET is_correct = " + request.isCorrect() + " " +
                " WHERE test_id = " + request.getTestId() + " " +
                " AND question_id = " + request.getQuestionId();
    }


    public String updateDifficulty(SendAnswerRequest request) {
        return
                " update " + QUESTIONS_TABLE + " " +
                        " set difficulty = (((difficulty/100)*@sum)+" +
                        (request.isCorrect() ? "1" : "-1") + ")*100 / (@sum +1) " +
                        " WHERE question_id = " + request.getQuestionId() + " ";

    }

    public String getCorrectAnswerSum(SendAnswerRequest request) {
        return " SET @sum = ( " +
                " SELECT sum(is_correct) " +
                " FROM " + TEST_QUESTION_TABLE + "  a inner join (select min(test_id) as test_id from " +
                TEST_QUESTION_TABLE + " GROUP by user_id) b on a.test_id = b.test_id" +
                " WHERE question_id = " + request.getQuestionId() + " " +
                " group by question_id ) ";
    }

    public String isFirstTimeAnswerThisQuestion(SendAnswerRequest request) {
        return " select count(*) " +
                " from " + TEST_QUESTION_TABLE +
                " WHERE question_id = " + request.getQuestionId() +
                " and user_id = " + request.getUserId();
    }

    public String updateGradeQuery(SendAnswerRequest request) {
        return " UPDATE " + TEST_TABLE + " "  +
                " SET grade = (" +
                " SELECT sum(is_correct)*100/ Count(*) as grade" +
                " FROM " + TEST_QUESTION_TABLE + " "  +
                " WHERE user_id = " + request.getUserId()+  " and test_id = " + request.getTestId() +
                " GROUP by user_id ,test_id)" +
                " WHERE user_id = " + request.getUserId()+  " and test_id = " + request.getTestId() ;
    }

    private String setComment(String comment) {
        return comment != null ? " , comment = " + comment : " ";
    }

    private String setMarked(Boolean marked) {
        return marked != null ? " , is_marked = " + marked : " ";
    }

    private String setDone(Boolean done) {
        return done != null ? " , is_done = " + done : " ";
    }

    private String setAnswered() {
        return " ,answered = answered + 1";
    }

}
