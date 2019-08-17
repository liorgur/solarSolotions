package com.xmed.services;

import com.xmed.dao.StatisticsDao;
import com.xmed.models.Requests.UserStatisticsRequest;
import com.xmed.models.Responses.UserStatisticsResponse;
import com.xmed.utils.DbHelper;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Lior Gur
 */

public class StatisticsService {
    @Autowired
    StatisticsDao dao;

    @Autowired
    DbHelper dbHelper;

    public UserStatisticsResponse getUserStatistics(UserStatisticsRequest request){
        try {
            ResultSet resultSet = dbHelper.executeQueryToResultSet(dao.getUserCorrectPercentageQuery(request));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null ; //todo

    }
}
