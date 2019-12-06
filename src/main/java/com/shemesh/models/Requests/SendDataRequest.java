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
public class SendDataRequest
{
    private String ip;
    private int tmp;
    private int humidity;
    private int volt;
    private int light;



}
