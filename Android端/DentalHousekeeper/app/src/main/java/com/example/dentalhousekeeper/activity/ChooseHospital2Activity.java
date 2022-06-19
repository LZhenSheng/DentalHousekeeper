package com.example.dentalhousekeeper.activity;

import androidx.appcompat.app.AppCompatActivity;
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
import com.example.dentalhousekeeper.adapter.ChooseHospitalAdapter;
import com.example.dentalhousekeeper.adapter.HttpObserver;
import com.example.dentalhousekeeper.api.Api;
import com.example.dentalhousekeeper.domin.Hospital;
import com.example.dentalhousekeeper.util.LogUtil;
import com.example.dentalhousekeeper.util.PreferenceUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ChooseHospital2Activity extends BaseTitleActivity {

    @BindView(R.id.rv)
    RecyclerView rv;

    ChooseHospitalAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_hospital2);
    }

    @Override
    protected void initViews() {
        super.initViews();
        lightStatusBar(R.color.main_color);
        //尺寸固定
        rv.setHasFixedSize(true);

        //设置布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(ChooseHospital2Activity.this);
        rv.setLayoutManager(layoutManager);


        //创建适配器
        adapter = new ChooseHospitalAdapter(R.layout.item_hospital,getApplicationContext());

        //设置适配器
        rv.setAdapter(adapter);

        fetchData();
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                System.out.println("dsfjkfsdf"+adapter.getData().get(i).getId());
                PreferenceUtil.setHOSPITALID(adapter.getData().get(i).getId());
                startActivity(ChooseDepartmentActivity2.class);
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
