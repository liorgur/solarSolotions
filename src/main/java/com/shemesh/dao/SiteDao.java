package com.shemesh.dao;

import com.shemesh.models.Enums.Difficulty;
import com.shemesh.models.Enums.QuestionType;
import com.shemesh.models.Requests.CreateNewTestRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.shemesh.models.Objects.Tables.*;

/**
 * @author Dan Feldman
 */
@Component
@Slf4j
public class SiteDao {

    public String CreateInsertIntoSiteQuery(String name) {
        return "INSERT INTO " + SITES_TABLE + " " +
                " (name) " +
                " VALUES (\"" + name + "\")";
    }

    public String CreateGetSitesQuery() {
        return " SELECT *" +
                " FROM  " + SITES_TABLE + " ";
    }
}
