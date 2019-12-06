package com.shemesh.services;

import com.shemesh.dao.DataDao;
import com.shemesh.dao.SiteDao;
import com.shemesh.models.Objects.Site;
import com.shemesh.models.Requests.CreateNewSiteRequest;
import com.shemesh.models.Requests.SendDataRequest;
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
public class DataService {
    @Autowired
    private DataDao dao;

    @Autowired
    private DbHelper dbHelper;

    public void SendData(SendDataRequest request) throws SQLException {

        String queryInsertData = dao.CreateInsertDataQuery(request);

       dbHelper.executeQuery(queryInsertData);

    }




}
