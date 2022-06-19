package com.example.demo;

import com.example.demo.domain.Constant;
import com.example.demo.domain.Result;
import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONArray;

import java.io.*;
import java.util.Iterator;
import java.util.Properties;

import static com.example.demo.util.ToastUtil.toast;

public class AddDepartmentController {

    @FXML
    TextField name;

    @FXML
    TextArea profile;

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
        HttpResponse<JsonNode> response = Unirest.post(Constant.uri+"addDepartment")
                .field("name",name.getText())
                .field("profile",profile.getText())
                .field("hospital",id)
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
