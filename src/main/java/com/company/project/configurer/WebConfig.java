//package com.company.project.configurer;
//
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.filter.CorsFilter;
//import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//
//@Configuration
//public class WebMvcConfig extends WebMvcConfigurerAdapter {
//
//    @Override
//    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
//        configurer.favorPathExtension(false);
//    }
//
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//
//        if (!registry.hasMappingForPattern("/webjars/**")) {
//            registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
//        }
//        if (!registry.hasMappingForPattern("/hystrix/**")) {
//            registry.addResourceHandler("/hystrix/**").addResourceLocations("classpath:/static/hystrix/");
//        }
//        registry.setOrder(1);
//    }
//
//    /**
//     * Slf4j MDC filter.
//     *
//     * @return
//     */
//    @Bean
//    public FilterRegistrationBean mdcFilterRegistration() {
//        FilterRegistrationBean registration = new FilterRegistrationBean();
//        registration.setFilter(new HttpRequestMDCFilter());
//        registration.addUrlPatterns("/*");
//        registration.addInitParameter("mappedCookies", "false");
//        registration.addInitParameter("mappedHeaders", "true");
//        registration.addInitParameter("mappedParameters", "false");
//        registration.setName("httpRequestMDCFilter");
////    registration.setOrder(1);
//        return registration;
//    }
//
//
//    /**
//     * CORS fitler
//     * @return
//     */
//    @Bean
//    public FilterRegistrationBean corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true);
//        config.addAllowedOrigin("*");
//        config.addAllowedHeader("*");
//        config.addAllowedMethod("*");
//        source.registerCorsConfiguration("/**", config);
//        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
//        bean.setOrder(0);
//        return bean;
//    }
//}
//
