package com.xmed.models.Requests;

import com.xmed.models.Enums.Difficulty;
import com.xmed.models.Enums.QuestionType;
import com.xmed.models.Enums.TestType;
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
public class CreateNewTestRequest {

    private TestType testType;
    private String name;
    private boolean isTime;
    private boolean isWithSolutions;
    private List<Difficulty> difficulties;
    private List<QuestionType> questionTypes;
    private int numOfQuestions;
    private int timeInMinutes;
    private int[] years;
    private int specialities;
    private int[] subjects;
    //private String comment;
    private int userId;
    private Boolean isMarked;
    private Boolean isHide ;

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

}
