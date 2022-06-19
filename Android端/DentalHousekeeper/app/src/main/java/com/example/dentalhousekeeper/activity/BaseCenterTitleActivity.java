package com.example.dentalhousekeeper.activity;

import android.view.MenuItem;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.example.dentalhousekeeper.R;
import com.tencent.liteav.tuiroom.ui.utils.StateBarUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 通用标题界面
 */
public class BaseCenterTitleActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.title)
    TextView title;

    @Override
    protected void initViews() {
        super.initViews();
        setSupportActionBar(toolbar);
        StateBarUtils.setLightStatusBar(this);
        lightStatusBar(R.color.main_color);
    }

    public void setTitle(String content){
        title.setText(content);
    }

    /**
     * 菜单点击了回调
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //Toolbar返回按钮点击
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
