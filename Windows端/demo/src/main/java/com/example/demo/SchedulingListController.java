package com.example.demo;

import com.example.demo.domain.Constant;
import com.example.demo.domain.Doctor;
import com.example.demo.domain.PreOrder;
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
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.json.JSONArray;

import java.io.*;
import java.util.Properties;

import static com.example.demo.util.ToastUtil.toast;

public class SchedulingListController {

    String id=null;
    @FXML
    TableView<KV> tableView;
    @FXML
    private void initialize() throws UnirestException{
        tableView.setEditable(true);
        // 每个Table的列
        TableColumn firstNameCol = new TableColumn("日期");
        firstNameCol.setPrefWidth(100);
        // 设置分箱的类下面的属性名
        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<>("date"));
        TableColumn secondNameCol = new TableColumn("开始时间");
        secondNameCol.setPrefWidth(60);
        secondNameCol.setCellValueFactory(
                new PropertyValueFactory<>("startTime"));
        TableColumn thirdNameCol = new TableColumn("结束时间");
        thirdNameCol.setPrefWidth(60);
        thirdNameCol.setCellValueFactory(
                new PropertyValueFactory<>("endTime"));
        TableColumn fourthNameCol = new TableColumn("总挂号数量");
        fourthNameCol.setPrefWidth(100);
        fourthNameCol.setCellValueFactory(
                new PropertyValueFactory<>("num"));
        TableColumn fifthNameCol = new TableColumn("挂号费");
        fifthNameCol.setPrefWidth(100);
        fifthNameCol.setCellValueFactory(
                new PropertyValueFactory<>("money"));
        TableColumn fixNameCol = new TableColumn("删除");
        fixNameCol.setPrefWidth(100);
        fixNameCol.setCellValueFactory(
                new PropertyValueFactory<>("delete"));
        TableColumn savenNameCol = new TableColumn("就诊类型");
        savenNameCol.setPrefWidth(100);
        savenNameCol.setCellValueFactory(
                new PropertyValueFactory<>("style"));
        tableView.getColumns().addAll(firstNameCol,
                secondNameCol,
                thirdNameCol,
                fourthNameCol,
                fifthNameCol,
                savenNameCol,
                fixNameCol);
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
        fresh();
    }

    private void fresh() throws UnirestException {
        HttpResponse<JsonNode> response = Unirest.post(Constant.uri + "getPreOrderByDoctorId")
                .field("id", id)
                .asJson();
        ObservableList<KV> data = FXCollections.observableArrayList();
        if (response.getStatus() == 200) {
//            System.out.println(response.getBody().toString());
            Gson gson = new Gson();
            JSONArray result = response.getBody().getArray();
            for (int i = 0; i < result.length(); i++) {
//                System.out.println(result.get(i).toString());
                PreOrder preOrder = gson.fromJson(result.get(i).toString(), PreOrder.class);
                data.add(new KV(preOrder.getStyle(),preOrder.getId(),preOrder.getDate(),preOrder.getStart_time(),preOrder.getEnd_time(),String.valueOf(preOrder.getNumber()),String.valueOf(preOrder.getMoney()),"删除"));
//                System.out.println(preOrder.toString());
            }
            tableView.setItems(data);
        }
    }

    public void add(ActionEvent actionEvent) throws IOException {
        new HelloApplication().startScheduling(new Stage());
    }

    public class KV {
        private Label date;
        private Label startTime;
        private Label endTime;
        private Label num;
        private Label money;
        private Label style;
        private Button delete;

        public Label getStyle() {
            return style;
        }

        public void setStyle(String  style) {
            this.style = new Label(style);
        }

        public Button getDelete() {
            return delete;
        }

        public void setDelete(String delete) {
            this.delete = new Button(delete);
        }

        public KV(String style,String id, String date, String startTime, String endTime, String num, String money, String delete) {
            this.style = new Label(style);
            this.style.setPrefWidth(100);
            this.style.setAlignment(Pos.CENTER);
            this.date = new Label(date);
            this.date.setPrefWidth(100);
            this.date.setAlignment(Pos.CENTER);
            this.startTime = new Label(startTime);
            this.startTime.setPrefWidth(60);
            this.startTime.setAlignment(Pos.CENTER);
            this.endTime = new Label(endTime);
            this.endTime.setPrefWidth(60);
            this.endTime.setAlignment(Pos.CENTER);
            this.num = new Label(num);
            this.num.setPrefWidth(100);
            this.num.setAlignment(Pos.CENTER);
            this.money = new Label(money+"元");
            this.money.setPrefWidth(100);
            this.money.setAlignment(Pos.CENTER);
            this.delete=new Button(delete);
            this.delete.setPrefWidth(100);
            this.delete.setAlignment(Pos.CENTER);
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

    public class KVV {}
}
