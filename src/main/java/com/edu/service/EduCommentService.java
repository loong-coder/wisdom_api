package com.edu.service;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.edu.domain.dto.comment.EduCommentAddDto;
import com.edu.domain.dto.comment.EduCommentDto;
import com.edu.domain.dto.comment.EduCommentQueryDto;
import com.edu.domain.entity.EduComment;
import com.edu.domain.entity.User;
import com.edu.mapper.EduCommentMapper;
import com.edu.util.PageUtil;
import com.edu.util.UserContextUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class EduCommentService {

    @Autowired
    private EduCommentMapper eduCommentMapper;

    @Autowired
    private UserService userService;

    public PageInfo<EduCommentDto> queryComment(EduCommentQueryDto eduCommentQueryDto) {
        LambdaQueryWrapper<EduComment> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(EduComment::getCourseId, eduCommentQueryDto.getCourseId());
        queryWrapper.eq(EduComment::getDeleted, Boolean.FALSE);
        queryWrapper.orderByDesc(EduComment::getCreateDate);

        if (Optional.ofNullable(eduCommentQueryDto.getPageNum()).orElse(0) > 0
                && Optional.ofNullable(eduCommentQueryDto.getSize()).orElse(0) > 0) {
            PageHelper.startPage(eduCommentQueryDto.getPageNum(), eduCommentQueryDto.getSize());
        }
        List<EduComment> eduComments = Optional.ofNullable(eduCommentMapper.selectList(queryWrapper)).orElseGet(Lists::newArrayList);
        if (CollectionUtils.isEmpty(eduComments)){
            return null;
        }
        List<Long> userIds = Optional.ofNullable(eduComments).orElseGet(Lists::newArrayList)
                .stream()
                .filter(dto -> !Objects.isNull(dto.getUserId()))
                .map(dto -> dto.getUserId())
                .distinct()
                .collect(Collectors.toList());
        Map<Long, User> groupUser = Optional.ofNullable(userService.listUserByIds(userIds)).orElse(new ArrayList<>())
                .stream()
                .collect(Collectors.toMap(User::getId, Function.identity()));

        PageInfo<EduComment> pageInfo = new PageInfo(eduComments);
        PageInfo<EduCommentDto> eduCommentDtoPageInfo = PageUtil.pageInfoCopy(pageInfo, EduCommentDto.class);
        for (EduCommentDto eduCommentDto : eduCommentDtoPageInfo.getList()) {
            User user = Optional.ofNullable(groupUser.get(eduCommentDto.getUserId())).orElse(new User());
            eduCommentDto.setUserName(Optional.ofNullable(user.getName()).orElse("匿名"));
        }
        return eduCommentDtoPageInfo;
    }

    public void add(EduCommentAddDto data) {
        EduComment eduComment = new EduComment();
        eduComment.setComment(data.getComment());
        eduComment.setCourseId(data.getCourseId());
        Long userId = Optional.ofNullable(UserContextUtils.getUserId()).orElse(-1L);
        eduComment.setUserId(userId);
        eduComment.setCreateUserId(userId);
        eduComment.setCreateDate(new Date());
        eduComment.setDeleted(Boolean.FALSE);
        eduCommentMapper.insert(eduComment);
    }
}
