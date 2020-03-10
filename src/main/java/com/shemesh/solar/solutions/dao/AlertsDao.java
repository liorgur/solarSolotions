package com.shemesh.solar.solutions.dao;

import com.shemesh.solar.solutions.models.Objects.Alert;
import com.shemesh.solar.solutions.models.Requests.SendDataRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.shemesh.solar.solutions.models.Objects.Tables.*;

/**
 * @author Lior Gur
 */
@Component
@Slf4j
public class AlertsDao {
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd : HH:mm:ss");

    public String CreateResetAlertQuery(int site_id) {
        return " UPDATE " + ALERTS_TABLE +
                " SET status = 0" +
                " WHERE site_id = " + site_id;
    }

    public String CreateGetAlertsQuery(Integer site_id) {
        String where = (site_id != null) ? "WHERE site_id = " + site_id : " ";
        String limit = " LIMIT " + ((site_id != null) ? " 10 " : " 20 ");

        return " SELECT * " +
                " FROM  " + ALERTS_TABLE + " " +
                where +
                limit ;
    }

    public String CreateAlertQuery(Alert alert) {
        Date date = new Date(System.currentTimeMillis());
        String site_idQuery = GetSiteIdQuery(alert.getIp());

        return "INSERT INTO " + ALERTS_TABLE + " " +
                " (type,value, site_id, time) " +
                " VALUES ('" + alert.getType() + "','" + alert.getValue() + "',(" + site_idQuery  + ",'"  + formatter.format(date) + "')";
    }

    private String GetSiteIdQuery(String ip){
        return "SELECT id from " + SITES_TABLE + " where ip = '" +ip + "')";
    }


}
