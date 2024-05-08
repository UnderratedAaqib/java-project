package com.example.demo;

import com.example.demo.BLL.Booking;
import com.example.demo.BLL.Feedback;
import com.example.demo.BLL.Refund;
import com.example.demo.BLL.Room;
import com.example.demo.DLL.DatabaseConnection;
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

//import java.awt.Label;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.io.IOException;
import java.sql.SQLException;

public class ManagerController {
    public AnchorPane roomPanel;
    public AnchorPane refundpanel;
    public AnchorPane hostelmanagepanel;
    public AnchorPane addedpanel;
    public AnchorPane viewPaymentpane;
    public AnchorPane viewallfeedbackpane;
    public AnchorPane RoomPane;
   // public TableColumn colRoomNumber;
    public TextField availablecapacity;
    public TextField price;
    public TextField status;
    public TextField roomType;
    public Button addbtn;
    public AnchorPane viewbookingpane;
    public TextField enterroomno;
    public Button addroomButton;
    public TextField refundcnic;
    public AnchorPane paymentManagerPane;
    public Button logOutButton;

    @FXML
    private TableView<PaymentDetail> paymentTable;
    @FXML
    private TableColumn<PaymentDetail, Integer> colRoomNumber2;
    @FXML
    private TableColumn<PaymentDetail, String> colCNIC1;
    @FXML
    private TableColumn<PaymentDetail, Double> colPayment;

    Room r;
    @FXML
    private TableView<Booking> bookingTable;
    @FXML
    private TableColumn<Booking, String> colBoC;

   // @FXML
   // private TableColumn<Booking, Integer> colBoid;

    @FXML
    private TableView<Feedback>feedbackTable;
    @FXML
    private TableColumn<Feedback,String> colFeedback;

    @FXML
    private TableColumn<Booking, Integer> colBoR;


    @FXML
    private TableView<Refund> retable;
    @FXML
    private TableColumn<Refund, String> cccnic;
    @FXML
    private TableColumn<Refund, Double> ccamount;


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

    private static final DatabaseConnection db = DatabaseConnection.getInstance();

    // Method to initialize TableView columns
    public void initialize() {
        setupRoomTableView(); // Set up the TableView columns
        loadBookingData();
        loadRoomData();
        loadPaymentData();
        loadFeedbackData();

        cccnic.setCellValueFactory(new PropertyValueFactory<>("CNIC"));
        ccamount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        loadRefundData(); // Load data when controller initializes
    }
    private void setupRoomTableView() {
        colRoomNumber.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        //colBoid.setCellValueFactory(new PropertyValueFactory<>("id"));
        colBoR.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        colBoC.setCellValueFactory(new PropertyValueFactory<>("cnic"));
        colRoomNumber2.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        colCNIC1.setCellValueFactory(new PropertyValueFactory<>("cnic"));
        colPayment.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colFeedback.setCellValueFactory(new PropertyValueFactory<>("content"));

        //colCapacity.setCellValueFactory(new PropertyValueFactory<>("Capacity"));
    }
    private void loadFeedbackData() {
        ObservableList<Feedback> feedbacks = FXCollections.observableArrayList();
        String query = "SELECT content FROM feedback;";  // Assuming your feedback table has a column named 'content'

        Connection conn = DatabaseConnection.getInstance().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String content = rs.getString("content");
                Feedback feedback = new Feedback(content);
                feedbacks.add(feedback);
            }
            feedbackTable.setItems(feedbacks);
        } catch (SQLException e) {
            e.printStackTrace(); // Proper error handling should be implemented
        }
    }



    private void loadBookingData() {
        ObservableList<Booking> bookings = FXCollections.observableArrayList();
        String query = "SELECT id, RoomNo, CNIC,Price FROM booking;";

        Connection conn = db.getConnection();
        try
        {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id=rs.getInt("id");
                int roomNumber = rs.getInt("RoomNo");
                String CNIC = rs.getString("CNIC");
                //String status = rs.getString("status");
                double price = rs.getDouble("price");

                bookings.add(new Booking(roomNumber, CNIC,price));
            }
            bookingTable.setItems(bookings);
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions appropriately
        }
    }

    private void loadPaymentData() {
        ObservableList<PaymentDetail> paymentDetails = FXCollections.observableArrayList();
        String query = "SELECT b.RoomNo, b.CNIC,b.Price, p.Amount, p.PaymentType " +
                "FROM booking b INNER JOIN payments p ON b.Price = p.Amount " +
                "WHERE p.Status = 'Processed';";  // Ensure the 'Status' filter is correctly applied as needed


        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Integer roomNumber = rs.getInt("RoomNo");
                String cnic = rs.getString("CNIC");
                Double amount = rs.getDouble("Amount");
                String paymentType = rs.getString("PaymentType");

                paymentDetails.add(new PaymentDetail(roomNumber, cnic, amount, paymentType));
            }
            paymentTable.setItems(paymentDetails);
        } catch (SQLException e) {
            e.printStackTrace(); // Proper error handling should be implemented
        }
    }


    // Method to load room data from the database
    private void loadRoomData() {
        ObservableList<Room> rooms = FXCollections.observableArrayList();
        String query = "SELECT roomNumber, type, status, price, Capacity FROM rooms;";

        Connection conn = db.getConnection();
        try
              {
                  PreparedStatement stmt = conn.prepareStatement(query);
                  ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int roomNumber = rs.getInt("roomNumber");
                String type = rs.getString("type");
                String status = rs.getString("status");
                double price = rs.getDouble("price");
                int capacity = rs.getInt("Capacity");
                rooms.add(new Room(roomNumber, type, status, price, capacity));
            }
            tableRooms.setItems(rooms);
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions appropriately
        }
    }

    // Method to load refund data from database
    private void loadRefundData() {
        ObservableList<Refund> refunds = FXCollections.observableArrayList();
        String query = "SELECT CNIC, amount FROM refund;";
        Connection conn = db.getConnection();
        try  {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String cnic = rs.getString("CNIC");
                double amount = rs.getDouble("amount");
                refunds.add(new Refund(cnic, amount));
            }
            retable.setItems(refunds);
        } catch (SQLException e) {
            e.printStackTrace();
            // Optionally, add error handling here
        }
    }


    @FXML
    public void AddRoom(ActionEvent actionEvent) {
        roomPanel.setVisible(true);
        refundpanel.setVisible(false);
        hostelmanagepanel.setVisible(false);
        addedpanel.setVisible(false);
        viewallfeedbackpane.setVisible(false);
        viewPaymentpane.setVisible(false);
        RoomPane.setVisible(false);
        viewbookingpane.setVisible(false);



        System.out.println("Pay button clicked");
    }

    public void hostelmanage(ActionEvent actionEvent) {
        roomPanel.setVisible(false);
        refundpanel.setVisible(false);
        addedpanel.setVisible(false);

        hostelmanagepanel.setVisible(true);
        viewallfeedbackpane.setVisible(false);
        viewPaymentpane.setVisible(false);
        RoomPane.setVisible(false);
        viewbookingpane.setVisible(false);

    }

    public void refundmanage(ActionEvent actionEvent) {
        roomPanel.setVisible(false);
        refundpanel.setVisible(true);
        hostelmanagepanel.setVisible(false);
        addedpanel.setVisible(false);
        viewallfeedbackpane.setVisible(false);
        viewPaymentpane.setVisible(false);
        RoomPane.setVisible(false);
        viewbookingpane.setVisible(false);


    }

    public void logOut(ActionEvent actionEvent) {
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

    public void createnewroom(ActionEvent actionEvent) {


        roomPanel.setVisible(false);
        refundpanel.setVisible(false);
        hostelmanagepanel.setVisible(false);
        //addedpanel.setVisible(true);
        viewallfeedbackpane.setVisible(false);
        viewPaymentpane.setVisible(false);
        RoomPane.setVisible(false);
        viewbookingpane.setVisible(false);
        try {
            String capacity = availablecapacity.getText().trim();
            String gprice = price.getText().trim();
            String gstatus = status.getText().trim();
            String roomno = enterroomno.getText().trim();
            String groom = roomType.getText().trim();

            if (capacity.isEmpty() || gprice.isEmpty() || gstatus.isEmpty() || roomno.isEmpty() || groom.isEmpty()) {
                showAlert("Validation Error", "All fields must be filled out.");
                return;
            }

            double doublePrice = Double.parseDouble(gprice); // This might throw NumberFormatException if not a valid double
            int cp = Integer.parseInt(capacity); // This might throw NumberFormatException if not a valid integer
            int rn = Integer.parseInt(roomno); // This might throw NumberFormatException if not a valid integer

            if (cp < 1 || rn < 1) {
                showAlert("Validation Error", "Room number and capacity must be greater than 0.");
                return;
            }

            r = new Room(rn, groom, gstatus, doublePrice, cp);
            r.insertIntoDB();
            addedpanel.setVisible(true); // Show success message or panel
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Please ensure that numbers are entered correctly.");
        }
    }

    public void handleGoBack(ActionEvent actionEvent) {
    }

    public void gotoBookings(MouseEvent mouseEvent) {
        roomPanel.setVisible(false);
        refundpanel.setVisible(false);
        hostelmanagepanel.setVisible(false);
        addedpanel.setVisible(false);
        viewallfeedbackpane.setVisible(false);
        viewPaymentpane.setVisible(false);
        RoomPane.setVisible(false);
        viewbookingpane.setVisible(true);

    }

    public void gotofeedbacklist(MouseEvent mouseEvent) {
        roomPanel.setVisible(false);
        refundpanel.setVisible(false);
        hostelmanagepanel.setVisible(false);
        addedpanel.setVisible(false);
        viewallfeedbackpane.setVisible(true);
        viewPaymentpane.setVisible(false);
        RoomPane.setVisible(false);
        viewbookingpane.setVisible(false);

    }

    public void gotopayment(MouseEvent mouseEvent) {
        roomPanel.setVisible(false);
        refundpanel.setVisible(false);
        hostelmanagepanel.setVisible(false);
        addedpanel.setVisible(false);
        viewallfeedbackpane.setVisible(false);
        viewPaymentpane.setVisible(true);
        RoomPane.setVisible(false);
        viewbookingpane.setVisible(false);

    }

    public void gotorefund(MouseEvent mouseEvent) {
        roomPanel.setVisible(false);
        refundpanel.setVisible(true);
        hostelmanagepanel.setVisible(false);
        addedpanel.setVisible(false);
        viewallfeedbackpane.setVisible(false);
        viewPaymentpane.setVisible(false);
        RoomPane.setVisible(false);
        viewbookingpane.setVisible(false);

    }

    public void gotoallroom(MouseEvent mouseEvent) {
        roomPanel.setVisible(false);
        refundpanel.setVisible(false);
        hostelmanagepanel.setVisible(false);
        addedpanel.setVisible(false);
        viewallfeedbackpane.setVisible(false);
        viewPaymentpane.setVisible(false);
        RoomPane.setVisible(true);
        viewbookingpane.setVisible(false);
        loadRoomData();

    }

    public void rejectrefund(ActionEvent actionEvent) {
    }

    public void acceptrefund(ActionEvent actionEvent) {

        String cnic = refundcnic.getText();
        if (cnic != null && !cnic.isEmpty()) {
            Refund.deleteRefundRecord(cnic);
        } else {
            showAlert("CNIC invalid","Enter a valid cnic");
            System.out.println("Please enter a CNIC.");
            return;
        }
        loadRefundData();
        initialize();
        showAlert("Success", "Refund processed successfully.");
        //load from db
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


}