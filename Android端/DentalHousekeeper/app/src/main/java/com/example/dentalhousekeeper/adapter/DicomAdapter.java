package com.example.dentalhousekeeper.adapter;

import android.app.Activity;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.dentalhousekeeper.R;
import com.example.dentalhousekeeper.api.Api;
import com.example.dentalhousekeeper.domin.DICOMImage;
import com.example.dentalhousekeeper.domin.Doctor;
import com.example.dentalhousekeeper.domin.Hospital;
import com.example.dentalhousekeeper.domin.Patient;
import com.example.dentalhousekeeper.util.ImageUtil;
import com.example.dentalhousekeeper.util.LogUtil;

public class DicomAdapter extends BaseQuickAdapter<DICOMImage, BaseViewHolder> {

    /**
     * 构造方法
     *
     * @param layoutResId
     */
    public DicomAdapter(int layoutResId) {
        super(layoutResId);
    }

    /**
     * 绑定数据
     *
     * @param helper
     * @param data
     */
    @Override
    protected void convert(@NonNull BaseViewHolder helper, DICOMImage data) {
        LogUtil.d("dlkfjslajkf",data.toString());
        helper.setText(R.id.date,data.getCreatedAt().toString());
        ImageView iv_avatar=helper.getView(R.id.avatar);
        ImageUtil.showAvatar((Activity) mContext, iv_avatar, data.getJpg());
        Doctor doctor=new Doctor();
        doctor.setId(data.getDoctorId());
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
                    }
                });
        Patient patient=new Patient();
        patient.setId(data.getPatientId());
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
                        helper.setText(R.id.patient,data.getName());
                    }
                });
    }
}
