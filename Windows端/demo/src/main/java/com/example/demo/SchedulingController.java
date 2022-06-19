package com.example.demo;

import com.example.demo.domain.Constant;
import com.example.demo.domain.Doctor;
import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.stage.Stage;
import org.json.JSONArray;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.Properties;

import static com.example.demo.util.ToastUtil.toast;

public class SchedulingController {

    @FXML
    MenuItem menuitem1;
    @FXML
    MenuItem menuitem2;
    @FXML
    MenuButton menubutton;
    @FXML
    MenuButton menubutton1;
    @FXML
    DatePicker datePicker;
    String id;
    @FXML
    TextField start_hour;
    @FXML
    TextField start_minute;
    @FXML
    TextField end_hour;
    @FXML
    TextField end_minute;
    @FXML
    TextField num;
    @FXML
    TextField money;

    @FXML
    private void initialize() throws UnirestException {
        Properties prop = new Properties();
        try {
            if (new File("user.properties").exists()) {
                InputStream in = new BufferedInputStream(new FileInputStream("user.properties"));
                prop.load(in);
                id = prop.getProperty("id");
                in.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        System.out.println("id==="+id);
        addMenu();
        menuitem1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                menubutton.setText("线下问诊");
            }
        });
        menuitem2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                menubutton.setText("远程问诊");
            }
        });
    }

    private void addMenu() throws UnirestException {
        HttpResponse<JsonNode> response = Unirest.post(Constant.uri + "findDoctorsByHospitalId")
                .field("id", id)
                .asJson();
        if (response.getStatus() == 200) {
//            System.out.println(response.getBody().toString());
            Gson gson = new Gson();
            JSONArray result = response.getBody().getArray();
            for (int i = 0; i < result.length(); i++) {
//                System.out.println(result.length() + result.get(i).toString());
                Doctor doctor = gson.fromJson(result.get(i).toString(), Doctor.class);
//                System.out.println(doctor.getEmail());
                MenuItem add = new MenuItem(doctor.getName());
                add.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        menubutton1.setText(doctor.getName());
                    }
                });
                menubutton1.getItems().add(add);
            }
        }
    }

    public void summit(ActionEvent actionEvent) throws UnirestException {
        if(datePicker.getValue().toString()==null||start_hour.getText()==null||start_minute.getText()==null
        ||end_hour.getText()==null||end_minute.getText()==null||num.getText()==null||money.getText()==null){
            toast("请补全信息");
        }else{
            if(Integer.valueOf(start_hour.getText())>Integer.valueOf(end_hour.getText())||
                    (Integer.valueOf(start_hour.getText())==Integer.valueOf(end_hour.getText())&&
                            (Integer.valueOf(start_minute.getText())>Integer.valueOf(end_minute.getText())))){
                toast("请输入正确的时间");
            }else{
//                System.out.println(menubutton.getText()+"2323");
//                System.out.println(menubutton.getText());
                HttpResponse<JsonNode> response = Unirest.post(Constant.uri+"createPreOrderByHospital")
                        .field("date", datePicker.getValue().toString())
                        .field("doctorId",menubutton1.getText())
                        .field("hospital",id)
                        .field("startTime",start_hour.getText()+"-"+start_minute.getText())
                        .field("endTime",end_hour.getText()+"-"+end_minute.getText())
                        .field("number",Integer.valueOf(num.getText()))
                        .field("money",Double.valueOf(money.getText()))
                        .field("style",menubutton.getText())
                        .asJson();
                if(response.getStatus()==200){
//                    System.out.println("添加成功");
                    Stage primaryStage = (Stage)datePicker.getScene().getWindow();
                    primaryStage.hide();
                }
            }
        }
    }
}
