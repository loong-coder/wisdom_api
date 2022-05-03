package com.edu.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.edu.domain.dto.course.EduCourseDto;
import com.edu.domain.dto.course.EduCoursePageDto;
import com.edu.domain.dto.course.EduCourseQueryDto;
import com.edu.domain.dto.course.EduCourseSaveOrUpdateDto;
import com.edu.domain.dto.report.CourseViewCountTopDto;
import com.edu.domain.dto.report.CourseViewDto;
import com.edu.domain.dto.report.SubjectPipDto;
import com.edu.domain.dto.user.UserDto;
import com.edu.domain.entity.EduCourse;
import com.edu.domain.entity.EduSubject;
import com.edu.domain.entity.User;
import com.edu.mapper.EduCourseMapper;
import com.edu.util.BeanConvertor;
import com.edu.util.PageUtil;
import com.edu.util.UserContextUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class EduCourseService {

    @Resource
    private EduCourseMapper eduCourseMapper;

    @Resource
    private EduSubjectService eduSubjectService;

    @Resource
    private EduCollectService eduCollectService;

    @Resource
    private UserService userService;

    public PageInfo<EduCourseDto> list(EduCourseQueryDto queryDto) {
        UserDto userDto = userService.queryUserById(UserContextUtils.getUserId());
        if (userDto.getRole() == 2) {
            queryDto.setTeacherId(UserContextUtils.getUserId());
        }
        List<EduCourse> eduCourses = queryCourseList(queryDto);
        PageInfo<EduCourseDto> pageInfo = PageUtil.pageInfoCopy(new PageInfo<>(eduCourses), EduCourseDto.class);
        fillUserName(pageInfo.getList());
        fillSubjectName(pageInfo.getList());
        return pageInfo;
    }

    private List<EduCourse> queryCourseList(EduCourseQueryDto queryDto) {
        LambdaQueryWrapper<EduCourse> queryWrapper = Wrappers.lambdaQuery();
        if (StrUtil.isNotBlank(queryDto.getTitle())) {
            queryWrapper.like(EduCourse::getTitle, queryDto.getTitle());
        }
        if (!Objects.isNull(queryDto.getStatus())) {
            queryWrapper.eq(EduCourse::getStatus, queryDto.getStatus());
        }
        if(!Objects.isNull(queryDto.getSubjectParentId())){
            queryWrapper.eq(EduCourse::getSubjectParentId, queryDto.getSubjectParentId());
        }
        if (!Objects.isNull(queryDto.getTeacherId())){
            queryWrapper.eq(EduCourse::getTeacherId, queryDto.getTeacherId());
        }
        queryWrapper.eq(EduCourse::getDeleted, Boolean.FALSE);
        if (Optional.ofNullable(queryDto.getQueryCollect()).orElse(Boolean.FALSE)){
            Long userId = UserContextUtils.getUserId();
            List<Long> courseIds = eduCollectService.queryCollectByUserId(userId);
            if (CollectionUtil.isEmpty(courseIds)){
                return null;
            }
            queryWrapper.in(EduCourse::getId, courseIds);
        }

        queryWrapper.orderByDesc(EduCourse::getCreateDate);
        if (Optional.ofNullable(queryDto.getPageNum()).orElse(0) > 0
                && Optional.ofNullable(queryDto.getSize()).orElse(0) > 0) {
            PageHelper.startPage(queryDto.getPageNum(), queryDto.getSize());
        }
        List<EduCourse> eduCourses = eduCourseMapper.selectList(queryWrapper);
        return eduCourses;
    }

    private void fillUserName(List<EduCourseDto> eduCourseDtos) {
        if (CollectionUtil.isEmpty(eduCourseDtos)) {
            return;
        }
        List<Long> userIds = Optional.ofNullable(eduCourseDtos).orElseGet(Lists::newArrayList)
                .stream()
                .flatMap(dto -> Lists.newArrayList(dto.getCreateUserId(), dto.getTeacherId()).stream())
                .filter(userId -> !Objects.isNull(userId))
                .distinct()
                .collect(Collectors.toList());
        List<User> userList = userService.listUserByIds(userIds);
        Map<Long, User> userMap = Optional.ofNullable(userList).orElseGet(Lists::newArrayList)
                .stream()
                .collect(Collectors.toMap(User::getId, Function.identity()));
        Function<Long, String> getUserNameFunc = id -> Optional.ofNullable(userMap.get(id)).orElseGet(User::new).getName();
        for (EduCourseDto eduCourseDto : eduCourseDtos) {
            eduCourseDto.setCreateUserName(getUserNameFunc.apply(eduCourseDto.getCreateUserId()));
            eduCourseDto.setTeacherName(getUserNameFunc.apply(eduCourseDto.getTeacherId()));
        }
    }


    private void fillSubjectName(List<EduCourseDto> eduCourseDtos) {
        if (CollectionUtil.isEmpty(eduCourseDtos)) {
            return;
        }
        List<Long> subjectIds = Optional.ofNullable(eduCourseDtos).orElseGet(Lists::newArrayList)
                .stream()
                .flatMap(dto -> Lists.newArrayList(dto.getSubjectId(), dto.getSubjectParentId()).stream())
                .filter(subjectId -> !Objects.isNull(subjectId))
                .distinct()
                .collect(Collectors.toList());
        List<EduSubject> eduSubjects = eduSubjectService.listAllById(subjectIds);
        Map<Long, EduSubject> eduSubjectMap = Optional.ofNullable(eduSubjects).orElseGet(Lists::newArrayList)
                .stream()
                .collect(Collectors.toMap(EduSubject::getId, Function.identity()));

        Function<Long, String> getSubjectNameFunc = id -> Optional.ofNullable(eduSubjectMap.get(id)).orElseGet(EduSubject::new).getTitle();
        for (EduCourseDto eduCourseDto : eduCourseDtos) {
            eduCourseDto.setSubjectName(getSubjectNameFunc.apply(eduCourseDto.getSubjectId()));
            eduCourseDto.setSubjectParentName(getSubjectNameFunc.apply(eduCourseDto.getSubjectParentId()));
        }
    }

    public void saveOrUpdate(EduCourseSaveOrUpdateDto eduCourseSaveOrUpdateDto) {
        EduCourse eduCourse = BeanConvertor.convert(eduCourseSaveOrUpdateDto, EduCourse.class);
        if (Objects.isNull(eduCourse.getId())){
            eduCourse.setDeleted(Boolean.FALSE);
            eduCourse.setCreateUserId(UserContextUtils.getUserId());
            eduCourse.setCreateDate(new Date());
            eduCourseMapper.insert(eduCourse);
        }else {
            eduCourse.setUpdateUserId(UserContextUtils.getUserId());
            eduCourse.setUpdateDate(new Date());
            eduCourseMapper.updateById(eduCourse);
        }
    }

    public EduCoursePageDto pageIndexQuery() {
        List<EduCourseDto>  popularEduCourseDtos = queryPopularEduCourseDtos();
        List<List<EduCourseDto>> partition = Lists.partition(popularEduCourseDtos, 4);

        List<EduCourseDto>  newestEduCourseDtos = queryNewestEduCourseDtos();
        List<List<EduCourseDto>> partition2 = Lists.partition(newestEduCourseDtos, 4);

        EduCoursePageDto eduCoursePageDto = new EduCoursePageDto();
        eduCoursePageDto.setPopularEduCourseDtos(partition);
        eduCoursePageDto.setNewestEduCourseDtos(partition2);
        return eduCoursePageDto;
    }

    private List<EduCourseDto> queryPopularEduCourseDtos() {
        LambdaQueryWrapper<EduCourse> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(EduCourse::getDeleted, Boolean.FALSE);
        queryWrapper.orderByDesc(Lists.newArrayList(EduCourse::getViewCount, EduCourse::getCreateDate));
        queryWrapper.last("limit 0,8");
        List<EduCourse> eduCourses = eduCourseMapper.selectList(queryWrapper);
        return BeanConvertor.convertCollection(eduCourses, EduCourseDto.class);
    }

    private List<EduCourseDto> queryNewestEduCourseDtos() {
        LambdaQueryWrapper<EduCourse> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(EduCourse::getDeleted, Boolean.FALSE);
        queryWrapper.orderByDesc(Lists.newArrayList(EduCourse::getCreateDate));
        queryWrapper.last("limit 0,8");
        List<EduCourse> eduCourses = eduCourseMapper.selectList(queryWrapper);
        return BeanConvertor.convertCollection(eduCourses, EduCourseDto.class);
    }

    public PageInfo<List<EduCourseDto>> pageCourseList(EduCourseQueryDto data) {
        List<EduCourse> eduCourses = Optional.ofNullable(this.queryCourseList(data)).orElseGet(Lists::newArrayList);
        PageInfo<EduCourse> sourcePageInfo = new PageInfo<>(eduCourses);

        List<EduCourseDto> eduCourseDtos = BeanConvertor.convertCollection(eduCourses, EduCourseDto.class);
        List<List<EduCourseDto>> partition = Lists.partition(eduCourseDtos, 4);
        PageInfo<List<EduCourseDto>> respPageInfo = new PageInfo<>();
        respPageInfo.setPageNum(sourcePageInfo.getPageNum());
        respPageInfo.setPageSize(sourcePageInfo.getPageSize());
        respPageInfo.setSize(sourcePageInfo.getSize());
        respPageInfo.setStartRow(sourcePageInfo.getStartRow());
        respPageInfo.setEndRow(sourcePageInfo.getEndRow());
        respPageInfo.setPages(sourcePageInfo.getPages());
        respPageInfo.setPrePage(sourcePageInfo.getPrePage());
        respPageInfo.setNextPage(sourcePageInfo.getNextPage());
        respPageInfo.setIsFirstPage(sourcePageInfo.isIsFirstPage());
        respPageInfo.setIsLastPage(sourcePageInfo.isIsLastPage());
        respPageInfo.setHasPreviousPage(sourcePageInfo.isHasPreviousPage());
        respPageInfo.setHasNextPage(sourcePageInfo.isHasNextPage());
        respPageInfo.setNavigatePages(sourcePageInfo.getNavigatePages());
        respPageInfo.setNavigatepageNums(sourcePageInfo.getNavigatepageNums());
        respPageInfo.setNavigateFirstPage(sourcePageInfo.getNavigateFirstPage());
        respPageInfo.setNavigateLastPage(sourcePageInfo.getNavigateLastPage());
        respPageInfo.setTotal(sourcePageInfo.getTotal());

        respPageInfo.setList(partition);

        return respPageInfo;
    }

    public EduCourseDto queryCourseById(Long courseId) {
        EduCourse eduCourse = getEduCourseById(courseId);
        EduCourseDto eduCourseDto = BeanConvertor.convert(eduCourse, EduCourseDto.class);
        fillUserName(Collections.singletonList(eduCourseDto));
        return eduCourseDto;
    }

    private EduCourse getEduCourseById(Long courseId) {
        LambdaQueryWrapper<EduCourse> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(EduCourse::getDeleted, Boolean.FALSE);
        queryWrapper.eq(EduCourse::getId, courseId);
        EduCourse eduCourse = eduCourseMapper.selectOne(queryWrapper);
        return eduCourse;
    }

    public void viewCountIncr(Long courseId) {
        EduCourse eduCourse = getEduCourseById(courseId);
        Long viewCount = Optional.ofNullable(eduCourse.getViewCount()).orElse(0L);
        eduCourse.setViewCount(viewCount+1);
        eduCourseMapper.updateById(eduCourse);
    }

    public List<SubjectPipDto> subjectPipe() {
        return  eduCourseMapper.subjectPipe();
    }

    public CourseViewCountTopDto courseViewCount() {
        List<String> titles =  Lists.newArrayList();
        List<Long> counts = Lists.newArrayList();
        List<CourseViewDto> courseViewDtos = Optional.ofNullable(eduCourseMapper.courseViewCount()).orElseGet(Lists::newArrayList);
        for (int i = courseViewDtos.size()-1; i >= 0 ; i--) {
            titles.add(courseViewDtos.get(i).getTitle());
            counts.add(Optional.ofNullable(courseViewDtos.get(i).getViewCount()).orElse(0L));
        }
//        for (CourseViewDto courseViewDto : courseViewDtos) {
//
//        }
        CourseViewCountTopDto courseViewCountTopDto = new CourseViewCountTopDto();
        courseViewCountTopDto.setCounts(counts);
        courseViewCountTopDto.setTitles(titles);
        return courseViewCountTopDto;
    }
}
