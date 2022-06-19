package com.example.demo.mapper;

import com.example.demo.domin.Patient;
import com.example.demo.domin.PreOrder;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户mapper
 */
@Repository
public interface PatientMapper {
    /**
     * 保存用户
     * @param data
     * @return
     */
    int create(Patient data);

    /**
     * 查询所有用户
     * @return
     */
    List<Patient> findAll();

    /**
     * 查询用户
     *
     * @param id
     * @return
     */
    Patient find(@Param("id") String id);

    /**
     * 根据手机号查找
     * @param data
     * @return
     */
    Patient findByPhone(String data);

    /**
     * 更新用户session
     *
     * @param id
     * @param data
     * @return
     */
    int updateSessionDigest(@Param("id") String id, @Param("data") String data);

    /***
    * 更新用户 
    */
    int update(Patient data);

    int updateCode(String id, String data);

    int updatePatientMessage(String id, String name,int gender,String province,String city,String area,String card,int age,String birthday,String avatar);
    int updatePatientMessage1(String id, String name,int gender,String province,String city,String area,String card,int age,String birthday);

    List<PreOrder> findPreorderByPatientId(String data);

    Patient findPatientByPhone(Patient data);
}