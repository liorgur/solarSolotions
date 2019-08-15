package com.xmed.models;

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
}