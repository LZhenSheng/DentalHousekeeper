package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;

public class HelloApplication extends Application {

    String path="path";
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hospital-login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 318);
        stage.setTitle("牙医管家");
        stage.getIcons().add(new Image(path));
        stage.setScene(scene);
        stage.getScene().getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public void startMain(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),970,597);
        stage.setResizable(false);
        stage.setTitle("牙医管家");
        stage.getIcons().add(new Image(path));
        stage.setScene(scene);
        stage.getScene().getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        stage.show();
    }

    public void startRegister(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("register-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),415,588);
        stage.setResizable(false);
        stage.setTitle("牙医管家");
        stage.getIcons().add(new Image(path));
        stage.setScene(scene);
        stage.getScene().getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        stage.show();
    }

    public void startLogin(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hospital-login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 318);
        stage.setTitle("牙医管家");
        stage.getIcons().add(new Image(path));
        stage.setScene(scene);
        stage.getScene().getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        stage.show();
    }

    public void startAddDoctor(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("add-doctor-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 970, 597);
        stage.setTitle("牙医管家");
        stage.getIcons().add(new Image(path));
        stage.setScene(scene);
        stage.getScene().getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        stage.show();
    }

    public void startHospitalMain(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-hospital-view2.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 970, 597);
        stage.setTitle("牙医管家");
        stage.getIcons().add(new Image(path));
        stage.setScene(scene);
        stage.getScene().getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        stage.show();
    }

    public void startUpdateDoctor(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("update-doctor-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 970, 597);
        stage.setTitle("牙医管家");
        stage.getIcons().add(new Image(path));
        stage.setScene(scene);
        stage.getScene().getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        stage.show();
    }

    public void startLoginDoctor(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("doctor-login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 402, 320);
        stage.setTitle("牙医管家");
        stage.getIcons().add(new Image(path));
        stage.setScene(scene);
        stage.getScene().getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        stage.show();
    }

    public void startSchedulingList(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("scheduling-list-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 970, 597);
        stage.setTitle("牙医管家");
        stage.getIcons().add(new Image(path));
        stage.setScene(scene);
        stage.getScene().getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        stage.show();
    }

    public void startScheduling(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("scheduling-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 970, 597);
        stage.setTitle("牙医管家");
        stage.getIcons().add(new Image(path));
        stage.setScene(scene);
        stage.getScene().getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        stage.show();
    }

    public void startDepartment(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("add-department-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 970, 597);
        stage.setTitle("牙医管家");
        stage.getIcons().add(new Image(path));
        stage.setScene(scene);
        stage.getScene().getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        stage.show();
    }

    public void startDoctorMain(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-doctor-view2.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 970, 597);
        stage.setTitle("牙医管家");
        stage.getIcons().add(new Image(path));
        stage.setScene(scene);
        stage.getScene().getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        stage.show();
    }

    public void startMainPatient(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-patient-view2.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 970, 597);
        stage.setTitle("牙医管家");
        stage.getIcons().add(new Image(path));
        stage.setScene(scene);
        stage.getScene().getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        stage.show();
    }

    public void startDisplay(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 970, 597);
        stage.setTitle("牙医管家");
        stage.getIcons().add(new Image(path));
        stage.setScene(scene);
        stage.getScene().getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        stage.show();
    }

    public void startUpdateDepartment(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("update-department-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 970, 597);
        stage.setTitle("牙医管家");
        stage.getIcons().add(new Image(path));
        stage.setScene(scene);
        stage.getScene().getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        stage.show();
    }

    public void startERecord(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("e-record-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 970, 597);
        stage.setTitle("牙医管家");
        stage.getIcons().add(new Image(path));
        stage.setScene(scene);
        stage.getScene().getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        stage.show();
    }
}