package com.example.demo;

import com.example.demo.domain.Constant;
import com.example.demo.domain.Hospital;
import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONArray;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import static com.example.demo.util.ToastUtil.toast;


public class LoginHospitalController {

    @FXML
    TextField accountTextField;
    @FXML
    PasswordField passwordTextField;
    @FXML
    Button loginButton;

    @FXML
    private void initialize() throws UnirestException {
        loginButton.setStyle("");
    }
    @FXML
    protected void onLoginButton() throws Exception {
//        System.out.println(accountTextField.getText().toString());
//        System.out.println(passwordTextField.getText().toString());
//        System.out.println("前往主页面");
        if(accountTextField.getText()==null||accountTextField.getText().length()!=11){
            toast("请输入手机号");
        }else if(passwordTextField.getText()==null||passwordTextField.getText().length()==0){
            toast("请输入密码");
        }else{
            HttpResponse<JsonNode> response = Unirest.post(Constant.uri + "loginHospital")
                    .field("phone", accountTextField.getText())
                    .field("password", passwordTextField.getText())
                    .asJson();
            if (response.getStatus() == 200) {
//            Hospital hospital=response.getBody().getObject();
//                System.out.println();
                Gson gson = new Gson();
                JSONArray result = response.getBody().getArray();
                Hospital hospital = gson.fromJson(result.get(0).toString(), Hospital.class);
//                System.out.println(hospital.toString());
                Properties prop = new Properties();
                try {
                    FileOutputStream oFile = new FileOutputStream("user.properties", false);
                    prop.setProperty("id", hospital.getId());
                    prop.store(oFile, null);
                    oFile.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Platform.runLater(() -> {
                    Stage primaryStage = (Stage)loginButton.getScene().getWindow();
                    //当前窗口隐藏
                    primaryStage.hide();
                    try {
                        new HelloApplication().startHospitalMain(new Stage());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
    }

    public void login(ActionEvent actionEvent) throws IOException {
        new HelloApplication().startLoginDoctor(new Stage());
        Stage primaryStage = (Stage)loginButton.getScene().getWindow();
        primaryStage.hide();
    }

    public void register(ActionEvent actionEvent) throws IOException {
        new HelloApplication().startRegister(new Stage());
    }
}