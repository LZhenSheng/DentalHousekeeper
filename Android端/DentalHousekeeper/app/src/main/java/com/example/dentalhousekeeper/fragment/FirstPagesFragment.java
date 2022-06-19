package com.example.dentalhousekeeper.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ServiceWorkerClient;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.dentalhousekeeper.R;
import com.example.dentalhousekeeper.activity.ChooseHospital2Activity;
import com.example.dentalhousekeeper.activity.DicomDoctorListActivity;
import com.example.dentalhousekeeper.activity.DisplayDicomActivity;
import com.example.dentalhousekeeper.activity.ERecordListDoctorActivity;
import com.example.dentalhousekeeper.activity.GroupConsultationActivity;
import com.example.dentalhousekeeper.activity.PreOrderActivity;
import com.example.dentalhousekeeper.activity.ReturnVisitActivity;
import com.example.dentalhousekeeper.adapter.HttpObserver;
import com.example.dentalhousekeeper.api.Api;
import com.example.dentalhousekeeper.domin.Doctor;
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
public class FirstPagesFragment extends BaseTitleFragment {

    /**
     * 构造方法
     * <p>
     * 固定写法
     *
     * @return
     */
    public static FirstPagesFragment newInstance() {
        Bundle args = new Bundle();

        FirstPagesFragment fragment = new FirstPagesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initViews() {
        super.initViews();
        //初始化定位
    }


    /***
     * 获取View
     */
    @Override
    protected View getLayoutView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_first_pages, container, false);
    }

    @OnClick({R.id.preorder,R.id.patient,R.id.e_record,R.id.dicom,R.id.group_consultation,R.id.preorder_consultation})
    public void onViewClicked(View view) {
        if (ClickUtil.isFastClick()) {
            return;
        }
        switch (view.getId()){
            case R.id.preorder:
                startActivity(PreOrderActivity.class);;
                break;
            case R.id.e_record:
                startActivity(ERecordListDoctorActivity.class);
                break;
            case R.id.dicom:
                startActivity(DicomDoctorListActivity.class);
                break;
            case R.id.patient:
                startActivity(ReturnVisitActivity.class);
                break;
            case R.id.group_consultation:
                startActivity(GroupConsultationActivity.class);
                break;
            case R.id.preorder_consultation:
                startActivity(ChooseHospital2Activity.class);
                break;
        }
    }
}
