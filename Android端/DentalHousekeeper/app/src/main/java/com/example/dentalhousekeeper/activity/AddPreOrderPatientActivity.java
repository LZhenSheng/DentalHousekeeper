package com.example.dentalhousekeeper.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.dentalhousekeeper.R;
import com.example.dentalhousekeeper.adapter.HttpObserver;
import com.example.dentalhousekeeper.api.Api;
import com.example.dentalhousekeeper.domin.AppointMent;
import com.example.dentalhousekeeper.domin.Doctor;
import com.example.dentalhousekeeper.domin.Hospital;
import com.example.dentalhousekeeper.domin.Patient;
import com.example.dentalhousekeeper.domin.Pay;
import com.example.dentalhousekeeper.domin.PreOrder;
import com.example.dentalhousekeeper.event.OnAlipayStatusChangedEvent;
import com.example.dentalhousekeeper.util.DensityUtil;
import com.example.dentalhousekeeper.util.LoadingUtil;
import com.example.dentalhousekeeper.util.LogUtil;
import com.example.dentalhousekeeper.util.PayUtil;
import com.example.dentalhousekeeper.util.PreferenceUtil;
import com.example.dentalhousekeeper.util.ToastUtil;
import com.king.zxing.util.CodeUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

public class AddPreOrderPatientActivity extends BaseTitleActivity {

    String patientId;
    String doctorId;
    String preorderId;

    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.hospital)
    TextView hospital;
    @BindView(R.id.main)
    LinearLayout linearLayout;

    @BindView(R.id.date)
    TextView date;

    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.doctor)
    TextView tdoctor;

    @BindView(R.id.card)
    TextView card;
    @BindView(R.id.number)
    TextView number;

    @BindView(R.id.money)
    TextView money;
    @BindView(R.id.style)
    TextView style;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pre_order_patient);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initViews() {
        super.initViews();
        lightStatusBar(R.color.main_color);
        PreOrder preOrder=new PreOrder();
        preOrder.setId(PreferenceUtil.getPREORDERID());
        Api.getInstance().findPreorderById(preOrder)
                .subscribe(new HttpObserver<PreOrder>() {
                    @Override
                    public void onSucceeded(PreOrder data) {
                        if (data != null) {
                            date.setText(data.getDate());
                            number.setText(data.getStart_time()+"~"+data.getEnd_time());
                            money.setText(String.valueOf(data.getMoney()).trim());
                            style.setText(data.getStyle());
                            preorderId=data.getId();
                        } else {
//                                    ToastUtil.errorShortToast(data);
                        }
                    }
                });
        Hospital data=new Hospital();
        data.setId(PreferenceUtil.getHOSPITALID());
        System.out.println("dklfjsdf"+PreferenceUtil.getHOSPITALID());
        Api.getInstance().findHospitalByHospitalId(data)
                .subscribe(new HttpObserver<Hospital>() {
                    @Override
                    public void onSucceeded(Hospital data) {
                        if (data != null) {
                            hospital.setText(data.getName());
                        } else {
                        }
                    }
                });
        Patient patient=new Patient();
        patient.setId(PreferenceUtil.getId());
        Api.getInstance().findPatientById(patient)
                .subscribe(new HttpObserver<Patient>() {
                    @Override
                    public void onSucceeded(Patient data) {
                        if (data != null) {
                            name.setText(data.getName());
                            card.setText(data.getCard());
                            phone.setText(data.getPhone());
                            patientId=data.getId();
                        } else {
                        }
                    }
                });
        Doctor doctor=new Doctor();
        doctor.setId(PreferenceUtil.getDOCTORID());
        Api.getInstance().findDoctorById(doctor)
                .subscribe(new HttpObserver<Doctor>() {
                    @Override
                    public void onSucceeded(Doctor data) {
                        if (data != null) {
                            tdoctor.setText(data.getName());
                            doctorId=data.getId();
                        } else {
                        }
                    }
                });
    }

    @OnClick({R.id.agree})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.agree:
                AppointMent appointMent = new AppointMent();
                appointMent.setPatient_id(PreferenceUtil.getId());
                appointMent.setDoctor_id(doctorId);
                appointMent.setPreorder_id(preorderId);
                Api.getInstance().createAppointment(appointMent)
                        .subscribe(new HttpObserver<Pay>() {
                            @Override
                            public void onSucceeded(Pay data) {
                                if (data != null) {
//                                    ToastUtil.successShortToast("预约成功");
                                    processAlipay(data.getPay());
//                                    processAlipay(2B4IHa8R6cb23PLbJpZwe6w2cE0soNFHdz91mi7Z%2BAS0p25iLvlyewTScoSuwhbFzLPQzNsw%2Bqo0tmydrCW821JNTlMcPpHDdb8MEitDInfwXXhJw0nPPqkwZXu4LD9Tc0xqChRVC9bmsNPbZMI%2FmB8D%2BDOSSmucUGfx0GNu13xFUXw0gILyPKtNffLKDAaq8FjoadAHg3r2NQ%3D%3D&sign_type=RSA2&timestamp=2022-04-19+16%3A10%3A03&version=1.0");
//                                    processAlipay("alipay_sdk=alipay-sdk-java-3.3.0&app_id=2019013063161737&biz_content=%7B%22out_trade_no%22%3A%2222222222%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22%E7%89%99%E5%8C%BB%E7%AE%A1%E5%AE%B6-%E9%A2%84%E7%BA%A6%22%2C%22total_amount%22%3A%2210.10%22%7D&charset=utf-8&format=json&method=alipay.trade.app.pay&sign=IlvdnW6aPazDjK1VOlM8QWh%2BStVrUvGpAUIbaY3aK9%2F%2FhJ0knHdbeZvmuHzghYISA5DW5%2BozhFxJUoGUjjYSgtW48%2BlOztZPIgsrAM95fvuwyg95QIRE8xiI0JUbnxoDwGA6VxJZcVoNayv5DqaMBGGkQwysGKCmpVtAdVFmIS%2FGpE0C2YGdPteyYuOQbqIcxcGQNXetbtcDucnpacEphk33WUkW7W%2FEPfSuDx09yplzMp6FhoQtDQ2kuztyJ8wRsor2kVJKC0eRSXCYyJSvH%2Brxs7TC%2FRimVWLJ137FFcXQCB15rWMhf8boRpvFNXrTgiyae9YU%2Fbc9lifwfhsrFA%3D%3D&sign_type=RSA2&timestamp=2022-04-19+15%3A27%3A09&version=1.0");
//                                    processAlipay("alipay_sdk=alipay-sdk-java-3.3.0&app_id=2019013063161737&biz_content=%7B%22out_trade_no%22%3A%2222222222%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22seller_id%22%3A%222759100807%40qq.com%22%2C%22subject%22%3A%22%E7%89%99%E5%8C%BB%E7%AE%A1%E5%AE%B6-%E9%A2%84%E7%BA%A6%22%2C%22total_amount%22%3A%2210.10%22%7D&charset=utf-8&format=json&method=alipay.trade.app.pay&sign=QW0kYzGSA65YwL95O0juglbgLq9dVVsbWzcvUwHXMsw1NTQwgHiCDBD8P5%2BNMgiPtYmDR10TaaGLArHNwQ5DJ%2BzIu7Fz%2F48UwtvzbXArDrQ7wZem03JwC3QA1Z3h%2FFinp6ob1QXu7mUszDAUJCfXbUeIpm5KOaRQht%2FHrAbwQBg07gAH1qVFmf1vDIZSvuEwmoNMwjAUxhuwEmuU%2FGX%2BUj9aGVYOqMOVcAjzgJv7NOF3MARWsmRFxHbN6u1wVWaxRMuwCCYx0NqIKryjMa5zIMx0GFuCFiMltbTNOYFLsnVgKIVAITUKS75pW%2FVsGjbSxNM23cLQ4AvUi4u8kMVHKA%3D%3D&sign_type=RSA2&timestamp=2022-04-19+14%3A02%3A30&version=1.0");
//                                    processAlipay("alipay_sdk=alipay-sdk-java-3.3.0&app_id=2019013063161737&biz_content=%7B%22out_trade_no%22%3A%2222222222%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22seller_id%22%3A%2218237056873%22%2C%22subject%22%3A%22%E7%89%99%E5%8C%BB%E7%AE%A1%E5%AE%B6-%E9%A2%84%E7%BA%A6%22%2C%22total_amount%22%3A%2210.10%22%7D&charset=utf-8&format=json&method=alipay.trade.app.pay&sign=qEyLamCI9TfuFLKKe46NsO%2B9YoaGqSkKEH5HoIf4pUwwbS0MFDScdOAXxPS464lpyNuNukqQpO3YFPasRwGBwLGlmA3Nh%2B2GlLKm9mQ2SRF9NXe6j5HzRZaEHop45ROnuw8m1FnNFDGVUFRbgv3LcuY14OQ5QzWhvtYj6EShLLYBLzHrTQ1bKAbdnL79pME323decc4JhgFR4n%2Fy97KpyNwqwCGq1pbJN8D0jRRtAcMjqwwCp7zthNOjZG7zgmki%2FcpVKNzN16x8LSTgrYteB6A5DtxJ0E2QV1HhX716ypjo5lVCdcSOew%2F7W1OzMN3lLzvOBP33e2IJvKj%2FIaoHeA%3D%3D&sign_type=RSA2&timestamp=2022-04-19+12%3A14%3A29&version=1.0");
//                                    PreferenceUtil.setAPPOINTMENTID(data.getId());
//                                    PreferenceUtil.setURI(data.getUri());
//                                    PreferenceUtil.setNO(data.getNo());
//                                    startActivity(CodeActivity.class);
//                                    LogUtil.d("dlksjfdf",data.getUri());
                                } else {
//                                    ToastUtil.errorShortToast(data);
                                }
                            }

                            @Override
                            public boolean onFailed(Pay data, Throwable e) {
                                ToastUtil.errorShortToast("提交失败");
                                LogUtil.d("dfdf", e.getMessage());
                                return super.onFailed(data, e);
                            }
                        });
                break;
        }
    }

    /**
     * 处理支付宝支付
     *
     * @param data
     */
    private void processAlipay(String data) {
        PayUtil.alipay(getMainActivity(), data);
    }

    /**
     * 支付宝支付状态改变了
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAlipayStatusChanged(OnAlipayStatusChangedEvent event) {
        String resultStatus = event.getData().getResultStatus();

        if ("9000".equals(resultStatus)) {
            //本地支付成功

            //不能依赖本地支付结果
            //一定要以服务端为准
            ToastUtil.successShortToast(R.string.hint_pay_wait);
            finish();
        } else {
            //支付取消
            //支付失败
            ToastUtil.errorShortToast(R.string.error_pay_failed);
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
