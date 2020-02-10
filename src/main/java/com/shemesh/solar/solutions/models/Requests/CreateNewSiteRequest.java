package com.shemesh.solar.solutions.models.Requests;

import com.shemesh.solar.solutions.models.Objects.Site;
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
public class CreateNewSiteRequest
{
    private Site site;


}
