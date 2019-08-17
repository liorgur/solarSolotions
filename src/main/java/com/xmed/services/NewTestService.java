package com.xmed.services;

import com.xmed.models.Question;
import com.xmed.models.Requests.CreateNewTestRequest;
import com.xmed.models.Responses.NewTestResponse;
import com.xmed.utils.DbHelper;
import com.xmed.dao.NewTestDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Lior Gur
 */
@Service
@Slf4j
public class NewTestService {
    @Autowired
    NewTestDao dao;

    @Autowired
    DbHelper dbHelper;

    public NewTestResponse CreateNewTest(CreateNewTestRequest request) throws SQLException {

        String queryCreateTest = dao.createQueryCreateTest(request);
        String queryInsertNewTest = dao.insertNewTestQuery(request);

        ResultSet questionsResultSet = dbHelper.executeQueryToResultSet(queryCreateTest);
        List<Question> questionList = GetQuestionListFromResultSet(questionsResultSet);

        int testId = (int) dbHelper.executeInsertQuery(queryInsertNewTest);

        InsertToTestQuestionRelationTable(questionList, testId);
        return new NewTestResponse(questionList);
    }

    private void InsertToTestQuestionRelationTable(List<Question> questionList, int testId) throws SQLException {
        for (Question question : questionList) {

            String relationQuery = dao.CreateInsertIntoTestQuestionRelationQuery(question.getQuestion_id(), testId);
            dbHelper.executeQuery(relationQuery);
        }
    }

    private List<Question> GetQuestionListFromResultSet(ResultSet questionsResultSet) {

        List<Question> questionList = new ArrayList();
        try {
            while (questionsResultSet.next()) {

                int question_id = questionsResultSet.getInt("question_id");
                String question = questionsResultSet.getString("question");
                String answer = questionsResultSet.getString("answer");
                String distractor_1 = questionsResultSet.getString("distractor_1");
                String distractor_2 = questionsResultSet.getString("distractor_2");
                String distractor_3 = questionsResultSet.getString("distractor_3");
                String solution = questionsResultSet.getString("solution");
                String reference = questionsResultSet.getString("reference");
                int year = questionsResultSet.getInt("year");
                int speciality_id = questionsResultSet.getInt("speciality_id");
                int subject_id = questionsResultSet.getInt("subject_id");

                Question question1 = new Question(question_id, question, answer, distractor_1, distractor_2,
                        distractor_3, solution, reference, year, speciality_id, subject_id);

                questionList.add(question1);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        return questionList;
    }

}
