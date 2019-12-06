package com.shemesh.dao;

import com.shemesh.models.Requests.SendDataRequest;
import com.shemesh.models.Requests.UpdateSiteRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.shemesh.models.Objects.Tables.DATA_TABLE;
import static com.shemesh.models.Objects.Tables.SITES_TABLE;

/**
 * @author Dan Feldman
 */
@Component
@Slf4j
public class DataDao {

    public String CreateInsertDataQuery(SendDataRequest request) {
        return "INSERT INTO " + DATA_TABLE + " " +
                " (ip, volt,humidity,light,tmp) " +
                " VALUES (" + request.getIp() + "," + request.getVolt() + ","  +request.getHumidity()+ "," +request.getLight()+ "," +request.getTmp() + " ) ";
    }
}
