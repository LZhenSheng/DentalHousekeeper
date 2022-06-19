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
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.json.JSONArray;

import java.io.*;
import java.util.Properties;

import static com.example.demo.util.ToastUtil.toast;

public class MainDoctorContoller {

    String id=null;

    @FXML
    TextField name;
    @FXML
    TextField birthday;
    @FXML
    TextField sex;
    @FXML
    TextField age;
    @FXML
    TextField email;
    @FXML
    TextArea description;
    @FXML
    TextField province;
    @FXML
    TextField city;
    @FXML
    TextField area;
    @FXML
    ImageView imageViwe;
    @FXML
    TableView<KV> tableView;
    String uri=null;
    @FXML
    TableView<KV> tableView1;

    @FXML
    TableView<KVV> tableView2;
    @FXML
    TableView<KVVV> tableView3;

    @FXML
    private void initialize() throws UnirestException {
        tableView.setEditable(true);
        tableView1.setEditable(true);
        tableView2.setEditable(true);
        tableView3.setEditable(true);
        // 每个Table的列
        TableColumn firstNameCol = new TableColumn("名字");
        firstNameCol.setPrefWidth(216);
        firstNameCol.setStyle( "-fx-alignment: CENTER;");
        // 设置分箱的类下面的属性名
        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<>("name"));
        TableColumn secondNameCol = new TableColumn("性别");
        secondNameCol.setPrefWidth(100);
        secondNameCol.setStyle( "-fx-alignment: CENTER;");
        secondNameCol.setCellValueFactory(
                new PropertyValueFactory<>("sex"));
        TableColumn thirdNameCol = new TableColumn("手机");
        thirdNameCol.setPrefWidth(200);
        thirdNameCol.setStyle( "-fx-alignment: CENTER;");
        thirdNameCol.setCellValueFactory(
                new PropertyValueFactory<>("phone"));
        TableColumn fourthNameCol = new TableColumn("日期");
        fourthNameCol.setPrefWidth(300);
        fourthNameCol.setStyle( "-fx-alignment: CENTER;");
        fourthNameCol.setCellValueFactory(
                new PropertyValueFactory<>("date"));
        TableColumn fifthNameCol = new TableColumn("进入");
        fifthNameCol.setPrefWidth(150);
        fifthNameCol.setStyle( "-fx-alignment: CENTER;");
        fifthNameCol.setCellValueFactory(
                new PropertyValueFactory<>("into"));
        tableView.getColumns().addAll(
                firstNameCol,
                secondNameCol,
                thirdNameCol,
                fourthNameCol,
                fifthNameCol);
        TableColumn firstNameCol1 = new TableColumn("名字");
        firstNameCol1.setPrefWidth(216);
        firstNameCol1.setStyle( "-fx-alignment: CENTER;");
        // 设置分箱的类下面的属性名
        firstNameCol1.setCellValueFactory(
                new PropertyValueFactory<>("name"));
        TableColumn secondNameCol1 = new TableColumn("性别");
        secondNameCol1.setPrefWidth(100);
        secondNameCol1.setStyle( "-fx-alignment: CENTER;");
        secondNameCol1.setCellValueFactory(
                new PropertyValueFactory<>("sex"));
        TableColumn thirdNameCol1 = new TableColumn("手机");
        thirdNameCol1.setPrefWidth(200);
        thirdNameCol1.setStyle( "-fx-alignment: CENTER;");
        thirdNameCol1.setCellValueFactory(
                new PropertyValueFactory<>("phone"));
        TableColumn fourthNameCol1 = new TableColumn("日期");
        fourthNameCol1.setPrefWidth(300);
        fourthNameCol1.setStyle( "-fx-alignment: CENTER;");
        fourthNameCol1.setCellValueFactory(
                new PropertyValueFactory<>("date"));
        TableColumn fifthNameCol1 = new TableColumn("进入");
        fifthNameCol1.setPrefWidth(150);
        fifthNameCol1.setStyle( "-fx-alignment: CENTER;");
        fifthNameCol1.setCellValueFactory(
                new PropertyValueFactory<>("into"));
        tableView1.getColumns().addAll(
                firstNameCol1,
                secondNameCol1,
                thirdNameCol1,
                fourthNameCol1,
                fifthNameCol1);
        TableColumn firstNameCol2 = new TableColumn("名字");
        firstNameCol2.setPrefWidth(216);
        firstNameCol2.setStyle( "-fx-alignment: CENTER;");
        // 设置分箱的类下面的属性名
        firstNameCol2.setCellValueFactory(
                new PropertyValueFactory<>("name"));
        TableColumn secondNameCo2 = new TableColumn("性别");
        secondNameCo2.setPrefWidth(100);
        secondNameCo2.setStyle( "-fx-alignment: CENTER;");
        secondNameCo2.setCellValueFactory(
                new PropertyValueFactory<>("sex"));
        TableColumn thirdNameCol2 = new TableColumn("手机");
        thirdNameCol2.setPrefWidth(200);
        thirdNameCol2.setStyle( "-fx-alignment: CENTER;");
        thirdNameCol2.setCellValueFactory(
                new PropertyValueFactory<>("phone"));
        TableColumn fourthNameCol2 = new TableColumn("创建时间");
        fourthNameCol2.setPrefWidth(300);
        fourthNameCol2.setStyle( "-fx-alignment: CENTER;");
        fourthNameCol2.setCellValueFactory(
                new PropertyValueFactory<>("date"));
        TableColumn fixthNameCol2 = new TableColumn("进入");
        fixthNameCol2.setPrefWidth(150);
        fixthNameCol2.setStyle( "-fx-alignment: CENTER;");
        fixthNameCol2.setCellValueFactory(
                new PropertyValueFactory<>("into"));
        tableView2.getColumns().addAll(
                firstNameCol2,
                secondNameCo2,
                thirdNameCol2,
                fourthNameCol2,
                fixthNameCol2);
        TableColumn firstNameCol3 = new TableColumn("头像");
        firstNameCol3.setPrefWidth(96);
        // 设置分箱的类下面的属性名
        firstNameCol3.setStyle( "-fx-alignment: CENTER;");
        firstNameCol3.setCellValueFactory(
                new PropertyValueFactory<>("avator"));
        TableColumn secondNameCol3 = new TableColumn("日期");
        secondNameCol3.setPrefWidth(570);
        secondNameCol3.setStyle( "-fx-alignment: CENTER;");
        secondNameCol3.setCellValueFactory(
                new PropertyValueFactory<>("date"));
        TableColumn thirdNameCol3 = new TableColumn("查看");
        thirdNameCol3.setPrefWidth(150);
        thirdNameCol3.setStyle( "-fx-alignment: CENTER;");
        thirdNameCol3.setCellValueFactory(
                new PropertyValueFactory<>("button"));
        TableColumn fourthNameCol3 = new TableColumn("查看");
        fourthNameCol3.setPrefWidth(150);
        fourthNameCol3.setStyle( "-fx-alignment: CENTER;");
        fourthNameCol3.setCellValueFactory(
                new PropertyValueFactory<>("name"));
        tableView3.getColumns().addAll(
                firstNameCol3,
                fourthNameCol3,
                secondNameCol3,
                thirdNameCol3);
        Properties prop = new Properties();
        try {
            if (new File("user.properties").exists()) {
                InputStream in = new BufferedInputStream(new FileInputStream("user.properties"));
                prop.load(in);
                id = prop.getProperty("doctor_id");
                in.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Gson gson = new Gson();
                HttpResponse<JsonNode> response = null;
                try {
                    response = Unirest.post(Constant.uri + "findDoctorByDoctorId")
                            .field("id", id)
                            .asJson();
                } catch (UnirestException e) {
                    e.printStackTrace();
                }
                if(response.getStatus()==200){
                    JSONArray result = response.getBody().getArray();
                    Doctor doctor = gson.fromJson(result.get(0).toString(), Doctor.class);
                    if(doctor.getAvatar()!=null){
                        imageViwe.setImage(new Image(doctor.getAvatar()));
                    }
                    if(doctor.getName()!=null){
                        name.setText(doctor.getName());
                    }
                    if (doctor.getDescription()!=null){
                        birthday.setText(doctor.getBirthday());
                    }
                    if (doctor.getGender()!=null){
                        sex.setText(doctor.getGender()==1?"男":"女");
                    }
                    if (doctor.getAge()!=null){
                        age.setText(doctor.getAge().toString());
                    }
                    if(doctor.getEmail()!=null){
                        email.setText(doctor.getEmail());
                    }
                    if(doctor.getDescription()!=null){
                        description.setText(doctor.getDescription());
                    }
                    if(doctor.getProvince()!=null){
                        province.setText(doctor.getProvince());
                    }
                    if(doctor.getArea()!=null){
                        area.setText(doctor.getArea());
                    }
                    if(doctor.getCity()!=null){
                        city.setText(doctor.getCity());
                    }
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
                    response = Unirest.post(Constant.uri + "findDicomssByDoctorId")
                            .field("id", id)
                            .asJson();
                } catch (UnirestException e) {
                    e.printStackTrace();
                }
                ObservableList<KVVV> data = FXCollections.observableArrayList();
                if (response.getStatus() == 200) {
//            System.out.println(response.getBody().toString());
                    Gson gson = new Gson();
                    JSONArray result = response.getBody().getArray();
                    for (int i = 0; i < result.length(); i++) {
//                System.out.println(result.length() + result.get(i).toString());
                        DICOMImage dicomImage = gson.fromJson(result.get(i).toString(), DICOMImage.class);
//                System.out.println(dicomImage.toString());
                        HttpResponse<JsonNode> response1 = null;
                        try {
                            response1 = Unirest.post(Constant.uri + "findPatientsByPatientId")
                                    .field("id", dicomImage.getPatientId())
                                    .asJson();
                        } catch (UnirestException e) {
                            e.printStackTrace();
                        }
                        if(response1.getStatus()==200){
                            JSONArray result1 = response1.getBody().getArray();
                            Patient patient = gson.fromJson(result1.get(0).toString(), Patient.class);
                            if (dicomImage.getJpg() != null) {
                                data.add(new KVVV(dicomImage.getId(), dicomImage.getJpg(), dicomImage.getCreatedAt().toString(),patient.getName()));
//                        System.out.println(dicomImage.toString());
                            }
                        }

                    }
                    tableView3.setItems(data);
                }
            }
        });
    }

    private void fresh2() throws UnirestException {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                HttpResponse<JsonNode> response = null;
                try {
                    response = Unirest.post(Constant.uri + "findERecordssByDoctorId")
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
//                System.out.println(result.length() + result.get(i).toString());
                        EReocrd eReocrd = gson.fromJson(result.get(i).toString(), EReocrd.class);
//                System.out.println(eReocrd.toString()+"------------");
                        HttpResponse<JsonNode> response1 = null;
                        try {
                            response1 = Unirest.post(Constant.uri + "findPatientsByPatientId")
                                    .field("id", eReocrd.getPatient_id())
                                    .asJson();
                        } catch (UnirestException e) {
                            e.printStackTrace();
                        }
                        if(response1.getStatus()==200){
                            JSONArray result1 = response1.getBody().getArray();
                            Patient patient = gson.fromJson(result1.get(0).toString(), Patient.class);
                            data.add(new KVV(patient.getId(),eReocrd.getId(),patient.getName(), patient.getGender() == 1 ? "男" : "女",
                                    patient.getPhone(),"进入",eReocrd.getCreated_at().toString()));
                        }
                    }
                    tableView2.setItems(data);
                }
            }
        });

    }

    private void fresh1() throws UnirestException {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                HttpResponse<JsonNode> response = null;
                try {
                    response = Unirest.post(Constant.uri + "findAppointmentByDoctorIdInToday")
                            .field("id", id)
                            .asJson();
                } catch (UnirestException e) {
                    e.printStackTrace();
                }
                ObservableList<KV> data = FXCollections.observableArrayList();
                if (response.getStatus() == 200) {
//            System.out.println(response.getBody().toString());
                    Gson gson = new Gson();
                    JSONArray result = response.getBody().getArray();
                    for (int i = 0; i < result.length(); i++) {
//                System.out.println(result.length() + result.get(i).toString());
                        AppointMent appointMent = gson.fromJson(result.get(i).toString(), AppointMent.class);
//                System.out.println(appointMent.toString()+"------------");
                        HttpResponse<JsonNode> response1 = null;
                        try {
                            response1 = Unirest.post(Constant.uri + "findPatientsByPatientId")
                                    .field("id", appointMent.getPatient_id())
                                    .asJson();
                        } catch (UnirestException e) {
                            e.printStackTrace();
                        }
                        if(response1.getStatus()==200){
                            JSONArray result1 = response1.getBody().getArray();
                            Patient patient = gson.fromJson(result1.get(0).toString(), Patient.class);
                            data.add(new KV(patient.getId(),appointMent.getId(),patient.getName(), patient.getGender() == 1 ? "男" : "女",
                                    patient.getPhone(),"进入",appointMent.getDoctor_id()));
                        }
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
                HttpResponse<JsonNode> response = null;
                try {
                    response = Unirest.post(Constant.uri + "findAppointMentByDoctorId")
                            .field("id", id)
                            .asJson();
                } catch (UnirestException e) {
                    e.printStackTrace();
                }
                ObservableList<KV> data = FXCollections.observableArrayList();
                if (response.getStatus() == 200) {
//            System.out.println(response.getBody().toString());
                    Gson gson = new Gson();
                    JSONArray result = response.getBody().getArray();
                    for (int i = 0; i < result.length(); i++) {
//                System.out.println(result.length() + result.get(i).toString());
                        AppointMent appointMent = gson.fromJson(result.get(i).toString(), AppointMent.class);
//                System.out.println(appointMent.toString()+"------------");
                        HttpResponse<JsonNode> response1 = null;
                        try {
                            response1 = Unirest.post(Constant.uri + "findPatientsByPatientId")
                                    .field("id", appointMent.getPatient_id())
                                    .asJson();
                        } catch (UnirestException e) {
                            e.printStackTrace();
                        }
                        if(response1.getStatus()==200){
                            JSONArray result1 = response1.getBody().getArray();
                            Patient patient = gson.fromJson(result1.get(0).toString(), Patient.class);
                            data.add(new KV(patient.getId(),appointMent.getId(),patient.getName(), patient.getGender() == 1 ? "男" : "女",
                                    patient.getPhone(),"进入",appointMent.getDoctor_id()));
                        }
                    }
                    tableView.setItems(data);
                }
            }
        });
    }

    public void update(ActionEvent mouseEvent) throws UnirestException {
        fresh();
    }

    public void exit(ActionEvent mouseEvent) throws IOException {
        new HelloApplication().startLogin(new Stage());
        Stage primaryStage = (Stage)tableView.getScene().getWindow();
        primaryStage.hide();
    }

    public void updateToday(ActionEvent actionEvent) throws UnirestException {
        fresh1();
    }

    public void updateERCord(ActionEvent actionEvent) throws UnirestException {
        fresh2();
    }

    public void updateDICOM(ActionEvent actionEvent) throws UnirestException {
        fresh3();
    }

    public void updateImage(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(new Stage());
        String endpoint = "endpoint";
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

    public void updateMessage(ActionEvent actionEvent) throws UnirestException {
        HttpResponse<JsonNode> response = Unirest.post(Constant.uri+"updateDoctorsMessage")
                .field("avatar",uri)
                .field("name",name.getText())
                .field("province",province.getText())
                .field("city",city.getText())
                .field("area",area.getText())
                .field("age",age.getText())
                .field("birthday",birthday.getText())
                .field("sex",sex.getText().equals("男")?1:0)
                .field("email",email.getText())
                .field("description",description.getText())
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

    @FXML
    TextField searchPreOrderInToday;
    public void searchPreorderToday(ActionEvent actionEvent) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                HttpResponse<JsonNode> response = null;
                try {
                    response = Unirest.post(Constant.uri + "findAppointmentByDoctorIdInToday")
                            .field("id", id)
                            .asJson();
                } catch (UnirestException e) {
                    e.printStackTrace();
                }
                ObservableList<KV> data = FXCollections.observableArrayList();
                if (response.getStatus() == 200) {
//            System.out.println(response.getBody().toString());
                    Gson gson = new Gson();
                    JSONArray result = response.getBody().getArray();
                    for (int i = 0; i < result.length(); i++) {
//                System.out.println(result.length() + result.get(i).toString());
                        AppointMent appointMent = gson.fromJson(result.get(i).toString(), AppointMent.class);
//                System.out.println(appointMent.toString()+"------------");
                        HttpResponse<JsonNode> response1 = null;
                        try {
                            response1 = Unirest.post(Constant.uri + "findPatientsByPatientId")
                                    .field("id", appointMent.getPatient_id())
                                    .asJson();
                        } catch (UnirestException e) {
                            e.printStackTrace();
                        }
                        if(response1.getStatus()==200){
                            JSONArray result1 = response1.getBody().getArray();
                            Patient patient = gson.fromJson(result1.get(0).toString(), Patient.class);
                            if(patient.getName().contains(searchPreOrderInToday.getText())||patient.getPhone().contains(searchPreOrderInToday.getText())){
                                data.add(new KV(patient.getId(),appointMent.getId(),patient.getName(), patient.getGender() == 1 ? "男" : "女",
                                        patient.getPhone(),"进入",appointMent.getDoctor_id()));
                            }
                        }
                    }
                    tableView1.setItems(data);
                }
            }
        });
    }

    @FXML
    TextField searchPreOrderInToday1;
    public void searchPreorderToday1(ActionEvent actionEvent) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                HttpResponse<JsonNode> response = null;
                try {
                    response = Unirest.post(Constant.uri + "findAppointMentByDoctorId")
                            .field("id", id)
                            .asJson();
                } catch (UnirestException e) {
                    e.printStackTrace();
                }
                ObservableList<KV> data = FXCollections.observableArrayList();
                if (response.getStatus() == 200) {
//            System.out.println(response.getBody().toString());
                    Gson gson = new Gson();
                    JSONArray result = response.getBody().getArray();
                    for (int i = 0; i < result.length(); i++) {
//                System.out.println(result.length() + result.get(i).toString());
                        AppointMent appointMent = gson.fromJson(result.get(i).toString(), AppointMent.class);
//                System.out.println(appointMent.toString()+"------------");
                        HttpResponse<JsonNode> response1 = null;
                        try {
                            response1 = Unirest.post(Constant.uri + "findPatientsByPatientId")
                                    .field("id", appointMent.getPatient_id())
                                    .asJson();
                        } catch (UnirestException e) {
                            e.printStackTrace();
                        }
                        if(response1.getStatus()==200){
                            JSONArray result1 = response1.getBody().getArray();
                            Patient patient = gson.fromJson(result1.get(0).toString(), Patient.class);
                            if(patient.getName().contains(searchPreOrderInToday1.getText())||patient.getPhone().contains(searchPreOrderInToday1.getText())){
                                data.add(new KV(patient.getId(),appointMent.getId(),patient.getName(), patient.getGender() == 1 ? "男" : "女",
                                        patient.getPhone(),"进入",appointMent.getDoctor_id()));
                            }
                        }
                    }
                    tableView.setItems(data);
                }
            }
        });
    }

    @FXML
    TextField searchPreOrderInToday2;
    public void searchPreorderToday2(ActionEvent actionEvent) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                HttpResponse<JsonNode> response = null;
                try {
                    response = Unirest.post(Constant.uri + "findERecordssByDoctorId")
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
//                System.out.println(result.length() + result.get(i).toString());
                        EReocrd eReocrd = gson.fromJson(result.get(i).toString(), EReocrd.class);
//                System.out.println(eReocrd.toString()+"------------");
                        HttpResponse<JsonNode> response1 = null;
                        try {
                            response1 = Unirest.post(Constant.uri + "findPatientsByPatientId")
                                    .field("id", eReocrd.getPatient_id())
                                    .asJson();
                        } catch (UnirestException e) {
                            e.printStackTrace();
                        }
                        if(response1.getStatus()==200){
                            JSONArray result1 = response1.getBody().getArray();
                            Patient patient = gson.fromJson(result1.get(0).toString(), Patient.class);
                            if(patient.getName().contains(searchPreOrderInToday2.getText())||patient.getPhone().contains(searchPreOrderInToday2.getText())){
                                data.add(new KVV(patient.getId(),eReocrd.getId(),patient.getName(), patient.getGender() == 1 ? "男" : "女",
                                        patient.getPhone(),"进入",eReocrd.getCreated_at().toString()));
                            }
                        }
                    }
                    tableView2.setItems(data);
                }
            }
        });
    }

    @FXML
    TextField searchPreOrderInToday3;
    public void searchPreorderToday3(ActionEvent actionEvent) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                HttpResponse<JsonNode> response = null;
                try {
                    response = Unirest.post(Constant.uri + "findDicomssByDoctorId")
                            .field("id", id)
                            .asJson();
                } catch (UnirestException e) {
                    e.printStackTrace();
                }
                ObservableList<KVVV> data = FXCollections.observableArrayList();
                if (response.getStatus() == 200) {
//            System.out.println(response.getBody().toString());
                    Gson gson = new Gson();
                    JSONArray result = response.getBody().getArray();
                    for (int i = 0; i < result.length(); i++) {
//                System.out.println(result.length() + result.get(i).toString());
                        DICOMImage dicomImage = gson.fromJson(result.get(i).toString(), DICOMImage.class);
//                System.out.println(dicomImage.toString());
                        HttpResponse<JsonNode> response1 = null;
                        try {
                            response1 = Unirest.post(Constant.uri + "findPatientsByPatientId")
                                    .field("id", dicomImage.getPatientId())
                                    .asJson();
                        } catch (UnirestException e) {
                            e.printStackTrace();
                        }
                        if(response1.getStatus()==200){
                            JSONArray result1 = response1.getBody().getArray();
                            Patient patient = gson.fromJson(result1.get(0).toString(), Patient.class);
                            if (dicomImage.getJpg() != null&&(patient.getName().contains(searchPreOrderInToday3.getText())||patient.getPhone().contains(searchPreOrderInToday3.getText()))) {
                                data.add(new KVVV(dicomImage.getId(), dicomImage.getJpg(), dicomImage.getCreatedAt().toString(),patient.getName()));
//                        System.out.println(dicomImage.toString());
                            }
                        }

                    }
                    tableView3.setItems(data);
                }
            }
        });
    }

    public class KV {
        private Label name;
        private Label sex;
        private Label phone;
        private Label date;
        private JFXButton into;

        public Label getName() {
            return name;
        }

        public void setName(String name) {
            this.name = new Label(name);
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

        public Label getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = new Label(date);
        }

        public JFXButton getInto() {
            return into;
        }

        public void setInto(String into) {
            this.into=new JFXButton(into);
        }

        public KV(String id, String appointmentId,String name, String sex, String phone, String schduling,String date) {
            this.name = new Label(name);
            this.name.setAlignment(Pos.CENTER);
            this.sex = new Label(sex);
            this.sex.setAlignment(Pos.CENTER);
            this.phone = new Label(phone);
            this.phone.setAlignment(Pos.CENTER);
            this.date=new Label(date);
            this.date.setAlignment(Pos.CENTER);
            this.into=new JFXButton(schduling);
            this.into.setAlignment(Pos.CENTER);
            this.into.getStyleClass().add("btn");
            this.into.getStyleClass().add("btn-info");
            this.into.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    Properties prop = new Properties();
                    try {
                        FileOutputStream oFile = new FileOutputStream("users.properties", false);
                        prop.setProperty("patient_id", id);
                        prop.setProperty("appointment_id",appointmentId);
                        prop.store(oFile, null);
                        oFile.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    };
                    try {
                        new HelloApplication().startMainPatient(new Stage());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public class KVV {
        private Label name;
        private Label sex;
        private Label phone;
        private Label date;
        private JFXButton into;

        public Label getName() {
            return name;
        }

        public void setName(String name) {
            this.name = new Label(name);
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

        public Label getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = new Label(date);
        }

        public JFXButton getInto() {
            return into;
        }

        public void setInto(String into) {
            this.into=new JFXButton(into);
        }

        public KVV(String id, String appointmentId,String name, String sex, String phone, String schduling,String date) {
            this.name = new Label(name);
            this.name.setAlignment(Pos.CENTER);
            this.sex = new Label(sex);
            this.sex.setAlignment(Pos.CENTER);
            this.phone = new Label(phone);
            this.phone.setAlignment(Pos.CENTER);
            this.date=new Label(date);
            this.date.setAlignment(Pos.CENTER);
            this.into=new JFXButton(schduling);
            this.into.setAlignment(Pos.CENTER);
            this.into.getStyleClass().add("btn");
            this.into.getStyleClass().add("btn-info");
            this.into.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    Properties prop = new Properties();
                    try {
                        FileOutputStream oFile = new FileOutputStream("users.properties", false);
                        prop.setProperty("patient_id", id);
                        prop.setProperty("e_record_id",appointmentId);
                        prop.store(oFile, null);
                        oFile.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    };
                    try {
                        new HelloApplication().startERecord(new Stage());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
    public class KVVV {
        private ImageView avator;
        private Label date;
        private JFXButton button;
        private Label name;

        public Label getName() {
            return name;
        }

        public void setName(Label name) {
            this.name = name;
        }

        public JFXButton getButton() {
            return button;
        }

        public void setButton(String button) {
            this.button = new JFXButton(button);
        }

        public ImageView getAvator() {
            return avator;
        }

        public void setAvator(String avator) {
            this.avator = new ImageView(new Image(avator));
        }

        public Label getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = new Label(date);
        }

        public KVVV(String id, String avator, String date,String name) {
//            System.out.println(id);
            this.name=new Label(name);
            this.avator = new ImageView(new Image(avator));
            this.avator.setFitHeight(100);
            this.avator.setFitWidth(100);
            this.date = new Label(date);
            this.button = new JFXButton("查看");
            this.button.getStyleClass().add("btn");
            this.button.getStyleClass().add("btn-info");
            this.button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    Properties prop = new Properties();
                    try {
                        FileOutputStream oFile = new FileOutputStream("users.properties", false);
                        prop.setProperty("dicom_image", id);
                        prop.store(oFile, null);
                        oFile.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        new HelloApplication().startDisplay(new Stage());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

    }
}
