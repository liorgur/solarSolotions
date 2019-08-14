package com.xmed.Objects.Responses;

import com.xmed.Objects.FinishedTestDetails;

import java.util.List;

/**
 * @author Dan Feldman
 */
public class FinishedTestResponse {
    List<FinishedTestDetails> finishedTestDetails;


    public FinishedTestResponse(List<FinishedTestDetails> finishedTestDetails) {
        this.finishedTestDetails = finishedTestDetails;
    }

    public List<FinishedTestDetails> getFinishedTestDetails() {
        return finishedTestDetails;
    }

    public void setFinishedTestDetails(List<FinishedTestDetails> finishedTestDetails) {
        this.finishedTestDetails = finishedTestDetails;
    }

}
