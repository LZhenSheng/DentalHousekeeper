package com.example.demo.mapper;

import com.example.demo.domin.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户mapper
 */
@Repository
public interface DoctorMapper {
    /**
     * 保存用户
     * @param data
     * @return
     */
    int create(Doctor data);

    /**
     * 查询所有用户
     * @return
     */
    List<Doctor> findAll();

    /**
     * 查询用户
     *
     * @param id
     * @return
     */
    Doctor find(@Param("id") String id);

    /**
     * 根据手机号查找
     * @param data
     * @return
     */
    Doctor findByPhone(String data);

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
    int update(Doctor data);

    int updateCode(String id, String data);

    /**
     * 保存用户
     * @param data
     * @return
     */
    int createProOrder(PreOrder data);

    int updateMessage(String id, String name,int gender,String province,String city,String area,Integer age,String birthday,String avatar,String email,String description);
    int updateMessage1(String id, String name,int gender,String province,String city,String area,Integer age,String birthday,String email,String description);


    /**
     * 查询所有用户
     * @return
     */
    List<String> findHospital();

    /**
     * 查询所有用户
     * @return
     */
    List<String> findDoctor(String data);

    PreOrder findPreorder(String id,String date);

    List<String> findDoctorId(String data);

    int createAppointment(AppointMent data);

    String findPreorderId(String name,String date);

    List<AppointMent> findAppointmentByPatientId(String data);

    Doctor findDoctorById(String data);

    PreOrder findPreorderById(String data);

    List<PreOrder> findPreorderByDoctorId(String data);

    List<AppointMent> findAppointmentByDoctorId(String data);

    int updatePreorder(AppointMent appointMent);

    Doctor findDoctorByPhone(Doctor data);

    int updateAppointMent(String data);

    void deleteAppointMent(String id);

    AppointMent findAppointmentById(String data);

    int createGroupConsultation(GroupConsultation id);

    List<GroupConsultation> findGroupConsultations(String start);

    void savePayment(Payment payment);

    AppointMent findAppointmentByAppointmentId(String id);

    PreOrder findPreorderByAppointmentId(String id);

    EReocrd findERecordByAppointmentId(String id);

    List<AppointMent> findAppointmentByDoctorIdInTodayWhereName(Doctor data);
}