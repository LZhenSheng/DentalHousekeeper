package com.example.demo.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.CertAlipayRequest;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayFundTransUniTransferModel;
import com.alipay.api.domain.Participant;
import com.alipay.api.request.AlipayFundTransUniTransferRequest;
import com.alipay.api.response.AlipayFundTransUniTransferResponse;
import com.example.demo.domin.*;
import com.example.demo.service.DoctorService;
import com.example.demo.service.HospitalService;
import com.example.demo.util.IDUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import java.util.Map;

import static com.example.demo.util.Constant.PAY_SUCCESS;

/**
 * 回调控制器
 */
@Api(tags={"回调"})
@RestController
@Slf4j
public class CallbackController {

    @Autowired
    private DoctorService service;

    @Autowired
    private HospitalService hospitalService;

    @ApiOperation(
            value = "支付宝回调函数",
            notes = "支付宝回调函数",
            produces = "application/json",
            consumes = "application/json",
            response = HttpServletRequest.class
    )
    @PostMapping(value = "/callback", produces = "text/plain;charset=UTF-8")
    public Object callback(HttpServletRequest request) {
        System.out.println("callback");
        Map<String, String[]> requestParameter = request.getParameterMap();
        String outTradeNo = requestParameter.get("out_trade_no")[0];
        String totalAmount = requestParameter.get("total_amount")[0];
//        System.out.println(outTradeNo+"\n"+totalAmount);
        System.out.println("--------------------------");
        System.out.println("outtradeno"+outTradeNo);
        System.out.println("totalAmount"+totalAmount);
        System.out.println("--------------------------");
        service.updateAppointMent(outTradeNo);
        System.out.println(outTradeNo);
        AppointMent data=service.findAppointmentById(outTradeNo);
        Doctor doctor=new Doctor();
        doctor.setId(data.getDoctorId());
        Doctor doctor1=hospitalService.findDoctorByDoctorId(doctor.getId());
        Hospital hospital=new Hospital();
        hospital.setId(doctor1.getHospital());
        Hospital hospital1=hospitalService.findHospitalByHospitalId(hospital);
        Payment payment=new Payment();
        payment.setDoctor(data.getDoctorId());
        payment.setPatient(data.getPatientId());
        payment.setAppointment(data.getId());
        payment.setPreorder(data.getPreorderId());
        payment.setHospital(hospital.getId());
        payment.setMoney(Double.valueOf(totalAmount));
        service.savePayment(payment);
        try {
            getAppPayParamR(hospital1.getPid(),totalAmount);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return PAY_SUCCESS;
    }

    private void getAppPayParamR(String pid,String totalAmount) throws AlipayApiException {
        CertAlipayRequest certAlipayRequest = new CertAlipayRequest();
        certAlipayRequest.setServerUrl("setServerUrl");
        certAlipayRequest.setAppId("setAppId");
        certAlipayRequest.setPrivateKey("setPrivateKey");
        certAlipayRequest.setFormat("json");
        certAlipayRequest.setCharset("UTF-8");
        certAlipayRequest.setSignType("RSA2");
        certAlipayRequest.setCertPath("/root/appCertPublicKey_2021003127632363.crt");
        certAlipayRequest.setAlipayPublicCertPath("/root/alipayCertPublicKey_RSA2.crt");
        certAlipayRequest.setRootCertPath("/root/alipayRootCert.crt");
        AlipayClient alipayClient = new DefaultAlipayClient(certAlipayRequest);
        AlipayFundTransUniTransferRequest request = new AlipayFundTransUniTransferRequest();
        AlipayFundTransUniTransferModel model = new AlipayFundTransUniTransferModel();
        model.setOutBizNo(IDUtil.getUUID());
        model.setRemark("牙医管家");
        model.setBusinessParams("{payer_show_name_use_alias:true}");
        model.setBizScene("DIRECT_TRANSFER");
        Participant payeeInfo = new Participant();
        payeeInfo.setIdentity(pid);
        payeeInfo.setIdentityType("ALIPAY_USER_ID");
        model.setPayeeInfo(payeeInfo);
        model.setTransAmount(totalAmount);
        model.setProductCode("TRANS_ACCOUNT_NO_PWD");
        model.setOrderTitle("牙医管家");
        request.setBizModel(model);
        AlipayFundTransUniTransferResponse response = alipayClient.certificateExecute(request);
        System.out.println(response.getBody());
        if (response.isSuccess()) {
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }

    }
}