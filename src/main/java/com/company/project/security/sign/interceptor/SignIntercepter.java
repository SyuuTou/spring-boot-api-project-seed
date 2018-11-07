package com.company.project.security.sign.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.company.project.constant.SysConstant;
import com.company.project.core.Result;
import com.company.project.core.ResultCode;
import com.company.project.security.sign.annotation.Sign;
import com.company.project.util.MyHttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


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
        //如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;

        if(handlerMethod.hasMethodAnnotation(Sign.class)){
//        Method method = handlerMethod.getMethod();
        //如果方法标注了签名认证注解
//        if (method.getAnnotation(Sign.class) != null) {

            //拦截器不需要把流复制一遍，直接读取就可以了，因为已经通过过滤器继续向下传递了
//        MyHttpServletRequestWrapper myWrapper = new MyHttpServletRequestWrapper(request);
//        String jsonStr = HttpUtil.getRequestJsonString(myWrapper);

            String jsonStr = MyHttpUtil.getRequestJsonString(request);
            //算法签名
            String sign = request.getHeader(SysConstant.SIGN);

            JSONObject paraMap = JSONObject.parseObject(jsonStr);

            //请求参数日志打印
            if (paraMap != null) {
                paraMap.forEach((k, v) ->
                {
                    log.info("key:【{}】: value:【{}】", k, v);
                });
            }

            //验证签名
            boolean pass = validateSign(paraMap,sign);
            if (pass) {
                return true;
            } else {
                log.warn("签名认证失败，请求接口：{}，请求IP：{}，请求参数：{}",
                        request.getRequestURI(), MyHttpUtil.getIpAddress(request), JSON.toJSONString(paraMap));
                //签名认证失败
                Result result = new Result(ResultCode.UNAUTHORIZED);
                MyHttpUtil.responseResult(response, result);
                return false;
            }
        }
        return true;
    }

    /**
     *
     * @param parameterMap 请求参数
     * @param requestSign 接口签名
     * @return
     * @throws IOException
     */
    private boolean validateSign(JSONObject parameterMap,String requestSign) throws IOException {
        if (parameterMap == null) {
            return false;
        }
        //取得签名
        if (StringUtils.isEmpty(requestSign)) {
            return false;
        }

//        String requestSign = String.valueOf(parameterMap.get("sign"));
//        if (StringUtils.isEmpty(requestSign)) {
//            return false;
//        }
        List<String> keys = new ArrayList<>(parameterMap.keySet());
//        keys.remove("sign");//排除sign参数
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


