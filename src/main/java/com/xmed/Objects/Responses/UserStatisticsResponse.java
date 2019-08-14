package com.xmed.Objects.Responses;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Lior Gur
 */
public class UserStatisticsResponse {

    private int correctPercentage;
    private List<SubjectDistribution> subjectDistributionList;

    public UserStatisticsResponse(int correctPercentage,
            List<SubjectDistribution> subjectDistributionList) {
        this.correctPercentage = correctPercentage;
        this.subjectDistributionList = subjectDistributionList;
    }

    public int getCorrectPercentage() {
        return correctPercentage;
    }

    public void setCorrectPercentage(int correctPercentage) {
        this.correctPercentage = correctPercentage;
    }

    public List<SubjectDistribution> getSubjectDistributionList() {
        return subjectDistributionList;
    }

    public void setSubjectDistributionList(List<SubjectDistribution> subjectDistributionList) {
        this.subjectDistributionList = subjectDistributionList;
    }

    public UserStatisticsResponse(ResultSet correctPpercentageRs, ResultSet subjectDistributionRs) {
        List<SubjectDistribution> list = new ArrayList<>();
        int correctPercentage = 0;
        try {
            if (correctPpercentageRs.next()) {
                this.correctPercentage = correctPpercentageRs.getInt("correct_ percentage");
            }
            while (subjectDistributionRs.next()){
                int total = subjectDistributionRs.getInt("total");
                int corrects = subjectDistributionRs.getInt("correct");
                int wrongs = subjectDistributionRs.getInt("wrong");
                list.add(new SubjectDistribution(total,corrects,wrongs));
            }
            this.subjectDistributionList = list;

        }
        catch (Exception ex)
        {
            //todo
        }
    }
}

class SubjectDistribution {
    private int total;
    private int corrects;
    private int wrongs;

    SubjectDistribution(int total, int corrects, int wrongs) {
        this.total = total;
        this.corrects = corrects;
        this.wrongs = wrongs;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCorrects() {
        return corrects;
    }

    public void setCorrects(int corrects) {
        this.corrects = corrects;
    }

    public int getWrongs() {
        return wrongs;
    }

    public void setWrongs(int wrongs) {
        this.wrongs = wrongs;
    }
}
