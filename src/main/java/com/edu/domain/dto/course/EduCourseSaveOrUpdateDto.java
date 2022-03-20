package com.edu.domain.dto.course;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class EduCourseSaveOrUpdateDto {
    private Long id;

    @ApiModelProperty(value = "教师")
    @NotNull(message = "教师不能为空")
    private Long teacherId;

    @ApiModelProperty(value = "分类id")
    @NotNull(message = "子分类不能为空")
    private Long subjectId;

    @ApiModelProperty(value = "父分类")
    @NotNull(message = "父分类不能为空")
    private Long subjectParentId;

    @ApiModelProperty(value = "课程名称")
    @NotNull(message = "课程名称分类不能为空")
    private String title;

    @ApiModelProperty(value = "课时")
    private Integer lessonNum;

    @ApiModelProperty(value = "封面图")
    private String cover;

    @ApiModelProperty(value = "课程状态 1 已发布  未发布")
    private Integer status;

    @ApiModelProperty(value = "删除")
    private Boolean deleted;
}
