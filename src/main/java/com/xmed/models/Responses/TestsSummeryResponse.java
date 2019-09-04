package com.xmed.models.Responses;

import com.xmed.models.Objects.TestDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Lior Gur
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestsSummeryResponse {

    private List<TestDetails> testDetailsList;
}
