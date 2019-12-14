package com.shemesh.solar.solutions.models.Responses;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Lior Gur
 */
@Data
@Builder
@NoArgsConstructor

public class NewSiteResponse {
    public NewSiteResponse(int siteId) {
        this.site_id = siteId;
    }

   private int site_id;
}
