package com.example.dentalhousekeeper.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dentalhousekeeper.R;
import com.example.dentalhousekeeper.activity.LoginDoctorActivity;
import com.example.dentalhousekeeper.activity.MessageDoctorActivity;
import com.example.dentalhousekeeper.adapter.HttpObserver;
import com.example.dentalhousekeeper.api.Api;
import com.example.dentalhousekeeper.domin.Doctor;
import com.example.dentalhousekeeper.util.ClickUtil;
import com.example.dentalhousekeeper.util.ImageUtil;
import com.example.dentalhousekeeper.util.LogUtil;
import com.example.dentalhousekeeper.util.PreferenceUtil;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * 首页-我的界面
 */
public class MeFragment extends BaseTitleFragment {

    @BindView(R.id.avatar)
    CircleImageView avatar;
    @BindView(R.id.nickName)
    TextView nickName;
    @BindView(R.id.phone)
    TextView phone;

    /**
     * 构造方法
     * <p>
     * 固定写法
     *
     * @return
     */
    public static MeFragment newInstance() {
        Bundle args = new Bundle();

        MeFragment fragment = new MeFragment();
        fragment.setArguments(args);
        return fragment;
    }


    /**
     * 返回布局文件
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    protected View getLayoutView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my, null);
    }

    @OnClick(R.id.exit)
    public void onViewClicked1() {
        if (ClickUtil.isFastClick()) {
            return;
        }
        startActivity(new Intent(getActivity(), LoginDoctorActivity.class));
        getActivity().finish();
    }

    @OnClick(R.id.user_message)
    public void onViewClicked() {
        startActivity(MessageDoctorActivity.class);
    }

    @Override
    protected void initViews() {
        super.initViews();
        Doctor doctor=new Doctor();
        doctor.setId(PreferenceUtil.getId());
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
                        if(data!=null){
                            ImageUtil.show(getContext(),avatar,data.getAvatar());
                            nickName.setText(data.getName());
                            phone.setText(data.getPhone());
                        }
                    }
                });
    }
}