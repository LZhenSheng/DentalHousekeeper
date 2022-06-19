package com.example.demo.interceptor;

import com.example.demo.annotation.Authorization;
import com.example.demo.domin.Patient;
import com.example.demo.service.SessionService;
import com.example.demo.util.Constant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 处理Authorization注解
 */
@Slf4j
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    /**
     * session服务
     */
    @Autowired
    private SessionService sessionService;

    /**
     * 执行该注释方法前调用
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断是方法拦截
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        //获取拦截的方法处理器
        HandlerMethod handlerMethod = (HandlerMethod) handler;

        //获取拦截的方法
        Method method = handlerMethod.getMethod();

        if (method.getAnnotation(Authorization.class) == null){
            //如果该方法没有Authorization注解就不处理
            return true;
        }

        //获取用户id
        String userId = request.getHeader(Constant.USER);

        //获取用户传递的session
        String authorization = request.getHeader(Constant.AUTHORIZATION);

        //判断是否有请求头
        try {
            if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(authorization)) {
                //检查session
                Patient data = sessionService.checkSession(userId, authorization);
                if (data!=null) {
                    //验证成功

                    //将用户保存到request
                    //因为后续可能会解析CurrentUser
                    //如果再解析就不用再去服务端查询
                    request.setAttribute(Constant.CURRENT_USER,data);
                    return true;
                }
            }
        } catch (Exception e) {
            //这部分发生错误后
            //统一当做认证失败
            log.debug("preHandle {}",e);
        }

        //设置响应码为401
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        return false;
    }
}