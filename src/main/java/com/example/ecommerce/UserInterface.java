package com.example.ecommerce;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

public class UserInterface {
    GridPane loginPage;
    HBox headerBar;
    HBox footerBar;
    VBox body;
    Button signBtn;
    Label welcomeUser;
    Label labelText;
    ProductList productList = new ProductList();
    OrderList orderList = new OrderList();
    VBox productPage;
    VBox OrdersPage;
    Button placeOrderButton = new Button("Place Order");

    ObservableList<Product>itemsInCart = FXCollections.observableArrayList();
    Customer loggedInCustomer;
    public BorderPane createContent()
    {
        BorderPane root = new BorderPane();
        root.setPrefSize(800,600);
       // root.setCenter(loginPage);
        root.setTop(headerBar);
        body = new VBox();
        body.setPadding(new Insets(10));
        body.setAlignment(Pos.CENTER);
        root.setCenter(body);
        productPage=productList.getAllProducts();
        OrdersPage = orderList.getAllProducts();
       // root.setCenter(productPage);
        body.getChildren().add(productPage);

        root.setBottom(footerBar);
        return root;
    }
    public UserInterface()
    {
        createLoginPage();
        createHeaderBar();
        createFooterBar();
    }
    private void createLoginPage() {
        Text userNameText = new Text("User Name");
        Text passwordText = new Text("Password");

        TextField userName = new TextField("karthikd20009@gmail.com");
        userName.setPromptText("Type your user name");

        TextField password = new TextField();
        password.setText("abcd");
        password.setPromptText("Type your password");
        Button loginBtn = new Button("Login");
        labelText = new Label("hi");
        loginPage = new GridPane();
        loginPage.setAlignment(Pos.CENTER);
        loginPage.setHgap(10);
        loginPage.setVgap(10);
        loginPage.add(userNameText,0,0);
        loginPage.add(userName,1,0);
        loginPage.add(passwordText,0,1);
        loginPage.add(password,1,1);
        loginPage.add(loginBtn,1,2);
        loginPage.add(labelText,0,2);

        loginBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {


                String name = userName.getText();
                String pass = password.getText();
                LoginLogic login = new LoginLogic();
                loggedInCustomer=login.customerLogic(name,pass);
                if(loggedInCustomer != null)
                {
                    labelText.setText("Welcome :"+loggedInCustomer.getName());
                    welcomeUser.setText("Welcome-"+loggedInCustomer.getName());
                    headerBar.getChildren().add(welcomeUser);
                    body.getChildren().clear();
                    body.getChildren().add(productPage);

                }
                else {
                    labelText.setText("Login Failed !! provide correct email or password");
                }

            }
        });
    }

    private void createHeaderBar()
    {


        Button homebtn = new Button();
        Image image = new Image("C:\\Users\\User\\Desktop\\Java\\E-Commerce\\src\\img.png");
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setFitWidth(40);
        imageView.setFitHeight(20);
        homebtn.setGraphic(imageView);


        TextField searchBar = new TextField();
        searchBar.setPromptText("Search");
        searchBar.setPrefWidth(300);

        Button searchBtn = new Button("Search");
        Button cartBtn = new Button("Cart");

        Button orderBtn = new Button("Orders");
        signBtn = new Button("Sign In");
        welcomeUser = new Label();
        headerBar = new HBox();
        headerBar.setSpacing(10);
        headerBar.setPadding(new Insets(30));
        headerBar.getChildren().addAll(homebtn,searchBar,searchBtn,signBtn,cartBtn,orderBtn);
        headerBar.setAlignment(Pos.CENTER);

        signBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                body.getChildren().clear();  // remove everything on the body
                body.getChildren().add(loginPage); // add login page on the body
                headerBar.getChildren().remove(signBtn); // remove signin btn when clicked on sign in
            }
        });

        cartBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                body.getChildren().clear();
                VBox productsInCart = productList.getAllProductsInCart(itemsInCart);
                productsInCart.setAlignment(Pos.CENTER);
                productsInCart.setSpacing(10);
                productsInCart.getChildren().add(placeOrderButton);
                body.getChildren().add(productsInCart);
                footerBar.setVisible(false); // need to be enable  when ever needed
            }
        });

        placeOrderButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {


                if (itemsInCart == null) {
                    showDialog("Please add some products in cart to place order");
                    return;
                }
                if(loggedInCustomer == null)
                {
                    showDialog("Please Login first to Place your order");
                    return;
                }
                int count = Order.placeMultipleOrder(loggedInCustomer,itemsInCart);
                if(count != 0)
                {
                    showDialog("Order for "+count+" products placed Successfully");
                }
                else {
                    showDialog("Order not Placed");
                }
            }
        });

        homebtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                body.getChildren().clear();
                body.getChildren().add(productPage);
                footerBar.setVisible(true);
                if(loggedInCustomer==null)
                {
                    if(headerBar.getChildren().indexOf(signBtn)==-1)  // not creating duplicate sign in buttons
                          headerBar.getChildren().add(signBtn);
                }
            }
        });

        orderBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                body.getChildren().clear();
                body.getChildren().add(OrdersPage);
            }
        });


    }

    private void createFooterBar() {
        Button BuyNowbtn = new Button("Buy Now");
        Button addToCartBtn = new Button("Add to Cart");
        footerBar = new HBox();
        footerBar.setSpacing(10);
        footerBar.setPadding(new Insets(30));
        footerBar.getChildren().addAll(BuyNowbtn,addToCartBtn);
        footerBar.setAlignment(Pos.CENTER);

        BuyNowbtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Product product = productList.getSelectedProduct();
                if (product == null) {
                    showDialog("Please Select a Product to place your order");
                    return;
                }
                if(loggedInCustomer == null)
                {
                    showDialog("Please Login first to Place your order");
                    return;
                }
                boolean status = Order.placeOrder(loggedInCustomer,product);
                if(status==true)
                {
                    showDialog("Order placed Successfully");
                }
                else {
                    showDialog("Order not Placed");
                }
            }
        });

        addToCartBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Product product = productList.getSelectedProduct();
                if (product == null) {
                    showDialog("Please Select a Product to add it your cart");
                    return;
                }
                itemsInCart.add(product);
                showDialog("Item added to the cart successfully!!");
            }
        });

     }


        private void showDialog(String msg)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText(msg);
            alert.setTitle("Message");
            alert.showAndWait();
        }



}
