package com.edu.domain.dto.course;

import com.edu.domain.dto.BasePage;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class EduCourseQueryDto extends BasePage implements Serializable {
    private static final long serialVersionUID = -5544438296161308571L;

    @ApiModelProperty(value = "课程名称")
    private String title;

    @ApiModelProperty(value = "课程父类id")
    private Long subjectParentId;

    @ApiModelProperty(value = "课程状态")
    private Integer status;

    @ApiModelProperty(value = "教师id")
    private Long teacherId;

    @ApiModelProperty(value = "查询我的收藏")
    private Boolean queryCollect;
}
