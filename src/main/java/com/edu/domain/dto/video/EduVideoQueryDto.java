package com.edu.domain.dto.video;

import com.edu.domain.dto.BasePage;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class EduVideoQueryDto extends BasePage implements Serializable {
    private static final long serialVersionUID = 8891695913206317373L;

    private Long courseId;

    private String title;
}
