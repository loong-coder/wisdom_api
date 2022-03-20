package com.edu.domain.dto.user;

import com.edu.domain.dto.BasePage;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserQueryDto extends BasePage {
    private String name;

    private String email;
}
