package com.shemesh.solar.solutions.dao;

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
public class DataDao {
    SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd : HH:mm:ss");

    public String CreateInsertDataQuery(SendDataRequest request) {
        Date date = new Date(System.currentTimeMillis());

        return "INSERT INTO " + DATA_TABLE + " " +
                " (ip, volt,humidity,light, tmp, gateway, time) " +
                " VALUES (" +
                 "\'" + request.getIp() + "\'" +  "," +
                        request.getVolt() + "," +
                        request.getHumidity() + "," +
                        request.getLight() + "," +
                        request.getTmp() + ", " +
                        request.getGateway() + ", " +
                 "\'" + formatter.format(date) +"\'" + " ) ";
    }

    public String CreateGetDataQuery(String ip) {
        return "SELECT * " +
                " FROM " + DATA_TABLE + " " +
                "WHERE EXISTS (select ip from SolarSolutions.sites where '" + ip + "' in (ip, ip2))" +
                " ORDER BY time desc" +
                " LIMIT 50";
    }


}
