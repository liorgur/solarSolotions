package com.xmed.services;

import com.xmed.Objects.Requests.SendAnserRequest;
import com.xmed.Utils.DbHelper;
import com.xmed.dao.SendAnswerDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.Arrays;

/**
 * @author Lior Gur
 */
@Service
@Slf4j
public class SendAnswerService {

    @Autowired
    SendAnswerDao dao;

    public void SendAnswer(SendAnserRequest request) {

        //todo move all queries to batch
        DbHelper.executeQuery(dao.updateAnswerTable(request));
        DbHelper.executeQuery(dao.updateTestTable(request));
        DbHelper.executeQuery(dao.updateTestQuestionTable(request));

        if (isFirstTimeAnswerThisQuestion(dao.isFirstTimeAnswerThisQuestion(request))) {
            DbHelper.executeBatchQueries(
                    Arrays.asList(dao.getCorrectAnswerSum(request), dao.updateDifficulty(request)));
        }

        if (request.isLastQuestion()) {
            String updateGradeQuery = dao.updateGradeQuery(request);
            DbHelper.executeQuery(updateGradeQuery);
        }
    }

    private boolean isFirstTimeAnswerThisQuestion(String isFirstTimeAnswerThisQuestionQuery) {
        ResultSet rs = DbHelper.executeQueryToResultSet(isFirstTimeAnswerThisQuestionQuery);
        boolean isFirstTimeAnswerThisQuestion = false;
        try {
            while (rs.next()) {
                isFirstTimeAnswerThisQuestion = rs.getInt(1) == 1;
            }
        } catch (Exception e) {
        }
        return isFirstTimeAnswerThisQuestion;
    }


}
