package ari.first.jdbc;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionTest {
    @BeforeAll
    static void registerBeforeAll(){
        try{
            Driver postgreDriver = new org.postgresql.Driver();
            DriverManager.registerDriver(postgreDriver);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Test
    void testConnection(){
        String jdbcUrl = "jdbc:postgresql://localhost:5432/db_warung_makan_bahari_2?serverTimeZone=Asia/Jakarta";
        String username = "postgres";
        String password = "Handball123";

        try(Connection conn = DriverManager.getConnection(jdbcUrl, username, password);){

            System.out.println("Connection Established");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
