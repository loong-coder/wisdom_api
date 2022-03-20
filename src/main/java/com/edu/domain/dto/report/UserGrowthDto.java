package com.edu.domain.dto.report;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserGrowthDto {
    private String time;

    private Long count;
}
