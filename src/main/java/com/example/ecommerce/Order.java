package com.example.ecommerce;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;

public class Order {

    private SimpleIntegerProperty id;
    private SimpleStringProperty name;
    private SimpleStringProperty order_status;
    private SimpleIntegerProperty quantity;


    public Order(int id, String name, String order_status, int quantity) {
        this.id = new SimpleIntegerProperty(id);
        this.name =new SimpleStringProperty(name);
        this.order_status =new SimpleStringProperty(order_status);
        this.quantity =new SimpleIntegerProperty(quantity);

    }

    public int getId() {
        return id.get();
    }


    public String getName() {
        return name.get();
    }


    public String getOrder_status() {
        return order_status.get();
    }


    public int getQuantity() {
        return quantity.get();
    }

    public static ObservableList<Order> getAllOrders()
    {
        String query = "SELECT orders.id,product.name,orders.order_status, orders.quantity FROM orders join product on orders.product_id=product.id";
        return fetchProduct(query);
    }
    public static ObservableList<Order> fetchProduct(String query)
    {
        ObservableList<Order> data = FXCollections.observableArrayList();
        DbConnection conn = new DbConnection();
        ResultSet rs = conn.getQueryTable(query);
        try
        {
            while(rs.next())
            {
                Order order = new Order(rs.getInt("id"),rs.getString("name"),rs.getString("order_status"),rs.getInt("quantity"));
                data.add(order);
            }
            return data;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    public static boolean placeOrder(Customer customer, Product product)
    {
        String groupOrderId = "SELECT max(group_order_id) +1 id FROM orders";
        DbConnection conn = new DbConnection();
        ResultSet rs = conn.getQueryTable(groupOrderId);
        try
        {
            if(rs.next())
            {
                String query = "INSERT INTO orders(group_order_id, customer_id, product_id)VALUES("+rs.getInt("id")+","+customer.getId()+","+product.getId()+")";
                return conn.updateDB(query) !=0;
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public static int placeMultipleOrder(Customer customer, ObservableList<Product>productList)
    {
        String groupOrderId = "SELECT max(group_order_id) +1 id FROM orders";
        DbConnection conn = new DbConnection();
        ResultSet rs = conn.getQueryTable(groupOrderId);
        try
        {
            int count=0;
            if(rs.next())
            {
                for(Product product:productList)
                {
                    String query = "INSERT INTO orders(group_order_id, customer_id, product_id)VALUES("+rs.getInt("id")+","+customer.getId()+","+product.getId()+")";
                    count += conn.updateDB(query);
                }
                return count;
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return 0;
    }
}
