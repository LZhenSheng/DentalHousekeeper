package com.example.dentalhousekeeper.activity;

import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.dentalhousekeeper.R;
import com.example.dentalhousekeeper.adapter.HttpObserver;
import com.example.dentalhousekeeper.adapter.PreOrderDoctorAdapter;
import com.example.dentalhousekeeper.adapter.ReturnVisitAdapter;
import com.example.dentalhousekeeper.api.Api;
import com.example.dentalhousekeeper.domin.AppointMent;
import com.example.dentalhousekeeper.domin.Doctor;
import com.example.dentalhousekeeper.domin.Patient;
import com.example.dentalhousekeeper.util.PreferenceUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ReturnVisitActivity extends BaseTitleActivity {

    @BindView(R.id.rv)
    RecyclerView rv;

    ReturnVisitAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_visit);
    }

    @Override
    public void initData() {
        super.initData();
        rv.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ReturnVisitActivity.this);
        rv.setLayoutManager(layoutManager);
        adapter = new ReturnVisitAdapter(R.layout.item_patient,getApplicationContext());
        rv.setAdapter(adapter);
        fetchData();
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                switch (view.getId()){
                    case R.id.returnback:
                        Patient patient=new Patient();
                        patient.setId(adapter.getData().get(i).getPatient_id());
                        Api.getInstance().findPatientById(patient)
                                .subscribe(new HttpObserver<Patient>() {
                                    @Override
                                    public void onSucceeded(Patient data) {
                                        if (data != null) {
                                            ChatActivity.start(ReturnVisitActivity.this,data.getPhone());
                                        } else {
//                                    ToastUtil.errorShortToast(data);
                                        }
                                    }
                                });
                        break;
                }
            }
        });
    }

    private void fetchData() {
        Doctor doctor=new Doctor();
        doctor.setId(PreferenceUtil.getId());
        Api.getInstance().findAppointmentByDoctorId(doctor)
                .subscribe(new HttpObserver<List<AppointMent>>() {
                    @Override
                    public void onSucceeded(List<AppointMent> data) {
                        if(data!=null){
                            adapter.replaceData(data);
                        }
                    }
                });
    }

    /**
     * 搜索控件
     */
    private SearchView searchView;

    /**
     * 返回菜单
     *
     * @param menu
     * @return
     */
    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        //查找搜索按钮
        MenuItem searchItem = menu.findItem(R.id.action_search);

        //查找搜索控件
        searchView = (SearchView) searchItem.getActionView();
        searchView.setIconified(true);
        //可以在这里配置SearchView

        //设置搜索监听器
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            /**
             * 提交了搜索
             * 回车搜索调用两次
             * 点击键盘上搜索
             * @param query
             * @return
             */
            @Override
            public boolean onQueryTextSubmit(String query) {
                performSearch(query);
                return true;
            }

            /**
             * 搜索输入框文本改变了
             * @param newText
             * @return
             */
            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

        //设置关闭监听器
        searchView.setOnCloseListener(() -> {
            return false;
        });

        //获取搜索管理器
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);

        //设置搜索信息
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
    }
    /**
     * 当前搜索关键字
     */
    private String content;
    /**
     * 执行搜索
     *
     * @param content
     */
    private void performSearch(String content) {
        this.content = content;
        Doctor doctor=new Doctor();
        doctor.setId(PreferenceUtil.getId());
        Api.getInstance().findAppointmentByDoctorId(doctor)
                .subscribe(new HttpObserver<List<AppointMent>>() {
                    @Override
                    public void onSucceeded(List<AppointMent> data) {
                        if(data!=null){
                            List<AppointMent> result=new ArrayList<>();
                            for(int i=0;i<data.size();i++){
                                if(data.get(i).getCreated_at().toString().contains(content)){
                                    result.add(data.get(i));
                                }
                            }
                            adapter.replaceData(result);
                        }
                    }
                });
    }
}
