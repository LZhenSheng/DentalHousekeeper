package com.example.dentalhousekeeper.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.dentalhousekeeper.R;
import com.example.dentalhousekeeper.activity.ChooseHospitalActivity;
import com.example.dentalhousekeeper.activity.DicomListActivity;
import com.example.dentalhousekeeper.activity.ERecordListActivity;
import com.example.dentalhousekeeper.activity.PreOrderPatientActivity;
import com.example.dentalhousekeeper.adapter.HttpObserver;
import com.example.dentalhousekeeper.api.Api;
import com.example.dentalhousekeeper.domin.Patient;
import com.example.dentalhousekeeper.util.ClickUtil;
import com.example.dentalhousekeeper.util.LogUtil;
import com.example.dentalhousekeeper.util.PreferenceUtil;
import com.example.dentalhousekeeper.util.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/***
 * 首页Fragment
 * @author 胜利镇
 * @time 2020/8/7 8:16
 */
public class FirstPagesPatientFragment extends BaseTitleFragment {

    /**
     * 构造方法
     * <p>
     * 固定写法
     *
     * @return
     */
    public static FirstPagesPatientFragment newInstance() {
        Bundle args = new Bundle();

        FirstPagesPatientFragment fragment = new FirstPagesPatientFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /***
     * 获取View
     */
    @Override
    protected View getLayoutView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_first_pages_patient, container, false);
    }

    @OnClick({R.id.preorder,R.id.dicomImage,R.id.erecord,R.id.my_preorder})
    public void onViewClicked(View view) {
        if (ClickUtil.isFastClick()) {
            return;
        }
        switch (view.getId()){
            case R.id.preorder:
                startActivity(ChooseHospitalActivity.class);
                break;
            case R.id.dicomImage:
                startActivity(DicomListActivity.class);
                break;
            case R.id.erecord:
                startActivity(ERecordListActivity.class);
                break;
            case R.id.my_preorder:
                startActivity(PreOrderPatientActivity.class);
                break;
        }
    }



}
