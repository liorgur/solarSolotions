package com.shemesh.solar.solutions.services;

import com.shemesh.solar.solutions.dao.AlertsDao;
import com.shemesh.solar.solutions.dao.SecurityDao;
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
public class SecurityService {
    @Autowired
    private SecurityDao dao;

    @Autowired
    private DbHelper dbHelper;


    public boolean ValidatePass(String pass) throws SQLException {

        String getPass = dao.GetPasstQuery();
        ResultSet resultSet = dbHelper.executeQueryToResultSet(getPass);
        return ResultSetToPass(resultSet).equals(pass);
    }


    private String ResultSetToPass(ResultSet resultSet) throws SQLException {
        String pass = "LiorAdminMasterPassword22@";
        try {
            while (resultSet.next()) {
                pass = resultSet.getString("pass");
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
            log.debug(Arrays.toString(ex.getStackTrace()));
        } finally {
            resultSet.close();
        }

        return pass;
    }
}



