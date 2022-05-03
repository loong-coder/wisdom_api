package com.edu.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.edu.domain.dto.collect.EduCollectAddOrUpdateDto;
import com.edu.domain.entity.EduCollect;
import com.edu.mapper.EduCollectMapper;
import com.edu.util.UserContextUtils;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EduCollectService {

    @Resource
    private EduCollectMapper eduCollectMapper;


    public void addOrUpdateCollect(EduCollectAddOrUpdateDto data) {
        Long userId = UserContextUtils.getUserId();
        LambdaQueryWrapper<EduCollect> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(EduCollect::getUserId,userId);
        queryWrapper.eq(EduCollect::getCourseId, data.getCourseId());

        EduCollect eduCollectDb = eduCollectMapper.selectOne(queryWrapper);
        if (Objects.isNull(eduCollectDb)){
            EduCollect eduCollect = new EduCollect();
            eduCollect.setUserId(userId);
            eduCollect.setCourseId(data.getCourseId());
            eduCollect.setIsCollect(data.getIsCollect());
            eduCollect.setCreateUserId(userId);
            eduCollect.setCreateDate(new Date());
            eduCollect.setDeleted(Boolean.FALSE);
            eduCollectMapper.insert(eduCollect);
        }else {
            eduCollectDb.setIsCollect(data.getIsCollect());
            eduCollectDb.setUpdateDate(new Date());
            eduCollectDb.setUpdateUserId(userId);
            eduCollectMapper.updateById(eduCollectDb);
        }

    }

    public Boolean queryCollectInfo(Long courseId) {
        Long userId = UserContextUtils.getUserId();
        LambdaQueryWrapper<EduCollect> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(EduCollect::getUserId,userId);
        queryWrapper.eq(EduCollect::getCourseId, courseId);
        queryWrapper.eq(EduCollect::getDeleted, Boolean.FALSE);
        Boolean isCollect = Optional.ofNullable(eduCollectMapper.selectOne(queryWrapper)).map(EduCollect::getIsCollect)
                .orElse(Boolean.FALSE);
        return isCollect;
    }

    public List<Long> queryCollectByUserId(Long userId) {
        LambdaQueryWrapper<EduCollect> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(EduCollect::getUserId,userId);
        queryWrapper.eq(EduCollect::getIsCollect, Boolean.TRUE);
        queryWrapper.eq(EduCollect::getDeleted, Boolean.FALSE);

        List<Long> courseIds = Optional.ofNullable(eduCollectMapper.selectList(queryWrapper)).orElseGet(Lists::newArrayList)
                .stream().map(EduCollect::getCourseId).collect(Collectors.toList());
        return courseIds;

    }
}
