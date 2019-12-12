package com.shemesh.models.Objects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

/**
 * @author Lior Gur
 */
@lombok.Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Data {

    private String ip;
    private float tmp;
    private float humidity;
    private float volt;
    private float light;

}
