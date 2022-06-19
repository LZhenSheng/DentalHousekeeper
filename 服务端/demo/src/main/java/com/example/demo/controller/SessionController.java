package com.example.demo.controller;

import com.example.demo.annotation.Authorization;
import com.example.demo.annotation.CurrentUser;
import com.example.demo.domin.Doctor;
import com.example.demo.domin.Patient;
import com.example.demo.domin.Session;
import com.example.demo.exception.ArgumentException;
import com.example.demo.exception.CommonException;
import com.example.demo.service.DoctorService;
import com.example.demo.service.PatientService;
import com.example.demo.service.SessionService;
import com.example.demo.util.BCryptUtil;
import com.example.demo.util.Constant;
import com.example.demo.util.IDUtil;
import com.example.demo.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/***
* session控制器
* @author 胜利镇
* @time 2022/1/3
* @dec
*/
@Slf4j
@RestController
public class SessionController {

    /**
     * 用户服务
     */
    @Autowired
    private PatientService patientService;

    /**
     * 用户服务
     */
    @Autowired
    private DoctorService doctorService;

    /**
     * 用户session服务
     */
    @Autowired
    private SessionService serssionService;

    /**
     * 登录
     *
     * @param data
     * @return
     */
    @PostMapping("/login_patient")
    public Object create(@RequestBody Patient data) {
        //查找用户
        Patient user = null;

        String password = null;
        String encodePassword = null;

        if (StringUtils.isNotBlank(data.getPhone()) &&
                StringUtils.isNotBlank(data.getPassword())) {
            //手机号，密码登录
            user = patientService.findByPhone(data.getPhone());

            //检查用户
            checkUser(user);

            //获取密码
            password = data.getPassword();
            encodePassword = user.getPassword();
        } else {
            //参数错误
            throw new ArgumentException();
        }

        //判断密码是否正确
        if (!BCryptUtil.matchEncode(password, encodePassword)) {
            //密码不匹配
            throw new CommonException(Constant.ERROR_USERNAME_OR_PASSWORD,
                    Constant.ERROR_USERNAME_OR_PASSWORD_MESSAGE);
        }

        //认证成功

        //随机字符串
        String sessionString = IDUtil.getUUID();

        //加密
        String sessionDigest = BCryptUtil.encrypt(sessionString);

        //保存session
        user.setSessionDigest(sessionDigest);

        //保存到数据库
        if (!serssionService.saveSession(user)) {
            //保存登录信息失败
            throw new CommonException(Constant.ERROR_SAVE_DATA, Constant.ERROR_SAVE_DATA_MESSAGE);
        }

        //向用户返回userId,session
        //返回userId的目的是，session加密不能直接查询
        //需要先找到用户
        Session session = new Session();
        session.setUser(user.getId());
        session.setSession(sessionString);
        return ResponseUtil.wrap(session);
    }

    /**
     * 登录
     *
     * @param data
     * @return
     */
    @PostMapping("/login_doctor")
    public Object create(@RequestBody Doctor data) {
        //查找用户
        Doctor user = null;

        String password = null;
        String encodePassword = null;

        if (StringUtils.isNotBlank(data.getPhone()) &&
                StringUtils.isNotBlank(data.getPassword())) {
            //手机号，密码登录
            user = doctorService.findByPhone(data.getPhone());

            //检查用户
            checkUser(user);

            //获取密码
            password = data.getPassword();
            encodePassword = user.getPassword();
        } else {
            //参数错误
            throw new ArgumentException();
        }

        //判断密码是否正确
        if (!password.equals(encodePassword)) {
            //密码不匹配
            throw new CommonException(Constant.ERROR_USERNAME_OR_PASSWORD,
                    Constant.ERROR_USERNAME_OR_PASSWORD_MESSAGE);
        }

        //认证成功

        //随机字符串
        String sessionString = IDUtil.getUUID();

        //加密
        String sessionDigest = BCryptUtil.encrypt(sessionString);

        //保存session
        user.setSessionDigest(sessionDigest);

        //保存到数据库
        if (!serssionService.saveSession(user)) {
            //保存登录信息失败
            throw new CommonException(Constant.ERROR_SAVE_DATA, Constant.ERROR_SAVE_DATA_MESSAGE);
        }
        log.info("23232"+user.getId());
        //向用户返回userId,session
        //返回userId的目的是，session加密不能直接查询
        //需要先找到用户
        Session session = new Session();
        session.setUser(user.getId());
        session.setSession(sessionString);
        return ResponseUtil.wrap(session);
    }

    /**
     * 检查用户
     *
     * @param data
     */
    private void checkUser(Patient data) {
        if (data == null) {
            throw new CommonException(Constant.ERROR_USER_NOT_EXIST, Constant.ERROR_USER_NOT_EXIST_MESSAGE);
        }
    }

    /**
     * 检查用户
     *
     * @param data
     */
    private void checkUser(Doctor data) {
        if (data == null) {
            throw new CommonException(Constant.ERROR_USER_NOT_EXIST, Constant.ERROR_USER_NOT_EXIST_MESSAGE);
        }
    }

    /**
     * 退出登录
     * @param id
     * @return
     */
    @DeleteMapping(path="/sessions/{id}")
    @Authorization
    public @ResponseBody
    Object destroy(@PathVariable String id, @CurrentUser Patient patient) {
        log.info("destroy {},{}",id,patient.getName());
        //清除登录信息
        if (!serssionService.deleteSession(patient.getId())) {
            //删除失败

            //提示更新数据失败
            throw new CommonException(Constant.ERROR_SAVE_DATA, Constant.ERROR_SAVE_DATA_MESSAGE);
        }
        return ResponseUtil.wrap();
    }


    /**
     * 更新用户
     *
     * @param id
     * @param data
     * @param currentUser
     * @return
     */
    @PatchMapping(path = "/users/{id}")
    @Authorization
    public Object update(@PathVariable String id, @RequestBody Patient data, @CurrentUser Patient currentUser) {
        //设置当前用户id
        //这里是为了避免更新到其他对象
        data.setId(currentUser.getId());

        if (!serssionService.update(data)) {
            //更新失败
            throw new CommonException(Constant.ERROR_SAVE_DATA, Constant.ERROR_SAVE_DATA_MESSAGE);
        }

        return ResponseUtil.wrap(data);
    }
}