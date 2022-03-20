package com.edu.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@TableName(value = "sys_user")
@Data
@NoArgsConstructor
public class User extends BaseEntity{

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String name;

    private String email;

    private String passWord;

    private Boolean gender;

    private String phoneNo;

    private String avatar;

    // 1 管理员 2 教师 3 学生
    private Integer role;

    private Boolean disable;

    private Date registerDate;

    private Date lastLoginDate;

}
