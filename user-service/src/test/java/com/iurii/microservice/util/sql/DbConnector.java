package com.iurii.microservice.util.sql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DbConnector {

    private static final Logger LOGGER = LoggerFactory.getLogger(DbConnector.class);

    private DbConnector() {}

    public static Connection createDbConnection(String dbDriver, String dbUrl, String dbUser, String dbPassword ) {
        Connection connection = null;
        try {
            Class.forName(dbDriver);
        } catch (ClassNotFoundException e) {
            LOGGER.info("Connection creation failed. Class was not found.", e);
        }
        try {
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        } catch (SQLException e) {
            LOGGER.info("Connection creation failed.", e);
        }
        return connection;
    }

    public static void closeConnection(Connection connection) {
        if(connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.info("Failed to close connection.", e);
            }
        }
    }

}
