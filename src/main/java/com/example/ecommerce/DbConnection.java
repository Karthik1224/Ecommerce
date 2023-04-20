package com.example.ecommerce;
import java.sql.*;
public class DbConnection {
    private final String url = "jdbc:mysql://localhost:3306/ecommerce";
    private final String userName = "root";
    private final String password = "Karthik22";

    private Statement getStatement()
    {
        try
        {
            Connection connection = DriverManager.getConnection(url,userName,password);
            return connection.createStatement();
        }
        catch(Exception e)
        {
            e.getStackTrace();
        }
        return null;
    }
    public ResultSet getQueryTable(String query)
    {
        try
        {
            Statement statement = getStatement();
            return statement.executeQuery(query);
        }
        catch(Exception e)
        {
            e.getStackTrace();
        }
        return null;

    }
    public int updateDB(String query)
    {
        try
        {
            Statement statement = getStatement();
            return statement.executeUpdate(query);
        }
        catch(Exception e)
        {
            e.getStackTrace();
        }
        return 0;
    }

    public static void main(String[] args) {
        DbConnection conn = new DbConnection();
        ResultSet rs = conn.getQueryTable("SELECT * FROM customer");
        if(rs != null)
        {
            System.out.println("Connection successful");

        }
        else {
            System.out.println("Connection Failed");
        }
    }
}
