package com.example.dentalhousekeeper.util;

import android.text.TextUtils;

import com.example.dentalhousekeeper.R;
import com.example.dentalhousekeeper.domin.BaseResponse;
import com.example.dentalhousekeeper.exception.ResponseDecryptException;
import com.example.dentalhousekeeper.exception.ResponseSignException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.HttpException;
import retrofit2.Response;

/**
 * 网络请求相关的辅助方法
 */
public class HttpUtil {
    /**
     * 网络请求错误处理
     *
     * @param data
     * @param error
     */
    public static void handlerRequest(Object data, Throwable error) {
        if (error != null) {
            //先处理有异常的请求

            //判断错误类型
            if (error instanceof UnknownHostException) {
                ToastUtil.errorShortToast(R.string.error_network_unknown_host);
            } else if (error instanceof ConnectException) {
                ToastUtil.errorShortToast(R.string.error_network_connect);
            } else if (error instanceof SocketTimeoutException) {
                ToastUtil.errorShortToast(R.string.error_network_timeout);
            } else if (error instanceof HttpException) {
                HttpException exception = (HttpException) error;

                //获取响应码
                int code = exception.code();

                handleHttpError(code);
            } else if (error instanceof ResponseSignException) {

                ToastUtil.errorShortToast(R.string.error_response_sign);
            } else if (error instanceof ResponseDecryptException) {

                ToastUtil.errorShortToast(R.string.error_response_decrypt);
            } else {
                ToastUtil.errorShortToast(R.string.error_network_unknown);
            }
        } else {
            if (data instanceof Response) {
                //retrofit里面的对象

                //获取响应对象
                Response response = (Response) data;

                //获取响应码
                int code = response.code();

                //判断响应码
                if (code >= 200 && code <= 299) {
                    //网络请求正常
                } else {
                    handleHttpError(code);
                }
            } else if (data instanceof BaseResponse) {
                //判断具体的业务请求是否成功
                BaseResponse response = (BaseResponse) data;

                if (TextUtils.isEmpty(response.getMessage())) {
                    //没有错误提示信息
                    ToastUtil.errorShortToast(R.string.error_network_unknown);
                } else {
                    //有错误提示
                    ToastUtil.errorShortToast(response.getMessage());
                }
            }
        }
    }

    /**
     * 处理Http错误
     *
     * @param code
     */
    private static void handleHttpError(int code) {
        if (code == 401) {
            ToastUtil.errorShortToast(R.string.error_network_not_auth);

//            AppContext.getInstance().logout();
        } else if (code == 403) {
            ToastUtil.errorShortToast(R.string.error_network_not_permission);
        } else if (code == 404) {
            ToastUtil.errorShortToast(R.string.error_network_not_found);
        } else if (code >= 500) {
            ToastUtil.errorShortToast(R.string.error_network_server);
        } else {
            ToastUtil.errorShortToast(R.string.error_network_unknown);
        }
    }
}
