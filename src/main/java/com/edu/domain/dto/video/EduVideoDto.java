package com.edu.domain.dto.video;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class EduVideoDto {

    private Long id;

    private Long courseId;

    private String title;

    private String videoSource;

    private String videoOriginalName;

    private Integer sort;

    private Long playCount;

    private Double duration;

    private Integer status;

    private Long size;

    private Boolean deleted;

    private Long createUserId;

    private Date createDate;

    private Long updateUserId;

    private Date updateDate;
}
