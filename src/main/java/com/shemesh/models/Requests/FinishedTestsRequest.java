package com.shemesh.models.Requests;

/**
 * @author Lior Gur
 */
public class FinishedTestsRequest {

    private int userId;
    private int testId;

    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }



    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
