package com.xmed.Objects.Requests;

/**
 * @author Lior Gur
 */
public class QuestionActionsRequest {

    private int userId;
    private int questionId;
    private Boolean isShare;
    private Boolean isMark;
    private Boolean isHide;
    private Boolean isComment;
    private String comment;
    private Boolean isFeedback;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public Boolean getIsShare() {
        return isShare;
    }

    public void setIsShare(Boolean isShare) {
        this.isShare = isShare;
    }

    public Boolean getIsMark() {
        return isMark;
    }

    public void setIsMark(Boolean isMark) {
        this.isMark = isMark;
    }

    public Boolean getIsHide() {
        return isHide;
    }

    public void setIsHide(Boolean isHide) {
        this.isHide = isHide;
    }

    public Boolean getIsComment() {
        return isComment;
    }

    public void setIsComment(Boolean isComment) {
        this.isComment = isComment;
    }

    public Boolean getIsFeedback() {
        return isFeedback;
    }

    public void setIsFeedback(Boolean isFeedback) {
        this.isFeedback = isFeedback;
    }
}
