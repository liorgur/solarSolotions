package com.shemesh.services;

import com.shemesh.dao.StatisticsDao;
import com.shemesh.models.Objects.SubjectDistribution;
import com.shemesh.models.Requests.UserStatisticsRequest;
import com.shemesh.models.Responses.UserStatisticsResponse;
import com.shemesh.utils.DbHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Lior Gur
 */
@Service
@Slf4j
public class StatisticsService {

    @Autowired
    private StatisticsDao dao;

    @Autowired
    private DbHelper dbHelper;

    public UserStatisticsResponse getUserStatistics(UserStatisticsRequest request) throws SQLException {

        ResultSet userCorrectPercentage = dbHelper
                .executeQueryToResultSet(dao.getUserCorrectPercentageQuery(request));

        ResultSet subjectDistributionRs = dbHelper.executeQueryToResultSet(dao.GetSubjectDistributionQuery(request));

        ResultSet numOfTestsRs = dbHelper.executeQueryToResultSet(dao.getNumberOfTests(request));

        ResultSet solvedPercentageQuestionsRs = dbHelper
                .executeQueryToResultSet(dao.getSolvedPercentageQuestions(request));

        return UserStatisticsResponse(userCorrectPercentage, subjectDistributionRs, numOfTestsRs,
                solvedPercentageQuestionsRs);
    }

        private UserStatisticsResponse UserStatisticsResponse(ResultSet correctPercentageRs,
            ResultSet subjectDistributionRs, ResultSet numOfTestsRs, ResultSet solvedPercentageQuestionsRs) {
        List<SubjectDistribution> list = new ArrayList<>();
        int correctPercentage = 0;
        int totalCorrect = 0;
        int numOfTests = 0;
        int totalQuestion = 0;
        int solvedPercentage = 0;

        try {
            if (correctPercentageRs.next()) {
                correctPercentage = correctPercentageRs.getInt("correct_ percentage");
                totalCorrect = correctPercentageRs.getInt("total");
            }
            if (numOfTestsRs.next()) {
                numOfTests = numOfTestsRs.getInt("num_of_tests");
            }
            while (subjectDistributionRs.next()) {
                int total = subjectDistributionRs.getInt("total");
                int corrects = subjectDistributionRs.getInt("correct");
                int wrongs = subjectDistributionRs.getInt("wrong");
                list.add(new SubjectDistribution(total, corrects, wrongs));
            }
            if (solvedPercentageQuestionsRs.next()) {
                totalQuestion = solvedPercentageQuestionsRs.getInt("total");
                solvedPercentage = subjectDistributionRs.getInt("solved_percentage");
            }
            return new UserStatisticsResponse(correctPercentage, totalCorrect, list, numOfTests, totalQuestion,
                    solvedPercentage);
        } catch (Exception ex) {
            //todo
        }
        return null;
    }
}
