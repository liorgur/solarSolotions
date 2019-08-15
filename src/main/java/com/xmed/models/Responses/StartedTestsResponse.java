package com.xmed.models.Responses;

import com.xmed.models.StartedTestDetails;

import java.util.List;

/**
 * @author Lior Gur
 */
public class StartedTestsResponse {

    private List<StartedTestDetails> startedTestDetailsList;


    public List<StartedTestDetails> getStartedTestDetailsList() {
        return startedTestDetailsList;
    }

    public void setStartedTestDetailsList(List<StartedTestDetails> startedTestDetailsList) {
        this.startedTestDetailsList = startedTestDetailsList;
    }

    public StartedTestsResponse(List<StartedTestDetails> startedTestDetailsList) {
        this.startedTestDetailsList = startedTestDetailsList;
    }
}
