package com.company.project.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName = "MyFilter", urlPatterns = "/*")
@Component
public class MyFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("--------------过滤器初始化------------");
    }

    /**
     * from net
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        System.out.println("--------------执行过滤操作------------");
//
//        // 防止流读取一次后就没有了, 所以需要将流继续写出去
//        ServletRequest requestWrapper = new BodyReaderHttpServletRequestWrapper((HttpServletRequest) request);
//
//        chain.doFilter(requestWrapper, response);
//    }

    /**
     * from 王哲
     *
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        ServletRequest requestWrapper = null;
        if (request instanceof HttpServletRequest) {
            requestWrapper = new MyHttpServletRequestWrapper((HttpServletRequest) request);
        }

        if (requestWrapper == null) {
            System.err.println("过滤器执行中 requestWrapper == null");
            chain.doFilter(request, response);
        } else {
            System.err.println("过滤器执行中 requestWrapper != null");

            chain.doFilter(requestWrapper, response);
        }
    }
    @Override
    public void destroy() {
        System.out.println("--------------过滤器销毁------------");
    }

}
