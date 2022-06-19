package com.example.dentalhousekeeper.api;

import android.media.browse.MediaBrowser;

import com.example.dentalhousekeeper.adapter.HttpObserver;
import com.example.dentalhousekeeper.domin.AppointMent;
import com.example.dentalhousekeeper.domin.BaseModel;
import com.example.dentalhousekeeper.domin.DICOMImage;
import com.example.dentalhousekeeper.domin.Department;
import com.example.dentalhousekeeper.domin.DetailResponse;
import com.example.dentalhousekeeper.domin.Doctor;
import com.example.dentalhousekeeper.domin.EReocrd;
import com.example.dentalhousekeeper.domin.GroupConsultation;
import com.example.dentalhousekeeper.domin.Hospital;
import com.example.dentalhousekeeper.domin.KV;
import com.example.dentalhousekeeper.domin.Patient;
import com.example.dentalhousekeeper.domin.Pay;
import com.example.dentalhousekeeper.domin.PreOrder;
import com.example.dentalhousekeeper.domin.Session;
import com.example.dentalhousekeeper.util.Constant;
import com.example.dentalhousekeeper.util.LogUtil;

import java.security.acl.Group;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络请求接口包装类
 * 对外部提供一个和框架无关的接口
 */
public class Api {
    /**
     * Api单例字段
     */
    private static Api instance;

    /**
     * Service单例
     */
    private final Service service;

    /**
     * 私有构造方法
     */
    private Api() {
        //初始化okhttp
        OkHttpClient.Builder okhttpClientBuilder = new OkHttpClient.Builder();

        if (LogUtil.isDebug) {
            //调试模式
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

            //设置日志等级
            interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

            okhttpClientBuilder.addInterceptor(interceptor);
        }

        //初始化retrofit
        Retrofit retrofit = new Retrofit.Builder()
                //让retrofit使用okhttp
                .client(okhttpClientBuilder.build())

                //api地址
                .baseUrl(Constant.ENDPOINT)

                //适配rxjava
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

                //使用gson解析json
                //包括请求参数和响应
                .addConverterFactory(GsonConverterFactory.create())

                //创建retrofit
                .build();

        //创建service
        service = retrofit.create(Service.class);
    }

    /**
     * 返回当前对象的唯一实例
     * <p>
     * 单例设计模式
     * 由于移动端很少有高并发
     * 所以这个就是简单判断
     *
     * @return
     */
    public static Api getInstance() {
        if (instance == null) {
            instance = new Api();
        }
        return instance;
    }

    /***
     * 注册接口
     * @param data
     * @return
     */
    public Observable<DetailResponse<BaseModel>> registerAccount(Patient data) {
        return service.registerAccount(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /***
     * 注册接口
     * @param data
     * @return
     */
    public Observable<DetailResponse<BaseModel>> registerAccount(Doctor data) {
        return service.registerAccount(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 发送短信验证码
     *
     * @param data
     * @return
     */
    public Observable<DetailResponse<BaseModel>> sendSMSCode(String data) {
        return service.sendSMSCode(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 病人登录
     *
     * @param data
     * @return
     */
    public Observable<DetailResponse<Session>> loginPatient(Patient data) {
        return service.loginPatient(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 医生登录
     *
     * @param data
     * @return
     */
    public Observable<DetailResponse<Session>> loginDoctor(Doctor data) {
        return service.loginDoctor(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /***
     * 注册接口
     * @param data
     * @return
     */
    public Observable<DetailResponse<BaseModel>> createPreorder(PreOrder data) {
        return service.createPreorder(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /***
     * 注册接口
     * @param data
     * @return
     */
    public Observable<DetailResponse<Boolean>> updateMessage(Doctor data) {
        return service.updateMessag(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /***
     * 注册接口
     * @param data
     * @return
     */
    public Observable<DetailResponse<Boolean>> updateMessage(Patient data) {
        return service.updateMessag(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /***
     * 注册接口
     * @return
     */
    public Observable<List<String>> findHospital() {
        return service.findHospital()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /***
     * 注册接口
     * @return
     */
    public Observable<List<String>> findDoctor(Doctor data) {
        return service.findDoctor(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /***
     * 注册接口
     * @return
     */
    public Observable<PreOrder> findPreorder(PreOrder data) {
        return service.findPreorder(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Pay> createAppointment(AppointMent appointMent) {
        return service.createAppointment(appointMent)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<AppointMent>> findAppointment(AppointMent appointMent) {
        return service.findAppointment(appointMent)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Doctor> findDoctorById(Doctor data) {
        return service.findDoctorById(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<PreOrder> findPreorderById(PreOrder data) {
        return service.findPreorderById(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Patient> findPatientById(Patient data) {
        return service.findPatientById(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<Integer>> findPreorderByPatientId(Patient data) {
        return service.findPreorderByPatientId(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<Integer>> findPreorderByDoctorId(Doctor data) {
        return service.findPreorderByDoctorId(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<AppointMent>> findAppointmentByDoctorId(Doctor data) {
        return service.findAppointmentByDoctorId(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<AppointMent>> findAppointmentByPatientId(Patient data) {
        return service.findAppointmentByPatientId(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<Hospital>> findAllHospital() {
        return service.findAllHospital()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<Doctor>> findDoctorsByHospitalId(Hospital data) {
        return service.findDoctorsByHospitalId(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Doctor> findDoctorssByDoctorId(Doctor data) {
        return service.findDoctorssByDoctorId(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<PreOrder>> getPreOrdersByDoctorId(Doctor data) {
        return service.getPreOrdersByDoctorId(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Hospital> findHospitalByHospitalId(Hospital data) {
        return service.findHospitalByHospitalId(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Patient> findPatientByPhone(Patient data) {
        return service.findPatientByPhone(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Doctor> findDoctorByPhone(Doctor data) {
        return service.findDoctorByPhone(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<KV> findMessageByPhone(String data) {
        return service.findMessageByPhone(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Patient> findQ() {
        return service.getQ()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<AppointMent> tradeQuery(AppointMent data) {
        return service.tradeQuery(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<DICOMImage>> findDicomsByPatientId(DICOMImage data) {
        return service.findDicomsByPatientId(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<DICOMImage> findDicoByPatientId(DICOMImage data) {
        return service.findDicoByPatientId(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<EReocrd>> findERecordByPatientId(Patient data) {
        return service.findERecordByPatientId(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<EReocrd> findERecordById(EReocrd data) {
        return service.findERecordById(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<EReocrd>> findERecordByDoctorId(Doctor data) {
        return service.findERecordByDoctorId(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<DICOMImage>> findDicomsByDoctorId(Doctor data) {
        return service.findDicomsByDoctorId(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Integer> createGroupConsultation(GroupConsultation data) {
        return service.createGroupConsultation(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<GroupConsultation>> findGroupConsultations(GroupConsultation data) {
        return service.findGroupConsultations(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<Department>> findDepartmentsByHospitalId(Hospital data) {
        return service.findDepartmentsByHospitalId(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
