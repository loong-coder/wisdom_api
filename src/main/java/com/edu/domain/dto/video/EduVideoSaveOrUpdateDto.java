package com.edu.domain.dto.video;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EduVideoSaveOrUpdateDto {
    private Long id;

    private Long courseId;

    private String title;

    private String videoSource;

    private String videoOriginalName;

    private Integer sort;

    private Double duration;

    private Long size;
}
