package com.example.dentalhousekeeper.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.emoji.bundled.BundledEmojiCompatConfig;
import androidx.emoji.text.EmojiCompat;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;


import com.example.dentalhousekeeper.activity.MainActivity;
import com.example.dentalhousekeeper.event.OnNewMessageEvent;
import com.example.dentalhousekeeper.util.Constant;
import com.example.dentalhousekeeper.util.LogUtil;
import com.example.dentalhousekeeper.util.MessageUtil;
import com.example.dentalhousekeeper.util.PreferenceUtil;
import com.example.dentalhousekeeper.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import cn.jiguang.analytics.android.api.JAnalyticsInterface;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.event.NotificationClickEvent;
import cn.jpush.im.android.api.model.Message;
import es.dmoral.toasty.Toasty;


/***
* 全局配置文件
* @author 胜利镇
* @time 2021/12/28
* @dec 
*/
public class AppContext extends Application implements Application.ActivityLifecycleCallbacks {

    /**
     * 当前activity引用
     * 通过弱引用保存
     * 不影响gc销毁该界面
     */
    private WeakReference<Activity> currentActivity;

    /**
     * 上下文
     */
    private static AppContext context;

    private static final String TAG = "AppContext";

    /**
     * 偏好设置
     * 存储离线数据和特殊标记位
     */
    private PreferenceUtil sp;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //MultiDex
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context=this;
//初始化Toast工具类
        Toasty.Config.getInstance().apply();
//初始化toast工具类
        ToastUtil.init(getApplicationContext());

        //偏好设置初始化
        sp=PreferenceUtil.getInstance(getApplicationContext());
        initJiGuang();
        //初始化emoji
        EmojiCompat.Config config = new BundledEmojiCompatConfig(this);
        EmojiCompat.init(config);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            builder.detectFileUriExposure();
        }
        closeAndroidPDialog();
    }

    private void closeAndroidPDialog() {
        try {
            Class aClass = Class.forName("android.content.pm.PackageParser$Package");
            Constructor declaredConstructor = aClass.getDeclaredConstructor(String.class);
            declaredConstructor.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Class cls = Class.forName("android.app.ActivityThread");
            Method declaredMethod = cls.getDeclaredMethod("currentActivityThread");
            declaredMethod.setAccessible(true);
            Object activityThread = declaredMethod.invoke(null);
            Field mHiddenApiWarningShown = cls.getDeclaredField("mHiddenApiWarningShown");
            mHiddenApiWarningShown.setAccessible(true);
            mHiddenApiWarningShown.setBoolean(activityThread, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取当前上下文
     *
     * @return
     */
    public static AppContext getInstance() {
        return context;
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {

    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {

    }

    /**
     * 初始化激光
     */
    private void initJiGuang() {
        //初始化激光统计
        JAnalyticsInterface.init(getApplicationContext());

        //设置激光统计调试模式
        JAnalyticsInterface.setDebugMode(LogUtil.isDebug);

        //初始化极光IM
        JMessageClient.init(getApplicationContext());

//注册极光消息回调
        JMessageClient.registerEventReceiver(this);
    }


    /**
     * 接收在线消息
     * 还有接收离线消息的事件
     * 如果有需要请查看文档
     * https://docs.jiguang.cn//jmessage/client/android_sdk/message/#_30
     *
     * @param event
     */
    public void onEventMainThread(MessageEvent event) {
        //获取消息
        Message data = event.getMessage();

        LogUtil.d(TAG, "onEventMainThread:" + data.getContentType() + "," + MessageUtil.getContent(data.getContent()));

        //发布消息事件
        EventBus.getDefault().post(new OnNewMessageEvent(data));
    }

    /**
     * 聊天消息通知栏点击
     *
     * @param event
     */
    public void onEventMainThread(NotificationClickEvent event) {
        //获取消息发送人id
        String id = event.getMessage().getFromUser().getUserName();

        //创建意图
        Intent intent = new Intent(this, MainActivity.class);

        //添加用户id
        intent.putExtra(Constant.ID, id);

        //要跳转到聊天界面
        //先启动主界面的
        //好处是
        //用户在聊天界面
        //返回正好看到的主界面
        //这样才符合应用逻辑
        intent.setAction(Constant.ACTION_MESSAGE);

        //在Activity以外启动界面
        //都要写这个标识
        //具体的还比较复杂
        //基础课程中讲解
        //这里学会这样用就行了
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        //启动界面
        startActivity(intent);
    }
}