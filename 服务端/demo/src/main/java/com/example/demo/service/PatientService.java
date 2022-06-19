package com.example.demo.service;

import com.example.demo.domin.Patient;
import com.example.demo.domin.PreOrder;
import com.example.demo.mapper.PatientMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.util.Constant.RESULT_OK;

/**
 * 用户服务器
 */
@Service
public class PatientService {


    /**
     * 用户数据仓库
     */
    @Autowired
    private PatientMapper mapper;

    public Patient findByPhone(String phone) {
        return mapper.findByPhone(phone);
    }

    public void create(Patient data) {
        mapper.create(data);
    }

    public List<Patient> findAll() {
        return mapper.findAll();
    }

    public Patient find(String id) {
        return mapper.find(id);
    }

    /**
     * 更新验证码
     *
     * @param id
     * @param data
     * @return
     */
    public boolean updateCode(String id, String data) {
        return mapper.updateCode(id, data) == RESULT_OK;
    }


    public boolean updatePatientMessage(String id, String name,int gender,String province,String city,String area,String card,
    Integer age,String birthday,String avator) {
        if(avator==null){
            return mapper.updatePatientMessage1(id, name,gender,province,city,area,card,age,birthday) == RESULT_OK;
        }else{
            return mapper.updatePatientMessage(id, name,gender,province,city,area,card,age,birthday,avator) == RESULT_OK;
        }
    }

    public List<PreOrder> findPreorderByPatientId(String data){
        return mapper.findPreorderByPatientId(data);
    }

    public Patient findPatientByPhone(Patient data) {
        return mapper.findPatientByPhone(data);
    }
}
