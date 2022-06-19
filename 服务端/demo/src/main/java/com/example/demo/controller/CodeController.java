package com.example.demo.controller;

import com.example.demo.domin.Code;
import com.example.demo.service.PatientService;
import com.example.demo.service.SMSService;
import com.example.demo.util.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;

@Api(tags={"验证码"})
@RestController
@Slf4j
public class CodeController {

    @Autowired
    private PatientService userService;

    @Autowired
    private SMSService smsService;

    @ApiOperation(
            value = "请求验证码",
            notes = "请求验证码",
            produces = "application/json",
            consumes = "application/json",
            response = String.class
    )
    @ApiResponses({
            @ApiResponse(code = 100,message = "请求参数错误"),
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 401, message = "登录信息过期"),
            @ApiResponse(code = 403, message = "没有权限"),
            @ApiResponse(code = 404, message = "找不到资源"),
            @ApiResponse(code = 500, message = "服务器错误")
    })
    @PostMapping("/request_sms_code")
    public Object requestSMSCode(@RequestBody String data) {
        int code = RandomUtil.int4();
        String codeString = String.valueOf(code);
        log.info(codeString);

        smsService.requestSMSCode(data,codeString);

        Code codeModel = new Code();
        codeModel.setCode(code);
        return ResponseUtil.wrap(codeModel);
    }
}
