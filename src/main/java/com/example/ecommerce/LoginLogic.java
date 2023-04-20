package com.example.ecommerce;

import java.sql.ResultSet;

public class LoginLogic {
    public Customer customerLogic(String username ,String password)
    {
        String loginQuery =" SELECT * FROM customer WHERE email='"+username+"' AND password ='"+password+"'";
        DbConnection conn = new DbConnection();
        ResultSet rs = conn.getQueryTable(loginQuery);
        try
        {
            if(rs.next())
            {
                return new Customer(rs.getInt("id"),rs.getString("name"),rs.getString("email"),rs.getString("mobile"));
            }

        }
        catch(Exception e)
        {
            e.getStackTrace();
        }
       return null;
    }

    public static void main(String[] args) {
        LoginLogic login = new LoginLogic();
        Customer customer = login.customerLogic("karthikd20009@gmail.com","abcd");
        System.out.println("welcome :"+ customer.getName());
    }
}
