package com.iurii.microservice.util.sql;

import com.iurii.microservice.util.dbtablemodels.UserTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public final class QueryExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(QueryExecutor.class);

    private QueryExecutor() {}

    public static void insertUser(Connection connection, String tableName, String id, String updatedTime,
                                  String birthDate, long money, String name) {
        String insertQuery = QueryProvider.insertUser(tableName, id, updatedTime, birthDate, money, name);
        PreparedStatement insertStatement = null;
        try {
            insertStatement = connection.prepareStatement(insertQuery);
            insertStatement.execute();
        } catch (SQLException e) {
            LOGGER.info("Failed to insert limit.", e);
        }
        closeStatement(insertStatement);
    }

    public static void deleteUser(Connection connection, String tableName, String userColumn, String user) {
        String deleteQuery = QueryProvider.deleteUser(tableName, userColumn, user);
        PreparedStatement deleteStatement = null;
        try {
            deleteStatement = connection.prepareStatement(deleteQuery);
            deleteStatement.execute();
        } catch (SQLException e) {
            LOGGER.info("Failed to remove limit.", e);
        }
        closeStatement(deleteStatement);
    }

    public static String selectUpdatedTime(Connection connection, String tableName, String idColumn, String id) {
        String query = QueryProvider.selectAll(tableName, idColumn, id);
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            LOGGER.info("Failed to select percentage.", e);
        }
        String updatedTime = getValueFromResultSet(resultSet, UserTable.UPDATED_TIME_COLUMN);
        closeResultSet(resultSet);
        closeStatement(statement);
        return updatedTime;
    }

    public static String selectBirthDate(Connection connection, String tableName, String idColumn, String id){
        String query = QueryProvider.selectAll(tableName, idColumn, id);
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            LOGGER.info("Failed to select credit amount.", e);
        }
        String birthDate = getValueFromResultSet(resultSet, UserTable.BIRTH_DATE_COLUMN);
        closeResultSet(resultSet);
        closeStatement(statement);
        return birthDate;
    }

    public static long selectMoney(Connection connection, String tableName, String idColumn, String id){
        String query = QueryProvider.selectAll(tableName, idColumn, id);
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
             statement = connection.prepareStatement(query);
             resultSet = statement.executeQuery();
        } catch (SQLException e) {
            LOGGER.info("Failed to select debit amount.", e);
        }
        long money = Long.parseLong(getValueFromResultSet(resultSet, UserTable.MONEY_COLUMN));
        closeResultSet(resultSet);
        closeStatement(statement);
        return money;
    }

    public static String selectName(Connection connection, String tableName, String idColumn, String id){
        String query = QueryProvider.selectAll(tableName, idColumn, id);
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            LOGGER.info("Failed to select debit amount.", e);
        }
        String name = getValueFromResultSet(resultSet, UserTable.NAME_COLUMN);
        closeResultSet(resultSet);
        closeStatement(statement);
        return name;
    }

    private static String getValueFromResultSet(ResultSet resultSet, String columnName) {
        String result = null;
        try {
            if (resultSet.next()) {
                result = resultSet.getString(columnName);
            }
        } catch (SQLException e) {
            LOGGER.info("Failed to get value from result set.", e);
        }
        return result;
    }

    private static void closeResultSet(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                LOGGER.info("Failed to close result set.", e);
            }
        }
    }
    private static void closeStatement(PreparedStatement statement) {
        if(statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.info("Failed to close statement.", e);
            }
        }
    }

}
