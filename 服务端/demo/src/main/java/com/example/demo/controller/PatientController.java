package com.example.demo.controller;

import com.example.demo.domin.Doctor;
import com.example.demo.domin.Patient;
import com.example.demo.domin.PreOrder;
import com.example.demo.exception.CommonException;
import com.example.demo.service.PatientService;
import com.example.demo.util.Constant;
import com.example.demo.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 用户控制器
 */
@Slf4j
@RestController
public class PatientController {

    /**
     * 用户服务
     * Autowired：自动注入
     */
    @Autowired
    private PatientService service;

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
    @PostMapping(path = "/registerPatient")
    public @ResponseBody
    Object create(@Valid @RequestBody Patient data, BindingResult bindingResult) {
        log.error("PatientController create:" + data.toString());


        //框架提供的参数校验
        List<String> detail = new ArrayList<>();
        if (bindingResult.hasErrors()) {
            //获取所有错误信息
            List<ObjectError> list = bindingResult.getAllErrors();

            //把消息添加到列表
            for (ObjectError objectError : list) {
                detail.add(objectError.getDefaultMessage());
            }

            //抛出自定义异常
            //并传入详细错误信息
            throw new CommonException(Constant.ERROR_ARGUMENT, Constant.ERROR_ARGUMENT_MESSAGE, detail);
        }

        //判断用户是否已经存在
        if (service.findByPhone(data.getPhone()) != null ) {
            throw new CommonException(Constant.ERROR_USER_EXIST, Constant.ERROR_USER_EXIST_MESSAGE);
        }

        data.encryptData();

        //保存用户
        service.create(data);
        //返回用户对象
        return ResponseUtil.wrap(data.getId());
    }

    /**
     * 用户列表
     *
     * @return 返回json
     */
    @RequestMapping("/users")
    public Object users() {
        //查询所有用户
        List<Patient> datum = service.findAll();

        //返回数据
        return  ResponseUtil.wrap(datum);
    }

    /**
     * 用户详情
     *
     * @return
     */
    @RequestMapping("/findPatientById")
    public Object userDetail(@RequestBody Patient patient) {
        //查询数据
        Patient data = service.find(patient.getId());

        log.info("create user {}",data);

        //返回数据
        return data;
    }

    /**
     * 创建用户（注册）
     */
    @PostMapping(path = "/updatePatientMessage")
    public @ResponseBody
    Object updateMessage(@RequestBody Patient data, BindingResult bindingResult) {
        log.error("PatientController create:" + data.toString());


        //框架提供的参数校验
        List<String> detail = new ArrayList<>();
        if (bindingResult.hasErrors()) {
            //获取所有错误信息
            List<ObjectError> list = bindingResult.getAllErrors();

            //把消息添加到列表
            for (ObjectError objectError : list) {
                detail.add(objectError.getDefaultMessage());
            }

            //抛出自定义异常
            //并传入详细错误信息
            throw new CommonException(Constant.ERROR_ARGUMENT, Constant.ERROR_ARGUMENT_MESSAGE, detail);
        }
        //保存用户
        boolean result=service.updatePatientMessage(data.getId(),data.getName(),data.getGender(),data.getProvince(),data.getCity(),data.getArea(),data.getCard(),data.getAge(),data.getBirthday(),data.getAvatar());
        //返回用户对象
        return ResponseUtil.wrap(result);
    }


    /**
     * 创建用户（注册）
     */
    @PostMapping(path = "/findPreorderByPatientId")
    public @ResponseBody
    Object findPreorderByPatientId(@RequestBody Patient data, BindingResult bindingResult) {
        log.error("PatientController create:" + data.toString());

        List<Integer> result=new ArrayList<>();
        List<PreOrder> tmp=service.findPreorderByPatientId(data.getId());
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String today = sdf.format(d);
        System.out.println("格式化后的日期：" + today);
        Calendar c = Calendar.getInstance();
        Date date=null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(today);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day=c.get(Calendar.DATE);
        c.set(Calendar.DATE,day+1);
        String tomorrow=new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        System.out.println("格式化后的日期：" + today+"---"+tomorrow);
        int todayNumber=0,tomorrowNumber=0;
        result.add(0,tmp.size());
        for(int i=0;i<tmp.size();i++){
            if(tmp.get(i).getDate().equals(today))
                todayNumber++;
            else if(tmp.get(i).getDate().equals(tomorrow))
                tomorrowNumber++;
        }
        result.add(1,todayNumber);
        result.add(2,tomorrowNumber);
        return result;
    }

    /**
     * 创建用户（注册）
     */
    @PostMapping(path = "/findPatientByPhone")
    public @ResponseBody
    Object findPatientByPhone(@RequestBody Patient data, BindingResult bindingResult) {
        log.error("PatientController findPatientByPhone:" + data.toString());
        return service.findPatientByPhone(data);
    }
}