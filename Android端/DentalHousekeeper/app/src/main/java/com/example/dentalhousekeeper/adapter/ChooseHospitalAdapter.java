package com.example.dentalhousekeeper.adapter;

import android.content.Context;

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
import com.example.dentalhousekeeper.util.ImageUtil;
import com.example.dentalhousekeeper.util.LogUtil;
import com.example.dentalhousekeeper.util.ToastUtil;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChooseHospitalAdapter extends BaseQuickAdapter<Hospital, BaseViewHolder> {

    Context context;
    /**
     * 构造方法
     *
     * @param layoutResId
     */
    public ChooseHospitalAdapter(int layoutResId, Context context) {
        super(layoutResId);
        this.context=context;
    }

    /**
     * 显示数据
     *
     * @param helper
     * @param data
     */
    @Override
    protected void convert(@NonNull BaseViewHolder helper, Hospital data) {
        helper.setText(R.id.name, data.getName());
        helper.setText(R.id.phone, data.getPhone());
        if(data.getProvince()!=null||data.getCity()!=null||data.getArea()!=null){
            helper.setText(R.id.address,data.getProvince()+"-"+data.getCity()+"-"+data.getArea());
        }
        CircleImageView circleImageView=helper.getView(R.id.avatar);
        if(data.getAvatar()!=null){
            ImageUtil.show(context,circleImageView,data.getAvatar());
        }
    }
}
