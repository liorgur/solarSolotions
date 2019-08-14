package com.xmed.Objects.Responses;

import com.xmed.Objects.Question;

import java.util.List;

/**
 * @author Dan Feldman
 */
public class NewTestResponse {
    public NewTestResponse(List<Question> questions) {
        this.questions = questions;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    List<Question> questions;
}
