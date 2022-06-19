package com.example.demo.service;


import com.example.demo.domin.Doctor;
import com.example.demo.domin.Patient;
import com.example.demo.mapper.DoctorMapper;
import com.example.demo.mapper.PatientMapper;
import com.example.demo.util.BCryptUtil;
import com.example.demo.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.print.Doc;

import static com.example.demo.util.Constant.RESULT_OK;

/**
 * 用户session服务接口
 */
@Service
public class SessionService {

    @Autowired
    PatientMapper patientMapper;

    @Autowired
    DoctorMapper doctorMapper;

    /**
     * 检查session
     * @param userId
     * @param authorization
     * @return
     */
//    Patient checkSession(String userId, String authorization);

    /**
     * 保存session
     * @param data
     * @return
     */
    public boolean saveSession(Patient data){
        return patientMapper.updateSessionDigest(data.getId(),data.getSessionDigest()) == RESULT_OK;
    }

    /**
     * 删除session
     * @param id
     * @return
     */
//    boolean deleteSession(String id);

    /**
     * 检查session
     * @param userId
     * @param authorization
     * @return
     */
    public Patient checkSession(String userId, String authorization) {
        //根据用户id查询用户
        Patient data = patientMapper.find(userId);

        //判断用户是否存在
        if (data!=null) {
            //判断session是否匹配
            if (BCryptUtil.matchEncode(authorization, data.getSessionDigest())) {
                return data;
            }
        }

        return null;
    }

    /***
     * 删除session
     */
    public boolean deleteSession(String id) {

        return patientMapper.updateSessionDigest(id,null)== RESULT_OK;

    }
    
    /***
    * 更新用户
    */
    public boolean update(Patient data) {
        int result=patientMapper.update(data);
        return result==RESULT_OK;
    }


    /**
     * 检查session
     * @param userId
     * @param authorization
     * @return
     */
//    Patient checkSession(String userId, String authorization);

    /**
     * 保存session
     * @param data
     * @return
     */
    public boolean saveSession(Doctor data){
        return doctorMapper.updateSessionDigest(data.getId(),data.getSessionDigest()) == RESULT_OK;
    }

    /**
     * 删除session
     * @param id
     * @return
     */
//    boolean deleteSession(String id);

    /**
     * 检查session
     * @param userId
     * @param authorization
     * @return
     */
    public Doctor checkSession(String userId, String authorization, int type) {
        //根据用户id查询用户
        Doctor data = doctorMapper.find(userId);

        //判断用户是否存在
        if (data!=null) {
            //判断session是否匹配
            if (BCryptUtil.matchEncode(authorization, data.getSessionDigest())) {
                return data;
            }
        }

        return null;
    }

    /***
     * 删除session
     */
    public boolean deleteSession(String id,int type) {

        return doctorMapper.updateSessionDigest(id,null)== RESULT_OK;

    }

    /***
     * 更新用户
     */
    public boolean update(Doctor data) {
        int result=doctorMapper.update(data);
        return result==RESULT_OK;
    }
}