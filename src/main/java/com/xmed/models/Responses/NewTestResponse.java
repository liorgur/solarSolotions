package com.xmed.models.Responses;

import com.xmed.models.Question;

import java.util.List;

/**
 * @author Lior Gur
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

    private List<Question> questions;
}