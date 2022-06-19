package com.example.dentalhousekeeper.adapter;

import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.dentalhousekeeper.R;
import com.example.dentalhousekeeper.api.Api;
import com.example.dentalhousekeeper.domin.AppointMent;
import com.example.dentalhousekeeper.domin.Doctor;
import com.example.dentalhousekeeper.domin.Hospital;
import com.example.dentalhousekeeper.domin.Patient;
import com.example.dentalhousekeeper.domin.PreOrder;
import com.example.dentalhousekeeper.util.LogUtil;
import com.example.dentalhousekeeper.util.ToastUtil;

public class PreOrderDoctorAdapter extends BaseQuickAdapter<AppointMent, BaseViewHolder> {

    /**
     * 构造方法
     *
     * @param layoutResId
     */
    public PreOrderDoctorAdapter(int layoutResId) {
        super(layoutResId);
    }

    /**
     * 显示数据
     *
     * @param helper
     * @param data
     */
    @Override
    protected void convert(@NonNull BaseViewHolder helper, AppointMent data) {
        Doctor doctor = new Doctor();
        doctor.setId(data.getDoctor_id());
        Api.getInstance().findDoctorById(doctor)
                .subscribe(new HttpObserver<Doctor>() {
                    @Override
                    public void onSucceeded(Doctor data) {
                        if (data != null) {
                            LogUtil.d("doctor111", data.toString() + data.getHospital());
                            helper.setText(R.id.doctor, data.getName());
                            Hospital hospital = new Hospital();
                            hospital.setId(data.getHospital());
                            Api.getInstance().findHospitalByHospitalId(hospital)
                                    .subscribe(new HttpObserver<Hospital>() {
                                        @Override
                                        public void onSucceeded(Hospital data) {
                                            if (data != null) {
                                                helper.setText(R.id.hospital, data.getName());
                                            }
                                        }

                                    });
                        }
                    }

                });
        PreOrder preOrder = new PreOrder();
        preOrder.setId(data.getPreorder_id());
        Api.getInstance().findPreorderById(preOrder)
                .subscribe(new HttpObserver<PreOrder>() {
                    @Override
                    public void onSucceeded(PreOrder data) {
                        if (data != null) {
                            LogUtil.d("preorder111", data.toString());
                            helper.setText(R.id.date, data.getDate());
                            helper.setText(R.id.time, data.getStart_time() + "~" + data.getEnd_time());
                            helper.setText(R.id.money, String.valueOf(data.getMoney()));
                            if(data.getStyle().equals("远程问诊")){
                                helper.setText(R.id.style,"远程问诊");
                                LinearLayout linearLayout=helper.getView(R.id.linearLayout);
                                linearLayout.setVisibility(View.VISIBLE);
                            }else{
                                helper.setText(R.id.style,"线下问诊");
                                LinearLayout linearLayout=helper.getView(R.id.linearLayout);
                                linearLayout.setVisibility(View.GONE);
                            }
                        } else {
//                                    ToastUtil.errorShortToast(data);
                        }
                    }
                });
        Patient patient = new Patient();
        patient.setId(data.getPatient_id());
        Api.getInstance().findPatientById(patient)
                .subscribe(new HttpObserver<Patient>() {
                    @Override
                    public void onSucceeded(Patient data) {
                        if (data != null) {
                            LogUtil.d("patient111", data.toString());
                            helper.setText(R.id.name, data.getName());
                        }
                    }
                });
        helper.addOnClickListener(R.id.into);
    }
}