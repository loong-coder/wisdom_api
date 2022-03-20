package com.edu.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.edu.domain.dto.SysMenuDto;
import com.edu.domain.dto.user.UserDto;
import com.edu.domain.entity.SysMenu;
import com.edu.mapper.SysMenuMapper;
import com.edu.util.BeanConvertor;
import com.edu.util.UserContextUtils;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SysMenuService {

    @Resource
    private SysMenuMapper sysMenuMapper;

    @Resource
    private UserService userService;

    public List<SysMenuDto> listAllMenu(){
        LambdaQueryWrapper<SysMenu> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(SysMenu::getDeleted, Boolean.FALSE);
        queryWrapper.eq(SysMenu::getDisable, Boolean.FALSE);
        queryWrapper.eq(SysMenu::getLayer, 1);
        List<SysMenu> sysMenus = sysMenuMapper.selectList(queryWrapper);
        List<Long> parentId = sysMenus.stream().map(SysMenu::getId).distinct().collect(Collectors.toList());

        UserDto userDto = userService.queryUserById(UserContextUtils.getUserId());
        // 教师角色只可以看到内容管理菜单
        if (userDto.getRole() == 2){
            sysMenus = sysMenus.stream().filter(sysMenu -> sysMenu.getId() == 3).collect(Collectors.toList());
            parentId = Collections.singletonList(3L);
        }

        LambdaQueryWrapper<SysMenu> querySubWrapper = Wrappers.lambdaQuery();
        querySubWrapper.eq(SysMenu::getDeleted, Boolean.FALSE);
        querySubWrapper.eq(SysMenu::getDisable, Boolean.FALSE);
        querySubWrapper.eq(SysMenu::getLayer, 2);
        querySubWrapper.in(SysMenu::getParentId, parentId);
        List<SysMenu> sysSubMenus = sysMenuMapper.selectList(querySubWrapper);
        Map<Long, List<SysMenu>> groupSub = sysSubMenus.stream().collect(Collectors.groupingBy(SysMenu::getParentId));

        List<SysMenuDto> sysMenuDtoList = sysMenus.stream().map(sysMenu -> {
            SysMenuDto sysMenuDto = BeanConvertor.convert(sysMenu, SysMenuDto.class);
            sysMenuDto.setIndex(Long.toString(sysMenu.getId()));
            List<SysMenu> subMenus = Optional.ofNullable(groupSub.get(sysMenu.getId())).orElseGet(Lists::newArrayList);
            List<SysMenuDto> sysMenuDtos = BeanConvertor.convertCollection(subMenus, SysMenuDto.class);
            for (SysMenuDto menuDto : sysMenuDtos) {
                menuDto.setIndex(menuDto.getParentId() + "-" + menuDto.getId());
            }
            sysMenuDto.setChildren(sysMenuDtos);
            return sysMenuDto;
        }).collect(Collectors.toList());
        return sysMenuDtoList;
    }


}
