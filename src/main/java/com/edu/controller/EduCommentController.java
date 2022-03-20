package com.edu.controller;

import com.edu.domain.dto.comment.EduCommentAddDto;
import com.edu.domain.dto.comment.EduCommentDto;
import com.edu.domain.dto.comment.EduCommentQueryDto;
import com.edu.domain.request.BaseRequest;
import com.edu.domain.response.BaseResponse;
import com.edu.service.EduCommentService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "课程评论")
@RestController
@RequestMapping(value = "comment")
public class EduCommentController {

    @Autowired
    private EduCommentService eduCommentService;

    @PostMapping(value = "listPageInfo")
    @ApiOperation("评论列表")
    public BaseResponse<PageInfo<EduCommentDto> > listPageInfo(@RequestBody BaseRequest<EduCommentQueryDto> request){
        PageInfo<EduCommentDto> eduCommentDtos = eduCommentService.queryComment(request.getData());
        return BaseResponse.success(eduCommentDtos);
    }

    @PostMapping(value = "add")
    @ApiOperation("新增评论")
    public BaseResponse<Boolean> add(@RequestBody BaseRequest<EduCommentAddDto> request){
        eduCommentService.add(request.getData());
        return BaseResponse.success(Boolean.TRUE);
    }
}
