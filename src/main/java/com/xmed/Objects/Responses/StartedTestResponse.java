package com.xmed.Objects.Responses;

import com.xmed.Objects.StartedTestDetails;

import java.util.List;

/**
 * @author Dan Feldman
 */
public class StartedTestResponse {

    private List<StartedTestDetails> startedTestDetailsList;


    public List<StartedTestDetails> getStartedTestDetailsList() {
        return startedTestDetailsList;
    }

    public void setStartedTestDetailsList(List<StartedTestDetails> startedTestDetailsList) {
        this.startedTestDetailsList = startedTestDetailsList;
    }

    public StartedTestResponse(List<StartedTestDetails> startedTestDetailsList) {
        this.startedTestDetailsList = startedTestDetailsList;
    }
}
