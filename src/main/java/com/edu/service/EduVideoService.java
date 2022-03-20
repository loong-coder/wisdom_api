package com.edu.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.edu.domain.dto.video.EduVideoDto;
import com.edu.domain.dto.video.EduVideoQueryDto;
import com.edu.domain.dto.video.EduVideoSaveOrUpdateDto;
import com.edu.domain.entity.EduVideo;
import com.edu.mapper.EduVideoMapper;
import com.edu.util.BeanConvertor;
import com.edu.util.PageUtil;
import com.edu.util.UserContextUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EduVideoService {

    @Resource
    private EduVideoMapper eduVideoMapper;

    public void saveOrUpdate(EduVideoSaveOrUpdateDto data) {
        EduVideo eduVideo = BeanConvertor.convert(data, EduVideo.class);
        if (Objects.isNull(data.getId())){
            eduVideo.setCreateUserId(UserContextUtils.getUserId());
            eduVideo.setDeleted(Boolean.FALSE);
            eduVideo.setCreateDate(new Date());
            eduVideoMapper.insert(eduVideo);
        }else {
            eduVideo.setUpdateUserId(UserContextUtils.getUserId());
            eduVideo.setUpdateDate(new Date());
            eduVideoMapper.updateById(eduVideo);
        }
    }

    public PageInfo<EduVideoDto> list(EduVideoQueryDto data) {
        LambdaQueryWrapper<EduVideo> queryWrapper = Wrappers.lambdaQuery();
        if (StrUtil.isNotBlank(data.getTitle())) {
            queryWrapper.like(EduVideo::getTitle, data.getTitle());
        }
        queryWrapper.eq(EduVideo::getCourseId, data.getCourseId());
        queryWrapper.eq(EduVideo::getDeleted, Boolean.FALSE);
        queryWrapper.orderByDesc(EduVideo::getCreateDate);
        if (Optional.ofNullable(data.getPageNum()).orElse(0) > 0
                && Optional.ofNullable(data.getSize()).orElse(0) > 0) {
            PageHelper.startPage(data.getPageNum(), data.getSize());
        }
        List<EduVideo> eduVideos = eduVideoMapper.selectList(queryWrapper);
        return PageUtil.pageInfoCopy(new PageInfo<>(eduVideos), EduVideoDto.class);
    }

    public void delete(Long videoId) {
        if (Objects.isNull(videoId)){
            return;
        }
        EduVideo eduVideo = new EduVideo();
        eduVideo.setId(videoId);
        eduVideo.setDeleted(Boolean.TRUE);
        eduVideo.setUpdateDate(new Date());
        eduVideo.setUpdateUserId(UserContextUtils.getUserId());
        eduVideoMapper.updateById(eduVideo);
    }

    public List<EduVideoDto> listVideoByCourseId(Long courseId) {
        LambdaQueryWrapper<EduVideo> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(EduVideo::getCourseId, courseId);
        queryWrapper.eq(EduVideo::getDeleted, Boolean.FALSE);
        List<EduVideo> eduVideos = eduVideoMapper.selectList(queryWrapper);
        return BeanConvertor.convertCollection(eduVideos, EduVideoDto.class);
    }
}
