package com.shemesh.services;

import com.shemesh.models.Enums.TestType;
import com.shemesh.models.Requests.FinishedTestsRequest;
import com.shemesh.models.Requests.StartedTestsRequest;
import com.shemesh.models.Requests.TestsSummeryRequest;
import com.shemesh.models.Responses.TestsSummeryResponse;
import com.shemesh.models.Objects.TestDetails;
import com.shemesh.utils.DbHelper;
import com.shemesh.dao.TestsSummeryDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Lior Gur
 */
@Service
@Slf4j
public class TestsSummeryService {

    @Autowired
    private TestsSummeryDao dao;

    @Autowired
    private DbHelper dbHelper;

    public TestsSummeryResponse GetFinishedTestSummery(FinishedTestsRequest request) throws SQLException {
        ResultSet resultSet = dbHelper.executeQueryToResultSet(dao.getFinishedTestsQuery(request));
        return ResultSetToTestSummeryResponse(resultSet);
    }

    public TestsSummeryResponse GetStartedTestSummery(StartedTestsRequest request) throws SQLException {
        ResultSet resultSet = dbHelper.executeQueryToResultSet(dao.getStartedTestsQuery(request));
        return ResultSetToTestSummeryResponse(resultSet);

    }
    public TestsSummeryResponse GetTestSummery(TestsSummeryRequest request) throws SQLException {
        ResultSet resultSet = dbHelper.executeQueryToResultSet(dao.getTestsSummeryQuery(request));
        return ResultSetToTestSummeryResponse(resultSet);

    }

    private TestsSummeryResponse ResultSetToTestSummeryResponse(ResultSet resultSet) {

        List<TestDetails> list = new ArrayList<>();

        try {
            while (resultSet.next()) {
                String testName = resultSet.getString("test_name");
                Date dateTime= resultSet.getDate("date_created");
                int numOfQuestions= resultSet.getInt("num_of_questions");
                int answered= resultSet.getInt("answered");
                int progress = (answered / numOfQuestions) * 100 ;
                String difficulties=resultSet.getString("difficulties");
                int grade = resultSet.getInt("grade");
                TestType testType = TestType.valueOf(resultSet.getString("test_type"));
                boolean isDone = (answered == numOfQuestions);
                list.add(new TestDetails(dateTime,testName,numOfQuestions,progress,grade,difficulties,testType, isDone));
            }
            return new TestsSummeryResponse(list);
        }
        catch (Exception ex) {
            log.error(ex.getMessage());
            log.debug(Arrays.toString(ex.getStackTrace())); //todo
        }
        return null;
    }

    //private TestsSummeryResponse ResultSetToFinishedTestResponse(ResultSet resultSet) {
    //
    //    List<FinishedTestDetails> list = new ArrayList<>();
    //    try {
    //        while (resultSet.next()) {
    //
    //            Date dateTime = resultSet.getDate("date_created");
    //            String testName = resultSet.getString("test_name");
    //            int numOfQuestions = resultSet.getInt("num_of_questions");
    //            String difficulties = resultSet.getString("difficulties");
    //            String testType = resultSet.getString("test_type");
    //            int grade = resultSet.getInt("grade");
    //            list.add(new FinishedTestDetails(dateTime, testName, numOfQuestions, grade, difficulties, testType));
    //        }
    //        return new FinishedTestsResponse(list);
    //
    //    } catch (Exception ex) {
    //        log.error(ex.getMessage());
    //        log.debug(Arrays.toString(ex.getStackTrace())); //todo
    //    }
    //    return null;
    //}
    //
    //private StartedTestsResponse ResultSetToStartedTestResponse(ResultSet resultSet) {
    //
    //    List<StartedTestDetails> list = new ArrayList<>();
    //
    //    try {
    //        while (resultSet.next()) {
    //            Date dateTime= resultSet.getDate("date_created");
    //            String testName = resultSet.getString("test_name");
    //            int numOfQuestions= resultSet.getInt("num_of_questions");
    //            int answered= resultSet.getInt("answered");
    //            int progress = (answered / numOfQuestions) * 100 ;
    //            String difficulties=resultSet.getString("difficulties");
    //            String testType = resultSet.getString("test_type");
    //
    //            list.add(new StartedTestDetails(dateTime,testName,numOfQuestions,progress,difficulties,testType));
    //        }
    //        return new StartedTestsResponse(list);
    //    }
    //    catch (Exception ex)
    //    {
    //        log.error(ex.getMessage());
    //        log.debug(Arrays.toString(ex.getStackTrace())); //todo
    //    }
    //    return null;
    //}

}
