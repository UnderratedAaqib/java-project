package com.example.demo;

import com.example.demo.BLL.*;
import com.example.demo.DLL.DatabaseConnection;
import com.example.demo.DLL.PaymentDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StudentController {

    private BookingFacade bookingFacade;
    public StudentController() {
        this.bookingFacade = new BookingFacadeImpl(DatabaseConnection.getInstance(), new PaymentDao(DatabaseConnection.getInstance()));
    }

    public AnchorPane refundPanel;
    public TextField refCnicText;
    public TextField refRoomNo;
    public Button refRequestBu;
    public TextField bargainPriceTextField;
    // public TableColumn<Room, Integer> capacityCol;
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

    private static final DatabaseConnection dbConnection = DatabaseConnection.getInstance();

    Refund re;
    public void initialize() {
        mainPanel.setVisible(true);

        this.paymentDao = new PaymentDao(dbConnection);
        fetchRoomDetails();

        loadRoomDataFromDB();
        colRoomNumber.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
       // capacityCol.setCellValueFactory(new PropertyValueFactory<>("Capacity"));

        //setupTableColumns();

    }

    private void fetchRoomDetails() {
        // This example uses MySQL syntax. Adjust the query if using a different database system.
        String query = "SELECT RoomNo, Price FROM booking ORDER BY RoomNo DESC LIMIT 1";  // Assuming you want the latest room number

        Connection conn=dbConnection.getConnection();
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
        String query = "SELECT roomNumber, type, status, price, Capacity FROM rooms WHERE status = 'Available';\n";

        Connection conn = dbConnection.getConnection();
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
        refundPanel.setVisible(false);
        String roomNumber=reserveSeatTextField.getText();
        if(roomNumber.isEmpty()){
            showAlert("Empty Field","All fields must be filled");
            return;
        }
        roomTextField.setText(roomNumber);



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
        refundPanel.setVisible(false);
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
        refundPanel.setVisible(false);

        System.out.println("Pay button clicked");
    }

    @FXML
    private void submitComplaint(ActionEvent a){
        String complaint = complaintTextField.getText();

        if (complaint.isEmpty()) {
            showAlert("Input Error", "Feedback content cannot be empty.");
            return;
        }

        Feedback feedback = new Feedback(complaint); // Create an instance of Feedback

        // SQL query to insert feedback into the database
        String insertQuery = "INSERT INTO feedback (content) VALUES (?);";

        Connection conn = dbConnection.getConnection();  // Assuming dbConnection is your DatabaseConnection instance
        try (PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
            pstmt.setString(1, feedback.getContent());  // Use getter to get content
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Feedback submitted successfully.");
                showAlert("Success", "Feedback submitted successfully.");
            } else {
                System.out.println("Failed to submit feedback.");
                showAlert("Error", "Failed to submit feedback.");
            }
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            showAlert("Database Error", "Failed to submit feedback due to a database error.");
            e.printStackTrace();
        }

        System.out.println("Submit button clicked");
    }
    @FXML
    private AnchorPane feedBackPanel;
    @FXML
    private void submitFeedback(ActionEvent a) {
        //String complaint=complaintTextField.getText();

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
    private void confirmBooking(ActionEvent event) {
        String roomNumberStr = roomTextField.getText().trim();
        String cnic = nicTextField.getText().trim();
        String noOfSeatsStr = seatTextField.getText().trim();
        String phoneNumber = phoneTextField.getText().trim();

        if (roomNumberStr.isEmpty() || cnic.isEmpty() || noOfSeatsStr.isEmpty() || phoneNumber.isEmpty()) {
            showAlert("Input Error", "All fields must be filled.");
            return;
        }

        try {
            int roomNumber = Integer.parseInt(roomNumberStr);
            int seats = Integer.parseInt(noOfSeatsStr);

            if (seats <= 0) {
                showAlert("Input Error", "Number of seats must be greater than zero.");
                return;
            }

            if (bookingFacade.confirmBooking(roomNumber, cnic, seats, phoneNumber)) {
                showAlert("Booking Confirmed", "Your booking has been successfully confirmed.");
                paymentPane.setVisible(true);
                reservationForm.setVisible(false);
                roomPaymentTextField.setText(roomNumberStr);
                feesTextField.setText(String.format("%.2f", roomPrice));
            } else {
                showAlert("Booking Error", "Not enough seats available or room number does not exist.");
            }
        } catch (NumberFormatException e) {
            showAlert("Input Error", "Please enter valid numbers for room number and number of seats.");
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

    //Strategy Pattern
    private PaymentStrategy paymentStrategy;

    // Proceed payment method
    @FXML
    private void proceedPayment(ActionEvent e) {
        double amount;
        try {
            amount = Double.parseDouble(feesTextField.getText());
        } catch (NumberFormatException ex) {
            showAlert("Invalid Input", "Please enter a valid amount.");
            return;
        }

        int selectedCount = 0;
        RadioButton selectedMethod = null;

        if (creditCardButton.isSelected()) {
            selectedCount++;
            selectedMethod = creditCardButton;
        }
        if (mobilePaymentButton.isSelected()) {
            selectedCount++;
            selectedMethod = mobilePaymentButton;
        }
        if (cashButton.isSelected()) {
            selectedCount++;
            selectedMethod = cashButton;
        }

        if (selectedCount != 1) {
            showAlert("Selection Error", "Please select exactly one payment method.");
            return;
        }

        // Proceed based on the selected payment method
        switch (selectedMethod.getId()) {
            case "creditCardButton":
                paymentPane.setVisible(false);
                creditPane.setVisible(true);
                confirmCreditButton.setOnAction(okayEvent -> {
                    paymentStrategy = new CreditCardPayment(cardNumberField.getText(), expiryDateField.getText(), cvvField.getText());
                    creditPane.setVisible(false);
                    completePayment(amount);
                });
                break;
            case "mobilePaymentButton":
                paymentPane.setVisible(false);
                mobilePane.setVisible(true);
                confirmMobileButton.setOnAction(okayEvent -> {
                    paymentStrategy = new MobilePayment(mobileNumberField.getText());
                    mobilePane.setVisible(false);
                    completePayment(amount);
                });
                break;
            case "cashButton":
                paymentStrategy = new CashPayment();
                completePayment(amount);
                break;
            default:
                showAlert("Error", "No valid payment method was selected.");
                break;
        }
    }

    private void completePayment(double amount) {
        paymentStrategy.pay(amount);
        paymentDao.logPayment(amount,getPaymentType());
        // Any further logic that needs to be executed after payment
    }
    private String getPaymentType() {
        // Count the number of selected payment methods


        // Return the selected payment type
        if (creditCardButton.isSelected()) return "Credit Card";
        if (mobilePaymentButton.isSelected()) return "Mobile Payment";
        if (cashButton.isSelected()) return "Cash";

        // If no payment method is selected
        showAlert("Selection Error", "No payment method selected. Please select a payment method to proceed.");
        return null;
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

    public void requestRefu(ActionEvent event) {

        String refCnic = refCnicText.getText();
        String rRoomNo = refRoomNo.getText();
        if(refCnic.isEmpty() || rRoomNo.isEmpty()){
            showAlert("Empty fields","Please enter all fields required");
            return;
        }


        int rRoomNumber = Integer.parseInt(rRoomNo); // Assuming RoomNo is stored as an integer in the database

        String query = "SELECT Price FROM booking WHERE CNIC = ? AND RoomNo = ?;";

        Connection conn = dbConnection.getConnection();
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, refCnic);
            stmt.setInt(2, rRoomNumber);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                double price = rs.getDouble("Price");
                System.out.println("The price for the booking with CNIC " + refCnic + " and Room No " + rRoomNumber + " is: " + price);
                re=new Refund(refCnic,price);
                re.insertIntoDB();

                // Additional logic for handling refunds could be placed here
            } else {
                System.out.println("No booking found for the given CNIC and Room Number.");
                // Handle case where no booking matches the query
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL Exception here. This could be logging the error or showing an error message to the user
        }
    }

    public void refundClick(ActionEvent event) {
        mainPanel.setVisible(false);
        reservePane.setVisible(false);
        paymentPane.setVisible(false);
        reservationForm.setVisible(false);
        creditPane.setVisible(false);
        mobilePane.setVisible(false);
        feedBackPanel.setVisible(false);
        refundPanel.setVisible(true);
    }


    @FXML
    private void bargainPrice1(ActionEvent event) {
        try {
            String proposedPriceStr = bargainPriceTextField.getText().trim();
            if(proposedPriceStr.isEmpty()) {
                showAlert("Input Error", "Proposed price is required.");
                return;
            }
            double proposedPrice = Double.parseDouble(proposedPriceStr);
            double originalPrice = Double.parseDouble(priceTextField.getText().trim());

            if (proposedPrice <= 0 || originalPrice <= 0) {
                showAlert("Input Error", "Prices must be greater than zero.");
                return;
            }

            double maxDiscount = originalPrice * 0.95;
            if (proposedPrice >= maxDiscount) {
                priceTextField.setText(String.format("%.2f", proposedPrice));
                roomPrice = proposedPrice; // Update internal price state
            } else {
                showAlert("Bargain Error", "Only up to 5% discount is allowed.");
            }
        } catch (NumberFormatException e) {
            showAlert("Input Error", "Please enter valid numbers in the price fields.");
        }
    }


    // Additional methods for other UI actions can be implemented here
}
