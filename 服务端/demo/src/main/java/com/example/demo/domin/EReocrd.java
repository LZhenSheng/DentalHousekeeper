package com.example.demo.domin;

import lombok.Data;

@Data
public class EReocrd extends Common{
    private String doctor;
    private String patientId;
    private String appointmentId;
    private String cheifComplaint;
    private String presentPastHistory;
    private String pastHistory;
    private String inspect;
    private String supplementaryExamination;
    private String diagnosis;
    private String treatmentPlan;
    private String medicalCare;
    private String medicalAdvice;
}
