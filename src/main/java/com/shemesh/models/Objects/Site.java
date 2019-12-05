package com.shemesh.models.Objects;

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
    private String name;
    private String owner;
    private int lat;
    private int lon;
    private String description;

    public Site(String siteName) {
    }
}
