package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField usernameTextField;

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField passwordTextField;
    public void cancelButtonOnAction(ActionEvent e){
        Stage stage=(Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private Label loginMessageLabel;

    public void loginButtonOnAction(ActionEvent e){


        if(usernameTextField.getText().isBlank()==false && passwordTextField.getText().isBlank()==false){
           // loginMessageLabel.setText("You try to login");

        validateLogin();
        }else{
            loginMessageLabel.setText("Please enter username and password");
        }
    }
    public void validateLogin(){
        DatabaseConnection connectNow=new DatabaseConnection();
        Connection connectDB=connectNow.getConnection();

        String verifyLogin="Select count(1) FROM useraccounts where username='"+ usernameTextField.getText() +"' AND password='"+ passwordTextField.getText() +"';";
        try{
            Statement statement=connectDB.createStatement();
            ResultSet queryResult=statement.executeQuery(verifyLogin);

            while(queryResult.next()){
                if(queryResult.getInt(1)==1){
                    loginMessageLabel.setText("Login Successful");
                    switchToPayment();
                }else{
                    loginMessageLabel.setText("Login Unsuccessful");
                }
            }

        }catch(Exception e){
                e.printStackTrace();
        }


    }

    public void switchToPayment() {
        try {
            // Load the payment FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("student.fxml"));
            Parent root = loader.load();

            // Get the current stage (window) from any control that is already loaded
            Stage stage = (Stage) loginButton.getScene().getWindow(); // Replace btnYourButton with any actual control from your current scene
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exceptions or display an error message
        }
    }
    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

}