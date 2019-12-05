package com.shemesh.models.Objects;

import com.shemesh.models.Enums.TestType;
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
public class TestDetails {
    private Date date;
    private String testName;
    private int numOfQuestions;
    private int progress;
    private int grade;
    private String difficulties;
    private TestType testType;
    private boolean isDone;

}
