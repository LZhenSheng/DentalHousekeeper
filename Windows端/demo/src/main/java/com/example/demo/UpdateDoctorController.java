package com.example.demo;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.utils.StringUtils;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.example.demo.domain.Constant;
import com.example.demo.domain.Department;
import com.example.demo.domain.Doctor;
import com.example.demo.domain.Hospital;
import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.json.JSONArray;
import java.io.*;
import java.util.Base64;
import java.util.Iterator;
import java.util.Properties;

import static com.example.demo.util.ToastUtil.toast;

public class UpdateDoctorController {

    @FXML
    TextField name;
    @FXML
    TextField phone;
    @FXML
    TextField password;
    @FXML
    TextField sex;
    @FXML
    MenuButton type;
    @FXML
    TextField email;
    @FXML
    TextField grade;
    @FXML
    TextArea description;
    @FXML
    ImageView imageViwe;


    private void addMenu() throws UnirestException {
        HttpResponse<JsonNode> response = Unirest.post(Constant.uri + "findDepartmentsByHospitalId")
                .field("id", pid)
                .asJson();
        if (response.getStatus() == 200) {
//            System.out.println(response.getBody().toString());
            Gson gson = new Gson();
            JSONArray result = response.getBody().getArray();
            for (int i = 0; i < result.length(); i++) {
//                System.out.println(result.length() + result.get(i).toString());
                Department department = gson.fromJson(result.get(i).toString(), Department.class);
                MenuItem add = new MenuItem(department.getName());
                add.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        type.setText(department.getName());
                    }
                });
                type.getItems().add(add);
            }
        }
    }
    String pid=null;
    @FXML
    private void initialize() throws UnirestException {
        Properties prop = new Properties();
        try {
            if (new File("user.properties").exists()) {
                InputStream in = new BufferedInputStream(new FileInputStream("user.properties"));
                prop.load(in);
                pid = prop.getProperty("id");
                in.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        System.out.println("id==="+pid);
        addMenu();
        String id = null;
        try {
            if (new File("user.properties").exists()) {
                InputStream in = new BufferedInputStream(new FileInputStream("users.properties"));
                prop.load(in);
                Iterator<String> it = prop.stringPropertyNames().iterator();
                id=prop.getProperty("doctor_id");
//                System.out.println(prop.getProperty("id"));
                in.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        HttpResponse<JsonNode> response = Unirest.post(Constant.uri+"findDoctorByDoctorId")
                .field("id",id)
                .asJson();
        Gson gson = new Gson();
        JSONArray result = response.getBody().getArray();
        Doctor doctor = gson.fromJson(result.get(0).toString(), Doctor.class);
//        System.out.println("111"+response.getBody().toString());
//        System.out.println("222"+doctor.toString());
        name.setText(doctor.getName());
        phone.setText(doctor.getPhone());
        password.setText(doctor.getPassword());
        sex.setText(doctor.getGender()==1?"男":"女");
        type.setText(doctor.getRoom());
        grade.setText(doctor.getType());
        email.setText(doctor.getEmail());
        description.setText(doctor.getDescription());
        if(doctor.getAvatar()!=null){
            imageViwe.setImage(new Image(doctor.getAvatar()));
        }
    }

    public void add(ActionEvent actionEvent) throws UnirestException {
        Properties prop = new Properties();
        String id = null;
        try {
            if (new File("user.properties").exists()) {
                InputStream in = new BufferedInputStream(new FileInputStream("users.properties"));
                prop.load(in);
                id=prop.getProperty("doctor_id");
//                System.out.println(prop.getProperty("id"));
                in.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        HttpResponse<JsonNode> response = Unirest.post(Constant.uri+"updateDoctorMessageByDoctorId")
                .field("id",id)
                .field("name",name.getText())
                .field("phone",phone.getText())
                .field("room",type.getText())
                .field("type",grade.getText())
                .field("password",password.getText())
                .field("gender",sex.getText()=="男"?1:0)
                .field("description",description.getText())
                .field("email",email.getText())
                .asJson();
        toast("添加成功");
        Stage primaryStage = (Stage)name.getScene().getWindow();
        primaryStage.hide();
    }

    public void updateImage(MouseEvent mouseEvent) {
    }

}
