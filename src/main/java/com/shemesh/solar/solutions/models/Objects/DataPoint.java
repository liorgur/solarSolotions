package com.shemesh.solar.solutions.models.Objects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

/**
 * @author Lior Gur
 */
@lombok.Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataPoint {

    private String ip;
    private Timestamp time;
    private float tmp;
    private float humidity;
    private float volt;
    private float light;
    private float gateway;

}
