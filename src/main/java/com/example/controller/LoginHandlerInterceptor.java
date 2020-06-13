package com.example.controller;

import com.example.entity.LoginInfo;
import com.example.inter.LoginInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Component
public class LoginHandlerInterceptor implements HandlerInterceptor {

    @Autowired
    private LoginInfoRepository loginInfoRepository;

    public LoginHandlerInterceptor(LoginInfoRepository loginInfoRepository){
        this.loginInfoRepository = loginInfoRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        Object uid = request.getSession().getAttribute("uid");
        if(uid == null)
            return false;
        if((Integer)uid == -1)
            return true;
        List<LoginInfo> loginInfoList = loginInfoRepository.findByUid((Integer) uid);
        return loginInfoList.size() <= 0 || loginInfoList.get(0).getSessionId().equals(request.getSession().getId());
    }
}
