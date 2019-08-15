package com.xmed.services;

import com.xmed.models.FinishedTestDetails;
import com.xmed.models.Requests.FinishedTestsRequest;
import com.xmed.models.Requests.StartedTestsRequest;
import com.xmed.models.Responses.FinishedTestsResponse;
import com.xmed.models.Responses.StartedTestsResponse;
import com.xmed.models.StartedTestDetails;
import com.xmed.utils.DbHelper;
import com.xmed.dao.TestsSummeryDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Lior Gur
 */
@Service
@Slf4j
public class TestsSummeryService {

    @Autowired
    TestsSummeryDao dao;

    @Autowired
    DbHelper dbHelper;

    public FinishedTestsResponse GetFinishedTestSummery(FinishedTestsRequest request) throws SQLException {
        ResultSet resultSet = dbHelper.executeQueryToResultSet(dao.getFinishedTestsQuery(request));
        return ResultSetToFinishedTestResponse(resultSet);
    }

    public StartedTestsResponse GetStartedTestSummery(StartedTestsRequest request) throws SQLException {
        ResultSet resultSet = dbHelper.executeQueryToResultSet(dao.getStartedTestsQuery(request));
        return ResultSetToStartedTestResponse(resultSet);

    }

    private FinishedTestsResponse ResultSetToFinishedTestResponse(ResultSet resultSet) {

        List<FinishedTestDetails> list = new ArrayList<>();
        try {
            while (resultSet.next()) {

                Date dateTime = resultSet.getDate("date_created");
                String testName = resultSet.getString("test_name");
                int numOfQuestions = resultSet.getInt("num_of_questions");
                String difficulties = resultSet.getString("difficulties");
                String testType = resultSet.getString("test_type");
                int grade = resultSet.getInt("grade");
                list.add(new FinishedTestDetails(dateTime, testName, numOfQuestions, grade, difficulties, testType));
            }
            return new FinishedTestsResponse(list);

        } catch (Exception ex) {
            log.error(ex.getMessage());
            log.debug(ex.getStackTrace().toString()); //todo
        }
        return null;
    }

    private StartedTestsResponse ResultSetToStartedTestResponse(ResultSet resultSet) {

        List<StartedTestDetails> list = new ArrayList<>();

        try {
            while (resultSet.next()) {
                Date dateTime= resultSet.getDate("date_created");
                String testName = resultSet.getString("test_name");
                int numOfQuestions= resultSet.getInt("num_of_questions");
                int answered= resultSet.getInt("answered");
                int progress = (answered / numOfQuestions) * 100 ;
                String difficulties=resultSet.getString("difficulties");
                String testType = resultSet.getString("test_type");

                list.add(new StartedTestDetails(dateTime,testName,numOfQuestions,progress,difficulties,testType));
            }
            return new StartedTestsResponse(list);
        }
        catch (Exception ex)
        {
            log.error(ex.getMessage());
            log.debug(ex.getStackTrace().toString()); //todo
        }
        return null;
    }

}
