package com.example.demo;

import com.example.demo.domain.Constant;
import com.example.demo.domain.EReocrd;
import com.example.demo.domain.Patient;
import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.json.JSONArray;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class ERecordController {

    @FXML
    TextField name;
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
    TextField createAt;
    String id;
    String eRecord;

    @FXML
    private void initialize() throws UnirestException {
        Properties prop = new Properties();
        try {
            if (new File("users.properties").exists()) {
                InputStream in = new BufferedInputStream(new FileInputStream("users.properties"));
                prop.load(in);
                id = prop.getProperty("patient_id");
                eRecord=prop.getProperty("e_record_id");
                in.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        HttpResponse<JsonNode> response1 = Unirest.post(Constant.uri + "findPatientsByPatientId")
                .field("id", id)
                .asJson();
        if (response1.getStatus() == 200) {
            JSONArray result1 = response1.getBody().getArray();
            Patient patient = gson.fromJson(result1.get(0).toString(), Patient.class);
//            System.out.println(patient.toString());
            name.setText(patient.getName());
        }
        HttpResponse<JsonNode> response2 = Unirest.post(Constant.uri + "findERecordsById")
                .field("id", eRecord)
                .asJson();
        if (response2.getStatus() == 200) {
            JSONArray result1 = response2.getBody().getArray();
            EReocrd eReocrd = gson.fromJson(result1.get(0).toString(), EReocrd.class);
            cheifComplaint.setText(eReocrd.getCheif_complaint());
            presentPastHistory.setText(eReocrd.getPresent_past_history());
            pastHistory.setText(eReocrd.getPast_history());
            inspect.setText(eReocrd.getInspect());
            supplementaryExamination.setText(eReocrd.getSupplementary_examination());
            diagnosis.setText(eReocrd.getDiagnosis());
            treatmentPlan.setText(eReocrd.getTreatment_plan());
            medicalCare.setText(eReocrd.getMedical_care());
            treatmentPlan.setText(eReocrd.getTreatment_plan());
            medicalAdvice.setText(eReocrd.getMedical_advice());
            createAt.setText(eReocrd.getCreated_at().toString());
            cheifComplaint.setEditable(false);
            presentPastHistory.setEditable(false);
            pastHistory.setEditable(false);
            inspect.setEditable(false);
            supplementaryExamination.setEditable(false);
            diagnosis.setEditable(false);
            treatmentPlan.setEditable(false);
            medicalCare.setEditable(false);
            treatmentPlan.setEditable(false);
            medicalAdvice.setEditable(false);
            createAt.setEditable(false);
        }
    }
}
