package com.shemesh.models.Requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Lior Gur
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSiteRequest
{
    private int id;
    private String name;
    private String owner;
    private int lat;
    private int lon;
    private String description;


}
