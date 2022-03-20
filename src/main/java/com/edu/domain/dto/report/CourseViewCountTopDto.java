package com.edu.domain.dto.report;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CourseViewCountTopDto {
    List<String> titles;
    List<Long> counts;
}
