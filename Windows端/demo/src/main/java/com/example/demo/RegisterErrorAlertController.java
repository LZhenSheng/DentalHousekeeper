package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class RegisterErrorAlertController {

    @FXML
    Button button;

    public void confirm() {
        Stage primaryStage = (Stage)button.getScene().getWindow();
        //当前窗口隐藏
        primaryStage.hide();
    }
}
