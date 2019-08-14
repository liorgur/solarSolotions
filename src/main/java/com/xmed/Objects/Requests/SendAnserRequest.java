package com.xmed.Objects.Requests;

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
public class SendAnserRequest {

    private int userId;
    private int questionId;
    private int testId;
    private boolean isCorrect;
    private String comment;
    private Boolean isMarked;
    private Boolean isDone;
    private boolean isLastQuestion;

    //public Boolean getMarked() {
    //    return isMarked;
    //}
    //
    //public void setMarked(Boolean marked) {
    //    isMarked = marked;
    //}
    //
    //public Boolean getDone() {
    //    return isDone;
    //}
    //
    //public boolean isLastQuestion() {
    //    return isLastQuestion;
    //}
    //
    //public void setLastQuestion(boolean lastQuestion) {
    //    this.isLastQuestion = lastQuestion;
    //}
    //
    //public Boolean isDone() {
    //    return isDone;
    //}
    //
    //public void setDone(Boolean done) {
    //    isDone = done;
    //}
    //
    //public int getUserId() {
    //    return userId;
    //}
    //
    //public void setUserId(int userId) {
    //    this.userId = userId;
    //}
    //
    //public int getQuestionId() {
    //    return questionId;
    //}
    //
    //public void setQuestionId(int questionId) {
    //    this.questionId = questionId;
    //}
    //
    //public int getTestId() {
    //    return testId;
    //}
    //
    //public void setTestId(int testd) {
    //    this.testId = testd;
    //}
    //
    //public boolean isCorrect() {
    //    return isCorrect;
    //}
    //
    //public void setCorrect(boolean correct) {
    //    isCorrect = correct;
    //}
    //
    //public String getComment() {
    //    return comment;
    //}
    //
    //public void setComment(String comment) {
    //    this.comment = comment;
    //}
    //
    //public boolean isMarked() {
    //    return isMarked;
    //}
    //
    //public void setMarked(boolean marked) {
    //    isMarked = marked;
    //}
}
