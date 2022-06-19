package com.example.dentalhousekeeper.fragment;

import android.content.Intent;
import android.util.Log;

import com.example.dentalhousekeeper.activity.BaseActivity;
import com.example.dentalhousekeeper.util.PreferenceUtil;

/**
 * 通用Fragment逻辑
 */
public abstract class BaseCommonFragment extends BaseFragment {
    private static String TAG="MYTAGDETAIL";
    protected PreferenceUtil sp;

    @Override
    protected void initData() {
        super.initData();

        //初始化偏好工具类
        sp = PreferenceUtil.getInstance(getMainActivity().getApplicationContext());
    }

    /**
     * 获取int值
     *
     * @param key
     * @return
     */
    protected int extraInt(String key) {
        return getArguments().getInt(key, -1);
    }


    /**
     * 启动界面
     *
     * @param clazz
     */
    protected void startActivity(Class<?> clazz) {
        Intent intent = new Intent(getActivity(), clazz);
        startActivity(intent);
    }

    /**
     * 启动界面并关闭当前界面
     *
     * @param clazz
     */
    protected void startActivityAfterFinishThis(Class<?> clazz) {
        startActivity(new Intent(getMainActivity(), clazz));
        getActivity().finish();
    }

    /**
     * 获取当前Fragment所在的Activity
     *
     * @return
     */
    public BaseActivity getMainActivity() {
        return (BaseActivity) getActivity();
    }


    /***
     * 打印日志
     * @param content
     */
    public static void d(String content) {
        Log.i(TAG, content);
    }

}