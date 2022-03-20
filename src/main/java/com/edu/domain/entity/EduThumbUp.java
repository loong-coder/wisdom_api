package com.edu.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class EduThumbUp extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -34329622190845809L;
    
    private Long userId;
    
    private Long businessId;

    // 1 视频点赞
    private Integer businessType;
    
    private Boolean thumbUp;

}