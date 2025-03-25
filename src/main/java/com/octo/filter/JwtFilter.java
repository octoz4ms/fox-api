package com.octo.filter;

import cn.hutool.json.JSONObject;
import com.octo.shiro.JwtToken;
import com.octo.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.http.HttpStatus;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;


/**
 * @author zms
 */
@Slf4j
public class JwtFilter extends BasicHttpAuthenticationFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        // 请求头获取 Token
        String token = getAuthzHeader(request);
        if (token == null || !token.startsWith("Bearer ")) {
            return false;
        }

        // 验证 Token 有效性
        String jwt = token.replace("Bearer ", "");
        boolean validated = JwtUtil.validateToken(jwt);
        if (!validated) {
            return false;
        }

        // 构建 Shiro 认证 Token 并登录
        JwtToken jwtToken = new JwtToken(jwt);
        Subject subject = getSubject(request, response);
        subject.login(jwtToken); // 触发 Realm 的 doGetAuthenticationInfo

        // 用户信息存入 request
        String username = JwtUtil.getUsername(jwt);
        request.setAttribute("username", username);
        return true;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        httpResponse.setContentType("application/json;charset=utf-8");

        JSONObject json = new JSONObject();
        json.putOnce("code", 401);
        json.putOnce("message", "无效或过期的 Token");
        httpResponse.getWriter().write(json.toString());

        return false; // 中断请求
    }
//    @Override
//    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
//        return false;
//    }
//
//    @Override
//    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws IOException {
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        HttpServletResponse response = (HttpServletResponse) servletResponse;
//        // 如果是预检请求，直接返回成功响应
//        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
//            response.setStatus(HttpServletResponse.SC_OK);
//            return true;
//        }
//        // 获取Token
//        String authorizationHeader = request.getHeader("Authorization");
//        String token = null;
//        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//            token = authorizationHeader.substring(7); // 从 "Bearer " 后面开始截取
//            // 封装成jwtToken进行登录认证
//            JwtToken jwtToken = new JwtToken(token);
//            try {
//                // 委托 realm 进行登录认证
//                getSubject(servletRequest, servletResponse).login(jwtToken);
//                return true;
//            } catch (Exception e) {
//                log.error(e.getMessage());
//                // 调用下面的方法向客户端返回错误信息
//                JwtFilter.onLoginFail(servletResponse, e);
//            }
//        }
//        log.error("---------------------");
//        return false;
//    }
//
//    /**
//     * 登录失败时默认返回 401 状态码
//     */
//    private static void onLoginFail(ServletResponse response, Exception e) throws IOException {
//        HttpServletResponse httpResponse = (HttpServletResponse) response;
//        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        response.setContentType("application/json;charset=UTF-8"); // 设置响应内容类型为 JSON
//        PrintWriter out = response.getWriter();
//        out.write("{\"code\":401,\"message\":\"" + e.getMessage() + "\",\"data\":null}");
//        out.flush();
//        out.close();
//    }
}
