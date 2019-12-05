package com.shemesh.dao;

import com.shemesh.models.Requests.UserStatisticsRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.shemesh.models.Objects.Tables.*;
import static com.shemesh.models.Objects.Tables.SUBJECTS_QUESTION_TABLE;

/**
 * @author Lior Gur
 */
@Slf4j
@Component
public class StatisticsDao {

    public String getUserCorrectPercentageQuery(UserStatisticsRequest request) {
        return "SELECT COUNT(*)/SUM(is_correct) as correct_percentage , COUNT(*) as total " +
                "FROM  " + ANSWERS_TABLE + " " +
                "WHERE user_id  = " + request.getUserId() + " " +
                "GROUP BY user_id";
    }

    public String GetSubjectDistributionQuery(UserStatisticsRequest request) {
        return " SELECT s.name as subject_name, sum(tq.is_correct) as num_of_corrects , count(*) as total" +
                " FROM " + TEST_QUESTION_TABLE + " tq " +
                " INNER JOIN " + TEST_TABLE + " q on tq.question_id = q.question_id" +
                " INNER JOIN " + SUBJECTS_QUESTION_TABLE + " s on s.subject_id = q.subject_id\n" +
                " WHERE user_id = " + request.getUserId() +
                " GROUP by s.name, tq.is_correct";
    }

    public String getNumberOfTests(UserStatisticsRequest request) {
        return " SELECT Count(*) as num_of_tests " +
                " FROM " + TEST_TABLE + " " +
                " WHERE user_id = " + request.getUserId() + " " +
                " GROUP BY test_id ";
    }

    public String getSolvedPercentageQuestions(UserStatisticsRequest request) {
        return " SELECT count(*) as solved_percentage, " +
                "       (SELECT count(*)  " +
                "        FROM  " + QUESTIONS_TABLE +
                "        WHERE speciality_id = " + request.getSpecialityId() + " ) as total " +
                " FROM answers " +
                " WHERE user_id = " + request.getUserId() + " and  speciality_id = " + request.getSpecialityId() +
                " GROUP BY user_id";
    }
}
