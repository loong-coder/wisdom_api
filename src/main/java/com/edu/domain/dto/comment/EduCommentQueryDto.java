package com.edu.domain.dto.comment;

import com.edu.domain.dto.BasePage;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EduCommentQueryDto extends BasePage {
    private Long courseId;
}
