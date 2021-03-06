package com.shemesh.solar.solutions.models.Objects;

import com.shemesh.solar.solutions.models.Enums.AlertType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @author Lior Gur
 */
@lombok.Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Alert {

//    private int site_id;
    private String name;
    private String ip;
    private Timestamp time;
    private AlertType type;
    private float value;
    private int status;


}
