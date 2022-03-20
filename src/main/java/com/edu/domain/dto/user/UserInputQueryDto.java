package com.edu.domain.dto.user;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class UserInputQueryDto implements Serializable {

    private String value;

    private Long id;
}
