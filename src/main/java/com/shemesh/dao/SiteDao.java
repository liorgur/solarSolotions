package com.shemesh.dao;

import com.shemesh.models.Requests.UpdateSiteRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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

    public String CreateUpdateSiteQuery(UpdateSiteRequest updateSiteRequest) {
        return " UPDATE " + SITES_TABLE +
                " SET name =  \'" + updateSiteRequest.getName() + "\' " +
                " WHERE id = " + updateSiteRequest.getId();

    }
}
