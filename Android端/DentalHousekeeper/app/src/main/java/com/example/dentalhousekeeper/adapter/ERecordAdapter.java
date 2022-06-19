package com.example.dentalhousekeeper.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.dentalhousekeeper.R;
import com.example.dentalhousekeeper.api.Api;
import com.example.dentalhousekeeper.domin.Doctor;
import com.example.dentalhousekeeper.domin.EReocrd;
import com.example.dentalhousekeeper.domin.Hospital;
import com.example.dentalhousekeeper.domin.Patient;
import com.example.dentalhousekeeper.util.ImageUtil;
import com.example.dentalhousekeeper.util.LogUtil;

public class ERecordAdapter extends BaseQuickAdapter<EReocrd, BaseViewHolder> {

    /**
     * 构造方法
     *
     * @param layoutResId
     */
    public ERecordAdapter(int layoutResId) {
        super(layoutResId);
    }

    /**
     * 绑定数据
     *
     * @param helper
     * @param data
     */
    @Override
    protected void convert(@NonNull BaseViewHolder helper, EReocrd data) {
        LogUtil.d("djklfs",data.toString());
        helper.setText(R.id.date,data.getCreated_at().toString());
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
                        helper.setText(R.id.doctor,data.getName());
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
                                        helper.setText(R.id.hospital,data.getName());
                                    }
                                });
                    }
                });
        Patient patient=new Patient();
        patient.setId(data.getPatient_id());
        Api.getInstance()
                .findPatientById(patient)
                .subscribe(new HttpObserver<Patient>() {
                    /**
                     * 登录成功
                     *
                     * @param data
                     */
                    @Override
                    public void onSucceeded(Patient data) {
                        helper.setText(R.id.name,data.getName());
                    }
                });
    }
}