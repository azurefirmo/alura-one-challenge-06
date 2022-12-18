package br.com.alura.hotel.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDb {
    public Connection databaseLink;

    public Connection getDBConnection() {
        try {
            String url = "jdbc:sqlite:db/alura_hotel.db";
            this.databaseLink = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return databaseLink;
    }
}
