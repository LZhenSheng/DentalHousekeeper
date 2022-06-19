package com.example.dentalhousekeeper.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.dentalhousekeeper.domin.AppointMent;
import com.example.dentalhousekeeper.domin.Department;

/**
 * 偏好设计工具类
 * 是否登录了，是否显示引导界面，用户Id
 */
public class PreferenceUtil {

    /***
     * Date标识
     */
    private static String TIMEFLAG="TIMEFLAG";
    private static String APPOINTMENTID="APPOINTMENTID";
    private static String DEPARTMENT="DEPARTMENT";

    /***
     * 用户注册验证码
     */
    private static String SMS_CODE="SMS_CODE";

    /***
     * 手机号
     */
    private static String PHONE="PHONE";
    private static String CHATPHONE="CHATPHONE";

    /***
     * 密码
     */
    private static String ID="ID";

    /***
     * 用户类型
     */
    private static String TYPE="TYPE";

    private static String HOSPITALID="HOSPITALID";
    private static String NO="NO";

    private static String DOCTORID="DOCTORID";
    private static String URI="URI";
    private static String PREORDERID="PREORDERID";
    private static String DICOMID="DICOMID";
    private static String ERECORDID="ERECORDID";
    /**
     * 实例
     * 单例
     */
    private static PreferenceUtil instance;

    /**
     * 偏好设置文件名称
     */
    private static final String NAME = "CHARITABLE";

    /**
     * 上下文
     */
    private final Context context;
    private static SharedPreferences preference;

    /**
     * 构造方法
     * @param context
     */
    public PreferenceUtil(Context context) {
        //保存上下文
        this.context=context.getApplicationContext();

        //这样写有内存泄漏
        //因为当前工具类不会马上释放
        //如果当前工具类引用了界面实例
        //当界面关闭后
        //因为界面对应在这里还有引用
        //所以会导致界面对象不会被释放
        //this.context = context;

        //获取偏好设置
        preference = this.context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
    }

    /**
     * 获取偏好设置单例
     * @param context
     * @return
     */
    public static PreferenceUtil getInstance(Context context) {
        if (instance == null) {
            instance=new PreferenceUtil(context);
        }
        return instance;
    }

    /**
     * 保存字符串
     *
     * @param key
     * @param value
     */
    private static void putString(String key, String value) {
        preference.edit().putString(key, value).commit();
    }

    /***
     * 获取字符串
     */
    private static String getString(String key) {
        return preference.getString(key,null);
    }
    /**
     * 保存boolean
     *
     * @param key
     * @param value
     */
    private static void putBoolean(String key, boolean value) {
        preference.edit().putBoolean(key, value).commit();
    }

    /***
     * 获取boolean
     * @param key
     * @return
     */
    private static boolean getBoolean(String key) {
        return preference.getBoolean(key, false);
    }

    /**
     * 删除内容
     *
     * @param key
     */
    private static void delete(String key) {
        preference.edit().remove(key).commit();
    }

    /***
     * 获取一个int
     * @param key
     * @return
     */
    private static int getInt(String key) {
        return preference.getInt(key, 0);
    }

    /**
     * 设置一个int
     *
     * @param key
     * @param data
     */
    private static void putInt(String key, int data) {
        preference.edit().putInt(key, data).apply();
    }

    /**
     * 取短信验证码
     */
    public static String getSmsCode() {
        return getString(SMS_CODE);
    }

    /***
     * 存短信验证码
     */
    public static void saveSmsCode(String objectID) {
        putString(SMS_CODE,objectID);
    }

    /**
     * 取手机号
     */
    public static String getPhone() {
        return getString(PHONE);
    }

    /***
     * 存手机号
     */
    public static void savePhone(String objectID) {
        putString(PHONE,objectID);
    }

    /**
     * 取手机号
     */
    public static String getId() {
        return getString(ID);
    }

    /***
     * 存手机号
     */
    public static void saveId(String objectID) {
        putString(ID,objectID);
    }

    /**
     * 取手机号
     */
    public static int getType() {
        return getInt(TYPE);
    }

    /***
     * 存手机号
     */
    public static void saveType(int objectID) {
        putInt(TYPE,objectID);
    }

    /**
     * 取手机号
     */
    public static int getTimeType() {
        return getInt(TIMEFLAG);
    }

    /***
     * 存手机号
     */
    public static void setTimeType(int objectID) {
        putInt(TIMEFLAG,objectID);
    }

    public static void setHOSPITALID(String data){
        putString(HOSPITALID,data);
    }

    public static String getHOSPITALID(){
        return getString(HOSPITALID);
    }

    public static void setDOCTORID(String data){
        putString(DOCTORID,data);
    }

    public static String getDOCTORID(){
        return getString(DOCTORID);
    }

    public static void setPREORDERID(String data){
        putString(PREORDERID,data);
    }

    public static String getPREORDERID(){
        return getString(PREORDERID);
    }


    public static void setCHATPHONE(String data){
        putString(CHATPHONE,data);
    }

    public static String getCHATPHONE(){
        return getString(CHATPHONE);
    }

    public static void setURI(String data){
        putString(URI,data);
    }

    public static String getURI(){
        return getString(URI);
    }


    public static void setNO(String data){
        putString(NO,data);
    }

    public static String getNO(){
        return getString(NO);
    }


    public static void setAPPOINTMENTID(String data){
        putString(APPOINTMENTID,data);
    }

    public static String getAPPOINTMENTID(){
        return getString(APPOINTMENTID);
    }

    public static void setDICOMID(String data){
        putString(DICOMID,data);

    }

    public static String getDICOMID(){
        return getString(DICOMID);
    }

    public static void setERECORDID(String data){
        putString(ERECORDID,data);

    }

    public static String getERECORDID(){
        return getString(ERECORDID);
    }

    public static void setDEPARTMENT(String data){
        putString(DEPARTMENT,data);

    }

    public static String getDEPARTMENT(){
        return getString(DEPARTMENT);
    }
}
