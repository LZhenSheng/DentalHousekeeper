package com.example.demo;

import com.example.demo.domain.Constant;
import com.example.demo.domain.Doctor;
import com.example.demo.domain.Hospital;
import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONArray;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class LoginDoctorController {
    @FXML
    TextField accountTextField;
    @FXML
    PasswordField passwordTextField;

    public void onLoginButton(ActionEvent actionEvent) throws UnirestException {
        HttpResponse<JsonNode> response = Unirest.post(Constant.uri + "loginDoctors")
                .field("phone", accountTextField.getText())
                .field("password", passwordTextField.getText())
                .asJson();
        if (response.getStatus() == 200) {
//            Hospital hospital=response.getBody().getObject();
//            System.out.println();
            Gson gson = new Gson();
            JSONArray result = response.getBody().getArray();
            Doctor doctor = gson.fromJson(result.get(0).toString(), Doctor.class);
//            System.out.println(doctor.toString());
            Properties prop = new Properties();
            try {
                FileOutputStream oFile = new FileOutputStream("user.properties", false);
                prop.setProperty("doctor_id", doctor.getId());
                prop.store(oFile, null);
                oFile.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Platform.runLater(() -> {
                Stage primaryStage = (Stage) accountTextField.getScene().getWindow();
                //当前窗口隐藏
                primaryStage.hide();
                try {
                    new HelloApplication().startDoctorMain(new Stage());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public void login(ActionEvent actionEvent) throws IOException {
        new HelloApplication().startLogin(new Stage());
        Stage primaryStage = (Stage)accountTextField.getScene().getWindow();
        primaryStage.hide();
    }
}
