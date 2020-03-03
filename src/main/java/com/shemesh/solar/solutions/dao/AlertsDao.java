package com.shemesh.solar.solutions.dao;

import com.shemesh.solar.solutions.models.Objects.Alert;
import com.shemesh.solar.solutions.models.Requests.SendDataRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.shemesh.solar.solutions.models.Objects.Tables.DATA_TABLE;

/**
 * @author Lior Gur
 */
@Component
@Slf4j
public class AlertsDao {
    SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd : HH:mm:ss");

    public String CreateResetAlertQuery(int id)
    {
        //todo create query
        return null;
    }

    public String CreateGetAlertsQuery(String ip) {
        //todo create query
        return null;
    }

    public String CreateAlertQuery(Alert alert) {
        //todo create query
        return null;
    }



}
