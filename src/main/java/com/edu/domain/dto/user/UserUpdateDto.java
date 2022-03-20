package com.edu.domain.dto.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserUpdateDto {
    private Long id;

    private String name;

    private Boolean gender;

    private String phoneNo;

    private Boolean disable;

    private String avatar;
}
