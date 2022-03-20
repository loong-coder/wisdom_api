package com.edu.controller;

import com.edu.domain.dto.subject.SubjectCascaderDto;
import com.edu.domain.dto.subject.SubjectDto;
import com.edu.domain.dto.subject.SubjectQueryDto;
import com.edu.domain.dto.subject.SubjectSaveOrUpdateDto;
import com.edu.domain.request.BaseRequest;
import com.edu.domain.response.BaseResponse;
import com.edu.service.EduSubjectService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api(tags = "课程分类相关接口")
@RestController
@RequestMapping("subject")
public class EduSubjectController {

    @Autowired
    private EduSubjectService eduSubjectService;

    /**
     * 主题分页列表查询
     *
     * @param id 主键
     * @return 单条数据
     */
    @PostMapping("list")
    @ApiOperation("主题分类列表")
    public BaseResponse<PageInfo<SubjectDto>> list(@RequestBody BaseRequest<SubjectQueryDto> request) {
        PageInfo<SubjectDto> pageInfo = eduSubjectService.list(request.getData());
        return BaseResponse.success(pageInfo);
    }

    @GetMapping("querySubject/{parentId}")
    @ApiOperation("根据父级分类id查询子分类")
    public BaseResponse<List<SubjectDto>> querySubject(@PathVariable("parentId") Long parentId) {
        List<SubjectDto> pageInfo = eduSubjectService.querySubjectByParentId(parentId);
        return BaseResponse.success(pageInfo);
    }

    @GetMapping("queryAllFirstLevel")
    @ApiOperation("查询所有的一级分类")
    public BaseResponse<List<SubjectDto>> queryAllFirstLevel(){
        List<SubjectDto> pageInfo = eduSubjectService.queryAllFirstLevel();
        return BaseResponse.success(pageInfo);
    }

    @PostMapping("saveOrUpdate")
    @ApiOperation("新增或者修改分类")
    public BaseResponse<Boolean> saveOrUpdate(@RequestBody BaseRequest<SubjectSaveOrUpdateDto> request){
        eduSubjectService.saveOrUpdate(request.getData());
        return BaseResponse.success(Boolean.TRUE);
    }

    @GetMapping("cascader")
    @ApiOperation("获取层叠结构课程分类数据")
    public BaseResponse<List<SubjectCascaderDto>> cascader(){
        List<SubjectCascaderDto> subjectCascaderDtos = eduSubjectService.cascader();
        return BaseResponse.success(subjectCascaderDtos);
    }
}