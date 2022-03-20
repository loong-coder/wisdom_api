package com.edu.domain.dto.subject;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubjectCascaderDto {
    private Long value;
    private String label;

    private List<SubjectCascaderDto> children;
}
