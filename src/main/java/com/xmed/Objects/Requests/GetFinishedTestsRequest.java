package com.xmed.Objects.Requests;

/**
 * @author Dan Feldman
 */
public class GetFinishedTestsRequest {

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
