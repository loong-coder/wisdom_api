package com.edu.domain.dto.thumb;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EduThumbUpAddDto {
    private Integer businessType;

    private Long businessId;

    private Boolean thumbUp;
}
