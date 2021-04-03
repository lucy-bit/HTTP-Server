package com.nannan.tomcat.http;

import com.nannan.standard.http.Cookie;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;
public class HttpRequestParser {
    public Request parse(InputStream socketInputStream) throws IOException, ClassNotFoundException {
        Scanner scanner = new Scanner(socketInputStream, "UTF-8");

        //next()是因为请求行的方法，路径，版本号是以“  ”进行分割的
        //方法
        String method = scanner.next().toUpperCase();// 因为standard 的Httpservlet中的方法定义的都是大写
        String path = scanner.next();
        Map<String, String> parameters = new HashMap<>(); //存储请求行路径的queryString部分的多组key-value

        //分割路径
        String requestURI = path;
        int i = path.indexOf("?");
        if (i != -1) {
            requestURI = path.substring(0, i);
            String queryString = path.substring(i + 1);
            for (String kv : queryString.split("&")) {
                String[] kvParts = kv.split("=");
                String name = URLDecoder.decode(kvParts[0].trim(), "UTF-8");
                String value = URLDecoder.decode(kvParts[1].trim(), "UTF-8");
                parameters.put(name, value);
            }
        }
        int j = requestURI.indexOf('/', 1);// 找到第二个 / ，第二个 / 之前的是contextPath，之后的是servletPath
        String contextPath = "/";
        String servletPath = requestURI;
        if (j != -1) {
            contextPath = requestURI.substring(1, j); //不要第一个/
            servletPath = requestURI.substring(j);
        }

        String version = scanner.nextLine();// 协议版本信息

        //读取请求头
        Map<String, String> headers = new HashMap<>();
        List<Cookie> cookieList = new ArrayList<>();

        String headerLine;
        while (scanner.hasNextLine() && !(headerLine = scanner.nextLine().trim()).isEmpty()) {
            //解析请求头
            String[] parts = headerLine.split(":");
            String name = parts[0].trim().toLowerCase();
            String value = parts[1].trim();
            headers.put(name, value);

            //还要解析cookie
            if (name.equals("cookie")) {
                String[] kvParts = value.split(";");
                for (String kvPart : kvParts) {
                    if (kvPart.trim().isEmpty()) {
                        continue;
                    }

                    String[] split = kvPart.split("=");
                    String cookieName = split[0].trim();
                    String cookieValue = split[1].trim();
                    Cookie cookie = new Cookie(cookieName, cookieValue);
                    cookieList.add(cookie);
                }
            }
            //请求体就不解析了
        }
        //工厂模式，如果把这些都放在构造方法里面去写的话，构造方法会非常庞大，所以专门抽出一个方法来把该做的工作
        //做完，这样构造方法会简单一些
        return new Request(method, requestURI, contextPath, servletPath, parameters, headers, cookieList);
    }
}
