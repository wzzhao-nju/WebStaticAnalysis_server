package com.example.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@CrossOrigin(origins = "*", allowCredentials = "true")
@WebFilter(filterName = "CORSFilter", urlPatterns = "/*")
public class CORSFilter implements Filter {
    @Override
    public void destroy() {
    }
    /**
     * 此过滤器只是处理跨域问题
     * @param servletRequest
     * @param servletResponse
     * @param chain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws ServletException, IOException {
        Logger log = LoggerFactory.getLogger(CORSFilter.class);
        log.info("filter\n");
        System.out.print("filter\n");
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        String origin = req.getHeader("Origin");
        System.out.print("Origin: " + origin+"\n");
        if(origin == null) {
            origin = req.getHeader("Referer");
            System.out.print("Referer: " + origin + "\n");
        }
        resp.setHeader("Access-Control-Allow-Origin", origin);//这里不能写*，*代表接受所有域名访问，如写*则下面一行代码无效。谨记
        resp.setHeader("Access-Control-Allow-Credentials", "true");//true代表允许携带cookie
        chain.doFilter(servletRequest,servletResponse);
    }
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
}
