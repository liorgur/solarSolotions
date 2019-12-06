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
    private Integer tmp;
    private Integer humidity;
    private Integer volt;
    private Integer light;



}
