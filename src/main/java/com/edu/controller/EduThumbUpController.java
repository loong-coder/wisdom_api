package com.edu.controller;

import com.edu.domain.dto.thumb.EduThumbUpAddDto;
import com.edu.domain.dto.thumb.EduThumbUpDto;
import com.edu.domain.dto.thumb.EduThumbUpQueryDto;
import com.edu.domain.request.BaseRequest;
import com.edu.domain.response.BaseResponse;
import com.edu.service.EduThumbUpService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "点赞相关接口")
@RestController
@RequestMapping("thumbUp")
public class EduThumbUpController {

    @Autowired
    private EduThumbUpService eduThumbUpService;

    @PostMapping("queryThumbUp")
    @ApiOperation("查询点赞信息")
    public BaseResponse<EduThumbUpDto> queryThumbUp(@RequestBody BaseRequest<EduThumbUpQueryDto> request){
        EduThumbUpDto eduThumbUpDto = eduThumbUpService.queryThumbUp(request.getData());
        return BaseResponse.success(eduThumbUpDto);
    }

    @PostMapping("add")
    @ApiOperation("查询点赞信息")
    public BaseResponse<Void> add(@RequestBody BaseRequest<EduThumbUpAddDto> request){
        eduThumbUpService.thumbUp(request.getData());
        return BaseResponse.success();
    }
}
