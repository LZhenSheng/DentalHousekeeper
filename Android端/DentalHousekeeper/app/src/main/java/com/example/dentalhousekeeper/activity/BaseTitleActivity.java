package com.example.dentalhousekeeper.activity;

import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;


import com.example.dentalhousekeeper.R;
import com.tencent.liteav.tuiroom.ui.utils.StateBarUtils;

import butterknife.BindView;

/**
 * 通用标题界面
 */
public class BaseTitleActivity extends BaseActivity {

    /**
     * 标题控件
     */
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void initViews() {
        super.initViews();
        //初始化Toolbar
        setSupportActionBar(toolbar);
        StateBarUtils.setLightStatusBar(this);
    }

    @Override
    public void initData() {
        super.initData();

        //是否显示返回按钮
        if (isShowBackMenu()) {
            showBackMenu();
        }
        setTitleCenter(toolbar);
    }


    /**
     * 显示返回按钮
     */
    protected void showBackMenu() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * 是否显示返回按钮
     * @return
     */
    protected boolean isShowBackMenu() {
        return true;
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


    public static void setTitleCenter(Toolbar toolbar) {
        String title = "title";
        final CharSequence originalTitle = toolbar.getTitle();
        toolbar.setTitle(title);
        for (int i = 0; i < toolbar.getChildCount(); i++) {
            View view = toolbar.getChildAt(i);
            if (view instanceof TextView) {
                TextView textView = (TextView) view;
                if (title.equals(textView.getText())) {
                    textView.setGravity(Gravity.CENTER);
                    Toolbar.LayoutParams params = new Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.MATCH_PARENT);
                    params.gravity = Gravity.CENTER;
                    textView.setLayoutParams(params);
                }
            }
            toolbar.setTitle(originalTitle);
        }
    }

}
