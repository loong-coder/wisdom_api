package com.edu.controller;


import com.edu.constant.PermissionEnum;
import com.edu.domain.dto.SysMenuDto;
import com.edu.domain.response.BaseResponse;
import com.edu.service.SysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "菜单相关接口")
@RestController
@RequestMapping("menu")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    @ApiOperation("查询所有菜单")
    @GetMapping("listAllMenu")
    @RequiresPermissions({ PermissionEnum.MENU})
    public BaseResponse< List<SysMenuDto>> listAllMenu(){
        return BaseResponse.success(sysMenuService.listAllMenu());
    }
}
