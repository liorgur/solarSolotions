package com.xmed.resources;

import com.xmed.Objects.Requests.GetFinishedTestsRequest;
import com.xmed.Objects.Responses.StartedTestResponse;
import com.xmed.Objects.StartedTestDetails;
import com.xmed.Utils.DbHelper;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static com.xmed.Objects.Tables.TEST_TABLE;

/**
 * Handler for requests to Lambda function.
 */
@RestController
public class GetStartedTestsTable  {

    @Transactional() //todo check is working
    @PostMapping("/Statistics/StartedTests")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ResponseBody
    public ResponseEntity<StartedTestResponse> handleRequest(@RequestBody GetFinishedTestsRequest getFinishedTestsRequest) {
        String startedTestsQuery = getStartedTestsQuery(getFinishedTestsRequest);
        ResultSet testResultSet = DbHelper.executeQueryToResultSet(startedTestsQuery);
        StartedTestResponse startedTestResponse =  ResultSetToStartedTestResponse(testResultSet);
        return ResponseEntity.ok()
                .body(startedTestResponse);
    }


    private StartedTestResponse ResultSetToStartedTestResponse(ResultSet resultSet) {

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
            return new StartedTestResponse(list);
        }
        catch (Exception ex)
        {
            //todo
        }
        return null;
    }

    private String getStartedTestsQuery(GetFinishedTestsRequest getFinishedTestsRequest) {
        return " SELECT * " +
                " FROM " + TEST_TABLE +
                " WHERE user_id = " + getFinishedTestsRequest.getUserId() + " " +
                " AND is_done = 0";
    }
}

