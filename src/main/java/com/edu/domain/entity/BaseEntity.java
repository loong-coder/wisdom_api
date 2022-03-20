package com.edu.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 3546062054540106309L;

    @TableId(type = IdType.AUTO)
    private Long id;

    protected Boolean deleted;

    protected Long createUserId;

    protected Date createDate;

    protected Long updateUserId;

    protected Date updateDate;
}
