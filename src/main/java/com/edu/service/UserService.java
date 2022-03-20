package com.edu.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpStatus;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.edu.cache.CacheManager;
import com.edu.constant.RoleEnum;
import com.edu.domain.dto.report.UserGrowthDto;
import com.edu.domain.dto.report.UserGrowthOperationDto;
import com.edu.domain.dto.user.*;
import com.edu.domain.entity.User;
import com.edu.error.BusinessException;
import com.edu.mapper.UserMapper;
import com.edu.util.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private JwtUtils jwtUtils;


    @Resource
    private CacheManager<String> cacheManager;

    public void signIn(UserSignInDto userInfoDto) {
        verifyAccount(userInfoDto.getEmail());
        User user = new User();
        BeanUtil.copyProperties(userInfoDto, user);
        String pw = Md5Utils.generatePassWdHash(userInfoDto.getPassWord());
        user.setPassWord(pw);
        user.setRole(RoleEnum.STUDENT.Value());
        user.setDisable(Boolean.FALSE);
        user.setDeleted(Boolean.FALSE);
        Date currDate = new Date();
        user.setRegisterDate(currDate);
        user.setCreateDate(currDate);
        userMapper.insert(user);
    }

    private void verifyAccount(String email) {
        User userDb = queryUserByEmail(email);
        if (!Objects.isNull(userDb)) {
            throw new BusinessException(400, "用户已经注册!");
        }
    }


    public User queryUserByEmail(String email) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail, email);
        List<User> users = Optional.ofNullable(userMapper.selectList(queryWrapper))
                .orElse(new ArrayList<>());

        return users.stream().findFirst().orElse(null);
    }

    public UserTokenDto login(UserLoginDto userLoginDto) {
        String verificationCodeCache = cacheManager.get(userLoginDto.getRandomCode());
        // 忽视验证码大小写
        if (!StrUtil.equalsIgnoreCase(userLoginDto.getCode(), verificationCodeCache)) {
            throw new BusinessException(HttpStatus.HTTP_UNAUTHORIZED, "验证码错误");
        }
        String email = userLoginDto.getEmail();
        String password = Md5Utils.generatePassWdHash(userLoginDto.getPassWord());
        User user = queryUserByEmail(email);
        if (Objects.isNull(user) || !StrUtil.equals(user.getPassWord(), password)) {
            throw new BusinessException(HttpStatus.HTTP_UNAUTHORIZED, "账号或密码错误");
        }
        user.setLastLoginDate(new Date());
        updateById(user);

        String jwt = jwtUtils.generateJwt(Long.toString(user.getId()), email);
        UserTokenDto userTokenDto = new UserTokenDto();
        userTokenDto.setToken(jwt);
        return userTokenDto;
    }

    public PageInfo<UserDto> list(UserQueryDto userQueryDto) {
        LambdaQueryWrapper<User> userLambdaQueryWrapper = Wrappers.lambdaQuery();
        userLambdaQueryWrapper.eq(User::getDeleted, Boolean.FALSE);
        if (!StrUtil.isEmpty(userQueryDto.getName())) {
            userLambdaQueryWrapper.like(User::getName, userQueryDto.getName());
        }
        if (!StrUtil.isEmpty(userQueryDto.getEmail())) {
            userLambdaQueryWrapper.like(User::getEmail, userQueryDto.getEmail());
        }

        if (Optional.ofNullable(userQueryDto.getPageNum()).orElse(0) > 0
                && Optional.ofNullable(userQueryDto.getSize()).orElse(0) > 0) {
            PageHelper.startPage(userQueryDto.getPageNum(), userQueryDto.getSize());
        }


        List<User> result = userMapper.selectList(userLambdaQueryWrapper);
        PageInfo<User> userInfo = new PageInfo<>(result);
        PageInfo<UserDto> pageInfo = PageUtil.pageInfoCopy(userInfo, UserDto.class);
        return pageInfo;
    }

    public void updateById(User user) {
        this.userMapper.updateById(user);
    }

    public void update(UserDto userDto) {
        User user = new User();
        BeanUtil.copyProperties(userDto, user);

        user.setUpdateDate(new Date());
        this.userMapper.updateById(user);
    }

    public void add(UserAddDto userDto) {
        verifyAccount(userDto.getEmail());
        User user = new User();
        BeanUtil.copyProperties(userDto, user);
        String pw = Md5Utils.generatePassWdHash(userDto.getPassWord());
        user.setPassWord(pw);
        user.setRole(userDto.getRole());
        user.setDisable(Boolean.FALSE);
        user.setDeleted(Boolean.FALSE);
        Date currDate = new Date();
        user.setRegisterDate(currDate);
        user.setCreateDate(currDate);
        userMapper.insert(user);
    }

    public UserDto queryUserById(Long userId) {
        User user = selectUserById(userId);
        return BeanConvertor.convert(user, UserDto.class);
    }

    private User selectUserById(Long userId) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getId, userId);
        queryWrapper.eq(User::getDeleted, Boolean.FALSE);
        User user = userMapper.selectOne(queryWrapper);
        return user;
    }

    public void updatePassWord(UserUpdatePassWordDto userUpdateDto) {
        if (Objects.isNull(userUpdateDto.getUserId())) {
            userUpdateDto.setUserId(UserContextUtils.getUserId());
        }
        User user = selectUserById(userUpdateDto.getUserId());
        if (!StrUtil.equals(Md5Utils.generatePassWdHash(userUpdateDto.getCurrentPass()), user.getPassWord())) {
            throw new BusinessException(HttpStatus.HTTP_BAD_REQUEST, "旧密码错误");
        }
        user.setPassWord(Md5Utils.generatePassWdHash(userUpdateDto.getPassword()));
        user.setUpdateDate(new Date());
        userMapper.updateById(user);
    }

    public void logout() {
        jwtUtils.logout();
    }

    public List<User> listUserByIds(List<Long> userIds) {
        LambdaQueryWrapper<User> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.in(User::getId, userIds);
        queryWrapper.eq(User::getDeleted, Boolean.FALSE);
        return userMapper.selectList(queryWrapper);
    }

    public List<UserInputQueryDto> listAllTeachers() {
        LambdaQueryWrapper<User> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(User::getRole, 2);
        queryWrapper.in(User::getDeleted, Boolean.FALSE);
        List<User> userList = Optional.ofNullable(userMapper.selectList(queryWrapper)).orElse(new ArrayList<>());

        return userList.stream().map(user -> {
            UserInputQueryDto userInputQueryDto = new UserInputQueryDto();
            userInputQueryDto.setId(user.getId());
            userInputQueryDto.setValue(user.getName());
            return userInputQueryDto;
        }).collect(Collectors.toList());
    }

    public UserGrowthOperationDto userGrowth() {
        List<UserGrowthDto> userGrowthDtos = Optional.ofNullable(userMapper.userGrowth()).orElseGet(Lists::newArrayList);
        List<String> xAxis = Lists.newArrayList();
        List<Long> series = Lists.newArrayList();
        for (UserGrowthDto userGrowthDto : userGrowthDtos) {
            xAxis.add(userGrowthDto.getTime());
            series.add(userGrowthDto.getCount());
        }
        UserGrowthOperationDto userGrowthOperationDto = new UserGrowthOperationDto();
        userGrowthOperationDto.setSeries(series);
        userGrowthOperationDto.setTimes(xAxis);
        return userGrowthOperationDto;
    }
}
