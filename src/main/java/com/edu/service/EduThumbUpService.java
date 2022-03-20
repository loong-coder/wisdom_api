package com.edu.service;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.edu.domain.dto.thumb.EduThumbUpAddDto;
import com.edu.domain.dto.thumb.EduThumbUpDto;
import com.edu.domain.dto.thumb.EduThumbUpQueryDto;
import com.edu.domain.entity.EduThumbUp;
import com.edu.mapper.EduThumbUpMapper;
import com.edu.util.UserContextUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EduThumbUpService {

    @Resource
    private EduThumbUpMapper eduThumbUpMapper;


    public EduThumbUpDto queryThumbUp(EduThumbUpQueryDto eduThumbUpQueryDto){
        LambdaQueryWrapper<EduThumbUp> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(EduThumbUp::getBusinessId, eduThumbUpQueryDto.getBusinessId());
        queryWrapper.eq(EduThumbUp::getBusinessType, eduThumbUpQueryDto.getBusinessType());
        queryWrapper.eq(EduThumbUp::getThumbUp, Boolean.TRUE);
        queryWrapper.eq(EduThumbUp::getDeleted, Boolean.FALSE);
        Integer count = eduThumbUpMapper.selectCount(queryWrapper);

        LambdaQueryWrapper<EduThumbUp> currentEduThumbUp = Wrappers.lambdaQuery();
        currentEduThumbUp.eq(EduThumbUp::getBusinessId, eduThumbUpQueryDto.getBusinessId());
        currentEduThumbUp.eq(EduThumbUp::getBusinessType, eduThumbUpQueryDto.getBusinessType());
        currentEduThumbUp.eq(EduThumbUp::getThumbUp, Boolean.TRUE);
        currentEduThumbUp.eq(EduThumbUp::getUserId, UserContextUtils.getUserId());
        currentEduThumbUp.eq(EduThumbUp::getDeleted, Boolean.FALSE);

        EduThumbUp eduThumbUp = Optional.ofNullable(eduThumbUpMapper.selectOne(currentEduThumbUp)).orElseGet(EduThumbUp::new);
        EduThumbUpDto eduThumbUpDto = new EduThumbUpDto();
        eduThumbUpDto.setCount(count);
        eduThumbUpDto.setThumbUp(Optional.ofNullable(eduThumbUp.getThumbUp()).orElse(Boolean.FALSE));

        return eduThumbUpDto;
    }

    public void thumbUp(EduThumbUpAddDto eduThumbUpAddDto){
        LambdaQueryWrapper<EduThumbUp> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(EduThumbUp::getBusinessId, eduThumbUpAddDto.getBusinessId());
        queryWrapper.eq(EduThumbUp::getBusinessType, eduThumbUpAddDto.getBusinessType());
        queryWrapper.eq(EduThumbUp::getUserId, UserContextUtils.getUserId());
        queryWrapper.eq(EduThumbUp::getDeleted, Boolean.FALSE);
        List<EduThumbUp> eduThumbUps = eduThumbUpMapper.selectList(queryWrapper);
        if (!CollectionUtil.isEmpty(eduThumbUps)){
            EduThumbUp eduThumbUp = eduThumbUps.get(0);
            eduThumbUp.setThumbUp(eduThumbUpAddDto.getThumbUp());
            eduThumbUp.setUserId(UserContextUtils.getUserId());
            eduThumbUp.setUpdateUserId(UserContextUtils.getUserId());
            eduThumbUp.setUpdateDate(new Date());
            eduThumbUpMapper.updateById(eduThumbUp);
        }else {
            EduThumbUp eduThumbUp = new EduThumbUp();
            eduThumbUp.setBusinessId(eduThumbUpAddDto.getBusinessId());
            eduThumbUp.setBusinessType(eduThumbUpAddDto.getBusinessType());
            eduThumbUp.setThumbUp(eduThumbUpAddDto.getThumbUp());
            eduThumbUp.setUserId(UserContextUtils.getUserId());
            eduThumbUp.setDeleted(Boolean.FALSE);
            eduThumbUp.setCreateUserId(UserContextUtils.getUserId());
            eduThumbUp.setCreateDate(new Date());
            eduThumbUpMapper.insert(eduThumbUp);
        }
    }
}
