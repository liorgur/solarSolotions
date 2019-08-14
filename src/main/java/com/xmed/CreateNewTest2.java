package com.xmed;

import com.xmed.Objects.Difficulty;
import com.xmed.Objects.Question;
import com.xmed.Objects.QuestionType;
import com.xmed.Objects.Requests.CreateNewTestRequest;
import com.xmed.Objects.Responses.NewTestResponse;
import com.xmed.Utils.DbHelper;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.core.Response;
import java.sql.ResultSet;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.xmed.Objects.Tables.*;

/**
 * Handler for requests to Lambda function.
 */
@RestController
public class CreateNewTest2 {

    //@Transactional() //todo check is working
    @PostMapping("/CreateNewTest2")
    public Response handleRequest(String input) {

        CreateNewTestRequest newTestRequest = new Gson().fromJson(input, CreateNewTestRequest.class);

        String queryCreateTest = createQueryCreateTest(newTestRequest);
        String queryInsertNewTest = insertNewTestQuery(newTestRequest);

        ResultSet questionsResultSet = DbHelper.executeQueryToResultSet(queryCreateTest);
        List<Question> questionList = GetQuestionListFromResultSet(questionsResultSet);

        int testId =(int) DbHelper.executeInsertQuery(queryInsertNewTest);

        InsertToTestQuestionRelationTable(questionList, testId);
        NewTestResponse newTestResponse =  new NewTestResponse(questionList);

        return Response
                .status(Response.Status.OK)
                .entity(newTestResponse)
                .build();

    }

    private void InsertToTestQuestionRelationTable(List<Question> questionList, int testId) {
        for (Question question: questionList) {

            String relationQuery = CreateInsertIntoTestQuestionRelationQuery(question.getQuestion_id(), testId);
            DbHelper.executeQuery(relationQuery);
        }
    }

    private String CreateInsertIntoTestQuestionRelationQuery(int questionId , int testId){
        return  "INSERT INTO " + TEST_QUESTION_TABLE+ " " +
                " (test_id , question_id) " +
                " VALUES (" + testId + "," + questionId + ")" ;
    }

    private List<Question> GetQuestionListFromResultSet(ResultSet questionsResultSet) {

        List<Question> questionList = new ArrayList();
        try {
            while (questionsResultSet.next()) {

                int question_id =  questionsResultSet.getInt("question_id");
                String question=  questionsResultSet.getString("question");
                String answer =  questionsResultSet.getString("answer");
                String distractor_1 =  questionsResultSet.getString("distractor_1");
                String distractor_2 =  questionsResultSet.getString("distractor_2");
                String distractor_3 =  questionsResultSet.getString("distractor_3");
                String solution=  questionsResultSet.getString("solution");
                String reference =  questionsResultSet.getString("reference");
                int year =  questionsResultSet.getInt("year");
                int speciality_id =  questionsResultSet.getInt("speciality_id");
                int subject_id =  questionsResultSet.getInt("subject_id");

                Question question1 = new Question(question_id, question, answer, distractor_1, distractor_2, distractor_3, solution, reference,year,speciality_id, subject_id);

                questionList.add(question1);
            }
        }
        catch (Exception ex)
        {

        }
        return questionList;
    }

    //private TestQuestionRelations getQuestionListFromNewTest(List<Question> questionList, int testId) {
    //    TestQuestionRelations questionRelation = new TestQuestionRelations();
    //    for (Question question:questionList) {
    //        TestQuestion testQuestion = new TestQuestion(testId, question.getQuestion_id());
    //        questionRelation.getTestQuestionList().add(testQuestion);
    //    }
    //
    //    return questionRelation;
    //}

    private String createQueryCreateTest(CreateNewTestRequest newTestRequest) {
        return " SELECT * " +
                " FROM " + QUESTIONS_TABLE + " left join " + ANSWERS_TABLE +
                " on questions.question_id = answers.question_id " +
                addOnStatement(newTestRequest.getQuestionTypeList(), newTestRequest.getUserId()) +
                " WHERE 1=1 " +
                getDifficultyWhere(newTestRequest.getDifficulties()) +
                getQuestionTypeWhere(newTestRequest.getQuestionTypeList())  +
                getYearsWhere(newTestRequest.getYears()) +
                getSubjectsWhere(newTestRequest.getSubjects()) +
                getSpecialityWhere(newTestRequest.getSpecialities()) +
                getMarkedWhere(newTestRequest) +
                getHideWhere(newTestRequest) +
                " ORDER BY RAND() ";
    }

    private String getHideWhere(CreateNewTestRequest request) {
        if(request.getMarked() != null){
            return " AND is_marked = " +request.getMarked();
        }
        return "";
    }

    private String getMarkedWhere(CreateNewTestRequest request) {
        if(request.getHide() != null){
            return " AND is_hide = " +request.getHide();
        }
        return "";
    }

    private String addOnStatement(List<QuestionType> questionTypeArray, int userId) {
        String onStatment = " AND (user_id = " + userId;
         if(questionTypeArray.stream().anyMatch(a-> a.equals(QuestionType.UNSOLVED))){
             onStatment +=  " OR user_id is null )" ;
        }
         else {
             onStatment += " ) ";
         }
         return onStatment;
    }

    private String insertNewTestQuery(CreateNewTestRequest newTestRequest) {
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
                newTestRequest.getDifficultiesAsString()  + "," +
                newTestRequest.getTestType() +
                "); " +

                " ";

    }


    private String getYearsWhere(int[] yearsParam) {
        String years = null;

        if(yearsParam.length != 0) {
            years = IntStream.of(yearsParam)
                    .mapToObj(Integer::toString)
                    .collect(Collectors.joining(", "));
        }
        return years != null ? " AND year in (" + years + ") "  : " ";
    }

    private String getSpecialityWhere(int[] specialitiesParam) {

        String specialities=  null;
        if(specialitiesParam != null && specialitiesParam.length != 0)
            specialities = IntStream.of(specialitiesParam)
                    .mapToObj(Integer::toString)
                    .collect(Collectors.joining(", "));
        return specialities != null ? " AND speciality_id in (" + specialities + ") "  : " ";
    }

    private String getSubjectsWhere(int[] subjectsParam) {

        String subjects=  null;
        if(subjectsParam!= null && subjectsParam.length != 0)
            subjects = IntStream.of(subjectsParam)
                    .mapToObj(Integer::toString)
                    .collect(Collectors.joining(", "));
        return subjects != null ? " AND subject_id in (" + subjects + ") "  : " ";
    }

    private String getQuestionTypeWhere(List<QuestionType> questionType) {

        String where = "";
        if(questionType.contains(QuestionType.WRONG)) {
            where += "AND " + ANSWERS_TABLE + ".is_correct = 0 ";
        }
        else if(questionType.contains(QuestionType.CORRECT)){
            where += "AND " + ANSWERS_TABLE + ".is_correct = 1 ";
        }
        if(questionType.contains(QuestionType.MARKED)){
            where += "AND " + ANSWERS_TABLE + ".is_marked = 1 ";
        }

        return where;
    }

    private String getDifficultyWhere(List<Difficulty> difficulties) {


        StringBuilder where = new StringBuilder();
        if(difficulties == null || difficulties.size() == 0)
        {
            return " ";
        }
        where = new StringBuilder(" AND ( ");
        for (Difficulty difficulty: difficulties) {
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
