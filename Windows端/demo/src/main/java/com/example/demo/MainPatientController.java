package com.example.demo;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ObjectMetadata;
import com.example.demo.domain.*;
import com.google.gson.Gson;
import com.jfoenix.controls.JFXButton;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.dcm4che3.io.DicomInputStream;
import org.json.JSONArray;
import javafx.scene.web.WebView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

public class MainPatientController {


    String appoinmentId = null;
    String did = null;
    String uri = null;
    String uriJPG = null;
    String id = null;
    @FXML
    TableView<KV> tableView;
    @FXML
    Label type;
    @FXML
    Label name;
    @FXML
    Label sex;
    @FXML
    Label age;
    @FXML
    Label birthday;
    @FXML
    Label phone;
    @FXML
    Label card;
    @FXML
    Label address;
    @FXML
    Label registerdate;
    @FXML
    Label doctor;
    @FXML
    TextField cheifComplaint;
    @FXML
    TextField presentPastHistory;
    @FXML
    TextField pastHistory;
    @FXML
    TextField inspect;
    @FXML
    TextField supplementaryExamination;
    @FXML
    TextField diagnosis;
    @FXML
    TextField treatmentPlan;
    @FXML
    TextField medicalCare;
    @FXML
    TextField medicalAdvice;

    @FXML
    JFXButton insert;
    @FXML
    JFXButton save;

    @FXML
    private void initialize() throws UnirestException {
        tableView.setEditable(true);
        // 每个Table的列
        TableColumn firstNameCol = new TableColumn("头像");
        firstNameCol.setPrefWidth(96);
        // 设置分箱的类下面的属性名
        firstNameCol.setStyle("-fx-alignment: CENTER;");
        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<>("avator"));
        TableColumn secondNameCol = new TableColumn("日期");
        secondNameCol.setPrefWidth(720);
        secondNameCol.setStyle("-fx-alignment: CENTER;");
        secondNameCol.setCellValueFactory(
                new PropertyValueFactory<>("date"));
        TableColumn thirdNameCol = new TableColumn("查看");
        thirdNameCol.setPrefWidth(150);
        thirdNameCol.setStyle("-fx-alignment: CENTER;");
        thirdNameCol.setCellValueFactory(
                new PropertyValueFactory<>("button"));
        tableView.getColumns().addAll(firstNameCol,
                secondNameCol,
                thirdNameCol);
        Properties prop = new Properties();
        try {
            if (new File("user.properties").exists()) {
                InputStream in = new BufferedInputStream(new FileInputStream("user.properties"));
                prop.load(in);
                did = prop.getProperty("doctor_id");
                in.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (new File("users.properties").exists()) {
                InputStream in = new BufferedInputStream(new FileInputStream("users.properties"));
                prop.load(in);
                id = prop.getProperty("patient_id");
                appoinmentId = prop.getProperty("appointment_id");
                in.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        System.out.println("did===" + did);
//        System.out.println(id);
        Gson gson = new Gson();
        HttpResponse<JsonNode> response = Unirest.post(Constant.uri + "findDoctorByDoctorId")
                .field("id", did)
                .asJson();
        if (response.getStatus() == 200) {
            JSONArray result = response.getBody().getArray();
            Doctor patient = gson.fromJson(result.get(0).toString(), Doctor.class);
            doctor.setText(patient.getName());
//            System.out.println(patient.toString());
        }
        HttpResponse<JsonNode> response1 = Unirest.post(Constant.uri + "findPatientsByPatientId")
                .field("id", id)
                .asJson();
        if (response1.getStatus() == 200) {
            JSONArray result1 = response1.getBody().getArray();
            Patient patient = gson.fromJson(result1.get(0).toString(), Patient.class);
//            System.out.println(patient.toString());
            type.setText("普通类型");
            name.setText(patient.getName());
            sex.setText(patient.getGender() == 0 ? "女" : "男");
            phone.setText(patient.getPhone());
            card.setText(patient.getCard());
            if (patient.getProvince() != null) {
                address.setText(patient.getProvince() + "-" + patient.getCity() + "-" + patient.getArea());
            }
            birthday.setText(patient.getBirthday());
            age.setText("23");
            registerdate.setText(patient.getCreated_at().toString());
        }
        fresh();
        freshView();
    }

    private void freshView() throws UnirestException {
//        System.out.println("dkljf");
        HttpResponse<JsonNode> response = Unirest.post(Constant.uri + "findPreorderByAppointmentId")
                .field("id", appoinmentId)
                .asJson();
//        System.out.println(response.getBody().toString());
        if (response.getStatus() == 200) {
            Gson gson = new Gson();
            JSONArray result = response.getBody().getArray();
            PreOrder preOrder = gson.fromJson(result.get(0).toString(), PreOrder.class);
            String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
//            System.out.println(today+"-----"+preOrder.getDate());
            if (!preOrder.getDate().equals(today)) {
                insert.setVisible(false);
                save.setVisible(false);
                HttpResponse<JsonNode> response1 = Unirest.post(Constant.uri + "findERecordByAppointmentId")
                        .field("id", appoinmentId)
                        .asJson();
                if (response1.getStatus() == 200) {
//                    System.out.println(response1.getBody().toString());
                    Gson gson1 = new Gson();
                    JSONArray result1 = response1.getBody().getArray();
                    EReocrd eReocrd = gson1.fromJson(result1.get(0).toString(), EReocrd.class);
                    cheifComplaint.setText(eReocrd.getCheif_complaint());
                    presentPastHistory.setText(eReocrd.getPresent_past_history());
                    pastHistory.setText(eReocrd.getPast_history());
                    diagnosis.setText(eReocrd.getDiagnosis());
                    supplementaryExamination.setText(eReocrd.getSupplementary_examination());
                    treatmentPlan.setText(eReocrd.getTreatment_plan());
                    medicalCare.setText(eReocrd.getMedical_care());
                    medicalAdvice.setText(eReocrd.getMedical_advice());
                    inspect.setText(eReocrd.getInspect());
                }
            }
        }
    }


    private void fresh() throws UnirestException {
        HttpResponse<JsonNode> response = Unirest.post(Constant.uri + "findDicomByPatientId")
                .field("patientId", id)
                .asJson();
        ObservableList<KV> data = FXCollections.observableArrayList();
        if (response.getStatus() == 200) {
//            System.out.println(response.getBody().toString());
            Gson gson = new Gson();
            JSONArray result = response.getBody().getArray();
            for (int i = 0; i < result.length(); i++) {
//                System.out.println(result.length() + result.get(i).toString());
                DICOMImage dicomImage = gson.fromJson(result.get(i).toString(), DICOMImage.class);
//                System.out.println(dicomImage.toString());
                if (dicomImage.getJpg() != null) {
                    data.add(new KV(dicomImage.getId(), dicomImage.getJpg(), dicomImage.getCreatedAt().toString()));
//                    System.out.println(dicomImage.toString());
                }
            }
            tableView.setItems(data);
        }
    }

    public static String getYear() {
        Calendar date = Calendar.getInstance();
        String year = String.valueOf(date.get(Calendar.YEAR));
        return year;
    }

    public void insert(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("DCM Files", "*.dcm"));
        File file = fileChooser.showOpenDialog(new Stage());
//        System.out.println(file.getAbsolutePath());
        String endpoint = "endpoint";
        String accessKeyId = "accessKeyId";
        String accessKeySecret = "accessKeySecret";
        String bucketName = "bucketName";
        String uriHeader = "uriHeader";
        String jpgHeader = "jpgHeader";
        new Thread(new Runnable() {

            @Override
            public void run() {
                OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
                if (file.getAbsolutePath() != null) {
                    try {
                        File file1=new File(file.getAbsolutePath() + ".jpg");
                        if(!file1.exists()){
                            readDicom(file.getAbsolutePath());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    ObjectMetadata objectMetadata = new ObjectMetadata();
                    objectMetadata.setContentType("image/jpg");
                    uriJPG = uriHeader + file.getName() + ".jpg";
                    ossClient.putObject(bucketName, file.getName(), file);
                    ossClient.putObject(bucketName, file.getName() + ".jpg", new File(file.getAbsolutePath() + ".jpg"), objectMetadata);
                    uri = uriHeader + file.getName();
                    try {
                        HttpResponse<JsonNode> response = Unirest.post(Constant.uri + "createDICOMImage")
                                .field("patientId", id)
                                .field("uri", uri)
                                .field("jpg", uriJPG)
                                .field("doctorId", did)
                                .asJson();
                    } catch (UnirestException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void update(ActionEvent actionEvent) throws UnirestException {
        fresh();
    }

    public void save(ActionEvent actionEvent) throws UnirestException {
        HttpResponse<JsonNode> response = Unirest.post(Constant.uri + "createERecord")
                .field("doctor", did)
                .field("cheifComplaint", cheifComplaint.getText())
                .field("presentPastHistory", presentPastHistory.getText())
                .field("pastHistory", pastHistory.getText())
                .field("inspect", inspect.getText())
                .field("supplementaryExamination", supplementaryExamination.getText())
                .field("diagnosis", diagnosis.getText())
                .field("treatmentPlan", treatmentPlan.getText())
                .field("medicalCare", medicalCare.getText())
                .field("medicalAdvice", medicalAdvice.getText())
                .field("appointmentId", appoinmentId)
                .field("patientId", id)
                .asJson();
        Stage primaryStage = (Stage) doctor.getScene().getWindow();
        //当前窗口隐藏
        primaryStage.hide();
    }

    public class KV {
        private ImageView avator;
        private Label date;
        private JFXButton button;

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

        public KV(String id, String avator, String date) {
//            System.out.println(id);
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

    private static void readDicom(String file) throws IOException {
        DicomInputStream dis = new DicomInputStream(new File(file));
        Attributes d = dis.readDataset(-1, -1);
        byte[] pn = d.getBytes(Tag.PatientName);
//        System.out.println(new String(pn));

        int nBitsAllocated = d.getInt(Tag.BitsAllocated, 0);
        int nPixelRepresentation = d.getInt(Tag.PixelRepresentation, -1);
        if (nBitsAllocated > 8 && nPixelRepresentation == 0) {
            processImagePixelFor16BitUnsigned(d, file);
        }
    }

    private static void processImagePixelFor16BitUnsigned(Attributes d, String file) throws IOException {
        if (d == null) {
            return;
        }

        int height = d.getInt(Tag.Rows, 0);
        int width = d.getInt(Tag.Columns, 0);
        float fC = d.getFloat(Tag.WindowCenter, 0);
        float fW = d.getFloat(Tag.WindowWidth, 0);
        byte[] pixelData = d.getBytes(Tag.PixelData);

        short[] shortPixelData = toShortArray(pixelData);

        float fMin = (float) ((2.0f * fC - fW) / 2.0f + 0.5);
        float fMax = (float) ((2.0f * fC + fW) / 2.0f + 0.5);
        BufferedImage BI = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int h = 0; h < height; h++) {

            for (int w = 0; w < width; w++) {
                int index = w + h * width;
                short pixel = shortPixelData[index];
                //int value = (int) (((pixel - fC) / fW + 0.5) * 255.0);
                int value = (int) ((pixel - fMin) * 255.0 / (fMax - fMin));
                int newVal = Math.min(Math.max(value, 0), 255);
                int pixelValue = 0xffffffff;
                pixelValue = (newVal << 16) & 0x00ff0000 | (pixelValue & 0xff00ffff);
                pixelValue = (newVal << 8) & 0x0000ff00 | (pixelValue & 0xffff00ff);
                pixelValue = (newVal) & 0x000000ff | (pixelValue & 0xffffff00);
                BI.setRGB(w, h, pixelValue);
            }
        }
        ImageIO.write(BI, "jpg", new File(file + ".jpg"));
    }

    public static short[] toShortArray(byte[] src) {
        int count = src.length >> 1;
        short[] dest = new short[count];
        for (int i = 0; i < count; i++) {
            dest[i] = (short) ((src[i * 2] & 0xff) | ((src[2 * i + 1] & 0xff) << 8));
        }
        return dest;
    }

}
