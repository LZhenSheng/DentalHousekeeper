package com.example.dentalhousekeeper.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.dentalhousekeeper.R;
import com.example.dentalhousekeeper.api.Api;
import com.example.dentalhousekeeper.domin.Doctor;
import com.example.dentalhousekeeper.domin.EReocrd;
import com.example.dentalhousekeeper.domin.GroupConsultation;
import com.example.dentalhousekeeper.domin.Hospital;
import com.example.dentalhousekeeper.util.LogUtil;

public class GroupConsultationAdapter extends BaseQuickAdapter<GroupConsultation, BaseViewHolder> {

    /**
     * 构造方法
     *
     * @param layoutResId
     */
    public GroupConsultationAdapter(int layoutResId) {
        super(layoutResId);
    }

    /**
     * 绑定数据
     *
     * @param helper
     * @param data
     */
    @Override
    protected void convert(@NonNull BaseViewHolder helper, GroupConsultation data) {
        LogUtil.d("djklfs",data.toString());
        helper.setText(R.id.date,data.getDate());
        helper.addOnClickListener(R.id.into);
        Doctor doctor=new Doctor();
        doctor.setId(data.getStart());
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
                        helper.setText(R.id.start,data.getName());
                    }
                });
        Doctor doctor1=new Doctor();
        doctor1.setId(data.getReback());
        Api.getInstance()
                .findDoctorById(doctor1)
                .subscribe(new HttpObserver<Doctor>() {
                    /**
                     * 登录成功
                     *
                     * @param data
                     */
                    @Override
                    public void onSucceeded(Doctor data) {
                        helper.setText(R.id.reback,data.getName());
                    }
                });
    }
}