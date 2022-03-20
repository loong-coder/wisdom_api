package com.edu.controller;

import com.edu.domain.dto.video.EduVideoDto;
import com.edu.domain.dto.video.EduVideoQueryDto;
import com.edu.domain.dto.video.EduVideoSaveOrUpdateDto;
import com.edu.domain.request.BaseRequest;
import com.edu.domain.response.BaseResponse;
import com.edu.service.EduVideoService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "视频相关接口")
@RestController
@RequestMapping("video")
public class EduVideoController {

    @Autowired
    private EduVideoService eduVideoService;

    @PostMapping("saveOrUpdate")
    @ApiOperation("课程列表")
    public BaseResponse<Boolean> saveOrUpdateVideo(@RequestBody BaseRequest<EduVideoSaveOrUpdateDto> request){
        eduVideoService.saveOrUpdate(request.getData());
        return BaseResponse.success(Boolean.TRUE);
    }

    @PostMapping("list")
    @ApiOperation("视频列表")
    public BaseResponse<PageInfo<EduVideoDto>> list(@RequestBody BaseRequest<EduVideoQueryDto> request) {
        PageInfo<EduVideoDto> pageInfo = eduVideoService.list(request.getData());
        return BaseResponse.success(pageInfo);
    }

    @DeleteMapping("/{videoId}")
    @ApiOperation("课程列表")
    public BaseResponse<Boolean> delete(@PathVariable("videoId") Long videoId){
        eduVideoService.delete(videoId);
        return BaseResponse.success(Boolean.TRUE);
    }

    @GetMapping("/listVideoByCourseId/{courseId}")
    @ApiOperation("根据课程id查询视频列表")
    public BaseResponse<List<EduVideoDto>> listVideoByCourseId(@PathVariable("courseId") Long courseId) {
        List<EduVideoDto> list = eduVideoService.listVideoByCourseId(courseId);
        return BaseResponse.success(list);
    }
}
