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
    private String name;
    private String contact_person;
    private String contact_phone;
    private double lat;
    private double lon;
    private String description;

}
