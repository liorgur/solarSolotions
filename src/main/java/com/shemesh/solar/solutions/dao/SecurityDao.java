package com.shemesh.solar.solutions.dao;

import com.shemesh.solar.solutions.models.Enums.AlertType;
import com.shemesh.solar.solutions.models.Objects.Alert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.shemesh.solar.solutions.models.Objects.Tables.SECURITY_TABLE;

/**
 * @author Lior Gur
 */
@Component
@Slf4j
public class SecurityDao {


    public String GetPasstQuery() {
        return " SELECT pass FROM " + SECURITY_TABLE;

    }



}
