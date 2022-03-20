package com.edu.domain.dto.subject;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SubjectSaveOrUpdateDto {
    private Integer subjectLevel;

    private Integer sort;

    private String firstLevelName;

    private Long firstLevel;

    private String secondLevelName;

    private Long id;

    private Long parentId;
}
