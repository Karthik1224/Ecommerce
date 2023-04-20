package com.example.ecommerce;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class OrderList {
    private TableView<Order> orderTable;
    public VBox createTable(ObservableList<Order> data)
    {
        //columns

        TableColumn id = new TableColumn("ID");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn name = new TableColumn("NAME");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn quantity = new TableColumn("QUANTITY");
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        TableColumn order_status = new TableColumn("ORDER_STATUS");
        order_status.setCellValueFactory(new PropertyValueFactory<>("order_status"));



        orderTable = new TableView<>();
        orderTable.getColumns().addAll(id,name,quantity,order_status);
        orderTable.setItems(data);
        orderTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(20));
        vbox.getChildren().add(orderTable);
        return vbox;
    }

    public VBox getAllProducts()
    {
        ObservableList<Order> data = Order.getAllOrders();
        return createTable(data);

    }
}
