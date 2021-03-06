package com.shemesh.solar.solutions.models.Objects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URL;

/**
 * @author Lior Gur
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Site {

    private int id;
    private String ip;
    private String ip2;
    private String name;
    private String contact_person;
    private String contact_phone;
    private double lat;
    private double lon;
    private String description;
    private String provider1;
    private String provider2;
    private String provider3;
    private String provider4;
    private String cameras_link;
    private boolean switch1;
    private boolean switch2;
    private int pwm;
    private String color;



}
