package com.edu.domain.dto.comment;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class EduCommentDto {
    private Long id;

    private Long courseId;

    private Long userId;

    private String userName;

    private String comment;

    private Boolean deleted;

    private Long createUserId;

    private Date createDate;

    private Long updateUserId;

    private Date updateDate;
}
