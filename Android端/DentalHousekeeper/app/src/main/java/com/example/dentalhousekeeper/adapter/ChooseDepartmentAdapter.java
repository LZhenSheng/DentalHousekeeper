package com.example.dentalhousekeeper.adapter;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.dentalhousekeeper.R;
import com.example.dentalhousekeeper.domin.Department;
import com.example.dentalhousekeeper.util.ImageUtil;

public class ChooseDepartmentAdapter extends BaseQuickAdapter<Department, BaseViewHolder> {

    private Context context;
    /**
     * 构造方法
     *
     * @param layoutResId
     */
    public ChooseDepartmentAdapter(int layoutResId,Context context) {
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
    protected void convert(@NonNull BaseViewHolder helper, Department data) {
        helper.setText(R.id.name, data.getName());
        helper.setText(R.id.info, data.getProfile());
    }
}