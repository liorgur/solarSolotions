package com.shemesh.models.Requests;

import com.shemesh.models.Enums.Difficulty;
import com.shemesh.models.Enums.QuestionType;
import com.shemesh.models.Enums.TestType;
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
public class CreateNewSiteRequest
{
    private int id;
    private String name;
    private String owner;
    private int lat;
    private int lon;
    private String description;


}
