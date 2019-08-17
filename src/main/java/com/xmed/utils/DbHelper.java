package com.xmed.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbutils.DbUtils;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.List;

@Slf4j
@Component
public class DbHelper {

    private static String url = "jdbc:mysql://xmed-db.cvtziznvjjvi.eu-west-1.rds.amazonaws.com:3306/Xmed";
    private static String username = "admin";
    private static String password = "password";

    public ResultSet executeQueryToResultSet(String query) throws SQLException {
        Connection connection = getConnection();
        ResultSet resultSet = null;

        try {
            Statement stmt = connection.createStatement();
            resultSet = stmt.executeQuery(query);

        } catch (Exception se) {
            log.error("Couldn't query the database " + se.getMessage());
            log.debug("Couldn't query the database.", se);
            se.printStackTrace();
        }
        return resultSet;
    }

    public long executeInsertQuery(String query) throws SQLException {

        int affectedRows;
        long insertedId;
        Connection connection = getConnection();
        try {
            assert connection != null;
            PreparedStatement preparedStmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            affectedRows = preparedStmt.executeUpdate();

            if (affectedRows == 0) {
                log.debug("Insert query failed, no rows affected , query : " + query);
                throw new SQLException("Insert query failed, no rows affected.");
            }

            try (ResultSet generatedKeys = preparedStmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    insertedId = generatedKeys.getLong(1);
                } else {
                    log.debug("Insert query failed, no ID obtained , query : " + query);
                    throw new SQLException("Insert query failed, no ID obtained.");
                }
            }
        } catch (SQLException se) {
            log.error("Couldn't query the database " + se.getErrorCode());
            log.debug("Couldn't query the database.", se);
            throw new RuntimeException("Couldn't query the database.", se);
        } finally {
            DbUtils.closeQuietly(connection);
        }
        return insertedId;
    }

    public void executeQuery(String query) throws SQLException {
        Connection connection = getConnection();
        try {
            assert connection != null;
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.execute();
        } catch (SQLException se) {
            log.error("Couldn't query the database " + se.getErrorCode());
            log.debug("Couldn't query the database.", se);
            throw new RuntimeException("Couldn't query the database.", se);
        } finally {
            DbUtils.closeQuietly(connection);
        }
    }

    public void executeBatchQueries(List<String> queries) throws SQLException {
        Connection connection = getConnection();

        Statement stmt;
        try {
            stmt = connection.createStatement(
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            for (String query : queries) {
                stmt.addBatch(query);
                log.debug("batchQuery :" + query);
            }
            connection.setAutoCommit(false);

            stmt.executeBatch();
            connection.commit();
            log.debug("executeBatchQueries Done");


        } catch (SQLException e) {
            e.printStackTrace();
            log.error("Couldn't query the database " + e.getErrorCode());
            log.debug("Couldn't query the database.", e);
        }
    }

    private Connection getConnection() throws SQLException {
        Connection connection;
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw e;
        }
        return connection;
    }

}
