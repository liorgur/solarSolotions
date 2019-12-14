package com.shemesh.solar.solutions.dao;

import com.shemesh.solar.solutions.models.Requests.UpdateSiteRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.shemesh.solar.solutions.models.Objects.Tables.SITES_TABLE;


/**
 * @author Lior Gur
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

    public String CreateUpdateSiteQuery(UpdateSiteRequest updateSiteRequest) {
        return " UPDATE " + SITES_TABLE +
                " SET name =  \'" + updateSiteRequest.getName() + "\' " +
                " WHERE id = " + updateSiteRequest.getId();

    }
}
