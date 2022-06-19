package com.example.demo.controller;

import com.example.demo.domin.*;
import com.example.demo.service.DoctorService;
import com.example.demo.service.HospitalService;
import com.example.demo.util.RandomUtil;
import com.example.demo.util.ResponseUtil;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.javassist.bytecode.DeprecatedAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ClassUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 用户控制器
 */
@Api(tags={"医院接口"})
@RestController
@Slf4j
public class HospitalController {

    /**
     * 用户服务
     * Autowired：自动注入
     */
    @Autowired
    private HospitalService service;

    /**
     * 创建用户（注册）
     *
     * @param data
     * @return
     */
    /**
     * @api {get} /user/:id Request User information
     * @apiName GetUser
     * @apiGroup User
     *
     * @apiParam {Number} id Users unique ID.
     *
     * @apiSuccess {String} firstname Firstname of the User.
     * @apiSuccess {String} lastname  Lastname of the User.
     */
    @PostMapping(path = "/sendCode")
    public
    Object create(Hospital data, BindingResult bindingResult) {
        //生成验证码
        int code = RandomUtil.int4();
        String codeString = String.valueOf(code);

        log.info(codeString);
        data.setCode(codeString);
        //发送验证码
        service.requestSMSCode(data.getPhone(),codeString,data);

        //返回验证码
        //这里只是方便测试
        //真实项目中不要返回
        Code codeModel = new Code();
        codeModel.setCode(code);

        return ResponseUtil.wrap(codeModel);
    }

    @PostMapping(path = "/updateHospital")
    public
    Object updateHospital(Hospital data, BindingResult bindingResult) {
        return ResponseUtil.wrap(service.updateHospital(data));
    }

    @PostMapping(path = "/loginHospital")
    public
    Object loginHospital(Hospital data, BindingResult bindingResult) {
        List<Hospital> result=service.loginHospital(data);
        if(result.size()==1) {
            return result;
        }else{
            return null;
        }
    }
    @PostMapping(path = "/uploadDoctorPicture")
    public
    Object uploadDoctorPicture(Hospital data, BindingResult bindingResult) throws Exception {
        System.out.println("eeee\n\n\n");
        String path= ClassUtils.getDefaultClassLoader().getResource("static").getPath();
        String fileName= UUID.randomUUID()+".jpg";
        String uri="image"+File.separator+fileName;
        log.info("图片访问URI"+uri);
        String savePath=path+File.separator+uri;
        log.info("图片保存地址:"+savePath);
        File saveFile=new File(savePath);
        if(!saveFile.exists()){
            saveFile.mkdirs();
        }
        buff2Image(data.getId().getBytes(),savePath);
        return uri;
    }

    static void buff2Image(byte[] b,String tagSrc) throws Exception
    {
        FileOutputStream fout = new FileOutputStream(tagSrc);
        fout.write(b);
        fout.close();
    }

    @PostMapping(path = "/addDoctor")
    public
    Object addDoctor(Doctor data, BindingResult bindingResult) throws Exception {
        System.out.println("eeee\n\n\n");
        System.out.println(data.toString());
        int result= service.addDoctor(data);
        Gson gson=new Gson();
        Result result1=new Result();
        result1.setResult(result);
        return gson.toJson(result1);
    }

    @PostMapping(path = "/updateHospitalMessage")
    public
    Object updateHospitalMessage(Hospital data, BindingResult bindingResult) throws Exception {
        System.out.println(data.toString());
        int result= service.updateHospitalMessage(data);
        Gson gson=new Gson();
        Result result1=new Result();
        result1.setResult(result);
        return gson.toJson(result1);
    }

    @PostMapping(path = "/addDepartment")
    public
    Object addDepartment(Department data, BindingResult bindingResult) throws Exception {
        System.out.println("eeee\n\n\n");
        System.out.println(data.toString());
        int result= service.addDepartment(data);
        Gson gson=new Gson();
        Result result1=new Result();
        result1.setResult(result);
        return gson.toJson(result1);
    }

    @PostMapping(path="/findDoctorsByHospitalId")
    public List<Doctor> findDoctorsByHospitalId(Hospital data,BindingResult bindingResult){
        return service.findDoctorsByHospitalId(data);
    }

    @PostMapping(path="/findPaymentsByHospitalId")
    public List<Payment> findPaymentsByHospitalId(Hospital data,BindingResult bindingResult){
        List<Payment> result=service.findPaymentsByHospitalId(data);
        for(int i=0;i<result.size();i++){
            result.get(i).setPatient(service.findPatientsByPatientId(result.get(i).getPatient()).getName());
        }
        return result;
    }

    @PostMapping(path="/findDepartmentsByHospitalId")
    public List<Department> findDepartmentsByHospitalId(Hospital data,BindingResult bindingResult){
        return service.findDepartmentsByHospitalId(data);
    }

    @PostMapping(path="/findDepartmentssByHospitalId")
    public @ResponseBody List<Department> findDepartmentssByHospitalId(@RequestBody Hospital data,BindingResult bindingResult){
        return service.findDepartmentsByHospitalId(data);
    }

    @PostMapping(path="/findSchedulingsByHospitalId")
    public List<PreOrder> findSchedulingsByHospitalId(Hospital data,BindingResult bindingResult){
        List<PreOrder> result=service.findSchedulingsByHospitalId(data);
        for(int i=0;i<result.size();i++){
            result.get(i).setDoctorId(service.findDoctorByDoctorId(result.get(i).getDoctorId()).getName());
        }
        return result;
    }

    @PostMapping(path="/findDoctorByDoctorId")
    public Doctor findDoctorByDoctorId(Doctor data,BindingResult bindingResult){
        System.out.println("findDoctorByDoctorId"+data.toString());
        return service.findDoctorByDoctorId(data.getId());
    }

    @PostMapping(path="/findDepartmentByDepartmentId")
    public Department findDepartmentByDepartmentId(Department data, BindingResult bindingResult){
        System.out.println("findDepartmentByDepartmentId"+data.toString());
        return service.findDepartmentByDepartmentId(data.getId());
    }

    @PostMapping(path="/updateDoctorMessageByDoctorId")
    public Object updateDoctorMessage(Doctor data,BindingResult bindingResult){
        System.out.println("updateDoctorMessage"+data.toString());
        int res=service.updateDoctorMessage(data);
        Result result=new Result();
        result.setResult(res);
        Gson gson=new Gson();
        return gson.toJson(result);
    }

    @PostMapping(path="/updateDepartmentByDepartmentId")
    public Object updateDepartmentByDepartmentId(Department data,BindingResult bindingResult){
        System.out.println("updateDepartmentByDepartmentId"+data.toString());
        int res=service.updateDepartmentByDepartmentId(data);
        Result result=new Result();
        result.setResult(res);
        Gson gson=new Gson();
        return gson.toJson(result);
    }

    @PostMapping(path="/deleteDoctorMessage")
    public Object deleteDoctorMessage(Doctor data,BindingResult bindingResult){
        System.out.println("deleteDoctorMessage"+data.toString());
        Result result=new Result();
        result.setResult(service.deleteDoctorMessage(data));
        Gson gson=new Gson();
        return gson.toJson(result);
    }

    @PostMapping(path="/deleteDepartmentByDepartmentId")
    public Object deleteDepartmentByDepartmentId(Department data,BindingResult bindingResult){
        System.out.println("deleteDepartmentByDepartmentId"+data.toString());
        Result result=new Result();
        result.setResult(service.deleteDepartmentByDepartmentId(data));
        Gson gson=new Gson();
        return gson.toJson(result);
    }


    @PostMapping(path = "/createPreOrderByHospital")
    public Object create( PreOrder data, BindingResult bindingResult) {
        log.error("createPreOrderByHospital:" + data.toString());
        Gson gson=new Gson();
        Result result=new Result();
        data.setDoctorId(service.finddoctorIdByName(data.getDoctorId()));
        result.setResult(service.createPreOrderByHospital(data));
        return gson.toJson(result);
    }

    @PostMapping(path = "/getPreOrderByDoctorId")
    public List<PreOrder> getPreOrderByDoctorId(Doctor data, BindingResult bindingResult) {
        log.error("createPreOrderByHospital:" + data.toString());
        return service.getPreOrderByDoctorId(data);
    }

    @PostMapping(path = "/deletePreOrderByPreOrderId")
    public Object deletePreOrderByPreOrderId(PreOrder data, BindingResult bindingResult) {
        log.error("deletePreOrderByPreOrderId:" + data.toString());
        Result result=new Result();
        result.setResult(service.deletePreOrderByPreOrderId(data));
        Gson gson=new Gson();
        return gson.toJson(result);
    }

    @RequestMapping(path="/findAllHospital")
    public  List<Hospital> findAllHospital(){
        return service.findAllHospital();
    }

    @PostMapping(path="/findDoctorssByHospitalId")
    public @ResponseBody List<Doctor> findDoctorssByHospitalId(@RequestBody Hospital data,BindingResult bindingResult){
        return service.findDoctorsByHospitalId(data);
    }

    @PostMapping(path="/findDoctorssByDoctorId")
    public @ResponseBody Doctor findDoctorssByDoctorId(@RequestBody Doctor data,BindingResult bindingResult){
        System.out.println("findDoctorByDoctorId"+data.toString());
        return service.findDoctorByDoctorId(data.getId());
    }

    @PostMapping(path = "/getPreOrdersByDoctorId")
    public @ResponseBody List<PreOrder> getPreOrdersByDoctorId(@RequestBody Doctor data, BindingResult bindingResult) {
        log.error("createPreOrderByHospital:" + data.toString());
        List<PreOrder> preOrders=service.getPreOrderByDoctorId(data);
        List<PreOrder> results=new ArrayList<>();
        Date date=new Date();
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        String today=dateFormat.format(date);
        System.out.println(today);
        System.out.println(data);
        for(int i=0;i<preOrders.size();i++){
            if(preOrders.get(i).getRest()>0&&preOrders.get(i).getDate().compareTo(today)>0){
                results.add(preOrders.get(i));
            }
        }
        return results;
    }

    @PostMapping(path = "/findHospitalByHospitalId")
    public @ResponseBody Hospital findHospitalByHospitalId(@RequestBody Hospital data, BindingResult bindingResult) {
        log.error("createPreOrderByHospital:" + data.toString());
        return service.findHospitalByHospitalId(data);
    }

    @PostMapping(path = "/findHospitalsByHospitalId")
    public  Hospital findHospitalsByHospitalId( Hospital data, BindingResult bindingResult) {
        log.error("createPreOrderByHospital:" + data.toString());
        return service.findHospitalByHospitalId(data);
    }
    @PostMapping(path = "/loginDoctors")
    public
    Object loginDoctor(Doctor data, BindingResult bindingResult) {
        log.error("loginDoctors:" + data.toString());
        List<Hospital> result=service.loginDoctor(data);
        if(result.size()==1) {
            return result;
        }else{
            return null;
        }
    }

    @PostMapping(path = "/findPatientsByPatientId")
    public
    Patient findPatientsByPatientId(Patient data, BindingResult bindingResult) {
        log.error("findPatientsByPatientId:" + data.toString()+"\n\n");
        return service.findPatientsByPatientId(data.getId());
    }

    @PostMapping(path = "/createDICOMImage")
    public
    Object createDICOMImage(DICOMImage data, BindingResult bindingResult) {
        log.error("createDICOMImage:" + data.toString()+"\n\n");
        Gson gson=new Gson();
        Result result=new Result();
        result.setResult(service.createDICOMImage(data));
        return gson.toJson(result);
    }

    @PostMapping(path = "/findDicomByPatientId")
    public
    List<DICOMImage> findDicomByPatientId(DICOMImage data, BindingResult bindingResult) {
        log.error("findDicomByPatientId:" + data.toString()+"\n\n");
        return service.findDicomByPatientId(data);
    }

    @PostMapping(path = "/findDicomByDicomId")
    public
    DICOMImage findDicomByDicomId(DICOMImage data, BindingResult bindingResult) {
        log.error("findDicomByPatientId:" + data.toString()+"\n\n");
        return service.findDicomByDicomId(data);
    }

    @PostMapping(path = "/createERecord")
    public
    Object createERecord(EReocrd data, BindingResult bindingResult) {
        log.error("createDICOMImage:" + data.toString()+"\n\n");
        Gson gson=new Gson();
        Result result=new Result();
        result.setResult(service.createERecord(data));
        service.updateAppointMentStatus(data.getAppointmentId());
        return gson.toJson(result);
    }

    @PostMapping(path = "/findDicomsByPatientId")
    public @ResponseBody List<DICOMImage> findDicomsByPatientId(@RequestBody DICOMImage data, BindingResult bindingResult) {
        log.error("findDicomByPatientId:" + data.toString()+"\n\n");
        return service.findDicomByPatientId(data);
    }

    @PostMapping(path = "/findDicoByDicomId")
    public @ResponseBody
    DICOMImage findDicoByDicomId(@RequestBody DICOMImage data, BindingResult bindingResult) {
        log.error("findDicoByDicomId:" + data.toString()+"\n\n");
        return service.findDicomByDicomId(data);
    }

    @PostMapping(path = "/findERecordByPatientId")
    public @ResponseBody
    List<EReocrd> findERecordByPatientId(@RequestBody Patient data, BindingResult bindingResult) {
        log.error("findDicoByDicomId:" + data.toString()+"\n\n");
        return service.findERecordByPatientId(data);
    }

    @PostMapping(path = "/findERecordById")
    public @ResponseBody
    EReocrd findERecordById(@RequestBody EReocrd data, BindingResult bindingResult) {
        log.error("findDicoByDicomId:" + data.toString()+"\n\n");
        return service.findERecordById(data);
    }

    @PostMapping(path = "/findERecordsById")
    public
    EReocrd findERecordsById(EReocrd data, BindingResult bindingResult) {
        log.error("findDicoByDicomId:" + data.toString()+"\n\n");
        return service.findERecordById(data);
    }

    @PostMapping(path = "/findERecordByDoctorId")
    public @ResponseBody
    List<EReocrd> findERecordByDoctorId(@RequestBody Doctor data, BindingResult bindingResult) {
        log.error("findERecordByDoctorId:" + data.toString()+"\n\n");
        return service.findERecordByDoctorId(data);
    }

    @PostMapping(path = "/findERecordsByDoctorId")
    public @ResponseBody
    List<EReocrd> findERecordsByDoctorId(@RequestBody Doctor data, BindingResult bindingResult) {
        log.error("findERecordByDoctorId:" + data.toString()+"\n\n");
        return service.findERecordByDoctorId(data);
    }

    @PostMapping(path = "/findERecordssByDoctorId")
    public
    List<EReocrd> findERecordssByDoctorId(Doctor data, BindingResult bindingResult) {
        log.error("findERecordByDoctorId:" + data.toString()+"\n\n");
        return service.findERecordByDoctorId(data);
    }

    @PostMapping(path = "/findDicomsByDoctorId")
    public @ResponseBody
    List<DICOMImage> findDicomsByDoctorId(@RequestBody Doctor data, BindingResult bindingResult) {
        log.error("findERecordByDoctorId:" + data.toString()+"\n\n");
        return service.findDicomsByDoctorId(data);
    }

    @PostMapping(path = "/findDicomssByDoctorId")
    public
    List<DICOMImage> findDicomssByDoctorId( Doctor data, BindingResult bindingResult) {
        log.error("findERecordByDoctorId:" + data.toString()+"\n\n");
        return service.findDicomsByDoctorId(data);
    }

    @PostMapping(path="/findPreorderByAppointmentId")
    public PreOrder findPreorderByAppointmentId(AppointMent data){
        return doctorService.findPreorderByAppointmentId(data.getId());
    }

    @PostMapping(path="/findERecordByAppointmentId")
    public EReocrd findERecordByAppointmentId(AppointMent data){
        return doctorService.findERecordByAppointmentId(data.getId());
    }


    /**
     * 用户服务
     * Autowired：自动注入
     */
    @Autowired
    private DoctorService doctorService;
}