package com.xmed.Objects;

/**
 * @author Dan Feldman
 */
public class Question {

    int question_id;
    String question;
    String answer;
    String distractor_1;
    String distractor_2;
    String distractor_3;
    String solution;
    String reference;
    int year;
    int speciality_id;
    int subject_id;

    public Question(int question_id, String question, String answer, String distractor_1, String distractor_2,
            String distractor_3, String solution, String reference, int year, int speciality_id, int subject_id) {
        this.question_id = question_id;
        this.question = question;
        this.answer = answer;
        this.distractor_1 = distractor_1;
        this.distractor_2 = distractor_2;
        this.distractor_3 = distractor_3;
        this.solution = solution;
        this.reference = reference;
        this.year = year;
        this.speciality_id = speciality_id;
        this.subject_id = subject_id;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getDistractor_1() {
        return distractor_1;
    }

    public void setDistractor_1(String distractor_1) {
        this.distractor_1 = distractor_1;
    }

    public String getDistractor_2() {
        return distractor_2;
    }

    public void setDistractor_2(String distractor_2) {
        this.distractor_2 = distractor_2;
    }

    public String getDistractor_3() {
        return distractor_3;
    }

    public void setDistractor_3(String distractor_3) {
        this.distractor_3 = distractor_3;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getSpeciality_id() {
        return speciality_id;
    }

    public void setSpeciality_id(int speciality_id) {
        this.speciality_id = speciality_id;
    }

    public int getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(int subject_id) {
        this.subject_id = subject_id;
    }


}
