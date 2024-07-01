package com.octo.filter;

import cn.hutool.core.util.StrUtil;
import com.octo.shiro.JwtToken;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @author zms
 */
public class JwtFilter extends BasicHttpAuthenticationFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws IOException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String jwt = request.getHeader("Authorization");
        if (StrUtil.isEmpty(jwt)) {
            JwtFilter.onLoginFail(servletResponse);
            return false;
        }
        jwt = jwt.substring(7);
        JwtToken jwtToken = new JwtToken(jwt);
        try {
            // 委托 realm 进行登录认证
            getSubject(servletRequest, servletResponse).login(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            // 调用下面的方法向客户端返回错误信息
            JwtFilter.onLoginFail(servletResponse);
            return false;
        }
        return true;
    }

    /**
     * 登录失败时默认返回 401 状态码
     *
     * @param response
     * @throws IOException
     */
    private static void onLoginFail(ServletResponse response) throws IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpResponse.getWriter().write("login error");
    }
}
