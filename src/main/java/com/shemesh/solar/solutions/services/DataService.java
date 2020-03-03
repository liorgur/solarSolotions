package com.shemesh.solar.solutions.services;

import com.shemesh.solar.solutions.dao.DataDao;
import com.shemesh.solar.solutions.models.Objects.DataPoint;
import com.shemesh.solar.solutions.models.Requests.SendDataRequest;
import com.shemesh.solar.solutions.models.Responses.DataResponse;
import com.shemesh.solar.solutions.utils.DbHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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

    @Autowired
    private AlertsService alertsService;

    public void SendData(SendDataRequest request) throws SQLException {
        String queryInsertData = dao.CreateInsertDataQuery(request);
        dbHelper.executeQuery(queryInsertData);
        alertsService.CreateAlertIfNeeded(request);
    }

    public DataResponse GetData(String ip) throws SQLException {
        String queryInsertData = dao.CreateGetDataQuery(ip);
        ResultSet resultSet = dbHelper.executeQueryToResultSet(queryInsertData);
        return ResultSetToData(resultSet);
    }

    private DataResponse ResultSetToData(ResultSet resultSet) {
        List<DataPoint> list = new ArrayList<>();
        try {
            while (resultSet.next()) {
                String ip = resultSet.getString("ip");
                float volt = resultSet.getFloat("volt");
                float humidity = resultSet.getFloat("humidity");
                float tmp = resultSet.getFloat("tmp");
                float light = resultSet.getFloat("light");
                Timestamp time = resultSet.getTimestamp("time");

                list.add(new DataPoint(ip, time, tmp, humidity, volt, light));
            }
            return new DataResponse(list);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            log.debug(Arrays.toString(ex.getStackTrace())); //todo
        }
        return null;
    }


}
