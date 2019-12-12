package com.shemesh.dao;

import com.shemesh.models.Requests.SendDataRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.shemesh.models.Objects.Tables.DATA_TABLE;

/**
 * @author Dan Feldman
 */
@Component
@Slf4j
public class DataDao {
    SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd : HH:mm:ss");

    public String CreateInsertDataQuery(SendDataRequest request) {
        Date date = new Date(System.currentTimeMillis());

        return "INSERT INTO " + DATA_TABLE + " " +
                " (ip, volt,humidity,light,tmp, time) " +
                " VALUES (" +
                 "\'" + request.getIp() + "\'" +  "," +
                        request.getVolt() + "," +
                        request.getHumidity() + "," +
                        request.getLight() + "," +
                        request.getTmp() + ", " +
                 "\'" + formatter.format(date) +"\'" + " ) ";
    }

    public String CreateGetDataQuery(String ip) {
        return "SELECT * " +
                " FROM " + DATA_TABLE + " " +
                " WHERE ip = '" + ip + "'" +
                " LIMIT 50";
    }
}
