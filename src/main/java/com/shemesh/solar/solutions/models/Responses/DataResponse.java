package com.shemesh.solar.solutions.models.Responses;

import com.shemesh.solar.solutions.models.Objects.DataPoint;
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
public class DataResponse {

    private List<DataPoint> data;
}
