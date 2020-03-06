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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
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


    public void CreateAlert(Alert alert) throws SQLException {
        String queryCreateAlert = dao.CreateAlertQuery(alert);
        dbHelper.executeQuery(queryCreateAlert);
    }

    public void CreateAlertIfNeeded(SendDataRequest request) throws SQLException {
        Timestamp date = new Timestamp(System.currentTimeMillis());

        if (request.getTmp() > 100) //todo
            CreateAlert(new Alert(0,request.getIp(), date , AlertType.TMP, request.getTmp(),false));
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
                int site_id = resultSet.getInt("site_id");
                boolean status = resultSet.getBoolean("status");
                float value = resultSet.getFloat("value");

                list.add(new Alert(site_id,"", time, AlertType.valueOf(type), value, status));
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
            log.debug(Arrays.toString(ex.getStackTrace())); //todo
        }
        return new AlertResponse(list);

    }
}



