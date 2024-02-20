package ari.first.jdbc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import first.Util;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.*;

public class ConnectionPoolTest {

    @BeforeAll
    static void hikariCPConnectionTest(){
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/mydatabase?serverTimeZone=Asia/Jakarta");
        config.setUsername("postgres");
        config.setPassword("Handball123");

        config.setMaximumPoolSize(10);
        config.setMinimumIdle(5);
        config.setIdleTimeout(60_000);
        config.setMaxLifetime(10*60_000);

        try {
            HikariDataSource dataSource = new HikariDataSource();
            Connection conn = dataSource.getConnection();
            conn.close();
            dataSource.close();
        } catch (Exception e){
            System.out.println(e);
        }

    }

    @Test
    void utilTest() throws Exception{
        Connection connection = Util.getDataSource().getConnection();
    }

    @Test
    void insertDateTest() throws SQLException {
        Connection conn = Util.getDataSource().getConnection();
        Statement statement = conn.createStatement();

        String query = """
                    INSERT INTO dummy_table(name, birth_date) values('Ahmad', '2001-08-17')
                """;
        statement.executeUpdate(query);

        statement.close();
        conn.close();
    }

    @Test
    void showTableData() throws SQLException {
        Connection conn = Util.getDataSource().getConnection();
        Statement statement = conn.createStatement();
        PreparedStatement preparedStatement = null;

        String query = """
                    SELECT * from dummy_table where id = ?
                """;

        preparedStatement = conn.prepareStatement(query);
        preparedStatement.setInt(1,6);
        ResultSet resultSet = preparedStatement.executeQuery();
        ResultSetMetaData metaData = preparedStatement.getMetaData();
        int column = metaData.getColumnCount();

        for(int i = 1; i<=column; i++){
            System.out.printf("%-20s", metaData.getColumnName(i));
        }
        System.out.println();
        while(resultSet.next()){
            for(int i =1; i<=column; i++){
                System.out.printf("%-20s", resultSet.getString(i));

            }
            System.out.println();
        }
    }
}
