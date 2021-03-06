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
     * ???????????????id
     */
    @Value("${alipay.id}")
    private String alipayAppId;

    /**
     * app??????
     */
    @Value("${alipay.private}")
    private String alipayAppPrivateKey;

    /**
     * ???????????????
     */
    @Value("${alipay.public}")
    private String alipayPublicKey;
    /**
     * ??????app????????????
     *
     * @return
     */
    @RequestMapping(path = "/getR")
    private void getAppPayParamR() throws AlipayApiException {
//         ??????client
        CertAlipayRequest certAlipayRequest = new CertAlipayRequest();
        // ??????????????????
        certAlipayRequest.setServerUrl("https://openapi.alipay.com/gateway.do");
        // ????????????Id
        certAlipayRequest.setAppId("setAppId");
        // ??????????????????
        certAlipayRequest.setPrivateKey("setPrivateKey");
        // ??????????????????????????????json
        certAlipayRequest.setFormat("json");
        // ???????????????
        certAlipayRequest.setCharset("UTF-8");
        // ??????????????????
        certAlipayRequest.setSignType("RSA2");
//        // ??????????????????????????????
        certAlipayRequest.setCertPath("/root/appCertPublicKey_2021003127632363.crt");
//        // ?????????????????????????????????
        certAlipayRequest.setAlipayPublicCertPath("/root/alipayCertPublicKey_RSA2.crt");
//        // ??????????????????????????????
        certAlipayRequest.setRootCertPath("/root/alipayRootCert.crt");
        // ??????client
        AlipayClient alipayClient = new DefaultAlipayClient(certAlipayRequest);
        AlipayFundTransUniTransferRequest request = new AlipayFundTransUniTransferRequest();
        AlipayFundTransUniTransferModel model = new AlipayFundTransUniTransferModel();
        model.setOutBizNo("201806300001");
        model.setRemark("201905??????");
        model.setBusinessParams("{payer_show_name_use_alias:true}");
        model.setBizScene("DIRECT_TRANSFER");
        Participant payeeInfo = new Participant();
        payeeInfo.setIdentity("2088132767760764");
        payeeInfo.setIdentityType("ALIPAY_USER_ID");
        model.setPayeeInfo(payeeInfo);
        model.setTransAmount("0.1");
        model.setProductCode("TRANS_ACCOUNT_NO_PWD");
        model.setOrderTitle("201905??????");
        request.setBizModel(model);
        AlipayFundTransUniTransferResponse response = alipayClient.certificateExecute(request);
        System.out.println(response.getBody());
        if (response.isSuccess()) {
            System.out.println("????????????");
        } else {
            System.out.println("????????????");
        }

    }
    /**
     * ??????app????????????
     *
     * @return
     */
    @RequestMapping(path = "/getW")
    private Pay getAppPayParam() {
        //??????????????????
        DefaultAlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",
                alipayAppId,
                alipayAppPrivateKey,
                "json",
                "utf-8",
                alipayPublicKey,
                "RSA2");

        //???????????????API?????????request???,??????????????????????????????,???????????????????????????alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();

        //SDK??????????????????????????????
        //?????????????????????????????????
        //???????????????sdk???model????????????(model???biz_content???????????????????????????biz_content)
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();

        //??????
        //?????????????????????
        //??????????????????????????????https://docs.open.alipay.com/204/105465/
        model.setSubject("????????????-??????");
//        model.setSellerId("2759100807@qq.com");
        //?????????????????????????????????????????????????????????????????????????????????????????? ID?????????????????????
        model.setOutTradeNo("22222222");

        //??????????????????
        //model.setTimeoutExpress("30m");

        //????????????????????????????????????????????????????????????????????? 5,123.99 ????????????????????? '5123.99'???
        model.setTotalAmount(StringUtil.formatFloat2(0.01));

        //??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????Id?????????????????????????????????????????????????????????????????????????????????
//        model.setPassbackParams(data.getId());

        //app????????????
        model.setProductCode("QUICK_MSECURITY_PAY");

        //??????????????????
        request.setBizModel(model);

        //??????????????????
//        request.setNotifyUrl(String.format(Constant.ALIPAY_NOTIFY_URL, host));
        try {
            //????????????????????????????????????
            //????????????sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);

            //?????????????????????
            //????????????????????????
            Pay pay = new Pay(10, response.getBody());

            return pay;
        } catch (AlipayApiException e) {
//            log.error("getAppPayParam failed {} {}", data.getId(), e);
        }
        throw new ArgumentException();
    }

}
