package com.xmed.Utils;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;

import java.sql.*;
import java.util.List;

public  class DbHelper {

  //static String url = "jdbc:mysql://10.0.0.20:3306";
    static String url = "jdbc:mysql://127.0.0.1:3306/Xmed";

  static String username = "admin";
  static  String password = "password";

    public static ResultSet executeQueryToResultSet(String query)  {
        ResultSet resultSet = null;
          try {
            Connection conn = DriverManager.getConnection(url, username, password);
            Statement stmt = conn.createStatement();
              resultSet = stmt.executeQuery(query);

          } catch (Exception e) {
            e.printStackTrace();
          }

          return resultSet;
    }

  public static long executeInsertQuery(String query) {

      int affectedRows;
      long insertedId;
    Connection connection = null;
    try {
      connection = DriverManager.getConnection(url, username, password);
    } catch (SQLException e) {
    }
    try {
      assert connection != null;
      PreparedStatement preparedStmt = connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
        affectedRows = preparedStmt.executeUpdate();

        if (affectedRows == 0) {
            throw new SQLException("Creating user failed, no rows affected.");
        }

        try (ResultSet generatedKeys = preparedStmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                insertedId = generatedKeys.getLong(1);
            }
            else {
                throw new SQLException("Creating user failed, no ID obtained.");
            }
        }
    } catch (SQLException se) {
      throw new RuntimeException("Couldn't query the database.", se);
    } finally {
      DbUtils.closeQuietly(connection);
    }
    return insertedId;
  }

    public static boolean executeQuery(String query) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
        }
        try {
            assert connection != null;
            PreparedStatement preparedStmt = connection.prepareStatement(query);
             preparedStmt.execute();
             return true;
        } catch (SQLException se) {
            throw new RuntimeException("Couldn't query the database.", se);
        } finally {
            DbUtils.closeQuietly(connection);
        }
    }


    public static void executeBatchQueries(List<String> queries) {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
        }
        Statement stmt = null;
        try {
            stmt = connection.createStatement(
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            for (String query: queries) {
                stmt.addBatch(query);
            }

            connection.setAutoCommit(false);

            stmt.executeBatch();
            connection.commit();

            System.out.println("Batch executed");

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

}
