package com.edu.util;

import com.edu.domain.dto.user.UserContextDto;

import java.util.Optional;

public class UserContextUtils {

    private static ThreadLocal<UserContextDto> userContextDtoThreadLocal = new ThreadLocal<>();

    public static void setUserContext(UserContextDto userContext) {
        userContextDtoThreadLocal.set(userContext);
    }


    public static void removeUserContext() {
        userContextDtoThreadLocal.remove();
    }

    public static Long getUserId() {
        return Optional.ofNullable(userContextDtoThreadLocal.get()).orElse(new UserContextDto()).getId();
    }

    public static String getUserEmail() {
        return Optional.ofNullable(userContextDtoThreadLocal.get()).orElse(new UserContextDto()).getEmail();
    }
}
