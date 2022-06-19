package com.example.dentalhousekeeper.adapter;

import android.widget.Button;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.dentalhousekeeper.R;
import com.example.dentalhousekeeper.activity.UserPersonMessageActivity;
import com.example.dentalhousekeeper.api.Api;
import com.example.dentalhousekeeper.domin.AppointMent;
import com.example.dentalhousekeeper.domin.Doctor;
import com.example.dentalhousekeeper.domin.Patient;
import com.example.dentalhousekeeper.domin.PreOrder;
import com.example.dentalhousekeeper.util.LogUtil;
import com.example.dentalhousekeeper.util.ToastUtil;

public class PreOrderUserMessageAdapter extends BaseQuickAdapter<PreOrder, BaseViewHolder> {

    /**
     * 构造方法
     *
     * @param layoutResId
     * @param userPersonMessageActivity
     */
    public PreOrderUserMessageAdapter(int layoutResId, UserPersonMessageActivity userPersonMessageActivity) {
        super(layoutResId);
    }

    /**
     * 显示数据
     *
     * @param helper
     * @param data
     */
    @Override
    protected void convert(@NonNull BaseViewHolder helper, PreOrder data) {
        helper.setText(R.id.date,data.getDate());
        helper.setText(R.id.time,data.getStart_time()+"~"+data.getEnd_time());
        helper.setText(R.id.money,String.valueOf(data.getMoney()));
        helper.setText(R.id.rest,String.valueOf(data.getRest()));
        helper.addOnClickListener(R.id.preorder);
        helper.setText(R.id.style,data.getStyle());
    }
}
