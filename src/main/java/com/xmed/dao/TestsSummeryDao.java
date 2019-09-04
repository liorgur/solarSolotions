package com.xmed.dao;

import com.xmed.models.Requests.FinishedTestsRequest;
import com.xmed.models.Requests.StartedTestsRequest;
import com.xmed.models.Requests.TestsSummeryRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.xmed.models.Objects.Tables.TEST_TABLE;

/**
 * @author Lior Gur
 */
@Component
@Slf4j
public class TestsSummeryDao {

    public String getFinishedTestsQuery(FinishedTestsRequest finishedTestsRequest) {
        return " SELECT * " +
                " FROM " + TEST_TABLE +
                " WHERE user_id = " + finishedTestsRequest.getUserId() + " " +
                " AND is_done = 1";
    }

    public String getStartedTestsQuery(StartedTestsRequest finishedTestsRequest) {
        return " SELECT * " +
                " FROM " + TEST_TABLE +
                " WHERE user_id = " + finishedTestsRequest.getUserId() + " " +
                " AND is_done = 0";
    }

    public String getTestsSummertQuery(TestsSummeryRequest request) {
        return " SELECT * " +
                " FROM " + TEST_TABLE +
                " WHERE user_id = " + request.getUserId() + "" +
                " ORDER BY  date_created DESC" ;
    }
}
