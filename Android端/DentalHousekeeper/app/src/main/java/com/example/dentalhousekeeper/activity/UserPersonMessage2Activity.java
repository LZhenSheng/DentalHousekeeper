package com.example.dentalhousekeeper.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.dentalhousekeeper.R;
import com.example.dentalhousekeeper.adapter.HttpObserver;
import com.example.dentalhousekeeper.adapter.PreOrderUserMessageAdapter;
import com.example.dentalhousekeeper.api.Api;
import com.example.dentalhousekeeper.domin.Doctor;
import com.example.dentalhousekeeper.domin.GroupConsultation;
import com.example.dentalhousekeeper.domin.PreOrder;
import com.example.dentalhousekeeper.util.ImageUtil;
import com.example.dentalhousekeeper.util.LogUtil;
import com.example.dentalhousekeeper.util.PreferenceUtil;
import com.example.dentalhousekeeper.util.ToastUtil;
import com.github.gzuliyujiang.wheelpicker.DatePicker;
import com.github.gzuliyujiang.wheelpicker.contract.OnDatePickedListener;

import java.util.List;

import butterknife.BindView;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserPersonMessage2Activity extends BaseTitleActivity implements OnDatePickedListener {

    @BindView(R.id.tv_nickname)
    TextView name;

    @BindView(R.id.type)
    TextView type;

    @BindView(R.id.room)
    TextView room;

    @BindView(R.id.address)
    TextView address;

    @BindView(R.id.descripition)
    TextView descripition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_person_message2);
    }

    @BindView(R.id.iv_avatar)
    CircleImageView iv_avatar;


    /**
     * 创建菜单方法
     *
     * 有点类似显示布局要写到onCreate方法一样
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return super.onCreateOptionsMenu(menu);
    }


    /**
     * 菜单点击了回调
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit:
                JMessageClient.register(PreferenceUtil.getCHATPHONE(), PreferenceUtil.getCHATPHONE(), new BasicCallback() {
                    /**
                     * @param responseCode    - 0 表示正常。大于 0 表示异常
                     *                        responseMessage 会有进一步的异常信息。
                     * @param responseMessage - 一般异常时会有进一步的信息提示。
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
                ChatActivity.start(this,PreferenceUtil.getCHATPHONE());
                break;
            case R.id.preorder:
                DatePicker picker = new DatePicker(this);
                picker.getWheelLayout().setResetWhenLinkage(false);
                picker.setOnDatePickedListener(this);
                picker.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initViews() {
        super.initViews();
        Doctor doctor=new Doctor();
        Log.d(TAG, "initViews: "+PreferenceUtil.getDOCTORID());
        doctor.setId(PreferenceUtil.getDOCTORID());
        Api.getInstance().findDoctorssByDoctorId(doctor)
                .subscribe(new HttpObserver<Doctor>() {
                    @Override
                    public void onSucceeded(Doctor data) {
                        if(data!=null){
                            PreferenceUtil.setCHATPHONE(data.getPhone());
                            name.setText(data.getName());
                            type.setText("职称:"+data.getType());
                            room.setText("所属科室:"+data.getRoom());
                            if(data.getProvince()!=null||data.getCity()!=null||data.getArea()!=null){
                                address.setText("地址:"+data.getProvince()+"-"+data.getCity()+"-"+data.getArea());
                            }else{
                                address.setVisibility(View.INVISIBLE);
                            }
                            descripition.setText("个人简介:"+data.getDescription());
                            ImageUtil.show(getApplicationContext(),iv_avatar,data.getAvatar());
                        }
                    }
                });
    }

    @Override
    public void onDatePicked(int year, int month, int day) {
        GroupConsultation groupConsultation=new GroupConsultation();
        if(month<10){
            if(day<10){
                groupConsultation.setDate(year+"-0"+month+"-0"+day);
            }else{
                groupConsultation.setDate(year+"-0"+month+"-"+day);
            }
        }else{
            if(day<10){
                groupConsultation.setDate(year+"-"+month+"-0"+day);
            }else{
                groupConsultation.setDate(year+"-"+month+"-"+day);
            }
        }
        groupConsultation.setStart(PreferenceUtil.getId());
        groupConsultation.setReback(PreferenceUtil.getDOCTORID());
        Api.getInstance().createGroupConsultation(groupConsultation)
                .subscribe(new HttpObserver<Integer>() {
                    @Override
                    public void onSucceeded(Integer data) {
                        if(data==1){
                            ToastUtil.successShortToast("预约成功");
                        }
                    }
                });
//        ToastUtil.successShortToast(year+"--"+month+"--"+day);
    }
}
