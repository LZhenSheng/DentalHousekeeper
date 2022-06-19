package com.example.demo;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.example.demo.domain.*;
import com.google.gson.Gson;
import com.jfoenix.controls.JFXButton;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.json.JSONArray;

import javax.security.auth.callback.Callback;
import java.io.*;
import java.util.Iterator;
import java.util.Properties;

import static com.example.demo.util.ToastUtil.toast;


public class MainHospitalController {
    String id = null;

    String uri=null;
    @FXML
    ImageView imageViwe;

    @FXML
    TableView<KV> tableView;

    @FXML
    TableView<KVV> tableView1;

    @FXML
    TableView<KVVV> tableView2;

    @FXML
    TableView<KVVVV> tableView3;
    @FXML
    private void initialize() throws UnirestException {
        tableView.setEditable(true);
        tableView1.setEditable(true);
        tableView2.setEditable(true);
        // 每个Table的列
        TableColumn firstNameCol = new TableColumn("头像");
        firstNameCol.setPrefWidth(96);
        // 设置分箱的类下面的属性名
        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<>("avator"));
        TableColumn secondNameCol = new TableColumn("姓名");
        secondNameCol.setPrefWidth(60);
        secondNameCol.setCellValueFactory(
                new PropertyValueFactory<>("name"));
        TableColumn thirdNameCol = new TableColumn("性别");
        thirdNameCol.setPrefWidth(60);
        thirdNameCol.setCellValueFactory(
                new PropertyValueFactory<>("sex"));
        TableColumn fourthNameCol = new TableColumn("手机号");
        fourthNameCol.setPrefWidth(100);
        fourthNameCol.setCellValueFactory(
                new PropertyValueFactory<>("phone"));
        TableColumn fifthNameCol = new TableColumn("e-mail");
        fifthNameCol.setPrefWidth(150);
        fifthNameCol.setCellValueFactory(
                new PropertyValueFactory<>("email"));
        TableColumn sixthNameCol = new TableColumn("级别");
        sixthNameCol.setPrefWidth(100);
        sixthNameCol.setCellValueFactory(
                new PropertyValueFactory<>("type"));
        TableColumn seventhNameCol = new TableColumn("科室");
        seventhNameCol.setPrefWidth(100);
        seventhNameCol.setCellValueFactory(
                new PropertyValueFactory<>("room"));
        TableColumn eightNameCol = new TableColumn("编辑");
        eightNameCol.setPrefWidth(150);
        eightNameCol.setStyle( "-fx-alignment: CENTER;");
        eightNameCol.setCellValueFactory(
                new PropertyValueFactory<>("edit"));
        TableColumn ninthNameCol=new TableColumn("删除");
        ninthNameCol.setStyle( "-fx-alignment: CENTER;");
        ninthNameCol.setPrefWidth(150);
        ninthNameCol.setCellValueFactory(
                new PropertyValueFactory<>("delete"));
        tableView.getColumns().addAll(firstNameCol,
                secondNameCol,
                thirdNameCol,
                fourthNameCol,
                fifthNameCol,
                sixthNameCol,
                seventhNameCol,
                eightNameCol,
                ninthNameCol);
        TableColumn firstNameCol1 = new TableColumn("日期");
        firstNameCol1.setPrefWidth(96);
        firstNameCol1.setStyle( "-fx-alignment: CENTER;");
        firstNameCol1.setCellValueFactory(
                new PropertyValueFactory<>("date"));
        TableColumn secondNameCol1 = new TableColumn("开始时间");
        secondNameCol1.setPrefWidth(100);
        secondNameCol1.setStyle( "-fx-alignment: CENTER;");
        secondNameCol1.setCellValueFactory(
                new PropertyValueFactory<>("startTime"));
        TableColumn thirdNameCol1 = new TableColumn("结束时间");
        thirdNameCol1.setPrefWidth(100);
        thirdNameCol1.setStyle( "-fx-alignment: CENTER;");
        thirdNameCol1.setCellValueFactory(
                new PropertyValueFactory<>("endTime"));
        TableColumn fourthNameCol1 = new TableColumn("总挂号数量");
        fourthNameCol1.setPrefWidth(100);
        fourthNameCol1.setStyle( "-fx-alignment: CENTER;");
        fourthNameCol1.setCellValueFactory(
                new PropertyValueFactory<>("num"));
        TableColumn fifthNameCol1 = new TableColumn("挂号费");
        fifthNameCol1.setPrefWidth(100);
        fifthNameCol1.setStyle( "-fx-alignment: CENTER;");
        fifthNameCol1.setCellValueFactory(
                new PropertyValueFactory<>("money"));
        TableColumn fixNameCol1 = new TableColumn("删除");
        fixNameCol1.setPrefWidth(170);
        fixNameCol1.setStyle( "-fx-alignment: CENTER;");
        fixNameCol1.setCellValueFactory(
                new PropertyValueFactory<>("delete"));
        TableColumn savenNameCol1 = new TableColumn("就诊类型");
        savenNameCol1.setPrefWidth(150);
        savenNameCol1.setStyle( "-fx-alignment: CENTER;");
        savenNameCol1.setCellValueFactory(
                new PropertyValueFactory<>("style"));
        TableColumn eightNameCol1 = new TableColumn("就诊牙医");
        eightNameCol1.setPrefWidth(150);
        eightNameCol1.setStyle( "-fx-alignment: CENTER;");
        eightNameCol1.setCellValueFactory(
                new PropertyValueFactory<>("doctor"));
        tableView1.getColumns().addAll(firstNameCol1,
                secondNameCol1,
                thirdNameCol1,
                fourthNameCol1,
                fifthNameCol1,
                eightNameCol1,
                savenNameCol1,
                fixNameCol1);
        TableColumn firstNameCol2 = new TableColumn("科室名称");
        firstNameCol2.setPrefWidth(96);
        firstNameCol2.setStyle( "-fx-alignment: CENTER;");
        firstNameCol2.setCellValueFactory(
                new PropertyValueFactory<>("name"));
        TableColumn secondNameCol2 = new TableColumn("科室简介");
        secondNameCol2.setPrefWidth(170);
        secondNameCol2.setStyle( "-fx-alignment: CENTER;");
        secondNameCol2.setCellValueFactory(
                new PropertyValueFactory<>("profile"));
        TableColumn thirdNameCol2 = new TableColumn("科室创建时间");
        thirdNameCol2.setPrefWidth(200);
        thirdNameCol2.setStyle( "-fx-alignment: CENTER;");
        thirdNameCol2.setCellValueFactory(
                new PropertyValueFactory<>("created"));
        TableColumn fourthNameCol2 = new TableColumn("科室上次更新时间");
        fourthNameCol2.setPrefWidth(200);
        fourthNameCol2.setStyle( "-fx-alignment: CENTER;");
        fourthNameCol2.setCellValueFactory(
                new PropertyValueFactory<>("updated"));
        TableColumn fifthNameCol2 = new TableColumn("编辑");
        fifthNameCol2.setPrefWidth(150);
        fifthNameCol2.setStyle( "-fx-alignment: CENTER;");
        fifthNameCol2.setCellValueFactory(
                new PropertyValueFactory<>("edit"));
        TableColumn fixNameCol2 = new TableColumn("删除");
        fixNameCol2.setPrefWidth(150);
        fixNameCol2.setStyle( "-fx-alignment: CENTER;");
        fixNameCol2.setCellValueFactory(
                new PropertyValueFactory<>("delete"));
        tableView2.getColumns().addAll(firstNameCol2,
                secondNameCol2,
                thirdNameCol2,
                fourthNameCol2,
                fifthNameCol2,
                fixNameCol2);
        TableColumn firstNameCol3 = new TableColumn("账单号");
        firstNameCol3.setPrefWidth(296);
        firstNameCol3.setStyle( "-fx-alignment: CENTER;");
        firstNameCol3.setCellValueFactory(
                new PropertyValueFactory<>("no"));
        TableColumn secondNameCol3 = new TableColumn("患者");
        secondNameCol3.setPrefWidth(300);
        secondNameCol3.setStyle( "-fx-alignment: CENTER;");
        secondNameCol3.setCellValueFactory(
                new PropertyValueFactory<>("patient"));
        TableColumn thirdNameCol3 = new TableColumn("金额");
        thirdNameCol3.setPrefWidth(200);
        thirdNameCol3.setStyle( "-fx-alignment: CENTER;");
        thirdNameCol3.setCellValueFactory(
                new PropertyValueFactory<>("money"));
        TableColumn fourthNameCol3 = new TableColumn("时间");
        fourthNameCol3.setPrefWidth(170);
        fourthNameCol3.setStyle( "-fx-alignment: CENTER;");
        fourthNameCol3.setCellValueFactory(
                new PropertyValueFactory<>("updated"));
        tableView3.getColumns().addAll(firstNameCol3,
                secondNameCol3,
                thirdNameCol3,
                fourthNameCol3);
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
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                HttpResponse<JsonNode> response = null;
                try {
                    response = Unirest.post(Constant.uri+"findHospitalsByHospitalId")
                            .field("id",id)
                            .asJson();
                } catch (UnirestException e) {
                    e.printStackTrace();
                }
                if(response.getStatus()==200){
                    JSONArray jsonArray=response.getBody().getArray();
                    Gson gson=new Gson();
                    Hospital hospital=gson.fromJson(jsonArray.get(0).toString(), Hospital.class);
//            System.out.println(jsonArray.get(0).toString());
                    if(hospital.getAvatar()!=null){
                        imageViwe.setImage(new Image(hospital.getAvatar()));
                    }
                    name.setText(hospital.getName());
                    province.setText(hospital.getProvince());
                    city.setText(hospital.getCity());
                    area.setText(hospital.getArea());
                }else{
                    toast("网络错误");
                }
            }
        });

//        System.out.println("id==="+id);
        fresh();
        fresh1();
        fresh2();
        fresh3();
    }

    private void fresh3() throws UnirestException {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                HttpResponse<JsonNode> response = null;
                try {
                    response = Unirest.post(Constant.uri + "findPaymentsByHospitalId")
                            .field("id", id)
                            .asJson();
                    ObservableList<KVVVV> data = FXCollections.observableArrayList();
                    if (response.getStatus() == 200) {
//            System.out.println(response.getBody().toString());
                        Gson gson = new Gson();
                        JSONArray result = response.getBody().getArray();
                        for (int i = 0; i < result.length(); i++) {
//                System.out.println(result.get(i).toString());
                            Payment payment = gson.fromJson(result.get(i).toString(), Payment.class);
                            data.add(new KVVVV(payment.getAppointment(),payment.getPatient(),payment.getMoney(),payment.getCreated_at().toString()));
                        }
                        tableView3.setItems(data);
                    }
                } catch (UnirestException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void fresh2() throws UnirestException {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpResponse<JsonNode> response = Unirest.post(Constant.uri + "findDepartmentsByHospitalId")
                            .field("id", id)
                            .asJson();
                    ObservableList<KVVV> data = FXCollections.observableArrayList();
                    if (response.getStatus() == 200) {
//            System.out.println(response.getBody().toString());
                        Gson gson = new Gson();
                        JSONArray result = response.getBody().getArray();
                        for (int i = 0; i < result.length(); i++) {
//                System.out.println(result.get(i).toString());
                            Department department = gson.fromJson(result.get(i).toString(), Department.class);
//                System.out.println(department.toString());
                            data.add(new KVVV(department.getId(),department.getName(),department.getProfile(),department.getCreated_at().toString(),department.getUpdated_at().toString(),"编辑","删除"));
                        }
                        tableView2.setItems(data);
                    }
                } catch (UnirestException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public void update(ActionEvent mouseEvent) throws  UnirestException {
        fresh();
    }

    private void fresh1() throws UnirestException {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                HttpResponse<JsonNode> response = null;
                try {
                    response = Unirest.post(Constant.uri + "findSchedulingsByHospitalId")
                            .field("id", id)
                            .asJson();
                } catch (UnirestException e) {
                    e.printStackTrace();
                }
                ObservableList<KVV> data = FXCollections.observableArrayList();
                if (response.getStatus() == 200) {
//            System.out.println(response.getBody().toString());
                    Gson gson = new Gson();
                    JSONArray result = response.getBody().getArray();
                    for (int i = 0; i < result.length(); i++) {
//                System.out.println(result.get(i).toString());
                        PreOrder preOrder = gson.fromJson(result.get(i).toString(), PreOrder.class);
                        data.add(new KVV(preOrder.getDoctor_id(),preOrder.getStyle(),preOrder.getId(),preOrder.getDate(),preOrder.getStart_time(),preOrder.getEnd_time(),String.valueOf(preOrder.getNumber()),String.valueOf(preOrder.getMoney()),"删除"));
//                System.out.println(preOrder.toString());
                    }
                    tableView1.setItems(data);
                }
            }
        });
    }

    private void fresh() throws UnirestException {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpResponse<JsonNode> response = Unirest.post(Constant.uri + "findDoctorsByHospitalId")
                            .field("id", id)
                            .asJson();
                    ObservableList<KV> data = FXCollections.observableArrayList();
                    if (response.getStatus() == 200) {
//            System.out.println(response.getBody().toString());
                        Gson gson = new Gson();
                        JSONArray result = response.getBody().getArray();
                        for (int i = 0; i < result.length(); i++) {
//                System.out.println(result.length() + result.get(i).toString());
                            Doctor doctor = gson.fromJson(result.get(i).toString(), Doctor.class);
//                System.out.println(doctor.getEmail());
                            if (doctor.getAvatar() == null) {
                                data.add(new KV(doctor.getId(),"http://42.192.116.184:8080/img.png", doctor.getName(),
                                        doctor.getGender() == 1 ? "男" : "女", doctor.getPhone(),
                                        doctor.getEmail(), doctor.getType(), doctor.getRoom(), "编辑","删除","排班"));
                            } else {
                                data.add(new KV(doctor.getId(),doctor.getAvatar(), doctor.getName(),
                                        doctor.getGender() == 1 ? "男" : "女", doctor.getPhone(),
                                        doctor.getEmail(), doctor.getType(), doctor.getRoom(), "编辑","删除","排班"));
                            }
                        }
                        tableView.setItems(data);
                    }
                } catch (UnirestException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void add(ActionEvent mouseEvent) throws IOException {
        new HelloApplication().startAddDoctor(new Stage());
    }

    public void exit(ActionEvent mouseEvent) throws IOException {
        new HelloApplication().startLogin(new Stage());
        Stage primaryStage = (Stage)tableView.getScene().getWindow();
        primaryStage.hide();
    }

    public void addScheduling(ActionEvent actionEvent) {
        try {
            new HelloApplication().startScheduling(new Stage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateScheduling(ActionEvent actionEvent) throws UnirestException {
        fresh1();
    }

    public void addDepartment(ActionEvent actionEvent) throws IOException {
        new HelloApplication().startDepartment(new Stage());
    }

    public void updateDepartment(ActionEvent actionEvent) throws UnirestException {
        fresh2();
    }

    public void updatePayment(ActionEvent actionEvent) throws UnirestException {
        fresh3();
    }

    @FXML
    TextField name;
    @FXML
    TextField province;
    @FXML
    TextField city;
    @FXML
    TextField area;
    public void summit(ActionEvent actionEvent) throws UnirestException {
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
        HttpResponse<JsonNode> response = Unirest.post(Constant.uri+"updateHospitalMessage")
                .field("avatar",uri)
                .field("name",name.getText())
                .field("province",province.getText())
                .field("city",city.getText())
                .field("area",area.getText())
                .field("id",id)
                .asJson();
        if(response.getStatus()==200){
            JSONArray jsonArray=response.getBody().getArray();
            Gson gson=new Gson();
            Result result=gson.fromJson(jsonArray.get(0).toString(), Result.class);
            if(result.getResult()==1){
                toast("更新成功");
            }else{
                toast("更新失败，请检查原因");
            }
        }else{
            toast("网络错误");
        }
    }

    public void updateImage(MouseEvent mouseEvent) {
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
        // 创建OSSClient实例。
        //创建异步任务
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

    @FXML
    TextField manageDoctor;
    public void manageDoctor(ActionEvent actionEvent) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpResponse<JsonNode> response = Unirest.post(Constant.uri + "findDoctorsByHospitalId")
                            .field("id", id)
                            .asJson();
                    ObservableList<KV> data = FXCollections.observableArrayList();
                    if (response.getStatus() == 200) {
//            System.out.println(response.getBody().toString());
                        Gson gson = new Gson();
                        JSONArray result = response.getBody().getArray();
                        for (int i = 0; i < result.length(); i++) {
//                System.out.println(result.length() + result.get(i).toString());
                            Doctor doctor = gson.fromJson(result.get(i).toString(), Doctor.class);
//                System.out.println(doctor.getEmail());
                            if(doctor.getName().contains(manageDoctor.getText())||doctor.getPhone().contains(manageDoctor.getText())||doctor.getEmail().contains(manageDoctor.getText())){
                                if (doctor.getAvatar() == null) {
                                    data.add(new KV(doctor.getId(),"http://42.192.116.184:8080/img.png", doctor.getName(),
                                            doctor.getGender() == 1 ? "男" : "女", doctor.getPhone(),
                                            doctor.getEmail(), doctor.getType(), doctor.getRoom(), "编辑","删除","排班"));
                                } else {
                                    data.add(new KV(doctor.getId(),doctor.getAvatar(), doctor.getName(),
                                            doctor.getGender() == 1 ? "男" : "女", doctor.getPhone(),
                                            doctor.getEmail(), doctor.getType(), doctor.getRoom(), "编辑","删除","排班"));
                                }
                            }
                        }
                        tableView.setItems(data);
                    }
                } catch (UnirestException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @FXML
    TextField manageScheduling;
    public void manageScheduling(ActionEvent actionEvent) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                HttpResponse<JsonNode> response = null;
                try {
                    response = Unirest.post(Constant.uri + "findSchedulingsByHospitalId")
                            .field("id", id)
                            .asJson();
                } catch (UnirestException e) {
                    e.printStackTrace();
                }
                ObservableList<KVV> data = FXCollections.observableArrayList();
                if (response.getStatus() == 200) {
//            System.out.println(response.getBody().toString());
                    Gson gson = new Gson();
                    JSONArray result = response.getBody().getArray();
                    for (int i = 0; i < result.length(); i++) {
//                System.out.println(result.get(i).toString());
                        PreOrder preOrder = gson.fromJson(result.get(i).toString(), PreOrder.class);
                        if(preOrder.getDate().contains(manageScheduling.getText())||preOrder.getDoctor_id().contains(manageScheduling.getText())||preOrder.getStyle().contains(manageScheduling.getText())){
                            data.add(new KVV(preOrder.getDoctor_id(),preOrder.getStyle(),preOrder.getId(),preOrder.getDate(),preOrder.getStart_time(),preOrder.getEnd_time(),String.valueOf(preOrder.getNumber()),String.valueOf(preOrder.getMoney()),"删除"));
                        }
//                System.out.println(preOrder.toString());
                    }
                    tableView1.setItems(data);
                }
            }
        });
    }

    @FXML
    TextField manageDepartment;
    public void manageDepartment(ActionEvent actionEvent) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpResponse<JsonNode> response = Unirest.post(Constant.uri + "findDepartmentsByHospitalId")
                            .field("id", id)
                            .asJson();
                    ObservableList<KVVV> data = FXCollections.observableArrayList();
                    if (response.getStatus() == 200) {
//            System.out.println(response.getBody().toString());
                        Gson gson = new Gson();
                        JSONArray result = response.getBody().getArray();
                        for (int i = 0; i < result.length(); i++) {
//                System.out.println(result.get(i).toString());
                            Department department = gson.fromJson(result.get(i).toString(), Department.class);
//                System.out.println(department.toString());
                            if(department.getName().contains(manageDepartment.getText())||department.getProfile().contains(manageDepartment.getText())){
                                data.add(new KVVV(department.getId(),department.getName(),department.getProfile(),department.getCreated_at().toString(),department.getUpdated_at().toString(),"编辑","删除"));
                            }
                        }
                        tableView2.setItems(data);
                    }
                } catch (UnirestException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @FXML
    TextField managePayment;
    public void managePayment(ActionEvent actionEvent) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                HttpResponse<JsonNode> response = null;
                try {
                    response = Unirest.post(Constant.uri + "findPaymentsByHospitalId")
                            .field("id", id)
                            .asJson();
                    ObservableList<KVVVV> data = FXCollections.observableArrayList();
                    if (response.getStatus() == 200) {
//            System.out.println(response.getBody().toString());
                        Gson gson = new Gson();
                        JSONArray result = response.getBody().getArray();
                        for (int i = 0; i < result.length(); i++) {
//                System.out.println(result.get(i).toString());
                            Payment payment = gson.fromJson(result.get(i).toString(), Payment.class);
                            if(payment.getPatient().contains(managePayment.getText())||payment.getCreated_at().toString().contains(managePayment.getText())){
                                data.add(new KVVVV(payment.getAppointment(),payment.getPatient(),payment.getMoney(),payment.getCreated_at().toString()));
                            }
                        }
                        tableView3.setItems(data);
                    }
                } catch (UnirestException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public class KV {
        private ImageView avator;
        private Label name;
        private Label sex;
        private Label phone;
        private Label email;
        private Label type;
        private Label room;
        private JFXButton edit;
        private JFXButton delete;

        public JFXButton getDelete() {
            return delete;
        }

        public void setDelete(String delete) {
            this.delete = new JFXButton(delete);
        }

        public JFXButton getEdit() {
            return edit;
        }

        public void setEdit(String edit) {
            this.edit = new JFXButton(edit);
        }

        public ImageView getAvator() {
            return avator;
        }

        public void setAvator(String avator) {
            this.avator = new ImageView(new Image(avator));
        }

        public Label getName() {
            return name;
        }

        public void setName(String name) {
            this.name=new Label(name);
        }

        public Label getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = new Label(sex);
        }

        public Label getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = new Label(phone);
        }

        public Label getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = new Label(email);
        }

        public Label getType() {
            return type;
        }

        public void setType(String grade) {
            this.type = new Label(grade);
        }

        public Label getRoom() {
            return room;
        }

        public void setRoom(String room) {
            this.room = new Label(room);
        }

        public KV(String id,String avator, String name, String sex,
                  String phone, String email, String grade, String room,
                  String edit,String delete,String schduling) {
            this.avator = new ImageView(new Image(avator));
            this.avator.setFitWidth(100);
            this.avator.setFitHeight(100);
            this.name = new Label(name);
            this.name.setPrefWidth(60);
            this.name.setPrefHeight(100);
            this.name.setAlignment(Pos.CENTER);
            this.sex = new Label(sex);
            this.sex.setPrefWidth(60);
            this.sex.setPrefHeight(100);
            this.sex.setAlignment(Pos.CENTER);
            this.phone = new Label(phone);
            this.phone.setPrefWidth(100);
            this.phone.setPrefHeight(100);
            this.phone.setAlignment(Pos.CENTER);
            this.email = new Label(email);
            this.email.setPrefWidth(150);
            this.email.setPrefHeight(100);
            this.email.setAlignment(Pos.CENTER);
            this.type = new Label(grade);
            this.type.setPrefWidth(100);
            this.type.setPrefHeight(100);
            this.type.setAlignment(Pos.CENTER);
            this.room =new Label(room);
            this.room.setPrefWidth(100);
            this.room.setPrefHeight(100);
            this.room.setAlignment(Pos.CENTER);
            this.edit = new JFXButton(edit);
            this.edit.setAlignment(Pos.CENTER);
            this.edit.getStyleClass().add("btn");
            this.edit.getStyleClass().add("btn-info");
            this.delete=new JFXButton(delete);
            this.delete.getStyleClass().add("btn");
            this.delete.getStyleClass().add("btn-danger");
            this.delete.setAlignment(Pos.CENTER);
            this.edit.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    Properties prop = new Properties();
                    try {
                        FileOutputStream oFile = new FileOutputStream("users.properties", false);
                        prop.setProperty("doctor_id", id);
                        prop.store(oFile, null);
                        oFile.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    };
                    try {
                        new HelloApplication().startUpdateDoctor(new Stage());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            this.delete.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    HttpResponse<JsonNode> response = null;
                    try {
                        response = Unirest.post(Constant.uri + "deleteDoctorMessage")
                                .field("id", id)
                                .asJson();
                    } catch (UnirestException e) {
                        e.printStackTrace();
                    }
                    if (response.getStatus() == 200) {
//                        System.out.println("删除成功");
                        try {
                            fresh();
                        } catch (UnirestException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }

        @Override
        public String toString() {
            return "KV{" +
                    "avator=" + avator +
                    ", name='" + name + '\'' +
                    ", sex='" + sex + '\'' +
                    ", phone='" + phone + '\'' +
                    ", emial='" + email + '\'' +
                    ", type='" + type + '\'' +
                    ", room='" + room + '\'' +
                    '}';
        }
    }

    public class KVV {
        private Label date;
        private Label startTime;
        private Label endTime;
        private Label num;
        private Label money;
        private Label style;
        private Label doctor;
        private JFXButton delete;

        public Label getDoctor() {
            return doctor;
        }

        public void setDoctor(String doctor) {
            this.doctor = new Label(doctor);
        }

        public Label getStyle() {
            return style;
        }

        public void setStyle(String  style) {
            this.style = new Label(style);
        }

        public JFXButton getDelete() {
            return delete;
        }

        public void setDelete(String delete) {
            this.delete = new JFXButton(delete);
        }

        public KVV(String doctor,String style,String id, String date, String startTime, String endTime, String num, String money, String delete) {
            this.doctor=new Label(doctor);
            this.style = new Label(style);
            this.date = new Label(date);
            this.startTime = new Label(startTime);
            this.endTime = new Label(endTime);
            this.num = new Label(num);
            this.money = new Label(money+"元");
            this.delete=new JFXButton(delete);
            this.delete.getStyleClass().add("btn");
            this.delete.getStyleClass().add("btn-danger");
            this.delete.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    try {
                        HttpResponse<JsonNode> response = Unirest.post(Constant.uri + "deletePreOrderByPreOrderId")
                                .field("id", id)
                                .asJson();
                        if(response.getStatus()==200){
//                            System.out.println(response.getBody());
                            toast("删除成功");
                        }
                    } catch (UnirestException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        public Label getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = new Label(date);
        }

        public Label getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = new Label(startTime);
        }

        public Label getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = new Label(endTime);
        }

        public Label getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = new Label(num);
        }

        public Label getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = new Label(money);
        }
    }

    public class KVVV {
        private Label name;
        private Label profile;
        private Label created;
        private Label updated;
        private JFXButton edit;
        private JFXButton delete;

        public Label getName() {
            return name;
        }

        public void setName(String name) {
            this.name = new Label(name);
        }

        public Label getProfile() {
            return profile;
        }

        public void setProfile(String profile) {
            this.profile = new Label(profile);
        }

        public Label getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = new Label(created);
        }

        public Label getUpdated() {
            return updated;
        }

        public void setUpdated(String updated) {
            this.updated = new Label(updated);
        }

        public JFXButton getEdit() {
            return edit;
        }

        public void setEdit(String edit) {
            this.edit = new JFXButton(edit);
        }

        public JFXButton getDelete() {
            return delete;
        }

        public void setDelete(String delete) {
            this.delete = new JFXButton(delete);
        }

        public KVVV(String id, String name, String profile, String createdAt, String updatedAt, String edit, String delete) {
            this.name=new Label(name);
            this.profile=new Label(profile);
            this.created=new Label(createdAt);
            this.updated=new Label(updatedAt);
            this.edit=new JFXButton(edit);
            this.edit.getStyleClass().add("btn");
            this.edit.getStyleClass().add("btn-info");
            this.edit.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    Properties prop = new Properties();
                    try {
                        FileOutputStream oFile = new FileOutputStream("users.properties", false);
                        prop.setProperty("department_id", id);
                        prop.store(oFile, null);
                        oFile.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    };
                    try {
                        new HelloApplication().startUpdateDepartment(new Stage());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            this.delete=new JFXButton(delete);
            this.delete.getStyleClass().add("btn");
            this.delete.getStyleClass().add("btn-danger");
            this.delete.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    try {
                        HttpResponse<JsonNode> response = Unirest.post(Constant.uri + "deleteDepartmentByDepartmentId")
                                .field("id", id)
                                .asJson();
                        if(response.getStatus()==200){
//                            System.out.println(response.getBody());
                            toast("删除成功");
                        }
                    } catch (UnirestException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public class KVVVV {
        private Label no;
        private Label patient;
        private Label money;
        private Label updated;

        public Label getUpdated() {
            return updated;
        }

        public void setUpdated(String updated) {
            this.updated = new Label(updated);
        }

        public Label getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = new Label(no);
        }

        public Label getPatient() {
            return patient;
        }

        public void setPatient(String patient) {
            this.patient = new Label(patient);
        }

        public Label getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = new Label(money);
        }


        public KVVVV(String no,String patient,double money,String createdAt) {
            this.no=new Label(no);
            this.patient=new Label(patient);
            this.money=new Label(String.valueOf(money));
            this.updated=new Label(createdAt);
        }
    }
}
