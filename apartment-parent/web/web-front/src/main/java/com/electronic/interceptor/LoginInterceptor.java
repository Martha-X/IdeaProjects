package com.electronic.interceptor;

import com.electronic.entity.UserInfo;
import com.electronic.result.Result;
import com.electronic.result.ResultCodeEnum;
import com.electronic.utils.WebUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取Session域中的userInfo对象
        UserInfo user = (UserInfo) request.getSession().getAttribute("USER");
        if (user == null) {
            // 证明还没有登录
            Result<String> result = Result.build("未登录", ResultCodeEnum.LOGIN_AUTH);
            // 将result写出到前端
            WebUtil.writeJSON(response, result);
            return false;
        }
        return true;
    }
}
