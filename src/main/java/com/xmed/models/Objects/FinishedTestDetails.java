package com.xmed.models.Objects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

/**
 * @author Lior Gur
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FinishedTestDetails {
    private Date date;
    private String testName;
    private int numOfQuestions;
    private int grade;
    private String difficulties;
    private String testType;

   }
