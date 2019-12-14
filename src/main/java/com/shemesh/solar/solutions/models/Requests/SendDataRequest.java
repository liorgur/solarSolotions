package com.shemesh.solar.solutions.models.Requests;

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
public class SendDataRequest
{
    private String ip;
    private Float tmp;
    private Float humidity;
    private Float volt;
    private Float light;



}
