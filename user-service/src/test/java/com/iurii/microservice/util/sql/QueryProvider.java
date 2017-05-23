package com.iurii.microservice.util.sql;

import com.iurii.microservice.util.time.DateTimeConverter;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZonedDateTime;

public class QueryProvider {

    public static String selectAll(String tableName, String userColumn, String user) {
        return  new StringBuilder("SELECT * FROM ")
                .append(tableName)
                .append(" WHERE ")
                .append(userColumn)
                .append("='")
                .append(user)
                .append("'").toString();
    }

    public static String insertUser(String tableName, String id, ZonedDateTime updatedTime,
                                    LocalDate birthDate, long money, String name) {
        return new StringBuilder("INSERT INTO ")
                .append(tableName)
                .append(" VALUES('")
                .append(id)
                .append("','")
                .append(name)
                .append("','")
                .append(Date.valueOf(birthDate))
                .append("','")
                .append(DateTimeConverter.convertToDbFormat(updatedTime))
                .append("',")
                .append(money)
                .append(")").toString();
    }

    public static String deleteUser(String tableName, String userColumn, String id) {
        return new StringBuilder("DELETE FROM ")
                .append(tableName)
                .append(" WHERE ")
                .append(userColumn)
                .append("='")
                .append(id)
                .append("'").toString();
    }

}
