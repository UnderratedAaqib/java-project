package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RoomController {

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

    public void initialize() {
        loadRoomDataFromDB();
        colRoomNumber.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Load room data from database
        loadRoomDataFromDB();
    }

    private void loadRoomDataFromDB() {
        ObservableList<Room> rooms = FXCollections.observableArrayList();
        String query = "SELECT roomNumber, type, status, price FROM rooms";  // Adjust your actual SQL query as necessary
        DatabaseConnection db=new DatabaseConnection();
        Connection conn = db.getConnection();
        try  {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int roomNumber = rs.getInt("roomNumber");
                String type = rs.getString("type");
                String status = rs.getString("status");
                double price = rs.getDouble("price");

                rooms.add(new Room(roomNumber, type, status, price));
            }
            tableRooms.setItems(rooms);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle errors here
        }

    }


    @FXML
    void handleAddRoom(ActionEvent event) {
        // Implement your logic here for adding a room
        System.out.println("Add Room button clicked");
    }

    // Handler for editing a room
    @FXML
    void handleEditRoom(ActionEvent event) {
        // Implement your logic here for editing a room
        System.out.println("Edit Room button clicked");
    }

    // Handler for deleting a room
    @FXML
    void handleDeleteRoom(ActionEvent event) {
        // Implement your logic here for deleting a room
        System.out.println("Delete Room button clicked");
    }

    // Handler for refreshing the room list
    @FXML
    void handleRefresh(ActionEvent event) {
        // Implement your logic here for refreshing the room list
        System.out.println("Refresh button clicked");
    }

    // Handler for going back to the previous page or main menu
    @FXML
    void handleBack(ActionEvent event) {
        // Implement your logic here to go back
        System.out.println("Back button clicked");
    }
}
