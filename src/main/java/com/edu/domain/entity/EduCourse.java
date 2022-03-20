package com.edu.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
@TableName("edu_course")
public class EduCourse extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -41975592230070844L;

    // 教师id
    private Long teacherId;

    // 分类
    private Long subjectId;

    // 父分类
    private Long subjectParentId;

    // 课程名词
    private String title;

    // 保留字段  价格
    private Long price;

    // 课时
    private Integer lessonNum;

    // 封面图
    private String cover;

    // 游览量
    private Long viewCount;

    // 课程状态 1 已发布  未发布
    private Integer status;
}