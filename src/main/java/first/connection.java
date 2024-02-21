package first;

import java.sql.*;

public class connection {
    static PreparedStatement preparedStatement = null;
    public static void main(String[] args) {
        transaction();
    }

    static void delete(Connection connection) throws SQLException {
        try{
            String query = """
                    delete from m_customer where id=?
                """;
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, 11);

            preparedStatement.executeUpdate();
        }catch (SQLException e){
            throw e;
        }


    }
    static void update(){
        try(Connection conn = Util.getDataSource().getConnection()){
            String query = """
                    update m_discount set pct = ? where id = ?
                """;
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1,15);
            preparedStatement.setInt(2, 2);

            preparedStatement.executeUpdate();
        }catch (SQLException e){
            System.out.println(e);
        }
    }
    static void showTableData() {
        PreparedStatement preparedStatement = null;

        try (Connection conn = Util.getDataSource().getConnection()){

            String tableName = "m_customer";
            String query = """
                    SELECT * from
                """ + tableName;

            preparedStatement = conn.prepareStatement(query);

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
            System.out.println(e);
        }
    }

    public static void insertDateTest(int id, String name, String mobile, boolean ismember) throws SQLException {
        PreparedStatement preparedStatement = null;
        try (Connection conn = Util.getDataSource().getConnection()){
            String query = """
                    INSERT INTO m_customer(id,customer_nameff, mobile_phone_no,is_member) values(?,?, ?,?)
                """;
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1,id);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, mobile);
            preparedStatement.setBoolean(4, ismember);

            preparedStatement.executeUpdate();
        }catch (SQLException e){
            throw e;
        }

    }

    public static void userLogin(String username, String password){
        PreparedStatement preparedStatement = null;
        try(Connection conn = Util.getDataSource().getConnection()){
            String query = """
                    SELECT * FROM m_customer_credential WHERE username = ? AND password = ?
            """;

            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet set = preparedStatement.executeQuery();
            if(set.next()){
                System.out.println(set.getString("username"));
            } else {
                System.out.println("Gagal");
            }

        }catch (Exception e){
            System.out.println(e);
        }
    }

    public static void transaction()  {
        Connection conn = null;
        try{
            conn = Util.getDataSource().getConnection();
            conn.setAutoCommit(false);
            insertDateTest(9,"kbsfkdjf", "829384237",false);
            delete(conn);

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
 }