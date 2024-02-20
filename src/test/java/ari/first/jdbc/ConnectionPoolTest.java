package ari.first.jdbc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import first.Util;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.extension.TestWatcher;

import java.sql.*;

public class ConnectionPoolTest {
    private static Connection conn;

    @BeforeEach
    void setUp()throws Exception{
        conn = Util.getDataSource().getConnection();

        conn.setAutoCommit(false);
    }

    @AfterEach
    void tearDown() throws SQLException{
        try {
            // Commit the transaction if all tests passed
            conn.commit();
        } catch (Exception e){
            conn.rollback();
            throw e;
        }
        finally {
            // Close the connection
            conn.close();
        }
    }
    @Test
    void hikariCPConnectionTest(){
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/mydatabase?serverTimeZone=Asia/Jakarta");
        config.setUsername("postgres");
        config.setPassword("Hkkii23");

        config.setMaximumPoolSize(10);
        config.setMinimumIdle(5);
        config.setIdleTimeout(60_000);
        config.setMaxLifetime(10*60_000);

        try {
            HikariDataSource dataSource = new HikariDataSource();
            conn = dataSource.getConnection();
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
    void updateTest(){
        PreparedStatement preparedStatement = null;

        try{
            String query = """
                    update dummy_table set name = ? where id = ?
                """;
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1,"AIUSRA");
            preparedStatement.setInt(2, 14);

            preparedStatement.executeUpdate();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    @Test
    void insertDateTest() throws SQLException {
        PreparedStatement preparedStatement = null;
        try {
            String query = """
                    INSERT INTO dummy_table(name, birth_date) values(?, ?)
                """;
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, "KoaSla");
            preparedStatement.setDate(2, Date.valueOf("2022-09-19"));

            preparedStatement.executeUpdate();
        }catch (Exception e){
            throw e;
        }

    }
    @Test
    void transaction(){

        try{
            conn.setAutoCommit(false);
            insertDateTest();
            delete();

            conn.commit();
        } catch (SQLException e) {
            if(conn != null){
                try{
                    System.out.println("Transaction Being Roll Back");
                    conn.rollback();
                }catch (Exception ex){
                    System.out.println(ex);
                }
            }
        }
    }

    @Test
    void delete() throws SQLException {
        PreparedStatement preparedStatement = null;
        String query = """
                    delete from dummy_table where id=?
                """;
        preparedStatement = conn.prepareStatement(query);
        preparedStatement.setInt(1, 12);

        preparedStatement.executeUpdate();

    }

    @Test
    void showTableData() throws SQLException {
        PreparedStatement preparedStatement = null;
        try {


            String query = """
                    SELECT * from dummy_table where id = ?
                """;

            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, 13);
            ResultSet resultSet = preparedStatement.executeQuery();
            ResultSetMetaData metaData = preparedStatement.getMetaData();
            int column = metaData.getColumnCount();

            for (int i = 1; i <= column; i++) {
                System.out.printf("%-20s", metaData.getColumnName(i));
            }
            System.out.println();
            while (resultSet.next()) {
                for (int i = 1; i <= column; i++) {
                    System.out.printf("%-20s", resultSet.getString(i));

                }
                System.out.println();
            }
        }catch (Exception e){
            throw e;
        }
    }

    @RegisterExtension
    static TestWatcher watchman = new TestWatcher() {
        @Override
        public void testFailed(ExtensionContext context, Throwable cause) {
            // Rollback the transaction if any test fails
            ConnectionPoolTest testInstance = (ConnectionPoolTest) context.getTestInstance().get();
            try {
                testInstance.conn.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    };

    @Test
    void insertBatchData(){
        PreparedStatement preparedStatement = null;
        try {
            String query = """
                    INSERT INTO dummy_table(name, birth_date) values(?, ?)
                """;
            preparedStatement = conn.prepareStatement(query);
            for(int i = 1; i <= 1000; i++){
                preparedStatement.setString(1, "Koala "+i);
                preparedStatement.setDate(2, Date.valueOf("2022-09-19"));
                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
