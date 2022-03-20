package com.edu.domain.dto.comment;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EduCommentAddDto {
    private String comment;

    private Long courseId;
}
