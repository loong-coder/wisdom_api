package com.edu.controller;

import com.edu.domain.dto.course.EduCourseDto;
import com.edu.domain.dto.course.EduCoursePageDto;
import com.edu.domain.dto.course.EduCourseQueryDto;
import com.edu.domain.dto.course.EduCourseSaveOrUpdateDto;
import com.edu.domain.dto.report.CourseViewCountTopDto;
import com.edu.domain.dto.report.SubjectPipDto;
import com.edu.domain.request.BaseRequest;
import com.edu.domain.response.BaseResponse;
import com.edu.service.EduCourseService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "课程相关接口")
@RestController
@RequestMapping(value = "course")
public class EduCourseController {

    @Autowired
    private EduCourseService eduCourseService;


    @PostMapping("list")
    @ApiOperation("课程列表")
    public BaseResponse<PageInfo<EduCourseDto>> list(@RequestBody BaseRequest<EduCourseQueryDto> request){
        PageInfo<EduCourseDto> list = eduCourseService.list(request.getData());
        return BaseResponse.success(list);
    }

    @PostMapping("saveOrUpdate")
    @ApiOperation("新增或者修改课程")
    public BaseResponse<Boolean> saveOrUpdate(@Valid @RequestBody BaseRequest<EduCourseSaveOrUpdateDto> request){
        eduCourseService.saveOrUpdate(request.getData());
        return BaseResponse.success(Boolean.TRUE);
    }


    @GetMapping("queryCourseById/{courseId}")
    @ApiOperation("根据id查询课程信息")
    public BaseResponse<EduCourseDto> queryCourseById(@PathVariable("courseId") Long courseId){
        EduCourseDto eduCoursePageDto = eduCourseService.queryCourseById(courseId);
        return BaseResponse.success(eduCoursePageDto);
    }

    @GetMapping("pageIndexQuery")
    @ApiOperation("前台首页查询课程信息")
    public BaseResponse<EduCoursePageDto> pageIndexQuery(){
        EduCoursePageDto eduCoursePageDto = eduCourseService.pageIndexQuery();
        return BaseResponse.success(eduCoursePageDto);
    }

    @PostMapping("pageCourseList")
    @ApiOperation("课程列表 不需要授权")
    public BaseResponse<PageInfo<List<EduCourseDto>>> pageCourseList(@RequestBody BaseRequest<EduCourseQueryDto> request){
        PageInfo<List<EduCourseDto>> list = eduCourseService.pageCourseList(request.getData());
        return BaseResponse.success(list);
    }

    @GetMapping("viewCountIncr/{courseId}")
    @ApiOperation("游览量自增")
    public BaseResponse<Boolean> viewCountIncr(@PathVariable("courseId") Long courseId){
        eduCourseService.viewCountIncr(courseId);
        return BaseResponse.success(Boolean.TRUE);
    }

    @GetMapping("subjectPip")
    @ApiOperation("课程分布图")
    public BaseResponse<List<SubjectPipDto>> subjectPip(){
        List<SubjectPipDto> subjectPipDtos = eduCourseService.subjectPipe();
        return BaseResponse.success(subjectPipDtos);
    }

    @GetMapping("courseViewCount")
    @ApiOperation("课程分布图")
    public BaseResponse<CourseViewCountTopDto> courseViewCount(){
        CourseViewCountTopDto courseViewCount = eduCourseService.courseViewCount();
        return BaseResponse.success(courseViewCount);
    }
}
