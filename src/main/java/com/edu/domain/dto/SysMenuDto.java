package com.edu.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class SysMenuDto {
    private Long id;

    private Long parentId;

    private String index;

    private String layer;

    private String title;

    private String path;

    private String icon;

    private Boolean disable;

    private List<SysMenuDto> children;
}
