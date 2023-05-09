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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author WINDOWS 10
 */
public class TeacherPortalController implements Initializable {

    @FXML
    private TextField teacher_teacherID;

    @FXML
    private PasswordField teacher_password;

    @FXML
    private Button teacher_loginBtn;

    @FXML
    private ComboBox<String> teacher_select;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    private Alert alert;

    private void errorMessage(String message) {

        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Message");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();

    }

    private void successMessage(String message) {

        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Message");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();

    }

    public void loginAccount() {

        if (teacher_teacherID.getText().isEmpty() || teacher_password.getText().isEmpty()) {
            errorMessage("Please fill all blank fields");
        } else {

            String selectData = "SELECT * FROM teacher WHERE teacher_id = ? AND password = ?";

            connect = Connect.connectDB();

            try {

                prepare = connect.prepareStatement(selectData);
                prepare.setString(1, teacher_teacherID.getText());
                prepare.setString(2, teacher_password.getText());

                result = prepare.executeQuery();

                if (result.next()) {

                    successMessage("Login Successfully!");
                    // LINK YOUR TEACHER MAIN FORM
                    Parent root = FXMLLoader.load(getClass().getResource("teacherMainForm.fxml"));

                    Stage stage = new Stage();

                    stage.setTitle("Teacher Main Form");

                    stage.setScene(new Scene(root));

                    stage.show();
                    
                    // TO HIDE YOUR TEACHER MAIN FORM
                    teacher_loginBtn.getScene().getWindow().hide();

                } else {

                    errorMessage("Incorrect Teacher ID/Password");

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    public void switchForm() {

        try {
            Parent root = null;
            if (teacher_select.getSelectionModel().getSelectedItem().equals("Teacher Portal")) {
                root = FXMLLoader.load(getClass().getResource("TeacherPortal.fxml")); // LINK YOUR LOGIN ADMIN PORTAl
            } else if (teacher_select.getSelectionModel().getSelectedItem().equals("Student Portal")) {
                root = FXMLLoader.load(getClass().getResource("StudentPortal.fxml"));
            } else if (teacher_select.getSelectionModel().getSelectedItem().equals("Admin Portal")) {
                root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
            }

            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            stage.show();

            // TO HIDE THE TEACHER PORTAL FORM 
            teacher_select.getScene().getWindow().hide();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void selectuser() {

        List<String> listU = new ArrayList<>();

        for (String data : users.users) {
            listU.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listU);
        teacher_select.setItems(listData);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        selectuser();
    }

}
