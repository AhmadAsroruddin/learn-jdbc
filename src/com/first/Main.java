package com.first;

import java.sql.Connection;
import java.sql.Date;

public class Main {
    public static void main(String[] args) {
        DbFunction dbFunction = new DbFunction();

        Connection conn = dbFunction.connect_to_db("mydatabase", "postgres", "Handball123");
//        dbFunction.createTable(conn, "dummy_table");
//        dbFunction.insertData(conn, "dummy_table", "Sonia", Date.valueOf("2001-09-14"));
//        dbFunction.readData(conn, "dummy_table");
        //dbFunction.updateName(conn, "dummy_table", "Ari", "Dummy");
//        dbFunction.searchData(conn, "dummy_table", "Ari");

        dbFunction.deleteData(conn, "dummy_table", "Sonia");
        dbFunction.readData(conn, "dummy_table");
    }


}
