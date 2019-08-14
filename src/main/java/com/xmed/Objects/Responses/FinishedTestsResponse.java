package com.xmed.Objects.Responses;

import com.xmed.Objects.FinishedTestDetails;

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
