package com.edu.domain.dto.user;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class UserDto {
    private Long id;

    private String name;

    private String email;

    private Boolean gender;

    private Integer role;

    private String phoneNo;

    private Boolean disable;

    private String avatar;

    private Date registerDate;

    private Date lastLoginDate;

    private Date updateDate;
}
