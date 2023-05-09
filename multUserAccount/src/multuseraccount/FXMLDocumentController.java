/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multuseraccount;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author WINDOWS 10
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private TextField admin_username;

    @FXML
    private PasswordField admin_password;

    @FXML
    private Button admin_loginBtn;

    @FXML
    private ComboBox<String> admin_user;

    // DONT FORGET TO IMPORT YOUR JAR FILES FOR DATABASE CONNECTOR
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    private Alert alert;

    private void errorMessage(String message) {

        alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error Message");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();

    }

    private void successMessage(String message) {

        alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information Message");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();

    }

    public void loginAccount() {

        // CHECK IF THERE ARE FIELDS THAT EMPTY
        if (admin_username.getText().isEmpty() || admin_password.getText().isEmpty()) {
            errorMessage("Please fill all blank fields");
        } else {
            String selectData = "SELECT * FROM admin WHERE username = ? and password = ?"; // MAKE SURE TO CHECK IF THE NAME OF YOUR TABLE AND COLUMNS ARE MATCH

            connect = Connect.connectDB();

            try {
                prepare = connect.prepareStatement(selectData);
                prepare.setString(1, admin_username.getText());
                prepare.setString(2, admin_password.getText());

                result = prepare.executeQuery();

                if (result.next()) {
                    // IF CORRECT USERNAME AND PASSWORD

                    successMessage("Login Successfully!");

                    // LETS CREATE YOUR MAIN FORM
                    // LINK YOUR ADMIN MAIN FORM
                    Parent root = FXMLLoader.load(getClass().getResource("adminMainForm.fxml"));

                    Stage stage = new Stage();
                    stage.setTitle("Admin Main Form");
                    stage.setScene(new Scene(root));

                    stage.show();

                    // TO HIDE YOUR ADMIN LOGIN FORM
                    admin_loginBtn.getScene().getWindow().hide();
                } else {
                    // IF INCORRECT USERNAME OR PASSWORD

                    errorMessage("Incorrect Username/Password");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void switchForm() {

        try {
            Parent root = null;
            if (admin_user.getSelectionModel().getSelectedItem().equals("Teacher Portal")) {
                root = FXMLLoader.load(getClass().getResource("TeacherPortal.fxml")); // LINK YOUR LOGIN ADMIN PORTAl
            } else if (admin_user.getSelectionModel().getSelectedItem().equals("Student Portal")) {
                root = FXMLLoader.load(getClass().getResource("StudentPortal.fxml"));
            } else if (admin_user.getSelectionModel().getSelectedItem().equals("Admin Portal")) {
                root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
            }

            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            stage.show();

            // TO HIDE YOUR ADMIN PORTAL FORM
            admin_user.getScene().getWindow().hide();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void selectUser() {

        List<String> listU = new ArrayList<>();

        for (String data : users.users) {
            listU.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listU);
        admin_user.setItems(listData);

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        selectUser();
    }

}


// DONT FORGET TO IMPORT YOUR FONTAWESOMEICON IN YOUR LIBRARY!


// THATS IT FOR THIS VIDEO, THANKS FOR WATCHING!! : ) 