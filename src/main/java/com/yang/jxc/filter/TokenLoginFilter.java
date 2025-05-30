package com.yang.jxc.filter;

import com.alibaba.fastjson.JSON;
import com.yang.jxc.constant.Constant;
import com.yang.jxc.domain.entity.Admin;
import com.yang.jxc.utils.JWTUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public TokenLoginFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    /**
     * 具体认证的方法
     *
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        Admin Admin = null;  //username、password
        // 前后端分离的项目中我们提交的数据是JSON字符串。不是表单提交的
        try {
            String loginInfo = getRequestJSON(request);
            Admin = JSON.parseObject(loginInfo, Admin.class);  //转换对象
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(Admin.getUserName(), Admin.getPassword());
            return authenticationManager.authenticate(authenticationToken);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    //获取用户的json对象
    private String getRequestJSON(HttpServletRequest request) throws IOException {
        //BufferedReader流操作
        BufferedReader streamReader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String inputStr = null;
        while ((inputStr = streamReader.readLine()) != null) {
            sb.append(inputStr);
        }
        return sb.toString();
    }

    /**
     * 登录成功的方法  登陆成功响应的内容
     *
     * @param request
     * @param response
     * @param chain
     * @param authResult
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response
            , FilterChain chain, Authentication authResult) throws IOException, ServletException {
        // 生成Token信息
        Map<String, String> map = new HashMap<>();
        //存放信息传给前端
        map.put("username", authResult.getName());   //username唯一的
        // TODO 还可以存储当前用户具有的角色
        // 生成对应的Token信息
        String token = JWTUtils.getToken(map);
        System.out.println("打印登陆成功生成的token:" + token);
        // 需要把生成的Token信息响应给客户端
        response.addHeader("Authorization", Constant.SYS_TOKEN_PREFIX + token);  //token加了一个前缀
        response.addHeader("Access-Control-Expose-Headers", "Authorization");  //token放到响应信息中
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter writer = response.getWriter();
        //响应信息的封装
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("code", HttpServletResponse.SC_OK);
        resultMap.put("msg", "认证通过");
        writer.write(JSON.toJSONString(resultMap));
        System.out.println("登陆成功：" + JSON.toJSONString((resultMap)));
        System.out.println("登陆成功消息：" + JSON.toJSONString((resultMap)));
        writer.flush();  //刷新
        writer.close();
    }

    /**
     * 登录失败的方法   加一个TokenVerifyFilter过滤器
     *
     * @param request
     * @param response
     * @param failed
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");

        response.setStatus(HttpServletResponse.SC_OK);  //?
        PrintWriter writer = response.getWriter();
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("code", HttpServletResponse.SC_UNAUTHORIZED);
        resultMap.put("msg", "用户名或密码错误!");
        writer.write(JSON.toJSONString(resultMap));
        System.out.println("登陆失败：" + JSON.toJSONString(resultMap));
        writer.flush();
        writer.close();
    }
}
