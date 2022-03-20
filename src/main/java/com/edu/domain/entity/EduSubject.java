package com.edu.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
@TableName("edu_subject")
public class EduSubject extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -86966205962835836L;

    private Long parentId;
    
    private String title;
    
    private Integer sort;
}