package com.example.dentalhousekeeper.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dentalhousekeeper.R;
import com.example.dentalhousekeeper.activity.LoginDoctorActivity;
import com.example.dentalhousekeeper.activity.MessagePatientActivity;
import com.example.dentalhousekeeper.adapter.HttpObserver;
import com.example.dentalhousekeeper.api.Api;
import com.example.dentalhousekeeper.domin.Patient;
import com.example.dentalhousekeeper.util.ClickUtil;
import com.example.dentalhousekeeper.util.ImageUtil;
import com.example.dentalhousekeeper.util.PreferenceUtil;
import com.example.dentalhousekeeper.util.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.im.android.api.JMessageClient;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * 首页-我的界面
 */
public class MePatientFragment extends BaseTitleFragment {

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
    public static MePatientFragment newInstance() {
        Bundle args = new Bundle();

        MePatientFragment fragment = new MePatientFragment();
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
        return inflater.inflate(R.layout.fragment_my_patient, null);
    }

    @OnClick(R.id.exit)
    public void onViewClicked1() {
        if (ClickUtil.isFastClick()) {
            return;
        }
        startActivity(new Intent(getActivity(), LoginDoctorActivity.class));
        getActivity().finish();
        JMessageClient.logout();
    }

    @OnClick(R.id.user_message)
    public void onViewClicked() {
        //退出极光聊天
        startActivity(MessagePatientActivity.class);
    }

    @Override
    protected void initViews() {
        super.initViews();
        Patient patient = new Patient();
        patient.setId(PreferenceUtil.getId());
        Api.getInstance()
                .findPatientById(patient)
                .subscribe(new HttpObserver<Patient>() {
                    @Override
                    public void onSucceeded(Patient data) {
                        ImageUtil.show(getContext(),avatar,data.getAvatar());
                        nickName.setText(data.getName());
                        phone.setText(data.getPhone());
                    }

                    @Override
                    public boolean onFailed(Patient data, Throwable e) {
                        ToastUtil.errorShortToast(R.string.error_network_connect);
                        return false;
                    }
                });
    }
}