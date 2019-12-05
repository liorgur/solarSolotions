package com.shemesh.services;

import com.shemesh.dao.SiteDao;
import com.shemesh.models.Requests.CreateNewSiteRequest;
import com.shemesh.models.Responses.NewSiteResponse;
import com.shemesh.utils.DbHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
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

}
