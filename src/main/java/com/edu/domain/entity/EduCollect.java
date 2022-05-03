package com.edu.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class EduCollect extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -7298928523207478464L;

    private Long courseId;

    private Long userId;

    private Boolean isCollect;

}
