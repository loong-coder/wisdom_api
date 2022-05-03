package com.edu.controller;


import com.edu.domain.dto.collect.EduCollectAddOrUpdateDto;
import com.edu.domain.request.BaseRequest;
import com.edu.domain.response.BaseResponse;
import com.edu.service.EduCollectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "收藏相关接口")
@RestController
@RequestMapping("collect")
public class EduCollectController {

    @Autowired
    private EduCollectService eduCollectService;


    @GetMapping("/{courseId}")
    @ApiOperation("查询是否收藏了该课程")
    public BaseResponse<Boolean> queryCollectInfo(@PathVariable("courseId") Long courseId){
        return BaseResponse.success( eduCollectService.queryCollectInfo(courseId));
    }

    @PostMapping
    @ApiOperation("收藏课程")
    public BaseResponse<Boolean> addOrUpdateCollect(@RequestBody BaseRequest<EduCollectAddOrUpdateDto> request){
        eduCollectService.addOrUpdateCollect(request.getData());
        return BaseResponse.success(Boolean.TRUE);
    }
}
