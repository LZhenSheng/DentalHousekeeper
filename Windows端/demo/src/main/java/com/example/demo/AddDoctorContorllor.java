package com.example.demo;
import com.aliyun.oss.model.PutObjectResult;
import com.example.demo.domain.*;
import javafx.event.EventHandler;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import org.joda.time.DateTime;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.utils.StringUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.opencv.core.Mat;

import java.io.*;
import java.util.Base64;
import java.util.Iterator;
import java.util.Properties;

import static com.example.demo.util.ToastUtil.toast;

public class AddDoctorContorllor {

    String uri=null;

    @FXML
    ImageView imageViwe;
    @FXML
    TextField name;
    @FXML
    TextField password;
    @FXML
    TextField grade;
    @FXML
    TextField phone;
    @FXML
    TextField sex;
    @FXML
    TextField email;
    @FXML
    TextArea description;
    @FXML
    MenuButton type;
    String id;

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
    }

    private void addMenu() throws UnirestException {
        HttpResponse<JsonNode> response = Unirest.post(Constant.uri + "findDepartmentsByHospitalId")
                .field("id", id)
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

    public void updateImage(MouseEvent mouseEvent) throws Exception {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(new Stage());
//        System.out.println(file.getAbsolutePath());
//        imageViwe.setImage(new Image("file:" + file.getAbsolutePath()));
        String endpoint = "endpoint";
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
        String accessKeyId = "accessKeyId";
        String accessKeySecret = "accessKeySecret";
        String bucketName = "bucketName";
        String uriHeader="uriHeader";
        new Thread(new Runnable() {

            @Override
            public void run() {
                OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
                if(file.getAbsolutePath()!=null) {
                    ossClient.putObject(bucketName,file.getName(), file);
                    uri=uriHeader+file.getName();
                    imageViwe.setImage(new Image(uri));
                }
            }
        }).start();
    }

    public void add(ActionEvent actionEvent) throws UnirestException, IOException {
        Properties prop = new Properties();
        String id = null;
        try {
            if (new File("user.properties").exists()) {
                InputStream in = new BufferedInputStream(new FileInputStream("user.properties"));
                prop.load(in);
                Iterator<String> it = prop.stringPropertyNames().iterator();
                id=prop.getProperty("id");
//                System.out.println(prop.getProperty("id"));
                in.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        int sex1;
        if(sex.getText().equals("男")){
            sex1=1;
        }else{
            sex1=0;
        }
        HttpResponse<JsonNode> response = Unirest.post(Constant.uri+"addDoctor")
                .field("name",name.getText())
                .field("phone",phone.getText())
                .field("room",type.getText())
                .field("type",grade.getText())
                .field("password",password.getText())
                .field("gender",sex1)
                .field("description",description.getText())
                .field("hospital",id)
                .field("email",email.getText())
                .field("avatar",uri)
                .asJson();
        if(response.getStatus()==200){
            JSONArray jsonArray=response.getBody().getArray();
            Gson gson=new Gson();
            Result result=gson.fromJson(jsonArray.get(0).toString(), Result.class);
            if(result.getResult()==1){
                toast("插入成功");
                Stage primaryStage = (Stage)name.getScene().getWindow();
                primaryStage.hide();
            }else{
                toast("插入失败，请检查原因");
            }
        }else{
            toast("网络错误");
        }

    }
}
