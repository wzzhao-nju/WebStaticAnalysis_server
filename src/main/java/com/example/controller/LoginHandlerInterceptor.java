package com.example.controller;

import com.example.entity.LoginInfo;
import com.example.inter.LoginInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
public class LoginHandlerInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(LoginHandlerInterceptor.class);

    @Autowired
    private LoginInfoRepository loginInfoRepository;

    public LoginHandlerInterceptor(LoginInfoRepository loginInfoRepository){
        this.loginInfoRepository = loginInfoRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){

        Integer uid = (Integer)request.getSession().getAttribute("uid");
        log.info(String.format("Receive a request (URI = %s, uid = %d)", request.getRequestURI(), uid));

        response.setCharacterEncoding("UTF-8");
        try {
            if(uid == null) {
                response.setStatus(430);
                response.getWriter().append("请先登录");
                log.info("Request denied because uid is null");
                return false;
            }
            if(uid == -1)
                return true;
            List<LoginInfo> loginInfoList = loginInfoRepository.findByUid(uid);
            if(loginInfoList.size() <= 0 || loginInfoList.get(0).getSessionId().equals(request.getSession().getId()))
                return true;
            else{
                response.setStatus(430);
                response.getWriter().append("登录已过期, 或者已在其他地方登录");
                log.info("Request denied becasue the login has expired");
                return false;
            }
        }catch (IOException e) {
            //这里不应该被执行
            e.printStackTrace();
            return false;
        }
    }
}
