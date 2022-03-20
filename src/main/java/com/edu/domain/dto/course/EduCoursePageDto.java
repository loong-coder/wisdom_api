package com.edu.domain.dto.course;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class EduCoursePageDto {

    @ApiModelProperty("推荐课程列表")
    private List<List<EduCourseDto>> popularEduCourseDtos;

    @ApiModelProperty("最新的课程列表")
    private List<List<EduCourseDto>> newestEduCourseDtos;


}
