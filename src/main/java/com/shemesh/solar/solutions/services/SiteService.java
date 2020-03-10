package com.shemesh.solar.solutions.services;

import com.shemesh.solar.solutions.dao.SiteDao;
import com.shemesh.solar.solutions.models.Objects.Site;
import com.shemesh.solar.solutions.models.Requests.CreateNewSiteRequest;
import com.shemesh.solar.solutions.models.Requests.UpdateSiteRequest;
import com.shemesh.solar.solutions.models.Responses.NewSiteResponse;
import com.shemesh.solar.solutions.models.Responses.SitesResponse;
import com.shemesh.solar.solutions.utils.DbHelper;
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
        String queryInsertNewSite = dao.CreateInsertIntoSiteQuery(request);
        int siteId = (int) dbHelper.executeInsertQuery(queryInsertNewSite);
        return new NewSiteResponse(siteId);
    }

    public SitesResponse GetSites(Integer id) throws SQLException {
        String queryGetSites= dao.CreateGetSitesQuery(id);
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
                String provider1 = resultSet.getString("provider1");
                String provider2 = resultSet.getString("provider2");
                String provider3 = resultSet.getString("provider3");
                String provider4 = resultSet.getString("provider4");
                String cameras_link = resultSet.getString("cameras_link");

                list.add(new Site(id,ip,siteName,contact_person,contact_phone,lat,lon,description,provider1,provider2,provider3,provider4, cameras_link));
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
