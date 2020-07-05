package com.shemesh.solar.solutions.services;

import com.shemesh.solar.solutions.cache.SiteCache;
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
    private SiteCache cache;

    @Autowired
    private DbHelper dbHelper;

    public NewSiteResponse CreateNewSite(CreateNewSiteRequest request) throws SQLException {
        String queryInsertNewSite = dao.CreateInsertIntoSiteQuery(request);
        int siteId = (int) dbHelper.executeInsertQuery(queryInsertNewSite);
        return new NewSiteResponse(siteId);
    }

    public SitesResponse GetSites() {

        return new SitesResponse(cache.GetSiteList());
//        String queryGetSites= dao.CreateGetSitesQuery(id);
//        ResultSet sites = dbHelper.executeQueryToResultSet(queryGetSites);
//        return ResultSetToSite(sites);
    }

    public void UpdateSite(UpdateSiteRequest updateSiteRequest) throws SQLException {
        String queryUpdateSite = dao.CreateUpdateSiteQuery(updateSiteRequest);
        dbHelper.executeQuery(queryUpdateSite);
    }

    public void UpdateSwitchStatus(int site_id, int switch_id, boolean status)throws SQLException {
        String queryUpdateSite = dao.UpdateSwitchStatus(site_id, switch_id, status);
        dbHelper.executeQuery(queryUpdateSite);
       cache.UpdateSiteCache();
    }

    public List<Site> ResultSetToSite(ResultSet resultSet) {
        List<Site> list = new ArrayList<>();
        try {
            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                String ip = resultSet.getString("ip");
                String ip2 = resultSet.getString("ip2");
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
                boolean switch1 = resultSet.getBoolean("switch1");
                boolean switch2 = resultSet.getBoolean("switch2");

                list.add(new Site(id, ip, ip2, siteName, contact_person, contact_phone, lat, lon, description, provider1, provider2, provider3, provider4, cameras_link, switch1, switch2));
            }
            return list;
        }
        catch (Exception ex) {
            log.error(ex.getMessage());
            log.debug(Arrays.toString(ex.getStackTrace())); //todo
        }
        return null;
    }


}
