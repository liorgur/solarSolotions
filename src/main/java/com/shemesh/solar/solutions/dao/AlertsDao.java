package com.shemesh.solar.solutions.dao;

import com.shemesh.solar.solutions.models.Enums.AlertType;
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

    public String UpdateAlertStatusTrue(String ip, float value, AlertType type) {
        Date date = new Date(System.currentTimeMillis());

        return " UPDATE " + ALERTS_TABLE +
                " SET status = 0, time ='" +   formatter.format(date) + "' , value = " + value + " " +
                " WHERE site_id =  (select id from "+SITES_TABLE+" where ip = '" + ip+  "')" + " and `type` = '" +  type + "'";
    }

    public String UpdateAlertStatus(String ip, AlertType type) {
        return " UPDATE " + ALERTS_TABLE +
                " SET status = status +1" +
                " " +
                " WHERE site_id =  (select id from "+SITES_TABLE+" where ip = '" + ip+  "')" + " and `type` = '" +  type + "' and status < 5";
    }


        public String CreateGetAlertsQuery(Integer site_id) {
        String query = " SELECT time, name, type, value, status " +
                " FROM " + ALERTS_TABLE+ " a inner join " + SITES_TABLE + " s on a.site_id = s.id " +
                " WHERE status < 5 "  + ((site_id != null) ? "and site_id = " + site_id : " ") ;
        return query;
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
