package com.octo.filter;

import com.octo.shiro.JwtToken;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


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
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        // 如果是预检请求，直接返回成功响应
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }
        // 获取Token
        String authorizationHeader = request.getHeader("Authorization");
        String token = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7); // 从 "Bearer " 后面开始截取
            // 封装成jwtToken进行登录认证
            JwtToken jwtToken = new JwtToken(token);
            try {
                // 委托 realm 进行登录认证
                getSubject(servletRequest, servletResponse).login(jwtToken);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                // 调用下面的方法向客户端返回错误信息
                JwtFilter.onLoginFail(servletResponse, e);
            }
        }
        return false;
    }

    /**
     * 登录失败时默认返回 401 状态码
     *
     * @param response
     * @throws IOException
     */
    private static void onLoginFail(ServletResponse response, Exception e) throws IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8"); // 设置响应内容类型为 JSON
        PrintWriter out = response.getWriter();
        out.write("{\"code\":401,\"message\":\"" + e.getMessage() + "\",\"data\":null}");
        out.flush();
        out.close();
    }
}
