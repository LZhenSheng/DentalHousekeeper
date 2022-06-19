package com.example.demo.service;

import com.example.demo.domin.*;
import com.example.demo.mapper.DoctorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.util.Constant.RESULT_OK;

@Service
public class DoctorService {
    /**
     * 用户数据仓库
     */
    @Autowired
    private DoctorMapper mapper;

    public Doctor findByPhone(String phone) {
        return mapper.findByPhone(phone);
    }

    public void create(Doctor data) {
        mapper.create(data);
    }

    public List<Doctor> findAll() {
        return mapper.findAll();
    }

    public Doctor find(String id) {
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

    public void createProOrder(PreOrder data) {
        mapper.createProOrder(data);
    }

    public boolean updateMessage(String id, String name,int gender,String province,String city,String area,Integer age,String birthday,String avatar,String emial,String description) {
        if(avatar==null){
            return mapper.updateMessage1(id, name,gender,province,city,area,age,birthday,emial,description) == RESULT_OK;
        }else{
            return mapper.updateMessage(id, name,gender,province,city,area,age,birthday,avatar,emial,description) == RESULT_OK;
        }
    }

    public List<String> findHospital() {
        return mapper.findHospital();
    }

    public List<String> findDoctor(String data){
        return mapper.findDoctor(data);
    }

    public PreOrder findPreorder(String id,String date){
        return mapper.findPreorder(id,date);
    }

    public String findDoctorId(String data){
        return mapper.findDoctorId(data).get(0);
    }

    public int createAppointment(AppointMent data){
        return mapper.createAppointment(data);
    }

    public String findPreorderId(String name,String date){
        return mapper.findPreorderId(name,date);
    }

    public List<AppointMent> findAppointmentByPatientId(String data){
        return mapper.findAppointmentByPatientId(data);
    }

    public Doctor findDoctorById(String data){
        return mapper.findDoctorById(data);
    }

    public PreOrder findPreorderById(String data){
        return mapper.findPreorderById(data);
    }

    public List<PreOrder> findPreorderByDoctorId(String data){
        return mapper.findPreorderByDoctorId(data);
    }

    public List<AppointMent> findAppointmentByDoctorId(String data){
        return mapper.findAppointmentByDoctorId(data);
    }

    public int updatePreorder(AppointMent appointMent) {
        return mapper.updatePreorder(appointMent);
    }

    public Doctor findDoctorByPhone(Doctor data) {
        return mapper.findDoctorByPhone(data);
    }

    public int updateAppointMent(String data) {
        return mapper.updateAppointMent(data);
    }

    public void deleteAppointMent(String id) {
        mapper.deleteAppointMent(id);
    }

    public AppointMent findAppointmentById(String id){
        return mapper.findAppointmentById(id);
    }

    public int createGroupConsultation(GroupConsultation id) {
        return mapper.createGroupConsultation(id);
    }

    public List<GroupConsultation> findGroupConsultations(String start) {
        return mapper.findGroupConsultations(start);
    }

    public void savePayment(Payment payment) {
        mapper.savePayment(payment);
    }

    public AppointMent findAppointmentByAppointmentId(String id) {
        return mapper.findAppointmentByAppointmentId(id);
    }

    public PreOrder findPreorderByAppointmentId(String id) {
        return mapper.findPreorderByAppointmentId(id);
    }

    public EReocrd findERecordByAppointmentId(String id) {
        return mapper.findERecordByAppointmentId(id);
    }

    public List<AppointMent> findAppointmentByDoctorIdInTodayWhereName(Doctor data) {
        return mapper.findAppointmentByDoctorIdInTodayWhereName(data);
    }
}
