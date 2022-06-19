package com.example.dentalhousekeeper.activity;

import androidx.annotation.NonNull;
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
import com.example.dentalhousekeeper.domin.PreOrder;
import com.example.dentalhousekeeper.util.ImageUtil;
import com.example.dentalhousekeeper.util.LogUtil;
import com.example.dentalhousekeeper.util.PreferenceUtil;

import java.util.List;

import butterknife.BindView;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserPersonMessageActivity extends BaseTitleActivity {

    @BindView(R.id.tv_nickname)
    TextView name;

    @BindView(R.id.rv)
    RecyclerView rv;

    @BindView(R.id.type)
    TextView type;

    @BindView(R.id.room)
    TextView room;

    @BindView(R.id.address)
    TextView address;

    @BindView(R.id.descripition)
    TextView descripition;

    PreOrderUserMessageAdapter adapter;

    @BindView(R.id.iv_avatar)
    CircleImageView iv_avatar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_person_message);
    }

    /**
     * 创建菜单方法
     *
     * 有点类似显示布局要写到onCreate方法一样
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
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
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void initData() {
        super.initData();
        //获取传递的id
        lightStatusBar(R.color.main_color);
        //尺寸固定
        rv.setHasFixedSize(true);

        //设置布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(getMainActivity());
        rv.setLayoutManager(layoutManager);

        adapter = new PreOrderUserMessageAdapter(R.layout.item_preorder_user_message,this);

        //设置适配器
        rv.setAdapter(adapter);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                switch (view.getId()){
                    case R.id.preorder:
                        PreferenceUtil.setPREORDERID(adapter.getData().get(i).getId());
                        startActivity(AddPreOrderPatientActivity.class);
                        break;
                }
            }
        });
        fetchData();
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
                            if(data.getAvatar()!=null){
                                ImageUtil.show(getApplicationContext(),iv_avatar,data.getAvatar());
                            }
                        }
                    }
                });
    }

    /***
     * 请求数据
     */
    public void fetchData(){
        Doctor doctor=new Doctor();
        doctor.setId(PreferenceUtil.getDOCTORID());
        Api.getInstance().getPreOrdersByDoctorId(doctor)
                .subscribe(new HttpObserver<List<PreOrder>>() {
                    @Override
                    public void onSucceeded(List<PreOrder> data) {
                        if(data!=null||data.size()!=0){
                            adapter.replaceData(data);
                        }
                    }
                });
    }
}
