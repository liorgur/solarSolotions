package com.shemesh.services;

import com.shemesh.dao.SiteDao;
import com.shemesh.models.Objects.Site;
import com.shemesh.models.Requests.CreateNewSiteRequest;
import com.shemesh.models.Requests.UpdateSiteRequest;
import com.shemesh.models.Responses.NewSiteResponse;
import com.shemesh.models.Responses.SitesResponse;
import com.shemesh.utils.DbHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class SiteService {
    @Autowired
    private SiteDao dao;

    @Autowired
    private DbHelper dbHelper;

    public NewSiteResponse CreateNewSite(CreateNewSiteRequest request) throws SQLException {

        String queryInsertNewSite = dao.CreateInsertIntoSiteQuery(request.getName());

        int siteId = (int) dbHelper.executeInsertQuery(queryInsertNewSite);

        return new NewSiteResponse(siteId);
    }

    public SitesResponse GetSites() throws SQLException {
        String queryGetSites= dao.CreateGetSitesQuery();

        ResultSet sites = dbHelper.executeQueryToResultSet(queryGetSites);


        return ResultSetToSite(sites);
    }

    public void UpdateSite(UpdateSiteRequest updateSiteRequest) throws SQLException {
        String queryUpdateSite = dao.CreateUpdateSiteQuery(updateSiteRequest);
        dbHelper.executeQuery(queryUpdateSite);
    }

    private SitesResponse ResultSetToSite(ResultSet resultSet) {

        List<Site> list = new ArrayList<>();

        try {
            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                String ip = resultSet.getString("ip");
                String siteName = resultSet.getString("name");
                String contact_person = resultSet.getString("contact_person");
                String contact_phone = resultSet.getString("contact_phone");
                double lat = resultSet.getDouble("lat");
                double lon = resultSet.getDouble("lon");
                String description = resultSet.getString("description");

                list.add(new Site(id,ip,siteName,contact_person,contact_phone,lat,lon,description));
            }
            return new SitesResponse(list);
        }
        catch (Exception ex) {
            log.error(ex.getMessage());
            log.debug(Arrays.toString(ex.getStackTrace())); //todo
        }
        return null;
    }


}
