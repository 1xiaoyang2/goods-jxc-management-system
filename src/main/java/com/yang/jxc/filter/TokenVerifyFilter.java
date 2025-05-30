package com.yang.jxc.filter;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.yang.jxc.constant.Constant;
import com.yang.jxc.utils.JWTUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

//在config/SpringSecurityConfig 配置生效
//用户携带token信息的校验

public class TokenVerifyFilter extends BasicAuthenticationFilter {
    public TokenVerifyFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    /**
     * 校验提交的Token是否合法的方法
     *
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 获取请求携带的Token信息
        String header = request.getHeader("Authorization");
        String requestURI = request.getRequestURI();
        String contextPath = request.getContextPath();
        String path = requestURI.replace(contextPath, "");
        //配置放过请求
        List<String> msgs = Arrays.asList("/doc.html", "/webjars", "/v2", "/v3",
                "/favicon.ico", "swagger-resources",
                "/swagger-ui.html,", "admin/login",
                "swagger-ui.html", "/META-INF/resources/"
        );
        for (String p : msgs) {
            if (path.contains(p)) {
                // 放过请求
                chain.doFilter(request, response);
                return;
            }
        }

        if (header != null && header.startsWith(Constant.SYS_TOKEN_PREFIX)) {
            // 传递了Token信息。同时有我们添加的对应的前缀
            // 1.获取到正常的token
            String token = header.replace(Constant.SYS_TOKEN_PREFIX, "");
            // 2.校验token信息是否合法
            DecodedJWT verify = JWTUtils.verify(token);
            if (verify == null) {
                responseLogin(response);  //校验失败
            }
            // 走到这儿说明是正常
            // 获取当前登录的账号信息
            String userName = verify.getClaim("username").asString();
            // 放过请求 后续的控制器可能需要相关的权限
            List<GrantedAuthority> list = new ArrayList<>();
            //SimpleGrantedAuthority表示用户验证通过后被授予的权限
            list.add(new SimpleGrantedAuthority("ADMIN"));
            // 根据账号获取相关的权限
            UsernamePasswordAuthenticationToken authenticationToken
                    = new UsernamePasswordAuthenticationToken(userName, "", list);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            // 放过请求
            chain.doFilter(request, response);

        } else {
            // 没有携带Token或者是非法的请求
            responseLogin(response);
        }
    }

    //校验失败的提示信息
    private void responseLogin(HttpServletResponse response) throws IOException {
        // 说明校验失败 -- 给用户提示请先登录
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);  //登陆状态 登陆错误
        PrintWriter writer = response.getWriter();
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("code", HttpServletResponse.SC_FORBIDDEN);
        resultMap.put("msg", "请先登录!");
        writer.write(JSON.toJSONString(resultMap));
        writer.flush();
        writer.close();
    }
}
