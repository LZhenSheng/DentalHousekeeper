package com.example.demo.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.example.demo.domin.*;
import com.example.demo.exception.ArgumentException;
import com.example.demo.exception.CommonException;
import com.example.demo.service.DoctorService;
import com.example.demo.service.PatientService;
import com.example.demo.util.Constant;
import com.example.demo.util.IDUtil;
import com.example.demo.util.ResponseUtil;
import com.google.gson.Gson;
import io.swagger.annotations.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Semaphore;

@Api(tags={"医生接口"})
@RestController
public class DoctorController {
    /**
     * 支付宝应用id
     */
    @Value("${alipay.id}")
    private String alipayAppId;

    /**
     * app私钥
     */
    @Value("${alipay.private}")
    private String alipayAppPrivateKey;

    /**
     * 支付宝公钥
     */
    @Value("${alipay.public}")
    private String alipayPublicKey;

    @Autowired
    private DoctorService service;

    @Autowired
    private PatientService patientService;

    @ApiOperation(
            value = "创建牙医",
            notes = "创建牙医",
            produces = "application/json",
            consumes = "application/json",
            response = Doctor.class
    )
    @ApiResponses({
            @ApiResponse(code = 100,message = "请求参数错误"),
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 401, message = "登录信息过期"),
            @ApiResponse(code = 403, message = "没有权限"),
            @ApiResponse(code = 404, message = "找不到资源"),
            @ApiResponse(code = 500, message = "服务器错误")
    })
    @PostMapping(path = "/registerDoctor")
    public @ResponseBody
    Object create(@Valid @RequestBody Doctor data, BindingResult bindingResult) {
        log.error("PatientController create:" + data.toString());
        List<String> detail = new ArrayList<>();
        if (bindingResult.hasErrors()) {
            List<ObjectError> list = bindingResult.getAllErrors();
            for (ObjectError objectError : list) {
                detail.add(objectError.getDefaultMessage());
            }
            throw new CommonException(Constant.ERROR_ARGUMENT, Constant.ERROR_ARGUMENT_MESSAGE, detail);
        }
        if (service.findByPhone(data.getPhone()) != null ) {
            throw new CommonException(Constant.ERROR_USER_EXIST, Constant.ERROR_USER_EXIST_MESSAGE);
        }
        data.encryptData();
        service.create(data);
        return ResponseUtil.wrap(data.getId());
    }

    @ApiOperation(
            value = "创建排班",
            notes = "创建排班",
            produces = "application/json",
            consumes = "application/json",
            response = PreOrder.class
    )
    @ApiResponses({
            @ApiResponse(code = 100,message = "请求参数错误"),
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 401, message = "登录信息过期"),
            @ApiResponse(code = 403, message = "没有权限"),
            @ApiResponse(code = 404, message = "找不到资源"),
            @ApiResponse(code = 500, message = "服务器错误")
    })
    @PostMapping(path = "/setPreorder")
    public @ResponseBody
    Object create(@Valid @RequestBody PreOrder data, BindingResult bindingResult) {
        log.error("PatientController create:" + data.toString());
        List<String> detail = new ArrayList<>();
        if (bindingResult.hasErrors()) {
            List<ObjectError> list = bindingResult.getAllErrors();
            for (ObjectError objectError : list) {
                detail.add(objectError.getDefaultMessage());
            }
            throw new CommonException(Constant.ERROR_ARGUMENT, Constant.ERROR_ARGUMENT_MESSAGE, detail);
        }
        data.setId(IDUtil.getUUID());
        service.createProOrder(data);
        return ResponseUtil.wrap(data.getId());
    }

    @ApiOperation(
            value = "更新牙医个人信息",
            notes = "更新牙医个人信息",
            produces = "application/json",
            consumes = "application/json",
            response = Doctor.class
    )
    @ApiResponses({
            @ApiResponse(code = 100,message = "请求参数错误"),
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 401, message = "登录信息过期"),
            @ApiResponse(code = 403, message = "没有权限"),
            @ApiResponse(code = 404, message = "找不到资源"),
            @ApiResponse(code = 500, message = "服务器错误")
    })
    @PostMapping(path = "/updateDoctorMessage")
    public @ResponseBody
    Object updateMessage(@ApiParam(name = "data", value = "牙医信息", required = true)@RequestBody Doctor data,@ApiIgnore BindingResult bindingResult) {
        log.error("PatientController create:" + data.toString());
        List<String> detail = new ArrayList<>();
        if (bindingResult.hasErrors()) {
            List<ObjectError> list = bindingResult.getAllErrors();
            for (ObjectError objectError : list) {
                detail.add(objectError.getDefaultMessage());
            }
            throw new CommonException(Constant.ERROR_ARGUMENT, Constant.ERROR_ARGUMENT_MESSAGE, detail);
        }
        boolean result=service.updateMessage(data.getId(),data.getName(),data.getGender(),data.getProvince(),data.getCity(),data.getArea(),data.getAge(),data.getBirthday(),data.getAvatar(),data.getEmail(),data.getDescription());
        return ResponseUtil.wrap(result);
    }

    @PostMapping(path = "/updateDoctorsMessage")
    public
    Object updateDoctorsMessage(Doctor data,BindingResult bindingResult) {
        log.error("PatientController create:" + data.toString());
        List<String> detail = new ArrayList<>();
        if (bindingResult.hasErrors()) {
            List<ObjectError> list = bindingResult.getAllErrors();
            for (ObjectError objectError : list) {
                detail.add(objectError.getDefaultMessage());
            }
            throw new CommonException(Constant.ERROR_ARGUMENT, Constant.ERROR_ARGUMENT_MESSAGE, detail);
        }
        boolean result=service.updateMessage(data.getId(),data.getName(),data.getGender(),data.getProvince(),data.getCity(),data.getArea(),data.getAge(),data.getBirthday(),data.getAvatar(),data.getEmail(),data.getDescription());
        return ResponseUtil.wrap(result);
    }
    @ApiOperation(
            value = "更新牙医个人信息",
            notes = "更新牙医个人信息",
            produces = "application/json",
            consumes = "application/json",
            response = Doctor.class
    )
    @ApiResponses({
            @ApiResponse(code = 100,message = "请求参数错误"),
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 401, message = "登录信息过期"),
            @ApiResponse(code = 403, message = "没有权限"),
            @ApiResponse(code = 404, message = "找不到资源"),
            @ApiResponse(code = 500, message = "服务器错误")
    })
    @RequestMapping("/findHospital")
    public List<String> findHospital() {
        //保存用户
        List<String> result=service.findHospital();
        Gson gson=new Gson();
        String str=gson.toJson(result);
        //返回用户对象
        return result;
    }

    @ApiOperation(
            value = "查找医院的所有医生",
            notes = "查找医院的所有医生",
            produces = "application/json",
            consumes = "application/json",
            response = Doctor.class
    )
    @ApiResponses({
            @ApiResponse(code = 100,message = "请求参数错误"),
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 401, message = "登录信息过期"),
            @ApiResponse(code = 403, message = "没有权限"),
            @ApiResponse(code = 404, message = "找不到资源"),
            @ApiResponse(code = 500, message = "服务器错误")
    })
    @PostMapping("/findDoctor")
    public  @ResponseBody List<String> updateMessage(@RequestBody Doctor data) {
        log.error("PatientController create:" + data.toString());
        //返回用户对象
        return service.findDoctor(data.getHospital());
    }

    @ApiOperation(
            value = "更新牙医个人信息",
            notes = "更新牙医个人信息",
            produces = "application/json",
            consumes = "application/json",
            response = Doctor.class
    )
    @ApiResponses({
            @ApiResponse(code = 100,message = "请求参数错误"),
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 401, message = "登录信息过期"),
            @ApiResponse(code = 403, message = "没有权限"),
            @ApiResponse(code = 404, message = "找不到资源"),
            @ApiResponse(code = 500, message = "服务器错误")
    })
    @PostMapping("/findPreorder")
    public  @ResponseBody PreOrder findPreorder(@RequestBody PreOrder data) {
        log.error("PatientController create:" + data.toString());
        //返回用户对象
        PreOrder preorder=service.findPreorder(service.findDoctorId(data.getDoctorId()),data.getDate());
        log.error(preorder.toString());
        return preorder;
    }

    Semaphore semaphore=new Semaphore(1);

    @ApiOperation(
            value = "更新牙医个人信息",
            notes = "更新牙医个人信息",
            produces = "application/json",
            consumes = "application/json",
            response = Doctor.class
    )
    @ApiResponses({
            @ApiResponse(code = 100,message = "请求参数错误"),
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 401, message = "登录信息过期"),
            @ApiResponse(code = 403, message = "没有权限"),
            @ApiResponse(code = 404, message = "找不到资源"),
            @ApiResponse(code = 500, message = "服务器错误")
    })
    @PostMapping("/createAppointment")
    public  @ResponseBody Pay createAppointment(@RequestBody AppointMent data) throws InterruptedException {
        log.error("PatientController create:" + data.toString());
        int availablePermits=semaphore.availablePermits();
        while(availablePermits<=0){
            Thread.sleep(100);
        }
        semaphore.acquire(1);
        data.setId(IDUtil.getUUID());
        int result=service.updatePreorder(data);
//        System.out.println("1111"+result);
        Pay pay=null;
        if(result!=0){
            pay= getAppPayParam(data);
        }
//            String myResult[]=test(appointMent.getPreorderId());
//            if(myResult.length==2){
//                appointMent.setNo(myResult[1]);
//                appointMent.setUri(myResult[0]);
        result=service.createAppointment(data);
//            }
//        }

        semaphore.release(1);
//        System.out.println("22222"+result);
        //返回用户对象
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                System.out.println("-------设定要指定任务1--------");
                System.out.println(data.getId());
                service.deleteAppointMent(data.getId());
            }
        }, 60000*5);
        if(result==1){
            return pay;
        }else{
            return null;
        }
    }

    Log log = LogFactory.getLog(PayController.class);

    private Pay getAppPayParam(AppointMent data) {
        //实例化客户端
        DefaultAlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",
                alipayAppId,
                alipayAppPrivateKey,
                "json",
                "utf-8",
                alipayPublicKey,
                "RSA2");

        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setSubject("牙医管家-预约");
        model.setOutTradeNo(data.getId());
        System.out.println("data.getID="+data.getId());
        System.out.println(data);
        PreOrder preOrder=service.findPreorderById(data.getPreorderId());
        System.out.println("data.getPreorderId"+data.getPreorderId());
        String totalAmount = String.valueOf(preOrder.getMoney());
        model.setTotalAmount(totalAmount);
        model.setProductCode("QUICK_MSECURITY_PAY");
        request.setBizModel(model);
        request.setNotifyUrl("42.192.116.184:8080/callback");
        try {
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            System.out.println(response);
            System.out.println("------------------------");
            Pay pay = new Pay(10, response.getBody());

            return pay;
        } catch (AlipayApiException e) {
        }
        throw new ArgumentException();
    }


    @ApiOperation(
            value = "更新牙医个人信息",
            notes = "更新牙医个人信息",
            produces = "application/json",
            consumes = "application/json",
            response = Doctor.class
    )
    @ApiResponses({
            @ApiResponse(code = 100,message = "请求参数错误"),
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 401, message = "登录信息过期"),
            @ApiResponse(code = 403, message = "没有权限"),
            @ApiResponse(code = 404, message = "找不到资源"),
            @ApiResponse(code = 500, message = "服务器错误")
    })
    @PostMapping("/findAppointment")
    public  @ResponseBody List<AppointMent> findAppointment(@RequestBody AppointMent data) {
        log.error("PatientController create:" + data.toString());
        //返回用户对象
        return service.findAppointmentByPatientId(data.getDoctorId());
    }

    @ApiOperation(
            value = "更新牙医个人信息",
            notes = "更新牙医个人信息",
            produces = "application/json",
            consumes = "application/json",
            response = Doctor.class
    )
    @ApiResponses({
            @ApiResponse(code = 100,message = "请求参数错误"),
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 401, message = "登录信息过期"),
            @ApiResponse(code = 403, message = "没有权限"),
            @ApiResponse(code = 404, message = "找不到资源"),
            @ApiResponse(code = 500, message = "服务器错误")
    })
    @PostMapping("/findDoctorById")
    public  @ResponseBody Doctor findDoctorById(@RequestBody Doctor data) {
        log.error("PatientController create:" + data.toString());
        //返回用户对象
        return service.findDoctorById(data.getId());
    }
    @ApiOperation(
            value = "更新牙医个人信息",
            notes = "更新牙医个人信息",
            produces = "application/json",
            consumes = "application/json",
            response = Doctor.class
    )
    @ApiResponses({
            @ApiResponse(code = 100,message = "请求参数错误"),
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 401, message = "登录信息过期"),
            @ApiResponse(code = 403, message = "没有权限"),
            @ApiResponse(code = 404, message = "找不到资源"),
            @ApiResponse(code = 500, message = "服务器错误")
    })
    @PostMapping("/findPreorderById")
    public  @ResponseBody PreOrder findPreorderById(@RequestBody PreOrder data) {
        log.error("DoctorMapper  2222 :" + data.toString()+service.findPreorderById(data.getId()).toString());

        //返回用户对象
        return service.findPreorderById(data.getId());
    }



    @ApiOperation(
            value = "更新牙医个人信息",
            notes = "更新牙医个人信息",
            produces = "application/json",
            consumes = "application/json",
            response = Doctor.class
    )
    @ApiResponses({
            @ApiResponse(code = 100,message = "请求参数错误"),
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 401, message = "登录信息过期"),
            @ApiResponse(code = 403, message = "没有权限"),
            @ApiResponse(code = 404, message = "找不到资源"),
            @ApiResponse(code = 500, message = "服务器错误")
    })
    @PostMapping(path = "/findPreorderByDoctorId")
    public @ResponseBody
    Object findPreorderByDoctorId(@RequestBody Doctor data, BindingResult bindingResult) {
        log.error("PatientController create:" + data.toString());

        List<Integer> result=new ArrayList<>();
        List<PreOrder> tmp=service.findPreorderByDoctorId(data.getId());
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
    @ApiOperation(
            value = "更新牙医个人信息",
            notes = "更新牙医个人信息",
            produces = "application/json",
            consumes = "application/json",
            response = Doctor.class
    )
    @ApiResponses({
            @ApiResponse(code = 100,message = "请求参数错误"),
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 401, message = "登录信息过期"),
            @ApiResponse(code = 403, message = "没有权限"),
            @ApiResponse(code = 404, message = "找不到资源"),
            @ApiResponse(code = 500, message = "服务器错误")
    })
    @PostMapping(path = "/findAppointmentByDoctorId")
    public @ResponseBody
    List<AppointMent> findAppointmentByDoctorId(@RequestBody Doctor data, BindingResult bindingResult) {
        log.error("PatientController create:" + data.toString());

        return service.findAppointmentByDoctorId(data.getId());
    }

    @PostMapping(path = "/findAppointMentByDoctorId")
    public
    List<AppointMent> findAppointMentByDoctorId(Doctor data, BindingResult bindingResult) {
        log.error("PatientController create:" + data.toString());
        List<AppointMent> result=service.findAppointmentByDoctorId(data.getId());
        List<AppointMent> returnData=new ArrayList<>();
        Date date=new Date();
        DateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        String day=format.format(date);
        for(int i=0;i<result.size();i++){
            String preorderDate=service.findPreorderById(result.get(i).getPreorderId()).getDate();
            if(preorderDate.compareTo(day)<0){
                result.get(i).setDoctorId(service.findPreorderById(result.get(i).getPreorderId()).getDate());
                returnData.add(result.get(i));
            }
        }
        return returnData;
    }
    @PostMapping(path = "/findAppointmentByDoctorIdInToday")
    public
    List<AppointMent> findAppointmentByDoctorIdInToday( Doctor data, BindingResult bindingResult) {
        log.error("PatientController create:" + data.toString());
        List<AppointMent> result=service.findAppointmentByDoctorId(data.getId());
        List<AppointMent> returnData=new ArrayList<>();
        Date date=new Date();
        DateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        String day=format.format(date);
        for(int i=0;i<result.size();i++){
            String preorderDate=service.findPreorderById(result.get(i).getPreorderId()).getDate();
            if(preorderDate.equals(day)){
                result.get(i).setDoctorId(day);
                returnData.add(result.get(i));
            }
        }
        return returnData;
    }

    @PostMapping(path = "/findAppointmentByDoctorIdInTodayWhereName")
    public
    List<AppointMent> findAppointmentByDoctorIdInTodayWhereName( Doctor data, BindingResult bindingResult) {
        log.error("PatientController create:" + data.toString());
        List<AppointMent> result=service.findAppointmentByDoctorIdInTodayWhereName(data);
        List<AppointMent> returnData=new ArrayList<>();
        Date date=new Date();
        DateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        String day=format.format(date);
        for(int i=0;i<result.size();i++){
            String preorderDate=service.findPreorderById(result.get(i).getPreorderId()).getDate();
            if(preorderDate.equals(day)){
                result.get(i).setDoctorId(day);
                returnData.add(result.get(i));
            }
        }
        return returnData;
    }

    @ApiOperation(
            value = "更新牙医个人信息",
            notes = "更新牙医个人信息",
            produces = "application/json",
            consumes = "application/json",
            response = Doctor.class
    )
    @ApiResponses({
            @ApiResponse(code = 100,message = "请求参数错误"),
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 401, message = "登录信息过期"),
            @ApiResponse(code = 403, message = "没有权限"),
            @ApiResponse(code = 404, message = "找不到资源"),
            @ApiResponse(code = 500, message = "服务器错误")
    })
    @PostMapping(path = "/findDoctorByPhone")
    public @ResponseBody
    Doctor findDoctorByPhone(@RequestBody Doctor data, BindingResult bindingResult) {
        log.error("DoctorController findDoctorByPhone:" + data.toString());
        return service.findDoctorByPhone(data);
    }

    @PostMapping(path = "/findAppointmentByPatientId")
    public @ResponseBody
    List<AppointMent> findAppointmentByPatientId(@RequestBody Patient data, BindingResult bindingResult) {
        log.error("PatientController create:" + data.toString());
        return service.findAppointmentByPatientId(data.getId());
    }

    @PostMapping(path = "/findAppointmentByAppointmentId")
    public
    AppointMent findAppointmentByAppointmentId(AppointMent data, BindingResult bindingResult) {
        log.error("PatientController create:" + data.toString());
        return service.findAppointmentByAppointmentId(data.getId());
    }

    @PostMapping(path = "/createGroupConsultation")
    public @ResponseBody int
     createGroupConsultation(@RequestBody GroupConsultation data, BindingResult bindingResult) {
        log.error("DoctorController createGroupConsultation:" + data.toString());
        return service.createGroupConsultation(data);
    }

    @PostMapping(path = "/findGroupConsultations")
    public @ResponseBody List<GroupConsultation>
    findGroupConsultations(@RequestBody GroupConsultation data, BindingResult bindingResult) {
        log.error("DoctorController findGroupConsultations:" + data.toString());
        return service.findGroupConsultations(data.getStart());
    }
}
