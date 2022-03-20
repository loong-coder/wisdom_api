package com.edu.intercept;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.ContentType;
import cn.hutool.json.JSONUtil;
import com.edu.constant.CacheConstant;
import com.edu.domain.response.BaseResponse;
import com.edu.shiro.JwtToken;
import com.google.common.net.HttpHeaders;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;



@Component
@Slf4j
public class JwtFilter extends BasicHttpAuthenticationFilter {


    /**
     * 拦截器的前置  最先执行的 这里只做了一个跨域设置
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        res.setHeader("Access-control-Allow-Origin", req.getHeader("Origin"));
        res.setHeader("Access-control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        res.setHeader("Access-control-Allow-Headers", req.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (req.getMethod().equals(RequestMethod.OPTIONS.name())) {
            res.setStatus(HttpStatus.OK.value());
            return false;
        }
        try {
            return super.preHandle(request, response);
        }catch (AuthenticationException e){
            BaseResponse<String> failed = BaseResponse.failed(e.getMessage());
            failed.setCode(HttpStatus.UNAUTHORIZED.value());
            response.getOutputStream().write(JSONUtil.toJsonStr(failed).getBytes(StandardCharsets.UTF_8));
            ((HttpServletResponse) response).setHeader(HttpHeaders.CONTENT_TYPE,ContentType.JSON.toString());
            e.printStackTrace();
        }
       return false;
    }

    /**
     * preHandle 执行完之后会执行这个方法
     * 再这个方法中 我们根据条件判断去去执行isLoginAttempt和executeLogin方法
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        /**
         * 先去调用 isLoginAttempt方法 字面意思就是是否尝试登陆 如果为true
         * 执行executeLogin方法
         */
        if (isLoginAttempt(request, response)) {
            return executeLogin(request, response);
        }else {
            throw new AuthenticationException("用户未登录");
        }
    }


    /**
     * 这里我们只是简单去做一个判断请求头中的token信息是否为空
     * 如果没有我们想要的请求头信息则直接返回false
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        String token = req.getHeader(CacheConstant.HEADER_AUTHORIZATION);
        return StrUtil.isNotBlank(token);
    }


    /**
     * 执行登陆
     * 因为已经判断token不为空了,所以直接执行登陆逻辑
     * 讲token放入JwtToken类中去
     * 然后getSubject方法是调用到了MyRealm的 执行方法  因为上面我是抛错的所有最后做个异常捕获就好了
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        String token = req.getHeader(CacheConstant.HEADER_AUTHORIZATION);
        JwtToken jwtToken = new JwtToken(token);
        getSubject(request, response).login(jwtToken);
        return true;
    }
}
