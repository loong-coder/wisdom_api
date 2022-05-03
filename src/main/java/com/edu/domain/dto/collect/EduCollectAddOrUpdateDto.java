package com.edu.domain.dto.collect;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EduCollectAddOrUpdateDto {

    private Long courseId;

    private Boolean isCollect;
}
