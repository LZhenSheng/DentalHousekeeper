package com.example.dentalhousekeeper.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.dentalhousekeeper.R;
import com.example.dentalhousekeeper.adapter.HttpObserver;
import com.example.dentalhousekeeper.api.Api;
import com.example.dentalhousekeeper.domin.Doctor;
import com.example.dentalhousekeeper.domin.EReocrd;
import com.example.dentalhousekeeper.domin.Hospital;
import com.example.dentalhousekeeper.util.PreferenceUtil;

import butterknife.BindView;

public class ERecordDetailActivity extends BaseTitleActivity {

    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.chief_complaint)
    TextView chief_complaint;
    @BindView(R.id.present_past_history)
    TextView present_past_history;
    @BindView(R.id.past_history)
    TextView past_history;
    @BindView(R.id.inspect)
    TextView inspect;
    @BindView(R.id.supplementary_examination)
    TextView supplementary_examination;
    @BindView(R.id.diagnosis)
    TextView diagnosis;
    @BindView(R.id.treatment_plan)
    TextView treatment_plan;
    @BindView(R.id.medical_care)
    TextView medical_care;
    @BindView(R.id.medical_advice)
    TextView medical_advice;
    @BindView(R.id.doctor)
    TextView ddoctor;
    @BindView(R.id.hospital)
    TextView hhospital;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e_record_detail);
    }

    @Override
    public void initData() {
        super.initData();
        EReocrd eReocrd=new EReocrd();
        eReocrd.setId(PreferenceUtil.getERECORDID());
        Api.getInstance()
                .findERecordById(eReocrd)
                .subscribe(new HttpObserver<EReocrd>() {
                    /**
                     * 登录成功
                     *
                     * @param data
                     */
                    @Override
                    public void onSucceeded(EReocrd data) {
                        date.setText(data.getCreated_at().toString());
                        chief_complaint.setText(data.getCheif_complaint());
                        present_past_history.setText(data.getPresent_past_history());
                        past_history.setText(data.getPast_history());
                        inspect.setText(data.getInspect());
                        supplementary_examination.setText(data.getSupplementary_examination());
                        diagnosis.setText(data.getDiagnosis());
                        treatment_plan.setText(data.getTreatment_plan());
                        medical_care.setText(data.getMedical_care());
                        medical_advice.setText(data.getMedical_advice());
                        Doctor doctor=new Doctor();
                        doctor.setId(data.getDoctor());
                        Api.getInstance()
                                .findDoctorById(doctor)
                                .subscribe(new HttpObserver<Doctor>() {
                                    /**
                                     * 登录成功
                                     *
                                     * @param data
                                     */
                                    @Override
                                    public void onSucceeded(Doctor data) {
                                        ddoctor.setText(data.getName());
                                        Hospital hospital=new Hospital();
                                        hospital.setId(data.getHospital());
                                        Api.getInstance()
                                                .findHospitalByHospitalId(hospital)
                                                .subscribe(new HttpObserver<Hospital>() {
                                                    /**
                                                     * 登录成功
                                                     *
                                                     * @param data
                                                     */
                                                    @Override
                                                    public void onSucceeded(Hospital data) {
                                                        hhospital.setText(data.getName());
                                                    }
                                                });
                                    }
                                });
                    }
                });

    }
}
