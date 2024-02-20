package com.first;

import javax.swing.plaf.nimbus.State;
import java.sql.*;

public class DbFunction {
    public Connection connect_to_db(String dbname, String username, String password){
        Connection conn = null;
        try{
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/"+dbname,username, password);

            if(conn != null){
                System.out.println("Connection Success");
            } else {
                System.out.println("Connection Failed");
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return conn;
    }

    public void createTable(Connection conn, String tableName){
        Statement statement;

        try{
            String query = String.format(
                    "CREATE TABLE %s ( " +
                            "id serial primary key, " +
                            "name varchar(100), " +
                            "birth_date date);",
                    tableName);
            statement = conn.createStatement();
            statement.executeUpdate(query);
            System.out.println("Table created");
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void insertData(Connection connection,String tableName, String name, Date birthDate){
        Statement statement;
        try{
            String query = String.format(
                    "INSERT INTO %s(name, birth_Date) values('%s', '%s');",
                    tableName, name, birthDate
            );
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("DATA INSERTED");
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void readData(Connection conn, String tableName){
        Statement statement;
        ResultSet data = null;

        try{
            String query  = String.format("select * from %s", tableName);
            statement = conn.createStatement();
            data = statement.executeQuery(query);
            ResultSetMetaData metadata = data.getMetaData();
            int columCount = metadata.getColumnCount();

            for(int i = 1; i <= columCount; i++){
                System.out.printf("%-20s", metadata.getColumnName(i));
            }
            System.out.println();


            while(data.next()){
                for(int i = 1; i<= columCount; i++){
                    System.out.printf("%20s", data.getString(i));
                }
            }

        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void updateName(Connection conn, String tableName, String newValue, String oldValue){
        Statement statement;
        try{
            String query = String.format("update %s set name = '%s' where name = '%s'",tableName, newValue,oldValue);
            statement = conn.createStatement();
            statement.executeUpdate(query);
            System.out.println("Update successfully");
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void searchData(Connection conn, String tableName, String keywordName){
        Statement statement;
        ResultSet resultSet;
        try{
            String query = String.format("select * from %s where name = '%s'", tableName, keywordName);
            statement = conn.createStatement();
            resultSet = statement.executeQuery(query);
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            for(int i = 1; i <= columnCount; i++){
                System.out.printf("%-20s", metaData.getColumnName(i));
            }
            System.out.println();
            while(resultSet.next()){
                for(int i = 1;i<=columnCount; i++){
                    System.out.printf("%-20s", resultSet.getString(i));
                }
            }
        } catch (Exception e){
            System.out.println(e);
        }
    }

    public void deleteData(Connection connection, String tableName, String keyWord){
        Statement statement;
        try{
            String query = String.format("delete from %s where name = '%s'", tableName, keyWord);
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("Deleted Data");
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
