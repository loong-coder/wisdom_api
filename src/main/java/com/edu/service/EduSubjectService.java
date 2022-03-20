package com.edu.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.edu.domain.dto.subject.SubjectCascaderDto;
import com.edu.domain.dto.subject.SubjectDto;
import com.edu.domain.dto.subject.SubjectQueryDto;
import com.edu.domain.dto.subject.SubjectSaveOrUpdateDto;
import com.edu.domain.entity.EduSubject;
import com.edu.domain.entity.User;
import com.edu.mapper.EduSubjectMapper;
import com.edu.util.BeanConvertor;
import com.edu.util.PageUtil;
import com.edu.util.UserContextUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class EduSubjectService {

    @Resource
    private EduSubjectMapper eduSubjectMapper;

    @Resource
    private UserService userService;

    public PageInfo<SubjectDto> list(SubjectQueryDto subjectQueryDto) {
        LambdaQueryWrapper<EduSubject> queryWrapper = Wrappers.lambdaQuery();
        if (StrUtil.isNotBlank(subjectQueryDto.getTitle())) {
            queryWrapper.like(EduSubject::getTitle, subjectQueryDto.getTitle());
        }
        queryWrapper.eq(EduSubject::getParentId, -1);
        queryWrapper.eq(EduSubject::getDeleted, Boolean.FALSE);
        queryWrapper.orderByAsc(Lists.newArrayList(EduSubject::getSort, EduSubject::getCreateDate));
        if (Optional.ofNullable(subjectQueryDto.getPageNum()).orElse(0) > 0
                && Optional.ofNullable(subjectQueryDto.getSize()).orElse(0) > 0) {
            PageHelper.startPage(subjectQueryDto.getPageNum(), subjectQueryDto.getSize());
        }
        List<EduSubject> eduSubjects = eduSubjectMapper.selectList(queryWrapper);
        PageInfo<EduSubject> eduSubjectPageInfo = new PageInfo<>(eduSubjects);
        PageInfo<SubjectDto> pageInfo = PageUtil.pageInfoCopy(eduSubjectPageInfo, SubjectDto.class);
        fillUserName(pageInfo.getList());
        fillHasChildren(pageInfo.getList());
        return pageInfo;
    }


    public List<SubjectDto> querySubjectByParentId(Long parentId) {
        List<EduSubject> eduSubjects = querySubjectByParentIds(Collections.singletonList(parentId));
        List<SubjectDto> subjectDtos = BeanConvertor.convertCollection(eduSubjects, SubjectDto.class);

        EduSubject eduSubject = eduSubjectMapper.selectById(parentId);
        for (SubjectDto subjectDto : subjectDtos) {
            subjectDto.setParentName(eduSubject.getTitle());
        }
        fillUserName(subjectDtos);
        return subjectDtos;
    }

    private List<EduSubject> querySubjectByParentIds(List<Long> ids) {
        LambdaQueryWrapper<EduSubject> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.in(EduSubject::getParentId, ids);
        queryWrapper.eq(EduSubject::getDeleted, Boolean.FALSE);
        queryWrapper.orderByAsc(Lists.newArrayList(EduSubject::getSort, EduSubject::getCreateDate));

        List<EduSubject> eduSubjects = eduSubjectMapper.selectList(queryWrapper);
        return eduSubjects;
    }

    private void fillUserName(List<SubjectDto> list) {
        if (CollectionUtil.isEmpty(list)) {
            return;
        }
        List<Long> userIds = Optional.ofNullable(list).orElseGet(Lists::newArrayList)
                .stream()
                .flatMap(dto -> {
                    List<Long> idList = Lists.newArrayList();
                    Long createUserId = dto.getCreateUserId();
                    idList.add(createUserId);
                    Long updateUserId = dto.getUpdateUserId();
                    if (Objects.isNull(updateUserId)) {
                        idList.add(updateUserId);
                    }
                    return idList.stream();
                })
                .filter(userId -> !Objects.isNull(userId))
                .distinct()
                .collect(Collectors.toList());
        List<User> userList = userService.listUserByIds(userIds);
        Map<Long, User> userMap = Optional.ofNullable(userList).orElseGet(Lists::newArrayList)
                .stream()
                .collect(Collectors.toMap(User::getId, Function.identity()));
        for (SubjectDto subjectDto : list) {
            Long createUserId = subjectDto.getCreateUserId();
            User createUser = Optional.ofNullable(userMap.get(createUserId)).orElse(new User());
            subjectDto.setCreateUserName(createUser.getName());
            Long updateUserId = subjectDto.getUpdateUserId();
            if (!Objects.isNull(updateUserId)) {
                User updateUser = Optional.ofNullable(userMap.get(createUserId)).orElse(new User());
                subjectDto.setUpdateUserName(updateUser.getName());
            }
        }
    }

    private void fillHasChildren(List<SubjectDto> list) {
        if (CollectionUtil.isEmpty(list)) {
            return;
        }

        List<Long> subjectId = list.stream().map(SubjectDto::getId).distinct().collect(Collectors.toList());
        LambdaQueryWrapper<EduSubject> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.in(EduSubject::getParentId, subjectId);
        queryWrapper.eq(EduSubject::getDeleted, Boolean.FALSE);
        List<EduSubject> eduSubjects = eduSubjectMapper.selectList(queryWrapper);
        Set<Long> existParentIds = Optional.ofNullable(eduSubjects).orElseGet(Lists::newArrayList)
                .stream()
                .map(EduSubject::getParentId)
                .filter(id -> !Objects.isNull(id))
                .collect(Collectors.toSet());
        for (SubjectDto subjectDto : list) {
            subjectDto.setHasChildren(existParentIds.contains(subjectDto.getId()));
        }
    }

    public List<SubjectDto> queryAllFirstLevel() {
        LambdaQueryWrapper<EduSubject> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(EduSubject::getParentId, -1);
        queryWrapper.eq(EduSubject::getDeleted, Boolean.FALSE);
        List<EduSubject> eduSubjects = Optional.ofNullable(eduSubjectMapper.selectList(queryWrapper)).orElseGet(Lists::newArrayList);
        return BeanConvertor.convertCollection(eduSubjects, SubjectDto.class);
    }

    public void saveOrUpdate(SubjectSaveOrUpdateDto data) {
        if (Objects.isNull(data.getId())) {
            saveSubject(data);
        } else {
            updateSubject(data);
        }
    }

    private void updateSubject(SubjectSaveOrUpdateDto data) {
        EduSubject eduSubject = new EduSubject();
        eduSubject.setId(data.getId());
        // 一级分类
        if (data.getSubjectLevel() == 1) {
            eduSubject.setTitle(data.getFirstLevelName());
        }
        // 二级分类
        if (data.getSubjectLevel() == 2) {
            eduSubject.setTitle(data.getSecondLevelName());
        }
        eduSubject.setSort(data.getSort());
        eduSubject.setUpdateUserId(UserContextUtils.getUserId());
        eduSubject.setUpdateDate(new Date());
        eduSubjectMapper.updateById(eduSubject);
    }

    private void saveSubject(SubjectSaveOrUpdateDto data) {
        EduSubject eduSubject = new EduSubject();

        // 一级分类
        if (data.getSubjectLevel() == 1) {
            eduSubject.setParentId(-1L);
            eduSubject.setTitle(data.getFirstLevelName());
        }
        // 二级分类
        if (data.getSubjectLevel() == 2) {
            eduSubject.setParentId(data.getFirstLevel());
            eduSubject.setTitle(data.getSecondLevelName());
        }
        eduSubject.setSort(data.getSort());
        eduSubject.setCreateUserId(UserContextUtils.getUserId());
        eduSubject.setCreateDate(new Date());
        eduSubject.setDeleted(Boolean.FALSE);
        eduSubjectMapper.insert(eduSubject);
    }

    public List<EduSubject> listAllById(List<Long> subjectIds) {
        LambdaQueryWrapper<EduSubject> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.in(EduSubject::getId, subjectIds);
        queryWrapper.eq(EduSubject::getDeleted, Boolean.FALSE);
        return eduSubjectMapper.selectList(queryWrapper);
    }

    public List<SubjectCascaderDto> cascader() {
        List<EduSubject> parentSubjects = this.querySubjectByParentIds(Collections.singletonList(-1L));
        if (CollectionUtil.isEmpty(parentSubjects)) {
            return null;
        }
        List<Long> parentIds = parentSubjects.stream().map(EduSubject::getId).collect(Collectors.toList());
        Map<Long, List<EduSubject>> groupSubject = Optional.ofNullable(this.querySubjectByParentIds(parentIds)).orElse(new ArrayList<>()).stream()
                .collect(Collectors.groupingBy(EduSubject::getParentId));

        List<SubjectCascaderDto> cascades = parentSubjects.stream().map(subject -> {
            SubjectCascaderDto subjectCascaderDto = new SubjectCascaderDto();
            subjectCascaderDto.setValue(subject.getId());
            subjectCascaderDto.setLabel(subject.getTitle());
            List<SubjectCascaderDto> childrens = Optional.ofNullable(groupSubject.get(subject.getId())).orElseGet(Lists::newArrayList)
                    .stream()
                    .map(sub -> {
                        SubjectCascaderDto children = new SubjectCascaderDto();
                        children.setValue(sub.getId());
                        children.setLabel(sub.getTitle());
                        return children;
                    }).collect(Collectors.toList());
            subjectCascaderDto.setChildren(childrens);
            return subjectCascaderDto;
        }).collect(Collectors.toList());
        return cascades;
    }
}
