package com.shemesh.solar.solutions.services;

import com.shemesh.solar.solutions.dao.AlertsDao;
import com.shemesh.solar.solutions.dao.DataDao;
import com.shemesh.solar.solutions.models.Enums.AlertType;
import com.shemesh.solar.solutions.models.Objects.Alert;
import com.shemesh.solar.solutions.models.Requests.SendDataRequest;
import com.shemesh.solar.solutions.models.Responses.AlertResponse;
import com.shemesh.solar.solutions.utils.DbHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Service
@Slf4j
public class AlertsService {
    @Autowired
    private AlertsDao dao;

    @Autowired
    private DbHelper dbHelper;

    public void CreateAlert(Alert alert) throws SQLException {
        String queryCreateAlert = dao.CreateAlertQuery(alert);
        dbHelper.executeQuery(queryCreateAlert);
    }

    public void CreateAlertIfNeeded(SendDataRequest request) throws SQLException {
        if (request.getTmp() > 100) //todo
            CreateAlert(new Alert(request.getIp(), LocalDateTime.now(), AlertType.TMP, request.getTmp()));
    }

    public AlertResponse GetAlerts(String ip) throws SQLException {
        String queryInsertData = dao.CreateGetAlertsQuery(ip);
        ResultSet resultSet = dbHelper.executeQueryToResultSet(queryInsertData);
        return ResultSetToAlert(resultSet);
    }

    public void resetAlert(int alertId) throws SQLException {
        String queryInsertData = dao.CreateResetAlertQuery(alertId);
        dbHelper.executeQuery(queryInsertData);
    }

    private AlertResponse ResultSetToAlert(ResultSet resultSet) {
//        List<Alert> list = new ArrayList<>();
//        try {
//            while (resultSet.next()) {
//                String ip = resultSet.getString("ip");
//                float volt = resultSet.getFloat("volt");
//                float humidity = resultSet.getFloat("humidity");
//                float tmp = resultSet.getFloat("tmp");
//                float light = resultSet.getFloat("light");
//                Timestamp time = resultSet.getTimestamp("time");
//
//                list.add(new Alert(ip, time, tmp, humidity, volt, light));
//            }
////            return new AlertResponse(list);
//        } catch (Exception ex) {
//            log.error(ex.getMessage());
//            log.debug(Arrays.toString(ex.getStackTrace())); //todo
//        }
        return null;
    }


}
