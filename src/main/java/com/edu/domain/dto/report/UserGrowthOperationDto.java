package com.edu.domain.dto.report;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserGrowthOperationDto {
    List<String> times;
    List<Long> series;
}
