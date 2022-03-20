package com.edu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edu.domain.dto.report.CourseViewCountTopDto;
import com.edu.domain.dto.report.CourseViewDto;
import com.edu.domain.dto.report.SubjectPipDto;
import com.edu.domain.entity.EduCourse;

import java.util.List;

public interface EduCourseMapper extends BaseMapper<EduCourse> {
    List<SubjectPipDto> subjectPipe();

    List<CourseViewDto> courseViewCount();
}
