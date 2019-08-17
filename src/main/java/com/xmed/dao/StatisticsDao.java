package com.xmed.dao;

import com.xmed.models.Requests.UserStatisticsRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.xmed.models.Tables.*;
import static com.xmed.models.Tables.SUBJECTS_QUESTION_TABLE;

/**
 * @author Lior Gur
 */
@Slf4j
@Component
public class StatisticsDao {

    public String getUserCorrectPercentageQuery(UserStatisticsRequest request) {
        return "SELECT COUNT(*)/SUM(is_correct) as correct_percentage " +
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
}
