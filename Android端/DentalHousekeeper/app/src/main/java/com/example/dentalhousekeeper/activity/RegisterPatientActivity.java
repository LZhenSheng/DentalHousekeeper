package com.example.dentalhousekeeper.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.dentalhousekeeper.R;
import com.example.dentalhousekeeper.adapter.HttpObserver;
import com.example.dentalhousekeeper.api.Api;
import com.example.dentalhousekeeper.domin.BaseModel;
import com.example.dentalhousekeeper.domin.DetailResponse;
import com.example.dentalhousekeeper.domin.Patient;
import com.example.dentalhousekeeper.util.ClickUtil;
import com.example.dentalhousekeeper.util.LogUtil;
import com.example.dentalhousekeeper.util.PreferenceUtil;
import com.example.dentalhousekeeper.util.StringUtil;
import com.example.dentalhousekeeper.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;

public class RegisterPatientActivity extends BaseTimeActivity {

    private CountDownTimer countDownTimer;

    @BindView(R.id.account)
    EditText accout;
    @BindView(R.id.verification_code)
    EditText verificationCode;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.confirm_password)
    EditText confirmPassword;
    @BindView(R.id.send_sms_code)
    Button sendSmsCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_patient);
        ButterKnife.bind(this);
    }

    @Override
    protected void initViews() {
        super.initViews();
        lightStatusBar(R.color.location_login);
    }

    @OnClick({R.id.send_sms_code, R.id.register})
    public void onViewClicked(View view) {
        if (ClickUtil.isFastClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.send_sms_code:
                if(StringUtil.isPhone(getContent(accout))){
                    Api.getInstance().sendSMSCode(getContent(accout))
                            .subscribe(new HttpObserver<DetailResponse<BaseModel>>() {
                                @Override
                                public void onSucceeded(DetailResponse<BaseModel> data) {
                                    if(data.getMessage()==null){
                                        ToastUtil.successShortToast(R.string.success_send_sms_code);
                                        PreferenceUtil.saveSmsCode(data.getData().getId());
                                        startCountDown();
                                    }else{
                                        ToastUtil.errorShortToast(data.getMessage());
                                    }
                                }

                                @Override
                                public boolean onFailed(DetailResponse<BaseModel> data, Throwable e) {
                                    ToastUtil.errorShortToast(R.string.error_network_connect);
                                    return super.onFailed(data, e);
                                }
                            });
                }else if(getContent(accout).length()==0){
                    ToastUtil.errorShortToast(R.string.error_empty_phone);
                }else{
                    ToastUtil.errorShortToast(R.string.error_format_phone);
                }
                break;
            case R.id.register:
                if(getContent(accout).length()==0){
                    ToastUtil.errorShortToast(R.string.error_empty_phone);
                }else if(StringUtil.isPhone(getContent(accout))){
                    if(!StringUtil.isPassword(getContent(password))){
                        ToastUtil.errorShortToast(R.string.err_account_or_password);
                    }else if(getContent(password)==getContent(confirmPassword)){
                        ToastUtil.errorShortToast(R.string.error_confirm_password);
                    }else{
                        if(!StringUtil.isCode(getContent(verificationCode))){
                            ToastUtil.errorShortToast(R.string.error_format_code);
                        }else{
                            Patient patient=new Patient(getContent(accout),getContent(accout),getContent(password));
                            Api.getInstance().registerAccount(patient)
                                    .subscribe(new HttpObserver<DetailResponse<BaseModel>>() {
                                        @Override
                                        public void onSucceeded(DetailResponse<BaseModel> data) {
                                            if(data.getMessage()==null){
                                                JMessageClient.register(getContent(accout), getContent(accout), new BasicCallback() {
                                                    /**
                                                     * @param responseCode    - 0 ????????????????????? 0 ????????????
                                                     *                        responseMessage ?????????????????????????????????
                                                     * @param responseMessage - ????????????????????????????????????????????????
                                                     */
                                                    @Override
                                                    public void gotResult(int responseCode, String responseMessage) {
                                                        if (responseCode != 0) {
                                                            LogUtil.d(TAG, "message register failed:" + responseMessage);
                                                        } else {
                                                            LogUtil.d(TAG, "message register success");

                                                        }
                                                    }
                                                });
                                                ToastUtil.successShortToast(R.string.success_register);
                                                PreferenceUtil.saveId(data.getData().getId());
                                            }else{
                                                ToastUtil.errorShortToast(data.getMessage());
                                            }
                                        }

                                        @Override
                                        public boolean onFailed(DetailResponse<BaseModel> data, Throwable e) {
                                            ToastUtil.errorShortToast(R.string.error_network_connect);
                                            return super.onFailed(data, e);
                                        }
                                    });
                        }
                    }
                }else {
                    ToastUtil.errorShortToast(R.string.error_format_phone);
                }
                break;
        }
    }

    String getContent(EditText editText){
        if (accout.equals(editText)) {
            return accout.getText().toString();
        } else if (password.equals(editText)) {
            return password.getText().toString();
        } else if (verificationCode.equals(editText)) {
            return verificationCode.getText().toString();
        } else if (confirmPassword.equals(editText)) {
            return confirmPassword.getText().toString();
        } else {
            return null;
        }
    }

    /**
     * ???????????????
     * ?????????????????????????????????
     * ?????????????????????????????????????????????
     */
    private void startCountDown() {
        //?????????????????????,??????
        //???????????????
        countDownTimer = new CountDownTimer(60000, 1000) {
            /**
             * ?????????????????????
             * @param millisUntilFinished
             */
            @Override
            public void onTick(long millisUntilFinished) {
                sendSmsCode.setText(getString(R.string.count_second, millisUntilFinished / 1000));
            }

            /**
             * ???????????????
             */
            @Override
            public void onFinish() {
                sendSmsCode.setText(R.string.send_code);

                //????????????
                sendSmsCode.setEnabled(true);
            }
        };

        //??????
        countDownTimer.start();

        //????????????
        sendSmsCode.setEnabled(false);
    }

}
