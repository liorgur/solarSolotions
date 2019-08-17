package com.xmed.services;

import com.xmed.models.Requests.SendAnswerRequest;
import com.xmed.utils.DbHelper;
import com.xmed.dao.SendAnswerDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * @author Lior Gur
 */
@Service
@Slf4j
@Transactional
public class SendAnswerService {

    @Autowired
    SendAnswerDao dao;

    @Autowired
    DbHelper dbHelper;

    public void SendAnswer(SendAnswerRequest request) throws SQLException {

        dbHelper.executeBatchQueries(Arrays.asList(
                dao.updateAnswerTable(request),
                dao.updateTestTable(request),
                dao.updateTestQuestionTable(request)));

        if (isFirstTimeAnswerThisQuestion(dao.isFirstTimeAnswerThisQuestion(request))) {
            dbHelper.executeBatchQueries(Arrays.asList(
                    dao.getCorrectAnswerSum(request),
                    dao.updateDifficulty(request)));
        }

        if (request.isLastQuestion()) {
            String updateGradeQuery = dao.updateGradeQuery(request);
            dbHelper.executeQuery(updateGradeQuery);
        }
    }

    private boolean isFirstTimeAnswerThisQuestion(String isFirstTimeAnswerThisQuestionQuery) throws SQLException {
        ResultSet rs = dbHelper.executeQueryToResultSet(isFirstTimeAnswerThisQuestionQuery);
        boolean isFirstTimeAnswerThisQuestion = false;
        try {
            while (rs.next()) {
                isFirstTimeAnswerThisQuestion = rs.getInt(1) == 1;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return isFirstTimeAnswerThisQuestion;
    }


}
