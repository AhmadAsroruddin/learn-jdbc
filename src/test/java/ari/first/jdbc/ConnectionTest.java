package ari.first.jdbc;

import org.junit.jupiter.api.BeforeAll;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionTest {
    @BeforeAll
    static void registerBeforeAll(){
        try{
            Driver mysqlDriver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(mysqlDriver);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    void testConnection(){

    }
}
