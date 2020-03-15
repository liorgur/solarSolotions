package com.shemesh.solar.solutions.services;

import com.shemesh.solar.solutions.dao.AlertsDao;
import com.shemesh.solar.solutions.models.Enums.AlertType;
import com.shemesh.solar.solutions.models.Objects.Alert;
import com.shemesh.solar.solutions.models.Requests.SendDataRequest;
import com.shemesh.solar.solutions.models.Responses.AlertResponse;
import com.shemesh.solar.solutions.utils.DbHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class AlertsService {
    @Autowired
    private AlertsDao dao;

    @Autowired
    private DbHelper dbHelper;

    private Date lastTmpAlert;
    private Date lastVoltAlert;


    public void CreateAlert(Alert alert) throws SQLException {
        String queryCreateAlert = dao.CreateAlertQuery(alert);
        dbHelper.executeQuery(queryCreateAlert);
    }

    public void SetAlertStatus(SendDataRequest request) throws SQLException {
        Timestamp date = new Timestamp(System.currentTimeMillis());
        if ((request.getVolt() < 23 || request.getVolt() > 30)) {
            String queryUpdateStatusTrue = dao.UpdateAlertStatusTrue(request.getIp(),request.getVolt(), AlertType.VOLT);
            dbHelper.executeQuery(queryUpdateStatusTrue);
        } else {
            String updateAlertStatus = dao.UpdateAlertStatus(request.getIp(), AlertType.VOLT);
            dbHelper.executeQuery(updateAlertStatus);
        }
        if (request.getTmp() > 40 || request.getTmp() < 0) {
            String queryUpdateStatusTrue = dao.UpdateAlertStatusTrue(request.getIp(),request.getTmp(), AlertType.TMP);
            dbHelper.executeQuery(queryUpdateStatusTrue);
        } else {
            String updateAlertStatus = dao.UpdateAlertStatus(request.getIp(), AlertType.TMP);
            dbHelper.executeQuery(updateAlertStatus);        }

    }

    public AlertResponse GetAlerts(Integer site_id) throws SQLException {

        String queryInsertData = dao.CreateGetAlertsQuery(site_id);
        ResultSet resultSet = dbHelper.executeQueryToResultSet(queryInsertData);
        return ResultSetToAlert(resultSet);
    }

    public void resetAlert(int alertId) throws SQLException {
        String queryInsertData = dao.CreateResetAlertQuery(alertId);
        dbHelper.executeQuery(queryInsertData);
    }

    private AlertResponse ResultSetToAlert(ResultSet resultSet) {
        List<Alert> list = new ArrayList<>();
        try {
            while (resultSet.next()) {
                String type = resultSet.getString("type");
                Timestamp time = resultSet.getTimestamp("time");
                String name = resultSet.getString("name");
                int status = resultSet.getInt("status");
                float value = resultSet.getFloat("value");

                list.add(new Alert(name, "", time, AlertType.valueOf(type), value, status));
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
            log.debug(Arrays.toString(ex.getStackTrace())); //todo
        }
        return new AlertResponse(list);

    }
}



