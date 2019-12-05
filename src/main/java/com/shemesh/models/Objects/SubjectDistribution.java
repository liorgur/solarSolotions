package com.shemesh.models.Objects;

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
public
class SubjectDistribution {
    private int total;
    private int corrects;
    private int wrongs;
}
