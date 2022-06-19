package com.example.dentalhousekeeper.activity;

import android.os.Handler;

public class BaseTimeActivity extends BaseActivity {
    /**
     * handler
     */
    Handler handler=new Handler();

    /***
     * 演示1秒
     */
    protected void timeOutFor1000() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
            }
        }, 1000);
    }

    /***
     * 演示0.5秒
     */
    protected void timeOutFor500() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
            }
        }, 500);
    }

    /***
     * 演示跳转主界面
     */
    protected void timeConsumingOperationLogin() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivityAfterFinishThis(MainActivity.class);
            }
        }, 1000);
    }

    /***
     * 演示关闭
     */
    protected void timeConsumingOperationFeedBack() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 1000);
    }

    /***
     * 演示跳转登录界面
     */
    protected void timeConsumingOperationRegister() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivityAfterFinishThis(LoginPatientActivity.class);
            }
        }, 1000);
    }

    @Override
    public void initData() {
        super.initData();
    }
}
