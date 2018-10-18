package ${basePackage}.controller;

import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.LinkedHashMap;
import java.util.Map;

/**
* Created by ${author} on ${date}.
*/
@RestController
@RequestMapping("demo")
public class DemoController {

    /**
     * request header信息获取
     * @param request
     * @return
     */
    @GetMapping("print/header")
    public Object header(HttpServletRequest request) {
        HttpSession session = request.getSession();

        Map<String, Object> map = new LinkedHashMap<>();
        map.put("Protocol", request.getProtocol());
        map.put("Scheme", request.getScheme());
        map.put("Server Name", request.getServerName());
        map.put("Server Port", request.getServerPort());
        map.put("Remote Addr", request.getRemoteAddr());
        map.put("Remote Host", request.getRemoteHost());
        map.put("Character Encoding", request.getCharacterEncoding());
        map.put("Content Length", request.getContentLength());
        map.put("Content Type", request.getContentType());
        map.put("Auth Type", request.getAuthType());
        map.put("HTTP Method", request.getMethod());
        map.put("path Info", request.getPathInfo());
        map.put("path Trans", request.getPathTranslated());
        map.put("Query String", request.getQueryString());
        map.put("Remote User", request.getRemoteUser());
        map.put("Session Id", request.getRequestedSessionId());
        map.put("Request URI", request.getRequestURI());
        map.put("Request URL", request.getRequestURL());
        map.put("Servlet Path", request.getServletPath());
        map.put("Accept", request.getHeader("Accept"));
        map.put("Host", request.getHeader("Host"));
        map.put("Referer", request.getHeader("Referer"));
        map.put("Accept-Language", request.getHeader("Accept-Language"));

        map.put("Accept-Encoding", request.getHeader("Accept-Encoding"));
        map.put("User-Agent", request.getHeader("User-Agent"));
        map.put("Connection", request.getHeader("Connection"));
        map.put("Cookie", request.getHeader("Cookie"));

        map.put("Created", session.getCreationTime());
        map.put("LastAccessed", session.getLastAccessedTime());


        //获取客户端向服务器端传送数据的协议名称
        System.out.println("Protocol: " + request.getProtocol());
//返回的协议名称.默认是http
        System.out.println("Scheme: " + request.getScheme());
//可以返回当前页面所在的服务器的名字;如果你的应用部署在本机那么其就返回localhost或者127.0.0.1 ，这两个是等价的
        System.out.println("Server Name: " + request.getServerName());
//可以返回当前页面所在的服务器使用的端口,就是9092
        System.out.println("Server Port: " + request.getServerPort());
        //request.getRemoteAddr()是获得客户端的ip地址
        System.out.println("Remote Addr: " + request.getRemoteAddr());
        //request.getRemoteHost()是获得客户端的主机名。
        System.out.println("Remote Host: " + request.getRemoteHost());
//返回字符编码
        System.out.println("Character Encoding: " + request.getCharacterEncoding());

        System.out.println("Content Length: " + request.getContentLength());
//定义网络文件的类型和网页的编码，决定浏览器将以什么形式、什么编码读取这个文件，
        System.out.println("Content Type: " + request.getContentType());
//如果servlet由一个鉴定方案所保护，如HTTP基本鉴定，则返回方案名称
        System.out.println("Auth Type: " + request.getAuthType());
//返回HTTP请求方法（例如GET、POST等等）
        System.out.println("HTTP Method: " + request.getMethod());
//返回在URL中指定的任意附加路径信息。
        System.out.println("path Info: " + request.getPathInfo());
        //返回在URL中指定的任意附加路径信息，被子转换成一个实际路径
        System.out.println("path Trans: " + request.getPathTranslated());
//返回查询字符串，即URL中?后面的部份。
        System.out.println("Query String: " + request.getQueryString());
//如果用户通过鉴定，返回远程用户名，否则为null。
        System.out.println("Remote User: " + request.getRemoteUser());
//返回客户端的会话ID
        System.out.println("Session Id: " + request.getRequestedSessionId());
//返回URL中一部分，从“/”开始，包括上下文，但不包括任意查询字符串。
        System.out.println("Request URI: " + request.getRequestURI());
        //返回请求URI上下文后的子串
        System.out.println("Servlet Path: " + request.getServletPath());
//返回指定的HTTP头标指。如果其由请求给出，则名字应为大小写不敏感。
        System.out.println("Accept: " + request.getHeader("Accept"));
        System.out.println("Host: " + request.getHeader("Host"));
        System.out.println("Referer : " + request.getHeader("Referer"));
        System.out.println("Accept-Language : " + request.getHeader("Accept-Language"));
        System.out.println("Accept-Encoding : " + request.getHeader("Accept-Encoding"));
        System.out.println("User-Agent : " + request.getHeader("User-Agent"));
        System.out.println("Connection : " + request.getHeader("Connection"));
        System.out.println("Cookie : " + request.getHeader("Cookie"));

        System.out.println("Created : " + session.getCreationTime());
        System.out.println("LastAccessed : " + session.getLastAccessedTime());

        return map;
    }


}
