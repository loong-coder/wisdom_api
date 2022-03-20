package com.edu.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@TableName("edu_video")
public class EduVideo extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 324994726066878113L;
    
    private Long courseId;
    
    private String title;
    
    private String videoSource;
    
    private String videoOriginalName;
    
    private Integer sort;
    
    private Long playCount;

    private Long praiseCount;
    
    private Double duration;
    
    private Integer status;
    
    private Long size;

    private Boolean deleted;

    private Long createUserId;

    private Date createDate;

    private Long updateUserId;

    private Date updateDate;

}