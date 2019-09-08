package com.xmed.dao;

import com.xmed.models.Enums.Difficulty;
import com.xmed.models.Enums.QuestionType;
import com.xmed.models.Requests.CreateNewTestRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.xmed.models.Objects.Tables.*;

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

    public String createQueryCreateCustomTest(CreateNewTestRequest newTestRequest) {
        return " SELECT * " +
                " FROM " + QUESTIONS_TABLE + " left join " + ANSWERS_TABLE +
                " on questions.question_id = answers.question_id " +
                addOnStatement(newTestRequest.getQuestionTypes(), newTestRequest.getUserId()) +
                " WHERE 1=1 " +
                getDifficultyWhere(newTestRequest.getDifficulties()) +
                getQuestionTypeWhere(newTestRequest.getQuestionTypes()) +
                getYearsWhere(newTestRequest.getYears()) +
                getSubjectsWhere(newTestRequest.getSubjects()) +
                getSpecialityWhere(newTestRequest.getSpecialities()) +
                getMarkedWhere(newTestRequest) +
                getHideWhere(newTestRequest) +
                " ORDER BY RAND() ";
    }

    public String createQueryCreateMistakeTest(CreateNewTestRequest newTestRequest) {
        return " SELECT * " +
                " FROM " + QUESTIONS_TABLE + " left join " + ANSWERS_TABLE +
                " on questions.question_id = answers.question_id " +
                " WHERE is_correct = 0" ;
    }

    public String createQueryCreateUnseenQuestionTest(CreateNewTestRequest newTestRequest) {
        return " SELECT * " +
                " FROM " + QUESTIONS_TABLE + " left join " + TEST_QUESTION_TABLE +
                " on questions.question_id = test_questions.question_id " +
                " WHERE is_correct = NULL" ;
    }

    public String createQueryCreateSurpriseTest(CreateNewTestRequest newTestRequest) {
        return createQueryCreateMistakeTest (newTestRequest) +
                " UNION " +
                createQueryCreateUnseenQuestionTest (newTestRequest);

    }



    private String getHideWhere(CreateNewTestRequest request) {
        if (request.getIsMarked() != null) {
            return " AND is_marked = " + request.getIsMarked();
        }
        return "";
    }

    private String getMarkedWhere(CreateNewTestRequest request) {
        if (request.getIsHide() != null) {
            return " AND is_hide = " + request.getIsHide();
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

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

        return " INSERT INTO  " + TEST_TABLE + " " +
                " (test_name, user_id, date_created, is_with_time, question_type, num_of_questions, is_with_solution, difficulties, test_type) " +
                " VALUES('" +
                newTestRequest.getName() + "'," +
                newTestRequest.getUserId() + "," +
                "'" + LocalDateTime.now().format(dtf) + "'," +  //todo format time
                newTestRequest.isTime() + "," +
                newTestRequest.getQuestionTypeAsString() + "," +
                //newTestRequest.getComment() + "," +
                newTestRequest.getNumOfQuestions() + "," +
                newTestRequest.isWithSolutions() + "," +
                newTestRequest.getDifficultiesAsString() + "," +
                "'" + newTestRequest.getTestType() + "' " +
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

    private String getSpecialityWhere(int specialitiesParam) {

        String specialities;
        //if (specialitiesParam != null && specialitiesParam.length != 0) {
            specialities = IntStream.of(specialitiesParam)
                    .mapToObj(Integer::toString)
                    .collect(Collectors.joining(", "));
        //}
        return specialities != null ? " AND questions.speciality_id in (" + specialities + ") " : " ";
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
        StringBuilder where;
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





}
