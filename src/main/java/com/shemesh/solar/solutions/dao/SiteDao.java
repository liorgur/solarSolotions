package com.shemesh.solar.solutions.dao;

import com.shemesh.solar.solutions.models.Requests.CreateNewSiteRequest;
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

    public String CreateInsertIntoSiteQuery(CreateNewSiteRequest request) {
        return "INSERT INTO " + SITES_TABLE + " " +
                " (name) " +
                " VALUES (\"" + request.getSite().getName() + "\")";
    }

    public String CreateGetSitesQuery(Integer id) {
        String where = (id != null) ? "WHERE id = " + id  : " ";

        return " SELECT * " +
                " FROM  " + SITES_TABLE + " " +
                where;
    }

    public String CreateUpdateSiteQuery(UpdateSiteRequest updateSiteRequest) {
        return " UPDATE " + SITES_TABLE +
                " SET name =  \'" + updateSiteRequest.getName() + "\' " +
                " WHERE id = " + updateSiteRequest.getId();

    }

    public String UpdateSwitchStatus(int site_id, int switch_id, boolean status) {
        return " UPDATE " + SITES_TABLE +
                " SET switch" +switch_id + " = " + status +
                " WHERE id = " +site_id;

    }


    public String UpdatePwm(int site_id, int pwm) {
        return " UPDATE " + SITES_TABLE +
                " SET pwm = " + pwm +
                " WHERE id = " +site_id;
    }
}
