package com.edu.domain.dto.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserUpdatePassWordDto {

    private Long userId;

    private String currentPass;

    private String password;
}
