package com.edu.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class SysMenu extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 853911728397502415L;
    
    private Long parentId;
    
    private Integer layer;
    
    private String title;
    
    private String path;
    
    private String icon;
    
    private Boolean disable;


}