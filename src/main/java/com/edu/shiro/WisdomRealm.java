package com.edu.shiro;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpStatus;
import com.edu.constant.CacheConstant;
import com.edu.constant.PermissionEnum;
import com.edu.constant.RoleEnum;
import com.edu.domain.dto.user.UserContextDto;
import com.edu.domain.entity.User;
import com.edu.error.BusinessException;
import com.edu.service.UserService;
import com.edu.util.JwtUtils;
import com.edu.util.UserContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class WisdomRealm extends AuthorizingRealm {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 只有当检测用户需要权限或者需要判定角色的时候才会走
     * */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("MyRealm doGetAuthorizationInfo() 方法授权 ");
        String token = principalCollection.toString();
        final String email = jwtUtils.getClaim(token, CacheConstant.CLAIM_EMAIL);
        if (StrUtil.isBlank(email)) {
            throw new BusinessException(HttpStatus.HTTP_UNAUTHORIZED, "token认证失败");
        }


        //查询当前
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //其实这里应该是查询当前用户的角色或者权限去的,意思就是将当前用户设置一个svip和vip角色
        //权限设置一级权限和耳机权限 正常来说应该是去读取数据库只添加当前用户的角色权限的
        User user = userService.queryUserByEmail(email);
        // 管理员角色
        switch (user.getRole()) {
            case 1:
                info.addRole(RoleEnum.ADMIN.name());
                info.addStringPermission(PermissionEnum.ALL);
                info.addStringPermission(PermissionEnum.MENU);

                break;
            case 2:
                info.addRole(RoleEnum.TEACHER.name());
                info.addStringPermission(PermissionEnum.TEACHER);
                info.addStringPermission(PermissionEnum.MENU);
                break;
            case 3:
                info.addRole(RoleEnum.STUDENT.name());
                info.addStringPermission(PermissionEnum.STUDENT);
                break;
        }
        return info;
    }

    /**
     * 默认使用此方法进行用户名正确与否验证, 如果没有权限注解的话就不会去走上面的方法只会走这个方法
     * 其实就是 过滤器传过来的token 然后进行 验证 authenticationToken.toString() 获取的就是
     * 你的token字符串,然后你在里面做逻辑验证就好了,没通过的话直接抛出异常就可以了
     * */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) {
        String jwt = (String) authenticationToken.getCredentials();
        if (!jwtUtils.verify(jwt)) {
            throw new AuthenticationException("Token认证失败");
        }

        return new SimpleAuthenticationInfo(authenticationToken.getPrincipal(), authenticationToken.getCredentials(), "MyRealm");
    }
}

