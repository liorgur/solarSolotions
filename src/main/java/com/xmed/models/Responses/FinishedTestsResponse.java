package com.xmed.models.Responses;

import com.xmed.models.Objects.FinishedTestDetails;

import java.util.List;

/**
 * @author Lior Gur
 */
public class FinishedTestsResponse {
    private List<FinishedTestDetails> finishedTestDetails;


    public FinishedTestsResponse(List<FinishedTestDetails> finishedTestDetails) {
        this.finishedTestDetails = finishedTestDetails;
    }

    public List<FinishedTestDetails> getFinishedTestDetails() {
        return finishedTestDetails;
    }

    public void setFinishedTestDetails(List<FinishedTestDetails> finishedTestDetails) {
        this.finishedTestDetails = finishedTestDetails;
    }

}
