package com.edu.controller;

import com.edu.constant.PermissionEnum;
import com.edu.domain.dto.report.UserGrowthOperationDto;
import com.edu.domain.dto.user.*;
import com.edu.domain.request.BaseRequest;
import com.edu.domain.response.BaseResponse;
import com.edu.service.UserService;
import com.edu.util.UserContextUtils;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;


@Api(tags = "用户相关接口")
@RestController
@RequestMapping("user")
public class UserController {

    @Resource
    private UserService userService;

    @ApiOperation("用户注册,只有学生才需要注册 教师由管理员添加 管理员用户由脚本直接生产到数据库")
    @PutMapping("signIn")
    public BaseResponse<Boolean> signIn(@Valid @RequestBody BaseRequest<UserSignInDto> request){
        userService.signIn(request.getData());
        return BaseResponse.success(Boolean.TRUE);
    }

    @ApiOperation("用户注册,只有学生才需要注册 教师由管理员添加 管理员用户由脚本直接生产到数据库")
    @GetMapping("detail")
    public BaseResponse<UserDto> getUserDetail(){
        UserDto userDto = userService.queryUserById(UserContextUtils.getUserId());
        return BaseResponse.success(userDto);
    }

    @ApiOperation("用户列表")
    @PostMapping("list")
    @RequiresPermissions(value = {PermissionEnum.ALL})
    public BaseResponse<PageInfo<UserDto>> list(@Valid @RequestBody BaseRequest<UserQueryDto> request){
        PageInfo<UserDto> userDtoIPage = userService.list(request.getData());
        return BaseResponse.success(userDtoIPage);
    }


    @ApiOperation("用户登录")
    @PostMapping("login")
    public BaseResponse<UserTokenDto> login(@Valid @RequestBody BaseRequest<UserLoginDto> request){
        UserTokenDto userTokenDto = userService.login(request.getData());
        return BaseResponse.success(userTokenDto);
    }

    @ApiOperation("用户注销")
    @PostMapping("logout")
    public BaseResponse<Boolean> logout(){
        userService.logout();
        return BaseResponse.success(Boolean.TRUE);
    }

    @ApiOperation("编辑用户")
    @PostMapping("update")
    public BaseResponse<Boolean> update(@Valid @RequestBody BaseRequest<UserDto> request){
        userService.update(request.getData());
        return BaseResponse.success(Boolean.TRUE);
    }

    @ApiOperation("新增用户")
    @PostMapping("add")
    public BaseResponse<Boolean> add(@Valid @RequestBody BaseRequest<UserAddDto> request){
        userService.add(request.getData());
        return BaseResponse.success(Boolean.TRUE);
    }


    @ApiOperation("修改密码")
    @PostMapping("updatePassWord")
    public BaseResponse<Boolean> updatePassWord(@RequestBody BaseRequest<UserUpdatePassWordDto> request){
        userService.updatePassWord(request.getData());
        return BaseResponse.success(Boolean.TRUE);
    }

    @ApiOperation("查询所有的老师")
    @GetMapping("listAllTeachers")
    public BaseResponse<List<UserInputQueryDto>> listAllTeachers(){
        List<UserInputQueryDto> userDtos = userService.listAllTeachers();
        return BaseResponse.success(userDtos);
    }

    /**
     * 没有权限的回调接口
     * @return
     */
    @GetMapping("unauthorized")
    public BaseResponse<String> unauthorized() {
        return BaseResponse.failed("unauthorized");
    }

    @GetMapping("userGrowth")
    public BaseResponse<UserGrowthOperationDto> userGrowth(){
        UserGrowthOperationDto userGrowthOperationDto = userService.userGrowth();
        return BaseResponse.success(userGrowthOperationDto);
    }
}
