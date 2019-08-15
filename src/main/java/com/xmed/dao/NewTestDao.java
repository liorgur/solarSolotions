package com.xmed.dao;

import com.xmed.models.Enums.Difficulty;
import com.xmed.models.Enums.QuestionType;
import com.xmed.models.Requests.CreateNewTestRequest;
import com.xmed.models.Requests.GetUserStatisticsRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.xmed.models.Tables.*;

/**
 * @author Dan Feldman
 */
@Component
@Slf4j
public class NewTestDao {


    public String CreateInsertIntoTestQuestionRelationQuery(int questionId, int testId) {
        return "INSERT INTO " + TEST_QUESTION_TABLE + " " +
                " (test_id , question_id) " +
                " VALUES (" + testId + "," + questionId + ")";
    }



    public String createQueryCreateTest(CreateNewTestRequest newTestRequest) {
        return " SELECT * " +
                " FROM " + QUESTIONS_TABLE + " left join " + ANSWERS_TABLE +
                " on questions.question_id = answers.question_id " +
                addOnStatement(newTestRequest.getQuestionTypeList(), newTestRequest.getUserId()) +
                " WHERE 1=1 " +
                getDifficultyWhere(newTestRequest.getDifficulties()) +
                getQuestionTypeWhere(newTestRequest.getQuestionTypeList()) +
                getYearsWhere(newTestRequest.getYears()) +
                getSubjectsWhere(newTestRequest.getSubjects()) +
                getSpecialityWhere(newTestRequest.getSpecialities()) +
                getMarkedWhere(newTestRequest) +
                getHideWhere(newTestRequest) +
                " ORDER BY RAND() ";
    }

    private String getHideWhere(CreateNewTestRequest request) {
        if (request.getMarked() != null) {
            return " AND is_marked = " + request.getMarked();
        }
        return "";
    }

    private String getMarkedWhere(CreateNewTestRequest request) {
        if (request.getHide() != null) {
            return " AND is_hide = " + request.getHide();
        }
        return "";
    }

    private String addOnStatement(List<QuestionType> questionTypeArray, int userId) {
        String onStatment = " AND (user_id = " + userId;
        if (questionTypeArray.stream().anyMatch(a -> a.equals(QuestionType.UNSOLVED))) {
            onStatment += " OR user_id is null )";
        } else {
            onStatment += " ) ";
        }
        return onStatment;
    }

    public String insertNewTestQuery(CreateNewTestRequest newTestRequest) {
        return " INSERT INTO  " + TEST_TABLE + " " +
                " (test_name, user_id, date_created, is_with_time, question_type, comment, num_of_questions, is_with_solution, difficulties, test_type) " +
                " VALUES('" +
                newTestRequest.getName() + "'," +
                newTestRequest.getUserId() + "," +
                "'" + ZonedDateTime.now() + "'," +  //todo format time
                newTestRequest.isTime() + "," +
                newTestRequest.getQuestionTypeAsString() + "," +
                newTestRequest.getComment() + "," +
                newTestRequest.getNumOfQuestions() + "," +
                newTestRequest.isWithSolutions() + "," +
                newTestRequest.getDifficultiesAsString() + "," +
                newTestRequest.getTestType() +
                "); " +

                " ";

    }


    private String getYearsWhere(int[] yearsParam) {
        String years = null;

        if (yearsParam.length != 0) {
            years = IntStream.of(yearsParam)
                    .mapToObj(Integer::toString)
                    .collect(Collectors.joining(", "));
        }
        return years != null ? " AND year in (" + years + ") " : " ";
    }

    private String getSpecialityWhere(int[] specialitiesParam) {

        String specialities = null;
        if (specialitiesParam != null && specialitiesParam.length != 0) {
            specialities = IntStream.of(specialitiesParam)
                    .mapToObj(Integer::toString)
                    .collect(Collectors.joining(", "));
        }
        return specialities != null ? " AND speciality_id in (" + specialities + ") " : " ";
    }

    private String getSubjectsWhere(int[] subjectsParam) {

        String subjects = null;
        if (subjectsParam != null && subjectsParam.length != 0) {
            subjects = IntStream.of(subjectsParam)
                    .mapToObj(Integer::toString)
                    .collect(Collectors.joining(", "));
        }
        return subjects != null ? " AND subject_id in (" + subjects + ") " : " ";
    }

    private String getQuestionTypeWhere(List<QuestionType> questionType) {

        String where = "";
        if (questionType.contains(QuestionType.WRONG)) {
            where += "AND " + ANSWERS_TABLE + ".is_correct = 0 ";
        } else if (questionType.contains(QuestionType.CORRECT)) {
            where += "AND " + ANSWERS_TABLE + ".is_correct = 1 ";
        }
        if (questionType.contains(QuestionType.MARKED)) {
            where += "AND " + ANSWERS_TABLE + ".is_marked = 1 ";
        }

        return where;
    }

    private String getDifficultyWhere(List<Difficulty> difficulties) {


        StringBuilder where = new StringBuilder();
        if (difficulties == null || difficulties.size() == 0) {
            return " ";
        }
        where = new StringBuilder(" AND ( ");
        for (Difficulty difficulty : difficulties) {
            if (difficulty.equals(Difficulty.ALL)) {
                return " ";
            }
            if (difficulty.equals(Difficulty.HARD)) {
                where.append(" difficulty > 70 OR ");
            }
            if (difficulty.equals(Difficulty.MEDIUM)) {
                where.append(" (difficulty > 30 and difficulty < 70) OR ");
            }
            if (difficulty.equals(Difficulty.EASY)) {
                where.append(" difficulty  < 30 OR");
            }
        }
        where.setLength(where.length() - 2);
        where.append(" ) ");
        return where.toString();
    }


    public String getUserCorrectPercentageQuery(GetUserStatisticsRequest request) {
        return "SELECT COUNT(*)/SUM(is_correct) as correct_percentage " +
                "FROM  " + ANSWERS_TABLE + " " +
                "WHERE user_id  = " + request.getUserId() + " " +
                "GROUP BY user_id";
    }

    public String GetSubjectDistributionQuery(GetUserStatisticsRequest request) {
        return " SELECT s.name as subject_name, sum(tq.is_correct) as num_of_corrects , count(*) as total" +
                " FROM " + TEST_QUESTION_TABLE + " tq " +
                " INNER JOIN " + TEST_TABLE + " q on tq.question_id = q.question_id" +
                " INNER JOIN " + SUBJECTS_QUESTION_TABLE + " s on s.subject_id = q.subject_id\n" +
                " WHERE user_id = " + request.getUserId() +
                " GROUP by s.name, tq.is_correct";
    }


}
