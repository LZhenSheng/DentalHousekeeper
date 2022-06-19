package com.example.demo.service;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.example.demo.domin.*;
import com.example.demo.mapper.DoctorMapper;
import com.example.demo.mapper.HospitalMapper;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.util.List;

@Service
@Slf4j
@PropertySource("classpath:application.yml")
public class HospitalService {

    /**
     * 阿里云ak
     */
    @Value("${aliyun.ak}")
    private String ak;

    /**
     * 阿里云sk
     */
    @Value("${aliyun.sk}")
    private String sk;

    /**
     * 阿里云短信地域id
     */
    @Value("${aliyun.sms.region}")
    private String regionId;

    /**
     * 用户数据仓库
     */
    @Autowired
    private HospitalMapper mapper;

    public void create(Hospital data) {
        mapper.create(data);
    }

    /**
     * 发送短信验证码
     * @param to
     * @param code
     */
    public void requestSMSCode(String to,String code,Hospital hospital) {
        //创建配置
        DefaultProfile profile = DefaultProfile.getProfile(regionId, ak, sk);

        //创建请求客户端
        IAcsClient client = new DefaultAcsClient(profile);

        //创建通用请求
        CommonRequest request = new CommonRequest();

        //以下是固定写法
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", regionId);

        //手机号
        request.putQueryParameter("PhoneNumbers", to);

        //设置短信模板
        //在阿里云后台查看
        request.putQueryParameter("TemplateCode", "SMS_156345375");

        //短信签名
        //大家要替换成自己的
        request.putQueryParameter("SignName", "爱学啊");

        //设置验证码
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("code", code);
        request.putQueryParameter("TemplateParam", jsonObject.toString());
        mapper.create(hospital);
        try {
            //发送验证码
            CommonResponse response = client.getCommonResponse(request);

            //这里就不处理错误了
            //真实项目中，可能会把状态保存到数据库
            log.info("requestSMSCode success {}", to);
        } catch (Exception e) {
            log.info("requestSMSCode failed {}", e);
        }
    }

    public int updateHospital(Hospital hospital){
        return mapper.updateHospital(hospital);
    }

    public List<Hospital> loginHospital(Hospital data) {
        return mapper.loginHospital(data);
    }

    public int addDoctor(Doctor data) {
        return mapper.addDoctor(data);
    }

    public List<Doctor> findDoctorsByHospitalId(Hospital data) {
        return mapper.findDoctorsByHospitalId(data);
    }

    public Doctor findDoctorByDoctorId(String data) {
        return mapper.findDoctorByDoctorId(data);
    }

    public int updateDoctorMessage(Doctor data) {
        return mapper.updateDoctorMessage(data);
    }

    public int deleteDoctorMessage(Doctor data) {
        return mapper.deleteDoctorMessage(data);
    }

    public int createPreOrderByHospital(PreOrder data) {
        return mapper.createPreOrderByHospital(data);
    }

    public List<PreOrder> getPreOrderByDoctorId(Doctor data) {
        return mapper.getPreOrderByDoctorId(data);
    }

    public int deletePreOrderByPreOrderId(PreOrder data) {
        return mapper.deletePreOrderByPreOrderId(data);
    }

    public List<Hospital> findAllHospital() {
        return mapper.findAllHospital();
    }

    public Hospital findHospitalByHospitalId(Hospital data) {
        return mapper.findHospitalByHospitalId(data);
    }

    public List<Hospital> loginDoctor(Doctor data) {
        return mapper.loginDoctor(data);
    }

    public List<AppointMent> findAppointMentByDoctorId(Doctor data) {
        return mapper.findAppointMentByDoctorId(data);
    }

    public Patient findPatientsByPatientId(String data) {
        return mapper.findPatientsByPatientId(data);
    }

    public int createDICOMImage(DICOMImage data) {
        return mapper.createDICOMImage(data);
    }

    public List<DICOMImage> findDicomByPatientId(DICOMImage data) {
        return mapper.findDicomByPatientId(data);
    }

    public DICOMImage findDicomByDicomId(DICOMImage data) {
        return mapper.findDicomByDicomId(data);
    }

    public int createERecord(EReocrd data) {
        return mapper.createERecord(data);
    }

    public void updateAppointMentStatus(String appointmentId) {
        mapper.updateAppointMentStatus(appointmentId);
    }

    public List<EReocrd> findERecordByPatientId(Patient data) {
        return mapper.findERecordByPatientId(data);
    }

    public EReocrd findERecordById(EReocrd data) {
        return mapper.findERecordById(data);
    }

    public List<EReocrd> findERecordByDoctorId(Doctor data) {
        return mapper.findERecordByDoctorId(data);
    }

    public List<DICOMImage> findDicomsByDoctorId(Doctor data) {
        return mapper.findDicomsByDoctorId(data);
    }

    public List<PreOrder> findSchedulingsByHospitalId(Hospital data) {
        return mapper.findSchedulingsByHospitalId(data);
    }

    public String finddoctorIdByName(String doctorId) {
        return mapper.finddoctorIdByName(doctorId).get(0);
    }

    public int addDepartment(Department data) {
        return mapper.addDepartment(data);
    }

    public List<Department> findDepartmentsByHospitalId(Hospital data) {
        return mapper.findDepartmentsByHospitalId(data);
    }

    public Department findDepartmentByDepartmentId(String id) {
        return mapper.findDepartmentByDepartmentId(id);
    }

    public int updateDepartmentByDepartmentId(Department data) {
        return mapper.updateDepartmentByDepartmentId(data);
    }

    public int deleteDepartmentByDepartmentId(Department data) {
        return mapper.deleteDepartmentByDepartmentId(data);
    }

    public List<Payment> findPaymentsByHospitalId(Hospital data) {
        return mapper.findPaymentsByHospitalId(data);
    }

    public int updateHospitalMessage(Hospital data) {
        return mapper.updateHospitalMessage(data);
    }
}
