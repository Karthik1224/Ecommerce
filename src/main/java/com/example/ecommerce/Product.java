package com.example.ecommerce;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;

public class Product {
    private SimpleIntegerProperty id;
    private SimpleStringProperty name;
    private SimpleDoubleProperty price;

    public Product(int id, String name,double price) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
    }
    public static ObservableList<Product> getAllProducts()
    {
        String query = "SELECT id,name,price FROM product";
        return fetchProduct(query);
    }
    public static ObservableList<Product> fetchProduct(String query)
    {
        ObservableList<Product> data = FXCollections.observableArrayList();
        DbConnection conn = new DbConnection();
        ResultSet rs = conn.getQueryTable(query);
        try
        {
             while(rs.next())
             {
                 Product product = new Product(rs.getInt("id"),rs.getString("name"),rs.getDouble("price"));
                 data.add(product);
             }
             return data;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }


    public int getId() {
        return id.get();
    }
    public String getName() {
        return name.get();
    }
    public double getPrice() {
        return price.get();
    }
}
