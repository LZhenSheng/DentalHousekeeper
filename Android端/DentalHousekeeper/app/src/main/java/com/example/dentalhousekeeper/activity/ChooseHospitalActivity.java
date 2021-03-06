package com.example.dentalhousekeeper.activity;
import androidx.appcompat.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.dentalhousekeeper.R;
import com.example.dentalhousekeeper.adapter.ChooseHospitalAdapter;
import com.example.dentalhousekeeper.adapter.HttpObserver;
import com.example.dentalhousekeeper.api.Api;
import com.example.dentalhousekeeper.domin.AppointMent;
import com.example.dentalhousekeeper.domin.Doctor;
import com.example.dentalhousekeeper.domin.Hospital;
import com.example.dentalhousekeeper.util.LogUtil;
import com.example.dentalhousekeeper.util.PreferenceUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ChooseHospitalActivity extends BaseTitleActivity {

    @BindView(R.id.rv)
    RecyclerView rv;

    ChooseHospitalAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_hospital);
    }

    @Override
    protected void initViews() {
        super.initViews();
        lightStatusBar(R.color.main_color);
        rv.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ChooseHospitalActivity.this);
        rv.setLayoutManager(layoutManager);
        adapter = new ChooseHospitalAdapter(R.layout.item_hospital,getApplicationContext());
        rv.setAdapter(adapter);
        fetchData();
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                System.out.println("dsfjkfsdf"+adapter.getData().get(i).getId());
                PreferenceUtil.setHOSPITALID(adapter.getData().get(i).getId());
                startActivity(ChooseDepartmentActivity.class);
            }
        });

    }

    private void fetchData() {
        Api.getInstance().findAllHospital()
                .subscribe(new HttpObserver<List<Hospital>>() {
                    @Override
                    public void onSucceeded(List<Hospital> data) {
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
        Api.getInstance().findAllHospital()
                .subscribe(new HttpObserver<List<Hospital>>() {
                    @Override
                    public void onSucceeded(List<Hospital> data) {
                        if(data!=null){
                            List<Hospital> result=new ArrayList<>();
                            for(int i=0;i<data.size();i++){
                                if(data.get(i).getName().contains(content)||data.get(i).getProvince().contains(content)||data.get(i).getCity().contains(content)||data.get(i).getArea().contains(content)||data.get(i).getPhone().contains(content)){
                                    result.add(data.get(i));
                                }
                            }
                            adapter.replaceData(result);
                        }
                    }
                });
    }
}
