package com.lucass.registration.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Objects;
import java.util.Properties;

public class DbUtils {

    private static Connection connection;

    private static Connection init(){
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (InputStream is = DbUtils.class.getClass().getResourceAsStream("/db.properties")){
            Properties prop =  new Properties();
            prop.load(is);

            connection = DriverManager.getConnection(
                    prop.getProperty("jdbc"),
                    prop.getProperty("username"),
                    prop.getProperty("password"));
            return connection;
        } catch (SQLException e) {
            System.out.println("ERROR: Ошибка подключения к БД. " + e.getMessage());
        } catch (IOException e) {
            System.out.println("ERROR: Ошибка чтения конфигурационного файла. " + e.getMessage());
        }
        return null;
    }

    public static Connection getConnection(){
        return Objects.nonNull(connection)? connection: init();
    }

    public static void close(){
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("ERROR: Ошибка закрытия подключения к БД. " + e.getMessage());
        }
    }

    public static boolean execute(String sql){
        boolean result = false;
        try {
            Statement statement = connection.createStatement();
            result = statement.execute(sql);
            statement.close();
        } catch (SQLException e) {
            System.out.println("ERROR: Ошибка SQL запроса. " + e.getMessage());
        }
        return result;
    }

    public static int executeUpdate(String sql){
        int medicatedRows = 0;
        try {
            Statement statement = connection.createStatement();
            medicatedRows = statement.executeUpdate(sql);
            statement.close();
        } catch (SQLException e) {
            System.out.println("ERROR: Ошибка SQL запроса. " + e.getMessage());
        }
        return  medicatedRows;
    }

    public static ResultSet executeQuery(String sql){
        ResultSet rs = null;
        try {
            Statement statement = connection.createStatement();
            rs = statement.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("ERROR: Ошибка SQL запроса. " + e.getMessage());
        }
        return rs;
    }
}
