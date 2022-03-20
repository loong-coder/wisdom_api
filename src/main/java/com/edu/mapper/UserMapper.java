package com.edu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edu.domain.dto.report.UserGrowthDto;
import com.edu.domain.entity.User;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {

    List<UserGrowthDto> userGrowth();
}
