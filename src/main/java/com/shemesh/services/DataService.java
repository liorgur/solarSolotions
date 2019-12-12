package com.shemesh.services;

import com.shemesh.dao.DataDao;
import com.shemesh.models.Objects.Data;
import com.shemesh.models.Requests.SendDataRequest;
import com.shemesh.models.Responses.DataResponse;
import com.shemesh.utils.DbHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
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

    public DataResponse GetData(String ip)throws SQLException {
        String queryInsertData = dao.CreateGetDataQuery(ip);

        ResultSet resultSet = dbHelper.executeQueryToResultSet(queryInsertData);
        DataResponse dataResponse = ResultSetToSite(resultSet);
        return dataResponse;
    }

    private DataResponse ResultSetToSite(ResultSet resultSet) {

        List<Data> list = new ArrayList<>();

        try {
            while (resultSet.next()) {


                String ip = resultSet.getString("ip");
                float volt = resultSet.getFloat("volt");
                float humidity = resultSet.getFloat("humidity");
                float tmp = resultSet.getFloat("tmp");
                float light = resultSet.getFloat("light");
                Timestamp time = resultSet.getTimestamp("time");

                list.add(new Data(ip,time,tmp,humidity,volt,light));
            }
            return new DataResponse(list);
        }
        catch (Exception ex) {
            log.error(ex.getMessage());
            log.debug(Arrays.toString(ex.getStackTrace())); //todo
        }
        return null;
    }





}
