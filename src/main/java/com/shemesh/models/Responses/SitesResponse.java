package com.shemesh.models.Responses;

import com.shemesh.models.Objects.Site;
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
@AllArgsConstructor
public class SitesResponse {

    private List<Site> sites;
}
