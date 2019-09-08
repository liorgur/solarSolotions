package com.xmed.models.Requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Lior Gur
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionActionsRequest {
    private int userId;
    private int questionId;
    private Boolean isShare;
    private Boolean isMark;
    private Boolean isHide;
    //private Boolean isComment;
    private String comment;
    private String feedback;
}
