package com.shemesh.solar.solutions.models.Objects;

import com.shemesh.solar.solutions.models.Enums.AlertType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * @author Lior Gur
 */
@lombok.Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Alert {

//    private int id;
    private String ip;
    private LocalDateTime time;
    private AlertType type;
    private Float value;


}
