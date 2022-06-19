package com.example.dentalhousekeeper.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.example.dentalhousekeeper.util.Constant;
import com.github.gzuliyujiang.wheelpicker.contract.OnOptionPickedListener;

import butterknife.ButterKnife;

public class BaseActivity extends AppCompatActivity {

    public static final String TAG = "MYTAGDETAIL";

    /***
     * 初始化操作
     */
    public void initData(){

    }

    /***
    * 设置监听器
    */
    protected void initListener() {}

    /***
     * 初始化控件
     */
    protected void initViews(){
        ButterKnife.bind(this);
    }
    /**
     * 全屏
     */
    protected void fullScreen() {
        //设置界面全屏

        //获取decorView
        View decorView = getWindow().getDecorView();

        //判断版本
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) {
            //11~18版本
            decorView.setSystemUiVisibility(View.GONE);
        } else if(Build.VERSION.SDK_INT >= 19) {
            //19及以上版本
            //SYSTEM_UI_FLAG_HIDE_NAVIGATION:隐藏导航栏
            //SYSTEM_UI_FLAG_IMMERSIVE_STICKY:从状态栏下拉会半透明悬浮显示一会儿状态栏和导航栏
            //SYSTEM_UI_FLAG_FULLSCREEN:全屏
            int options = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                    View.SYSTEM_UI_FLAG_FULLSCREEN;

            //设置到控件
            decorView.setSystemUiVisibility(options);
        }
    }

    /**
     * 隐藏状态栏
     */
    protected void hideStatusBar() {
        //隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 状态栏文字显示白色
     * 内容显示到状态栏下面
     */
    protected void lightStatusBar(int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //设置为自定义颜色
            getWindow().setStatusBarColor(getResources().getColor(id));
        }
    }

    /**
     * 启动界面
     * @param clazz
     */
    protected void startActivity(Class<?> clazz){
        //创建Intent
        Intent intent = new Intent(getMainActivity(), clazz);

        //启动界面
        startActivity(intent);
    }

    /**
     * 启动界面
     * @param clazz
     */
    protected void startActivity(Class<?> clazz,String objectID){
        //创建Intent
        Intent intent = new Intent(getMainActivity(), clazz);
        intent.putExtra(Constant.ID,objectID);
        //启动界面
        startActivity(intent);
    }

    /**
     * 启动界面并关闭当前界面
     * @param clazz
     */
    protected void startActivityAfterFinishThis(Class<?> clazz) {
        startActivity(clazz);

        //关闭当前界面
        finish();
    }


    /**
     * 获取界面方法
     * @return
     */
    protected BaseActivity getMainActivity() {
        return this;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_SWIPE_TO_DISMISS);
    }

    /**
     * 在onCreate方法后面调用
     * @param savedInstanceState
     */
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        initViews();
        initData();
        initListener();
    }

    /***
     * 打印日志
     * @param content
     */
    public static void d(String content) {
        Log.i(TAG, content);
    }

    /***
     * 打印日志
     */
    public static void d() {
        Log.i(TAG, "");
    }

    /**
     * 启动界面，可以传递一个字符串参数
     *
     * @param clazz
     * @param id
     */
    protected void startActivityExtraId(Class<?> clazz, String id) {
        //创建Intent
        Intent intent = new Intent(getMainActivity(), clazz);

        //传递数据
        if (!TextUtils.isEmpty(id)) {
            //不为空才传递
            intent.putExtra(Constant.ID, id);
        }

        //启动界面
        startActivity(intent);
    }

    /**
     * 启动界面，可以传递一个字符串参数
     *
     * @param clazz
     */
    protected void startActivityN(Class<?> clazz) {
        //创建Intent
        Intent intent = new Intent(getMainActivity(), clazz);

        //启动界面
        startActivity(intent);
    }

    /**
     * 获取字符串类型Id
     *
     * @return
     */
    protected String extraId() {
        return extraString(Constant.ID);
    }

    /**
     * 获取字符串类型Id
     *
     * @return
     */
    protected String extraPId() {
        return extraString(Constant.COMMENT_ID);
    }

    /**
     * 获取字符串
     *
     * @param key
     * @return
     */
    protected String extraString(String key) {
        return getIntent().getStringExtra(key);
    }


    /**
     * 返回页面标识
     * @return
     */
    protected String pageId() {
        return null;
    }
}
