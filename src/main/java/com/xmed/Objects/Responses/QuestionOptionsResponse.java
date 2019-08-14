package com.xmed.Objects.Responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Dan Feldman
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionOptionsResponse {

    private int test;
    private boolean aBoolean;
    private String lior;
}
