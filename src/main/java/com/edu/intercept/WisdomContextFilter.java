package com.edu.intercept;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.edu.constant.CacheConstant;
import com.edu.domain.dto.user.UserContextDto;
import com.edu.util.JwtUtils;
import com.edu.util.UserContextUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 添加链路id 追踪用户日志行为
 * 删除用户上下文
 */
@Component
public class WisdomContextFilter implements Filter {

    private static final String TRACE_ID = "x-wisdom-traceId";

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String traceId = ((HttpServletRequest) servletRequest).getHeader(TRACE_ID);
        if (StrUtil.isEmpty(traceId)){
            traceId = Long.toString(IdWorker.getId());
        }

        String jwt = ((HttpServletRequest) servletRequest).getHeader(CacheConstant.HEADER_AUTHORIZATION);
        if (StrUtil.isNotBlank(jwt)){
            Long userId = Long.valueOf(jwtUtils.getClaim(jwt, CacheConstant.CLAIM_USER_ID));
            String email = jwtUtils.getClaim(jwt, CacheConstant.CLAIM_EMAIL);
            UserContextDto userContextDto = UserContextDto.builder()
                    .id(userId)
                    .email(email)
                    .build();
            UserContextUtils.setUserContext(userContextDto);
        }
        MDC.put(TRACE_ID, traceId);
        ((HttpServletResponse) servletResponse).addHeader(TRACE_ID, traceId);
        try {
            filterChain.doFilter(servletRequest,servletResponse);
        }finally {
            MDC.remove(TRACE_ID);
            UserContextUtils.removeUserContext();
        }

    }
}
