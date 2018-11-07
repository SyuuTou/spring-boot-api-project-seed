package com.company.project.security.authorization.interceptor;

import com.company.project.security.authorization.annotation.Authorization;
import com.company.project.security.authorization.manager.TokenService;
import com.company.project.constant.SysConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 自定义拦截器，判断此次请求是否有权限
 */
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private TokenService tokenService;

    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        //如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

//      UserInfo userInfo = userService.getUserByToken(token);
//      request.setAttribute("currentUser", userInfo);
//      return true;

//        if (method.getAnnotation(Authorization.class) != null) {
        if (method.isAnnotationPresent(Authorization.class)) {
            //从header中得到授权信息
            String authorization = request.getHeader(SysConstant.AUTHORIZATION);
            //解析授权信息并验证token
            int userId = tokenService.parseAuthorization(authorization);
            //如果token验证成功，将token对应的用户id存在request中，便于之后注入
            request.setAttribute(SysConstant.CURRENT_USER_ID, userId);
            return true;

        }

        return true;
    }
}
