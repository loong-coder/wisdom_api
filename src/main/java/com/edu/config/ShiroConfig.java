package com.edu.config;

import com.edu.intercept.JwtFilter;
import com.edu.shiro.WisdomRealm;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;



@Configuration
@Component
public class ShiroConfig {

    /**
     * 注入 securityManager
     */
    @Bean("securityManager")
    public DefaultWebSecurityManager securityManager(WisdomRealm wisdomRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置自定义 realm.
        securityManager.setRealm(wisdomRealm);
        // 关闭shiro自带的session
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);
        return securityManager;
    }


    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();

        //添加自己的过滤器 并且取名为filter
        Map<String, Filter> filterMap = new LinkedHashMap<>();
        //设置自定义的JWT过滤器
//        JwtFilter jwtFilter = SpringContextUtil.getBean(JwtFilter.class);
        filterMap.put("jwt", new JwtFilter());
        factoryBean.setFilters(filterMap);
        factoryBean.setSecurityManager(securityManager);

        Map<String, String> filterRuleMap = new LinkedHashMap<>();


        //设置需要通过过滤器的请求 /**意思是 所有请求接口都通过自定义的jwt过滤器
        //anon的意思是 不需要走这一套逻辑 自己配置就好了

        // 用户注册 等于 获取验证码
        filterRuleMap.put("/user/login","anon");
        filterRuleMap.put("/user/signIn","anon");
        filterRuleMap.put("/file","anon");
        filterRuleMap.put("/captcha","anon");
        filterRuleMap.put("/course/pageIndexQuery","anon");
        filterRuleMap.put("/course/pageCourseList","anon");
        filterRuleMap.put("/course/queryCourseById/**","anon");
        filterRuleMap.put("/course/viewCountIncr/**","anon");
        filterRuleMap.put("/comment/*","anon");
        filterRuleMap.put("/subject/queryAllFirstLevel","anon");
        filterRuleMap.put("/video/listVideoByCourseId/**","anon");

        // Swagger 文档相关接口  不需要授权
        filterRuleMap.put("/doc.html","anon");
        filterRuleMap.put("/swagger/**","anon");
        filterRuleMap.put("/webjars/**","anon");
        filterRuleMap.put("/swagger-ui.html","anon");
        filterRuleMap.put("/swagger-resources/**","anon");
        filterRuleMap.put("/favicon.ico","anon");
        filterRuleMap.put("/v3/**","anon");

        filterRuleMap.put("/**", "jwt");
        factoryBean.setFilterChainDefinitionMap(filterRuleMap);
        return factoryBean;
    }


    /**
     * 添加注解支持
     * */
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){

        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator=new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") DefaultWebSecurityManager securityManager){

        AuthorizationAttributeSourceAdvisor advisor=new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }
}
