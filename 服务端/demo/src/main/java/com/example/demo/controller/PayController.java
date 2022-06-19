package com.example.demo.controller;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.CertAlipayRequest;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayFundTransUniTransferModel;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.Participant;
import com.alipay.api.request.AlipayFundAccountQueryRequest;
import com.alipay.api.request.AlipayFundTransUniTransferRequest;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayFundAccountQueryResponse;
import com.alipay.api.response.AlipayFundTransUniTransferResponse;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
//import com.alipay.demo.trade.config.Configs;
//import com.alipay.demo.trade.model.ExtendParams;
//import com.alipay.demo.trade.model.GoodsDetail;
//import com.alipay.demo.trade.model.builder.*;
//import com.alipay.demo.trade.model.hb.*;
//
//import com.alipay.demo.trade.model.result.AlipayF2FPayResult;
//import com.alipay.demo.trade.model.result.AlipayF2FPrecreateResult;
//import com.alipay.demo.trade.model.result.AlipayF2FQueryResult;
//import com.alipay.demo.trade.model.result.AlipayF2FRefundResult;
//import com.alipay.demo.trade.service.AlipayMonitorService;
//import com.alipay.demo.trade.service.AlipayTradeService;
//import com.alipay.demo.trade.service.impl.AlipayMonitorServiceImpl;
//import com.alipay.demo.trade.service.impl.AlipayTradeServiceImpl;
//import com.alipay.demo.trade.service.impl.AlipayTradeWithHBServiceImpl;
import com.example.demo.domin.Patient;
import com.example.demo.domin.Pay;
import com.example.demo.exception.ArgumentException;
import com.example.demo.util.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class PayController {
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
    /**
     * 获取app支付参数
     *
     * @return
     */
    @RequestMapping(path = "/getR")
    private void getAppPayParamR() throws AlipayApiException {
//         构造client
        CertAlipayRequest certAlipayRequest = new CertAlipayRequest();
        // 设置网关地址
        certAlipayRequest.setServerUrl("https://openapi.alipay.com/gateway.do");
        // 设置应用Id
        certAlipayRequest.setAppId("setAppId");
        // 设置应用私钥
        certAlipayRequest.setPrivateKey("setPrivateKey");
        // 设置请求格式，固定值json
        certAlipayRequest.setFormat("json");
        // 设置字符集
        certAlipayRequest.setCharset("UTF-8");
        // 设置签名类型
        certAlipayRequest.setSignType("RSA2");
//        // 设置应用公钥证书路径
        certAlipayRequest.setCertPath("/root/appCertPublicKey_2021003127632363.crt");
//        // 设置支付宝公钥证书路径
        certAlipayRequest.setAlipayPublicCertPath("/root/alipayCertPublicKey_RSA2.crt");
//        // 设置支付宝根证书路径
        certAlipayRequest.setRootCertPath("/root/alipayRootCert.crt");
        // 构造client
        AlipayClient alipayClient = new DefaultAlipayClient(certAlipayRequest);
        AlipayFundTransUniTransferRequest request = new AlipayFundTransUniTransferRequest();
        AlipayFundTransUniTransferModel model = new AlipayFundTransUniTransferModel();
        model.setOutBizNo("201806300001");
        model.setRemark("201905代发");
        model.setBusinessParams("{payer_show_name_use_alias:true}");
        model.setBizScene("DIRECT_TRANSFER");
        Participant payeeInfo = new Participant();
        payeeInfo.setIdentity("2088132767760764");
        payeeInfo.setIdentityType("ALIPAY_USER_ID");
        model.setPayeeInfo(payeeInfo);
        model.setTransAmount("0.1");
        model.setProductCode("TRANS_ACCOUNT_NO_PWD");
        model.setOrderTitle("201905代发");
        request.setBizModel(model);
        AlipayFundTransUniTransferResponse response = alipayClient.certificateExecute(request);
        System.out.println(response.getBody());
        if (response.isSuccess()) {
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }

    }
    /**
     * 获取app支付参数
     *
     * @return
     */
    @RequestMapping(path = "/getW")
    private Pay getAppPayParam() {
        //实例化客户端
        DefaultAlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",
                alipayAppId,
                alipayAppPrivateKey,
                "json",
                "utf-8",
                alipayPublicKey,
                "RSA2");

        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();

        //SDK已经封装掉了公共参数
        //这里只需要传入业务参数
        //以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();

        //标题
        //这里传递订单号
        //支付宝官网参数解释：https://docs.open.alipay.com/204/105465/
        model.setSubject("牙医管家-预约");
//        model.setSellerId("2759100807@qq.com");
        //是你作为商户提供给支付宝的订单号。建议引用你应用内相应模型的 ID，并不能重复。
        model.setOutTradeNo("22222222");

        //支付超时时间
        //model.setTimeoutExpress("30m");

        //是支付订单的金额，精确到小数点后两位。例如金额 5,123.99 元的参数值应为 '5123.99'。
        model.setTotalAmount(StringUtil.formatFloat2(0.01));

        //公用回传参数，如果请求的时候传递了，支付宝异步通知的时候会带上，这样回调的时候我们就可以校验Id和单号是否一样，其实回调的时候根据订单号查询也是可以的
//        model.setPassbackParams(data.getId());

        //app快捷支付
        model.setProductCode("QUICK_MSECURITY_PAY");

        //设置业务模型
        request.setBizModel(model);

        //设置异步通知
//        request.setNotifyUrl(String.format(Constant.ALIPAY_NOTIFY_URL, host));
        try {
            //这里和普通的接口调用不同
            //使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);

            //获取支付字符串
            //直接返回给客户端
            Pay pay = new Pay(10, response.getBody());

            return pay;
        } catch (AlipayApiException e) {
//            log.error("getAppPayParam failed {} {}", data.getId(), e);
        }
        throw new ArgumentException();
    }

}
