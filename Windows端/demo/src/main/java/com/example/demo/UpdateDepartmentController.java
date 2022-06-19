package com.example.demo;

import com.example.demo.domain.Constant;
import com.example.demo.domain.Department;
import com.example.demo.domain.Doctor;
import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.json.JSONArray;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;

import static com.example.demo.util.ToastUtil.toast;

public class UpdateDepartmentController {

    @FXML
    TextArea profile;
    @FXML
    TextField name;

    @FXML
    private void initialize() throws UnirestException {
        Properties prop = new Properties();
        String id = null;
        try {
            if (new File("users.properties").exists()) {
                InputStream in = new BufferedInputStream(new FileInputStream("users.properties"));
                prop.load(in);
                Iterator<String> it = prop.stringPropertyNames().iterator();
                id=prop.getProperty("department_id");
//                System.out.println(id+"updateDepartmentController");
                in.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        HttpResponse<JsonNode> response = Unirest.post(Constant.uri+"findDepartmentByDepartmentId")
                .field("id",id)
                .asJson();
        Gson gson = new Gson();
        JSONArray result = response.getBody().getArray();
        Department department = gson.fromJson(result.get(0).toString(), Department.class);
        name.setText(department.getName());
        profile.setText(department.getProfile());
    }

    public void add(ActionEvent actionEvent) throws UnirestException {
        Properties prop = new Properties();
        String id = null;
        try {
            if (new File("users.properties").exists()) {
                InputStream in = new BufferedInputStream(new FileInputStream("users.properties"));
                prop.load(in);
                id=prop.getProperty("department_id");
//                System.out.println(prop.getProperty("id"));
                in.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        HttpResponse<JsonNode> response = Unirest.post(Constant.uri+"updateDepartmentByDepartmentId")
                .field("id",id)
                .field("name",name.getText())
                .field("profile",profile.getText())
                .asJson();
        toast("添加成功");
        Stage primaryStage = (Stage)name.getScene().getWindow();
        primaryStage.hide();
    }
}
