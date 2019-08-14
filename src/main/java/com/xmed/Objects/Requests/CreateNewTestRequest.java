package com.xmed.Objects.Requests;

import com.xmed.Objects.Difficulty;
import com.xmed.Objects.QuestionType;
import com.xmed.Objects.TestType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Dan Feldman
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateNewTestRequest {

    //Create Test
    private String name;
    private boolean isTime;
    private boolean isWithSolutions;
    private List<Difficulty> difficulties;
    private List<QuestionType> questionTypes;
    private int numOfQuestions;
    private int[] years;
    private int[] specialities;
    private int[] subjects;
    private String comment;
    private int userId;
    private TestType testType;
    private Boolean isMarked;
    private Boolean isHide ;

    public Boolean getMarked() {
        return isMarked;
    }

    public void setMarked(Boolean marked) {
        isMarked = marked;
    }

    public Boolean getHide() {
        return isHide;
    }

    public void setHide(Boolean hide) {
        isHide = hide;
    }

    public List<QuestionType> getQuestionTypes() {
        return questionTypes;
    }

    public TestType getTestType() {
        return testType;
    }

    public void setTestType(TestType testType) {
        this.testType = testType;
    }


    public void setDifficulties(List<Difficulty> difficulties) {
        this.difficulties = difficulties;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isTime() {
        return isTime;
    }

    public void setTime(boolean time) {
        isTime = time;
    }

    public boolean isWithSolutions() {
        return isWithSolutions;
    }

    public void setWithSolutions(boolean withSolutions) {
        isWithSolutions = withSolutions;
    }

    public List<Difficulty> getDifficulties() {
        return difficulties;
    }

    public String getDifficultiesAsString() {
        StringBuilder difficulties = new StringBuilder();
        if (this.difficulties == null || this.difficulties.size() == 0) {
            return "NULL";
        }
        difficulties.append("'");
        for (Difficulty difficulty : this.difficulties) {
            difficulties.append(difficulty.getValue()).append(",");
        }
        difficulties.setLength(difficulties.length() - 1);
        difficulties.append("'");

        return difficulties.toString();
    }

    public void setDifficulty(List<Difficulty> difficulty) {
        this.difficulties = difficulty;
    }

    public List<QuestionType> getQuestionTypeList() {
        return questionTypes;
    }

    public String getQuestionTypeAsString() {
        StringBuilder questionTypes = new StringBuilder();
        if (this.questionTypes == null || this.questionTypes.size() == 0) {
            return "NULL";
        }
        questionTypes.append("'");
        for (QuestionType questionType : this.questionTypes) {
            questionTypes.append(questionType.getValue()).append(",");
        }
        questionTypes.setLength(questionTypes.length() - 1);
        questionTypes.append("'");

        return questionTypes.toString();
    }

    public void setQuestionTypes(List<QuestionType> questionTypes) {
        this.questionTypes = questionTypes;
    }

    public int getNumOfQuestions() {
        return numOfQuestions;
    }

    public void setNumOfQuestions(int numOfQuestions) {
        this.numOfQuestions = numOfQuestions;
    }

    public int[] getYears() {
        return years;
    }

    public void setYears(int[] years) {
        this.years = years;
    }

    public int[] getSpecialities() {
        return specialities;
    }

    public void setSpecialities(int[] specialities) {
        this.specialities = specialities;
    }

    public int[] getSubjects() {
        return subjects;
    }

    public void setSubjects(int[] subjects) {
        this.subjects = subjects;
    }
}
