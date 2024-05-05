package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

public class StudentController {

    @FXML
    private TableView<Room> tableRooms;
    @FXML
    private TableColumn<Room, Integer> colRoomNumber;
    @FXML
    private TableColumn<Room, String> colType;
    @FXML
    private TableColumn<Room, String> colStatus;
    @FXML
    private TableColumn<Room, Double> colPrice;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private AnchorPane reservePane;

    @FXML
    private AnchorPane reservationForm;

    @FXML
    private AnchorPane paymentPane;

    private PaymentDao paymentDao;


    private double roomPrice;


    @FXML
    private Label roomNumberLabel;

    @FXML
    private Label seatNumberLabel;

    @FXML
    private Label cnicNumberLabel;

    @FXML
    private Label phoneNumberLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private Label welcomePaymentLabel;

    @FXML
    private Label roomNoPaymentLabel;

    @FXML
    private Label feesLabel;


    @FXML
    private Button logOutButton;



    @FXML
    private AnchorPane mainPanel;

    @FXML
    private Label mainLabel,feedbackLabel;

    @FXML
    private TextField roomPaymentTextField;

    @FXML
    private TextField feesTextField,complaintTextField;

    @FXML
    private RadioButton creditCardButton,mobilePaymentButton,cashButton;

    @FXML
    private Button proceedPaymentButton,submitButton;

    @FXML
    private TextField roomTextField;

    @FXML
    private TextField seatTextField;

    @FXML
    private TextField nicTextField;

    @FXML
    private TextField phoneTextField;

    @FXML
    private TextField priceTextField;

    @FXML
    private Button confirmBookingButton;
    @FXML
    private Button bargainButton;

    @FXML
    private Button confirmCreditButton;

    @FXML
    private Label cardNoLabel,cvvLabel,expiryLabel;

    @FXML
    private TextField cardNumberField,cvvField,expiryDateField;


    @FXML
    private TextField reserveSeatTextField;
    @FXML
    private Button finalReserveButton;
    @FXML
    private Label studentLabel; // Additional UI component for displaying student info
    @FXML
    private Label statusLabel; // Status label to show messages

    private DatabaseConnection dbConnection;

    public void initialize() {
        mainPanel.setVisible(true);
        DatabaseConnection dbConnection = new DatabaseConnection();
        this.paymentDao = new PaymentDao(dbConnection);
        fetchRoomDetails();

        loadRoomDataFromDB();
        colRoomNumber.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));


        //setupTableColumns();

    }

    private void fetchRoomDetails() {
        // This example uses MySQL syntax. Adjust the query if using a different database system.
        String query = "SELECT RoomNo, Price FROM booking ORDER BY RoomNo DESC LIMIT 1";  // Assuming you want the latest room number
        DatabaseConnection db=new DatabaseConnection();
        Connection conn=db.getConnection();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                int roomNumber = rs.getInt("RoomNo");
                double price = rs.getDouble("Price");
                // Assuming there are text fields for room number and price
                roomPaymentTextField.setText(String.valueOf(roomNumber));
                feesTextField.setText(String.format("%.2f", price));
            } else {
                // Handle case where booking details are not found
                //alertDetailsNotFound();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Handle database errors
           // alertDatabaseError();
        }
    }


    private void loadRoomDataFromDB() {
        ObservableList<Room> rooms = FXCollections.observableArrayList();
        String query = "SELECT roomNumber, type, status, price,Capacity FROM rooms";
        DatabaseConnection db = new DatabaseConnection();
        Connection conn = db.getConnection();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int roomNumber = rs.getInt("roomNumber");
                String type = rs.getString("type");
                String status = rs.getString("status");
                double price = rs.getDouble("price");
                int Capacity=rs.getInt("Capacity");
                rooms.add(new Room(roomNumber, type, status, price,Capacity));
            }
            tableRooms.setItems(rooms);
        } catch (SQLException e) {
            e.printStackTrace(); // Consider more sophisticated error handling
        }
    }
    @FXML
    private void finalReserve(ActionEvent e) {


        reservePane.setVisible(false);
        reservationForm.setVisible(true);
        paymentPane.setVisible(false);
        String roomNumber=reserveSeatTextField.getText();
        roomTextField.setText(roomNumber);
        Room r=new Room(1,"FullBed","Availabe",35.5,4);

    }
    @FXML
    private void reserveSeats(ActionEvent as) {
        // Add your code logic for reserveSeats

        mainPanel.setVisible(false);

        reservationForm.setVisible(false);
        creditPane.setVisible(false);
        feedBackPanel.setVisible(false);
        mobilePane.setVisible(false);
        paymentPane.setVisible(false);
        reservePane.setVisible(true);
        System.out.println("Reserve Seats button clicked");
    }

    @FXML
    private void pay(ActionEvent event) {
        // Add your code logic for payment processing
        mainPanel.setVisible(false);
        reservePane.setVisible(false);
        reservationForm.setVisible(false);
        creditPane.setVisible(false);
        feedBackPanel.setVisible(false);
        mobilePane.setVisible(false);
        paymentPane.setVisible(true);


        System.out.println("Pay button clicked");
    }

    @FXML
    private void submitComplaint(ActionEvent a){
        System.out.println("Submit button clicked");
    }
    @FXML
    private AnchorPane feedBackPanel;
    @FXML
    private void submitFeedback(ActionEvent a) {
        // Add your code logic for submitting feedback
        mainPanel.setVisible(false);
        reservePane.setVisible(false);
        paymentPane.setVisible(false);
        reservationForm.setVisible(false);
        creditPane.setVisible(false);
        mobilePane.setVisible(false);
        feedBackPanel.setVisible(true);

        System.out.println("Submit Feedback button clicked");
    }

    @FXML
    private void confirmBooking(ActionEvent cb){
            String roomNumber=reserveSeatTextField.getText();
            int roomNumbers = Integer.parseInt(roomNumber);
            String cnic=nicTextField.getText();


            Booking b=new Booking(roomNumbers,cnic,roomPrice);
             try {
            // Insert the booking into the database
                b.insertBookingIntoDB();
                paymentPane.setVisible(true);
                reservationForm.setVisible(false);
                roomPaymentTextField.setText(roomNumber);
                String prices=String.valueOf(roomPrice);
                feesTextField.setText(prices);


            } catch (SQLException e) {
            // Handle the exception, or log it, or propagate it further up the call stack
            e.printStackTrace(); // Example: Print the stack trace for debugging
            // Optionally, display an error message to the user
            // showErrorDialog("Error making booking: " + e.getMessage());
            }


    }

    @FXML
    private void bargainPrice(ActionEvent bp){
        String noOfSeats=seatTextField.getText();

        Room r=new Room(1,"FullBed","Available",35.5,4);
        String roomNumber=reserveSeatTextField.getText();
        //String noOfSeats=seatTextField.getText();


        roomPrice=r.specificPrice(roomNumber,noOfSeats);
        String rp=String.valueOf(roomPrice);
        priceTextField.setText(rp);

    }

    @FXML
    private void creditCard(ActionEvent cd){


    }


    @FXML
    private void confirmCredit(ActionEvent a){

    }

    @FXML
    private void mobilePayment(ActionEvent cd){}
    @FXML
    private void cashPayment(ActionEvent c){}

    public void CreditPane(){
          paymentPane.setVisible(false);
          creditPane.setVisible(true);



    }
    private PaymentStrategy paymentStrategy;

    @FXML
    private AnchorPane creditPane;

    @FXML
    private AnchorPane mobilePane;

    @FXML
    private Label mobileLabel,numberLabel;

    @FXML
    private TextField mobileNumberField;

    @FXML
    private Button confirmMobileButton;


    @FXML
    private void confirmMobile(ActionEvent cm){

    }

    // Proceed payment method
    @FXML
    private void proceedPayment(ActionEvent e){
        double amount = Double.parseDouble(feesTextField.getText());

        if (creditCardButton.isSelected()) {
            paymentPane.setVisible(false);
            creditPane.setVisible(true);
            creditPane.setManaged(true);
            confirmCreditButton.setOnAction(okayEvent -> {
                // Validate and process credit card information
                paymentStrategy = new CreditCardPayment(cardNumberField.getText(), expiryDateField.getText(), cvvField.getText());
                creditPane.setVisible(false);
                creditPane.setManaged(false);

                // Show the main payment pane
                paymentPane.setVisible(true);
                paymentPane.setManaged(true);

                // Continue with the rest of the payment logic
                completePayment(amount);
            });
        } else if (mobilePaymentButton.isSelected()) {
            paymentPane.setVisible(false);
            mobilePane.setVisible(true);
            mobilePane.setManaged(true);
            confirmMobileButton.setOnAction(okayEvent -> {
                // Validate and process credit card information
                //paymentStrategy = new CreditCardPayment(cardNumberField.getText(), expiryDateField.getText(), cvvField.getText());
                paymentStrategy = new MobilePayment(mobileNumberField.getText());
                mobilePane.setVisible(false);
                mobilePane.setManaged(false);

                // Show the main payment pane
                paymentPane.setVisible(true);
                paymentPane.setManaged(true);

                // Continue with the rest of the payment logic
                completePayment(amount);
            });
        } else if (cashButton.isSelected()) {
            paymentStrategy = new CashPayment();
            completePayment(amount);
        } else {
            showAlert("No payment method selected", "Please select a payment method to proceed.");
            return;
        }
    }
    private void completePayment(double amount) {
        paymentStrategy.pay(amount);
        paymentDao.logPayment(amount,getPaymentType());
        // Any further logic that needs to be executed after payment
    }
    private String getPaymentType() {
        if (creditCardButton.isSelected()) return "Credit Card";
        if (mobilePaymentButton.isSelected()) return "Mobile Payment";
        return "Cash";
    }



    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void logOut(ActionEvent l){
        try {
            // Load the login FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = loader.load();

            // Get the current stage (window) from any control that is already loaded
            Stage stage = (Stage) logOutButton.getScene().getWindow(); // Replace btnYourButton with any actual control from your current scene
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exceptions or display an error message
        }
    }

    // Additional methods for other UI actions can be implemented here
}
