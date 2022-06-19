package com.example.dentalhousekeeper.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.dentalhousekeeper.R;
import com.example.dentalhousekeeper.adapter.HttpObserver;
import com.example.dentalhousekeeper.api.Api;
import com.example.dentalhousekeeper.domin.DetailResponse;
import com.example.dentalhousekeeper.domin.Doctor;
import com.example.dentalhousekeeper.domin.Patient;
import com.example.dentalhousekeeper.domin.Session;
import com.example.dentalhousekeeper.util.LogUtil;
import com.example.dentalhousekeeper.util.PreferenceUtil;
import com.example.dentalhousekeeper.util.StringUtil;
import com.example.dentalhousekeeper.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;

public class LoginDoctorActivity extends BaseTimeActivity {

    @BindView(R.id.account)
    EditText account;
    @BindView(R.id.password)
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_doctor);
    }

    @Override
    protected void initViews() {
        super.initViews();
        lightStatusBar(R.color.location_login);
    }

    String getContent(EditText editText) {
        if (account.equals(editText)) {
            return account.getText().toString();
        } else if (password.equals(editText)) {
            return password.getText().toString();
        } else {
            return null;
        }
    }

    @OnClick({R.id.register, R.id.forget, R.id.login, R.id.replace_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.register:
                startActivity(RegisterPatientActivity.class);
                break;
            case R.id.login:
                Doctor doctor=new Doctor(getContent(account),getContent(password));
//调用登录接口
                Api.getInstance()
                        .loginDoctor(doctor)
                        .subscribe(new HttpObserver<DetailResponse<Session>>() {
                            /**
                             * 登录成功
                             *
                             * @param data
                             */
                            @Override
                            public void onSucceeded(DetailResponse<Session> data) {
//                                LogUtil.d(TAG, "onLoginClick success:" + data.getData()+data.getMessage());

                                if(StringUtil.isEmpty(data.getMessage())){
                                    JMessageClient.login(getContent(account), getContent(account), new BasicCallback() {
                                        /**
                                         * @param responseCode    - 0 表示正常。大于 0 表示异常
                                         *                        responseMessage 会有进一步的异常信息。
                                         * @param responseMessage - 一般异常时会有进一步的信息提示。
                                         */
                                        @Override
                                        public void gotResult(int responseCode, String responseMessage) {
                                            if (responseCode != 0) {
                                                LogUtil.d(TAG, "message login failed:" + responseMessage);
                                            } else {
                                                LogUtil.d(TAG, "message login success");
                                            }
                                        }
                                    });
                                    startActivityAfterFinishThis(MainActivity.class);
                                    ToastUtil.successShortToast(R.string.success_login);
                                    LogUtil.d("dlkfjsldfjlsdf",data.getData().getUser());
                                    PreferenceUtil.saveId(data.getData().getUser());
                                    PreferenceUtil.saveType(0);
                                    PreferenceUtil.savePhone(account.getText().toString());
                                    LogUtil.d("dlkfjsldfjlsdf",PreferenceUtil.getId());
                                }else{
                                    ToastUtil.errorShortToast(R.string.error_login);
                                }

                            }

                            /**
                             * 登录失败
                             *
                             * @param data
                             * @param e
                             * @return
                             */
                            @Override
                            public boolean onFailed(DetailResponse<Session> data, Throwable e) {
                                ToastUtil.errorShortToast(R.string.error_network_connect);
                                return false;
                            }
                        });
                break;
            case R.id.replace_login:
                startActivityAfterFinishThis(LoginPatientActivity.class);
                break;
        }
    }
}
