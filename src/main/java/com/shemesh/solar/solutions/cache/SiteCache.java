package com.shemesh.solar.solutions.cache;

import com.shemesh.solar.solutions.dao.SiteDao;
import com.shemesh.solar.solutions.models.Objects.Site;
import com.shemesh.solar.solutions.services.SiteService;
import com.shemesh.solar.solutions.utils.DbHelper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
public class SiteCache {

    @Autowired
    private SiteDao dao;

    @Autowired
    private DbHelper dbHelper;

    @Autowired
    private SiteService service;

    private List<Site> siteList;
    private LocalDateTime cacheLastUpdate;


    public List<Site> GetSiteList() {
        if (siteList == null || LocalDateTime.now().minusMinutes(10).isAfter(cacheLastUpdate)){
            UpdateSiteCache();
        }
        return siteList;
    }


    public void UpdateSiteCache() {
        String queryGetSites = dao.CreateGetSitesQuery(null);
        try {
            ResultSet sites = dbHelper.executeQueryToResultSet(queryGetSites);
            siteList = service.ResultSetToSite(sites);
            cacheLastUpdate = LocalDateTime.now();

        } catch (Exception ex) {
            log.error("Error " + ex.getMessage());
        }

    }


}
