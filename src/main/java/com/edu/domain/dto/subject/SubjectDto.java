package com.edu.domain.dto.subject;

import lombok.Data;

import java.util.Date;

@Data
public class SubjectDto {
    private Long id;

    private Long parentId;

    private String parentName;

    private String title;

    private Integer sort;

    private Long createUserId;

    private String createUserName;

    private Date createDate;

    private Long updateUserId;

    private String updateUserName;

    private Date updateDate;

    private Boolean hasChildren;
}
