package com.shemesh.models.Requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Lior Gur
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
//@Entity
public class SendAnswerRequest {

    private int userId;
    private int questionId;
    private int testId;
    private boolean isCorrect;
    //private String comment;
    //private Boolean isMarked;
    private Boolean isDone;
    private boolean isLastQuestion;

    }
