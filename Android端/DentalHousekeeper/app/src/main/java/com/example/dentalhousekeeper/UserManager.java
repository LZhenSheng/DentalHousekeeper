package com.example.dentalhousekeeper;

import android.content.Context;
import android.util.Log;

import com.example.dentalhousekeeper.adapter.HttpObserver;
import com.example.dentalhousekeeper.api.Api;
import com.example.dentalhousekeeper.domin.Doctor;
import com.example.dentalhousekeeper.domin.KV;
import com.example.dentalhousekeeper.domin.Patient;
import com.example.dentalhousekeeper.domin.User;
import com.example.dentalhousekeeper.listener.UserListener;
import com.example.dentalhousekeeper.util.LogUtil;
import com.example.dentalhousekeeper.util.PreferenceUtil;
import com.example.dentalhousekeeper.util.ToastUtil;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户管理器
 */
public class UserManager {
    /**
     * 实例
     */
    private static UserManager instance;

    /**
     * 上下文
     */
    private final Context context;

    /**
     * 用户缓存
     */
    private Map<String, User> userCaches = new HashMap<>();

    /**
     * 构造方法
     *
     * @param context
     */
    private UserManager(Context context) {
        this.context = context;
    }

    /**
     * 获取用户管理器实例
     *
     * @param context
     * @return
     */
    public static synchronized UserManager getInstance(Context context) {
        if (instance == null) {
            instance = new UserManager(context.getApplicationContext());
        }
        return instance;
    }

    /**
     * 获取用户
     *
     * @param userId       用户id
     * @param userListener
     */
    public void getUser(String userId, UserListener userListener) {
        //先从缓存中获取
        LogUtil.d("dsklfj",userId);
        User data = userCaches.get(userId);
        if (data != null) {
            LogUtil.d(":dkjflsdf",data.getName());
            userListener.onUser(data);
            return;
        }
        LogUtil.d("dklfj",userId+"----"+PreferenceUtil.getPhone()+"-----"+PreferenceUtil.getType());
        if(userId.equals(PreferenceUtil.getPhone())){
            if(PreferenceUtil.getType()==1){
                Patient patient=new Patient();
                patient.setPhone(userId);
                Api.getInstance()
                        .findPatientByPhone(patient)
                        .subscribe(new HttpObserver<Patient>() {
                            /**
                             * 登录成功
                             *
                             * @param data
                             */
                            @Override
                            public void onSucceeded(Patient data) {
                                if(data!=null){
                                    User user=new User(data);
                                    userListener.onUser(user);
                                    userCaches.put(userId,user);
                                }
                            }

                            /**
                             * 登录失败
                             *
                             * @param data
                             * @param e
                             * @return
                             */
                            @Override
                            public boolean onFailed(Patient data, Throwable e) {
//                        ToastUtil.errorShortToast(R.string.error_network_connect);
                                return false;
                            }
                        });
            }else{
                Doctor doctor=new Doctor();
                doctor.setPhone(userId);
                Api.getInstance()
                        .findDoctorByPhone(doctor)
                        .subscribe(new HttpObserver<Doctor>() {
                            /**
                             * 登录成功
                             *
                             * @param data
                             */
                            @Override
                            public void onSucceeded(Doctor data) {
                                if(data!=null){
                                    User user=new User(data);
                                    userListener.onUser(user);
                                    userCaches.put(userId,user);
                                }
                            }

                            /**
                             * 登录失败
                             *
                             * @param data
                             * @param e
                             * @return
                             */
                            @Override
                            public boolean onFailed(Doctor data, Throwable e) {
//                        ToastUtil.errorShortToast(R.string.error_network_connect);
                                return false;
                            }
                        });
            }
        }else{
            if(PreferenceUtil.getType()==0){
                Patient patient=new Patient();
                patient.setPhone(userId);
                Api.getInstance()
                        .findPatientByPhone(patient)
                        .subscribe(new HttpObserver<Patient>() {
                            /**
                             * 登录成功
                             *
                             * @param data
                             */
                            @Override
                            public void onSucceeded(Patient data) {
                                if(data!=null){
                                    User user=new User(data);
                                    userListener.onUser(user);
                                    userCaches.put(userId,user);
                                }
                            }

                            /**
                             * 登录失败
                             *
                             * @param data
                             * @param e
                             * @return
                             */
                            @Override
                            public boolean onFailed(Patient data, Throwable e) {
//                        ToastUtil.errorShortToast(R.string.error_network_connect);
                                return false;
                            }
                        });
            }else{
                Doctor doctor=new Doctor();
                doctor.setPhone(userId);
                Api.getInstance()
                        .findDoctorByPhone(doctor)
                        .subscribe(new HttpObserver<Doctor>() {
                            /**
                             * 登录成功
                             *
                             * @param data
                             */
                            @Override
                            public void onSucceeded(Doctor data) {
                                if(data!=null){
                                    User user=new User(data);
                                    userListener.onUser(user);
                                    userCaches.put(userId,user);
                                }
                            }

                            /**
                             * 登录失败
                             *
                             * @param data
                             * @param e
                             * @return
                             */
                            @Override
                            public boolean onFailed(Doctor data, Throwable e) {
//                        ToastUtil.errorShortToast(R.string.error_network_connect);
                                return false;
                            }
                        });
            }
        }
    }
}
