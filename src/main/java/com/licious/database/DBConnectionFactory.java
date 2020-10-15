package com.licious.database;

import java.sql.Connection;
import java.sql.DriverManager;


public class DBConnectionFactory {

    Connection con = null;

    public DBConnectionFactory(){
    }

    public Connection getConnection(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/licious_inventory?user=licious_admin&password=welcome1");
        }catch(Exception e){
            e.printStackTrace();
        }
        return con;
    }
}
