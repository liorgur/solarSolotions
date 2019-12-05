package com.shemesh.models.Responses;


import com.shemesh.models.Objects.SubjectDistribution;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Lior Gur
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserStatisticsResponse {

    //תשובות נכונות
    private int correctPercentage;
    private int totalCorrect;

    private List<SubjectDistribution> subjectDistributionList;

    //סהכ מבחנים
    private int numOfTests;

    //שאלות שנצפו
    private int totalQuestion;
    private int solvedPercentage;


}

