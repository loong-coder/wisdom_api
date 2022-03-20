package com.edu.domain.dto.report;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CourseViewDto {
    private String title;

    private Long viewCount;
}
