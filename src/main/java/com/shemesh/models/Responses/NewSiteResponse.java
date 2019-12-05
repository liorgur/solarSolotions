package com.shemesh.models.Responses;

import com.shemesh.models.Objects.Question;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
