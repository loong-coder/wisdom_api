package com.edu.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.io.Serializable;


@Data
@NoArgsConstructor
public class EduComment extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -90265967107732288L;

    private Long courseId;
    
    private Long userId;
    
    private String comment;

    private Boolean deleted;

    private Long createUserId;

    private Date createDate;

    private Long updateUserId;

    private Date updateDate;
}