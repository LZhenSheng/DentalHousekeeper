package com.example.dentalhousekeeper.adapter;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.dentalhousekeeper.R;
import com.example.dentalhousekeeper.domin.Doctor;
import com.example.dentalhousekeeper.domin.Hospital;
import com.example.dentalhousekeeper.util.ImageUtil;

public class ChooseDoctorAdapter extends BaseQuickAdapter<Doctor, BaseViewHolder> {

    private Context context;
    /**
     * 构造方法
     *
     * @param layoutResId
     */
    public ChooseDoctorAdapter(int layoutResId,Context context) {
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
    protected void convert(@NonNull BaseViewHolder helper, Doctor data) {
        Log.d(TAG, "convert: "+data.toString());
        helper.setText(R.id.name, data.getName());
        helper.setText(R.id.phone, "职称"+data.getType());
        helper.setText(R.id.address,"所属科室"+data.getRoom());
        ImageView imageView=helper.getView(R.id.avatar);
        ImageUtil.show(context,imageView,data.getAvatar());
    }
}