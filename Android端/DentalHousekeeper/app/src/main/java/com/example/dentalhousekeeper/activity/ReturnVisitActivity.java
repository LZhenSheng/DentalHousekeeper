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
     * ????????????
     */
    private SearchView searchView;

    /**
     * ????????????
     *
     * @param menu
     * @return
     */
    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        //??????????????????
        MenuItem searchItem = menu.findItem(R.id.action_search);

        //??????????????????
        searchView = (SearchView) searchItem.getActionView();
        searchView.setIconified(true);
        //?????????????????????SearchView

        //?????????????????????
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            /**
             * ???????????????
             * ????????????????????????
             * ?????????????????????
             * @param query
             * @return
             */
            @Override
            public boolean onQueryTextSubmit(String query) {
                performSearch(query);
                return true;
            }

            /**
             * ??????????????????????????????
             * @param newText
             * @return
             */
            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

        //?????????????????????
        searchView.setOnCloseListener(() -> {
            return false;
        });

        //?????????????????????
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);

        //??????????????????
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
    }
    /**
     * ?????????????????????
     */
    private String content;
    /**
     * ????????????
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
