package com.example.dentalhousekeeper.activity;

import androidx.appcompat.widget.SearchView;
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
import com.example.dentalhousekeeper.adapter.ERecordAdapter;
import com.example.dentalhousekeeper.adapter.HttpObserver;
import com.example.dentalhousekeeper.api.Api;
import com.example.dentalhousekeeper.domin.DICOMImage;
import com.example.dentalhousekeeper.domin.Doctor;
import com.example.dentalhousekeeper.domin.EReocrd;
import com.example.dentalhousekeeper.domin.Patient;
import com.example.dentalhousekeeper.util.LogUtil;
import com.example.dentalhousekeeper.util.PreferenceUtil;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;

public class ERecordListActivity extends BaseTitleActivity {

    @BindView(R.id.rv)
    RecyclerView rv;

    ERecordAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e_record_list);
    }

    @Override
    public void initData() {
        super.initData();
        rv.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ERecordListActivity.this);
        rv.setLayoutManager(layoutManager);
        adapter = new ERecordAdapter(R.layout.item_record);
        rv.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                PreferenceUtil.setERECORDID(adapter.getData().get(i).getId());
                LogUtil.d("djklfs",PreferenceUtil.getERECORDID());
                startActivity(ERecordDetailActivity.class);
            }
        });
        fetchData();
    }

    private void fetchData() {
        System.out.println(PreferenceUtil.getId()+"dlskfjkjf");
        Patient patient=new Patient();
        patient.setId(PreferenceUtil.getId());
        System.out.println(patient.toString()+"dlskfjkjf");
        Api.getInstance().findERecordByPatientId(patient)
                .subscribe(new HttpObserver<List<EReocrd>>() {
                    @Override
                    public void onSucceeded(List<EReocrd> data) {
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
        System.out.println(PreferenceUtil.getId()+"dlskfjkjf");
        Patient patient=new Patient();
        patient.setId(PreferenceUtil.getId());
        System.out.println(patient.toString()+"dlskfjkjf");
        Api.getInstance().findERecordByPatientId(patient)
                .subscribe(new HttpObserver<List<EReocrd>>() {
                    @Override
                    public void onSucceeded(List<EReocrd> data) {
                        if(data!=null){
                            List<EReocrd> result=new ArrayList<>();
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
