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
public class StudentPortalController implements Initializable {

    @FXML
    private TextField student_studentID;

    @FXML
    private PasswordField student_password;

    @FXML
    private Button student_loginBtn;

    @FXML
    private ComboBox<String> student_selectUser;

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

        if (student_studentID.getText().isEmpty() || student_password.getText().isEmpty()) {
            errorMessage("Please fill all blank fields");
        } else {

            String selectData = "SELECT * FROM student WHERE student_id = ? AND password = ?";

            connect = Connect.connectDB();

            try {

                prepare = connect.prepareStatement(selectData);
                prepare.setString(1, student_studentID.getText());
                prepare.setString(2, student_password.getText());

                result = prepare.executeQuery();

                if (result.next()) {

                    successMessage("Login Successfully!");

                    // LINK YOUR STUDENT MAIN FORM 
                    Parent root = FXMLLoader.load(getClass().getResource("studentMainForm.fxml"));

                    Stage stage = new Stage();
                    stage.setTitle("Student Main Form");
                    stage.setScene(new Scene(root));

                    stage.show();

                    // TO HIDE YOUR STUDENT LOGIN FORM
                    student_loginBtn.getScene().getWindow().hide();

                } else {

                    errorMessage("Incorrect Student ID/Password");

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    public void switchForm() {

        try {
            Parent root = null;
            if (student_selectUser.getSelectionModel().getSelectedItem().equals("Teacher Portal")) {
                root = FXMLLoader.load(getClass().getResource("TeacherPortal.fxml")); // LINK YOUR LOGIN ADMIN PORTAl
            } else if (student_selectUser.getSelectionModel().getSelectedItem().equals("Student Portal")) {
                root = FXMLLoader.load(getClass().getResource("StudentPortal.fxml"));
            } else if (student_selectUser.getSelectionModel().getSelectedItem().equals("Admin Portal")) {
                root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
            }

            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            stage.show();

            // TO HIDE STUDENT PORTAL FORM
            student_selectUser.getScene().getWindow().hide();

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
        student_selectUser.setItems(listData);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        selectuser(); // TO DISPLAY THE DATA IN OUR COMBOBOX
    }

}
