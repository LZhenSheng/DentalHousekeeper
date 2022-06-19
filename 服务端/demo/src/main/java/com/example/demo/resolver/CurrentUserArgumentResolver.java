package com.example.demo.resolver;

import com.example.demo.annotation.CurrentUser;
import com.example.demo.domin.Patient;
import com.example.demo.service.PatientService;
import com.example.demo.util.Constant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * CurrentUser注解参数解析器
 */
public class CurrentUserArgumentResolver implements HandlerMethodArgumentResolver {
    /**
     * 用户服务
     */
    @Autowired
    private PatientService userService;

    /**
     * 是否支持参数解析
     * <p>
     * 是否要解析参数
     *
     * @param parameter
     * @return true:要解析，才会执行resolveArgument；false:不解析
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        //参数类型是user
        //并且有CurrentUser注解
        return parameter.getParameterType().isAssignableFrom(Patient.class) &&
                parameter.hasParameterAnnotation(CurrentUser.class);
    }

    /**
     * 参数解析
     *
     * @param parameter
     * @param mavContainer
     * @param webRequest
     * @param binderFactory
     * @return
     * @throws Exception
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        //从request中取出用户对象
        Patient user = (Patient) webRequest.getAttribute(Constant.CURRENT_USER, RequestAttributes.SCOPE_REQUEST);

        if (user == null) {
            //获取用户id
            String userId = webRequest.getHeader(Constant.USER);

            if (StringUtils.isNotBlank(userId)) {
                user = userService.find(userId);
            }
        }

        //判断是否找到用户
        if (user == null) {
            //没有找到用户

            //可以抛出异常
            //但我们这里不抛出
            //因为我们的实现是
            //CurrentUser可以为空
        }

        return user;
    }
}