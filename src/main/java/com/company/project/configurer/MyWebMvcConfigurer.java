package com.company.project.configurer;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.company.project.security.authorization.interceptor.AuthorizationInterceptor;
import com.company.project.security.authorization.resolvers.CurrentUserMethodArgumentResolver;
import com.company.project.core.Result;
import com.company.project.core.ResultCode;
import com.company.project.core.ServiceException;
import com.company.project.security.sign.interceptor.SignIntercepter;
import com.company.project.util.MyHttpUtil;
import com.company.project.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

@Configuration
@Slf4j
public class MyWebMvcConfigurer implements WebMvcConfigurer {

    //当前激活的配置文件
    @Value("${spring.profiles.active}")
    private String env;

    @Autowired
    private SignIntercepter signIntercepter;

    @Autowired
    private AuthorizationInterceptor authorizationInterceptor;

    @Autowired
    private CurrentUserMethodArgumentResolver currentUserMethodArgumentResolver;

    //统一异常处理
    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        exceptionResolvers.add(new HandlerExceptionResolver() {
            public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) {

                Result result = new Result();

                if (e instanceof ServiceException) {//业务失败的异常，如“账号或密码错误”
                    ServiceException serviceEx = (ServiceException) e;
                    result = Result.failure(serviceEx);

                    log.error(StringUtil.buildExceptionJSON(serviceEx));
                } else if (e instanceof NoHandlerFoundException) { //请求路径不存在
                    result.setCode(ResultCode.NOT_FOUND.getCode());
                    result.setMessage("接口 [" + request.getRequestURI() + "] 不存在");

                } else if (e instanceof ServletException) { //servlet 异常
                    result.setCode(ResultCode.SERVLET_FAIL.getCode());
                    result.setMessage(e.getMessage());

                } else {
                    result.setCode(ResultCode.INTERNAL_SERVER_ERROR.getCode());
                    result.setMessage("接口 [" + request.getRequestURI() + "] 内部错误，请联系管理员");

                    String message;
                    if (handler instanceof HandlerMethod) {
                        HandlerMethod handlerMethod = (HandlerMethod) handler;
                        message = String.format("接口 [%s] 出现异常，方法：%s.%s，异常摘要：%s",
                                request.getRequestURI(),
                                handlerMethod.getBean().getClass().getName(),
                                handlerMethod.getMethod().getName(),
                                e.getMessage());
                    } else {
                        message = e.getMessage();
                    }

                    log.error(message, e);
                }
                //响应流输出返回结果
                MyHttpUtil.responseResult(response, result);
                return new ModelAndView();
            }

        });
    }

    //拦截器的添加顺序就是执行顺序
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        //接口签名认证拦截器，该签名认证比较简单，实际项目中可以使用Json Web Token(JWT)或其他更好的方式替代。

//        if (!"dev".equals(env)) { //开发环境忽略签名认证
//            registry.addInterceptor(signIntercepter)
//                    //取消对swagger的拦截
//                    .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**");
//        }
        registry.addInterceptor(authorizationInterceptor)
                //取消对swagger的拦截
                .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**");
        registry.addInterceptor(signIntercepter)
                //取消对swagger的拦截
                .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**");
    }

    @Override
    //取消对swagger的拦截2，(缺一不可)
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        FastJsonConfig config = new FastJsonConfig();
        config.setSerializerFeatures(SerializerFeature.WriteMapNullValue);//保留空的字段
        //SerializerFeature.WriteNullStringAsEmpty,//String null -> ""
        //SerializerFeature.WriteNullNumberAsZero//Number null -> 0
        // 按需配置，更多参考FastJson文档哈

        converter.setFastJsonConfig(config);
        converter.setDefaultCharset(Charset.forName("UTF-8"));
        converter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
        converters.add(converter);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(currentUserMethodArgumentResolver);
    }


    //解决跨域问题
    //TODO 拦截器和addCorsMappings生效顺序的问题???
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }


    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void addViewControllers(ViewControllerRegistry arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void configurePathMatch(PathMatchConfigurer arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public MessageCodesResolver getMessageCodesResolver() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Validator getValidator() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        // TODO Auto-generated method stub

    }



}