package com.xmed.services;

import com.xmed.Objects.FinishedTestDetails;
import com.xmed.Objects.Requests.GetFinishedTestsRequest;
import com.xmed.Objects.Responses.FinishedTestResponse;
import com.xmed.Utils.DbHelper;
import com.xmed.dao.TestsSummeryDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Lior Gur
 */
@Service
@Slf4j
public class TestSummeryService {

    @Autowired
    TestsSummeryDao dao;

    public FinishedTestResponse GetFinishedTestSummery(GetFinishedTestsRequest request)
    {
        ResultSet resultSet = DbHelper.executeQueryToResultSet(dao.getFinishedTestsQuery(request));
        return ResultSetToFinishedTestResponse(resultSet);

    }

    private FinishedTestResponse ResultSetToFinishedTestResponse(ResultSet resultSet) {

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
            return new FinishedTestResponse(list);

        } catch (Exception ex) {
            //todo
        }
        return null;
    }

}
