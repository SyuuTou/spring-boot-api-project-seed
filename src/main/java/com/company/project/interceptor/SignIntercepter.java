package com.company.project.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.company.project.core.Result;
import com.company.project.core.ResultCode;
import com.company.project.filter.MyHttpServletRequestWrapper;
import com.company.project.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.invoke.MethodHandle;
import java.lang.reflect.Method;
import java.util.*;


@Slf4j
@Component
/**
 * 签名拦截器
 */
public class SignIntercepter implements HandlerInterceptor {

    /**
     * 签名秘钥
     */
    @Value("${sign.secret}")
    private String signSecret;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HandlerMethod methodHandle = (HandlerMethod) handler;
        Object bean = methodHandle.getBean();
        Class<?> beanType = methodHandle.getBeanType();
        Method method = methodHandle.getMethod();
        MethodParameter[] methodParameters = methodHandle.getMethodParameters();
        for (MethodParameter e:methodParameters
             ) {
            System.err.println(e.getParameterName());
        }
        System.err.println(bean);
        System.err.println(beanType);
        System.err.println(method);
        System.err.println(Arrays.toString(methodParameters));


        //拦截器获取pathVariable中的数据
//        Map pathVars = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
//        log.warn(pathVars.toString());

        //拦截器不需要把流复制一遍，直接读取就可以了，因为已经通过过滤器继续向下传递了
//        MyHttpServletRequestWrapper myWrapper = new MyHttpServletRequestWrapper(request);
//        String jsonStr = HttpUtil.getRequestJsonString(myWrapper);

        String jsonStr = HttpUtil.getRequestJsonString(request);

        JSONObject paraMap = JSONObject.parseObject(jsonStr);
        //日志打印
        if (paraMap != null) {
            paraMap.forEach((k, v) ->
            {
                log.info("key:【{}】: value:【{}】", k, v);
            });
        }

        //验证签名
        boolean pass = validateSign(paraMap);
        if (pass) {
            return true;
        } else {
            log.warn("签名认证失败，请求接口：{}，请求IP：{}，请求参数：{}",
                    request.getRequestURI(), HttpUtil.getIpAddress(request), JSON.toJSONString(paraMap));
            //签名认证失败
            Result result = new Result(ResultCode.UNAUTHORIZED);
            HttpUtil.responseResult(response, result);
            return false;
        }
    }


    //    private boolean validateSign(HttpServletRequest request) throws IOException {
    private boolean validateSign(JSONObject parameterMap) throws IOException {
        if (parameterMap == null) {
            return false;
        }
        //取得签名
        String requestSign = String.valueOf(parameterMap.get("sign"));
        if (StringUtils.isEmpty(requestSign)) {
            return false;
        }
        List<String> keys = new ArrayList<>(parameterMap.keySet());
        keys.remove("sign");//排除sign参数
        Collections.sort(keys);//排序

        StringBuilder sb = new StringBuilder();
        for (
                String key : keys) {
            sb.append(key).append("=").append(parameterMap.get(key)).append("&");//拼接字符串
        }

        String linkString = sb.toString();
        linkString = StringUtils.substring(linkString, 0, linkString.length() - 1);//去除最后一个'&'
        log.info("请求参数拼接后的字符串 linkString :【{}】", linkString);

        String secret = signSecret;//密钥，自己修改
        String sign = DigestUtils.md5Hex(linkString + secret);//混合密钥md5
        log.info("服务端生成的签名(拼接字符串连接秘钥并经过MD5加密) sign :【{}】", sign);

        return StringUtils.equals(sign, requestSign);//比较
    }
}


