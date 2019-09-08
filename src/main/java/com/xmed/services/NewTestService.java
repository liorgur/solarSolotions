package com.xmed.services;

import com.xmed.models.Objects.Question;
import com.xmed.models.Requests.CreateNewTestRequest;
import com.xmed.models.Responses.NewTestResponse;
import com.xmed.utils.DbHelper;
import com.xmed.dao.NewTestDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URL;
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
    private NewTestDao dao;

    @Autowired
    private DbHelper dbHelper;

    public NewTestResponse CreateNewTest(CreateNewTestRequest request) throws SQLException {

        String queryCreateTest = GetCreateNewTestQuery(request);
        String queryInsertNewTest = dao.insertNewTestQuery(request);

        ResultSet questionsResultSet = dbHelper.executeQueryToResultSet(queryCreateTest);
        List<Question> questionList = GetQuestionListFromResultSet(questionsResultSet);

        int testId = (int) dbHelper.executeInsertQuery(queryInsertNewTest);

        InsertToTestQuestionRelationTable(questionList, testId);
        return new NewTestResponse(questionList);
    }

    private String GetCreateNewTestQuery(CreateNewTestRequest request) {
        String queryCreateTest;
        switch (request.getTestType()) {
            case  CUSTOM :
                queryCreateTest = dao.createQueryCreateCustomTest(request);
                break;
            case UNSEEN:
                queryCreateTest = dao.createQueryCreateUnseenQuestionTest(request);
                break;
            case MISTAKES:
                queryCreateTest = dao.createQueryCreateMistakeTest(request);
                break;
            case SURPRISE:
                queryCreateTest = dao.createQueryCreateSurpriseTest(request);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + request.getTestType());
                //todo add logs
        }
        return queryCreateTest;
    }

    private void InsertToTestQuestionRelationTable(List<Question> questionList, int testId) throws SQLException {
        for (Question question : questionList) {
            String relationQuery = dao.CreateInsertIntoTestQuestionRelationQuery(question.getQuestion_id(), testId);
            dbHelper.executeQuery(relationQuery); //todo add in transaction
        }
    }

    private List<Question> GetQuestionListFromResultSet(ResultSet questionsResultSet) {

        List<Question> questionList = new ArrayList<>();
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
                URL questionImage = questionsResultSet.getURL("question_image");
                URL solutionImage = questionsResultSet.getURL("solution_image");

                Question question1 = new Question(question_id, question, answer, distractor_1, distractor_2,
                        distractor_3, solution, reference, year, speciality_id, subject_id, questionImage, solutionImage);

                questionList.add(question1);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        return questionList;
    }

}
