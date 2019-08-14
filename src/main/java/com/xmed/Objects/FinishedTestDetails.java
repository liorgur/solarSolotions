package com.xmed.Objects;

import java.sql.Date;

/**
 * @author Dan Feldman
 */
public class FinishedTestDetails {
    private Date date;
    private String testName;
    private int numOfQuestions;
    private int grade;
    private String difficulties;
    private String testType;

    public FinishedTestDetails(Date date, String testName, int numOfQuestions, int grade,
            String difficulties, String testType) {
        this.date = date;
        this.testName = testName;
        this.numOfQuestions = numOfQuestions;
        this.grade = grade;
        this.difficulties = difficulties;
        this.testType = testType;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public int getNumOfQuestions() {
        return numOfQuestions;
    }

    public void setNumOfQuestions(int numOfQuestions) {
        this.numOfQuestions = numOfQuestions;
    }


    public String getDifficulties() {
        return difficulties;
    }

    public void setDifficulties(String difficulties) {
        this.difficulties = difficulties;
    }

    public String getTestType() {
        return testType;
    }

    public void setTestType(String testType) {
        this.testType = testType;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
