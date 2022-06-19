package com.example.demo;

import com.example.demo.domain.Constant;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import static com.example.demo.util.ToastUtil.toast;

public class RegisterController {

    @FXML
    TextField name;

    @FXML
    TextField phone;

    @FXML
    TextField code;
    @FXML
    TextField password;

    @FXML
    TextField province;

    @FXML
    TextField city;

    @FXML
    TextField area;

    @FXML
    TextField pid;

    public void sendSMS() throws IOException, UnirestException {
        if(phone.getText()==null||phone.getText().length()==0){
            Stage stage=new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(RegisterController.class.getResource("register-error-alert-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(),297,196);
            stage.setResizable(false);
            stage.setTitle("牙医管家");
            stage.setScene(scene);
            stage.show();
        }else{
            HttpResponse<JsonNode> response = Unirest.post(Constant.uri+"sendCode")
                    .field("phone",phone.getText())
                    .asJson();
            if(response.getStatus()==200){
                toast("发送成功");
            }else{
                toast("发送失败");
            }
//            System.out.println(response.getBody());
        }
    }

    public void register(ActionEvent actionEvent) throws UnirestException, IOException {
        if(name.getText()==null||name.getText().length()==0){
            toast("请输入医院名");
        }else if(phone.getText()==null||phone.getText().length()!=11){
            toast("请输入手机号");
        }else if(code.getText()==null||code.getText().length()!=4){
            toast("请输入验证码");
        }else if(province.getText()==null||province.getText().length()==0){
            toast("请输入省份");
        }else if(city.getText()==null||city.getText().length()==0){
            toast("请输入城市");
        }else if(area.getText()==null||area.getText().length()==0){
            toast("请输入区域");
        }else if(pid.getText()==null||pid.getText().length()==0) {
            toast("请输入PID");
        }else{
                HttpResponse<JsonNode> response = Unirest.post(Constant.uri+"updateHospital")
                        .field("phone",phone.getText())
                        .field("name",name.getText())
                        .field("code",code.getText())
                        .field("password",password.getText())
                        .field("province",province.getText())
                        .field("city",city.getText())
                        .field("area",area.getText())
                        .field("pid",pid.getText())
                        .asJson();
                if(response.getStatus()==200){
                    toast("注册成功");
                }else{
                    toast("注册失败");
                }
//                System.out.println(response.getBody());
                Stage primaryStage = (Stage)name.getScene().getWindow();
                primaryStage.hide();
                new HelloApplication().startLogin(new Stage());
            }
    }
}
