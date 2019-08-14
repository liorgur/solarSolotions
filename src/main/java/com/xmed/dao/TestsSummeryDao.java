package com.xmed.dao;

import com.xmed.Objects.Requests.GetFinishedTestsRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.xmed.Objects.Tables.TEST_TABLE;

/**
 * @author Dan Feldman
 */
@Component
@Slf4j
public class TestsSummeryDao {

    public String getFinishedTestsQuery(GetFinishedTestsRequest getFinishedTestsRequest) {
        return " SELECT * " +
                " FROM " + TEST_TABLE +
                " WHERE user_id = " + getFinishedTestsRequest.getUserId() + " " +
                " AND is_done = 1";
    }
}
