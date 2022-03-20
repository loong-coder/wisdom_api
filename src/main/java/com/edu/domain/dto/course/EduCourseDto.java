package com.edu.domain.dto.course;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class EduCourseDto {
    private Long id;

    @ApiModelProperty(value = "教师id")
    private Long teacherId;

    @ApiModelProperty(value = "教师名称")
    private String teacherName;

    @ApiModelProperty(value = "分类id")
    private Long subjectId;

    @ApiModelProperty(value = "分类名")
    private String subjectName;

    @ApiModelProperty(value = "父分类")
    private Long subjectParentId;

    @ApiModelProperty(value = "父分类名")
    private String subjectParentName;

    @ApiModelProperty(value = "课程名称")
    private String title;

    @ApiModelProperty(value = "价格 保留字段")
    private Long price;

    @ApiModelProperty(value = "课时")
    private Integer lessonNum;

    @ApiModelProperty(value = "封面图")
    private String cover;

    @ApiModelProperty(value = "游览量")
    private Long viewCount;

    @ApiModelProperty(value = "课程状态 1 已发布  未发布")
    private Integer status;

    private Boolean deleted;

    private Long createUserId;

    @ApiModelProperty(value = "创建人名称")
    private String createUserName;

    private Date createDate;

    private Long updateUserId;

    private Date updateDate;
}
