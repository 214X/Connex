package com.burakkurucay.connex.mapper;

import com.burakkurucay.connex.dto.user.UserResponse;
import com.burakkurucay.connex.entity.user.User;

public class UserMapper {
    public static UserResponse toResponse(User user) {
        return new UserResponse(user.getId(),
            user.getEmail(),
            user.getAccountType(),
            user.getCreatedAt(),
            user.getUpdatedAt());
    }
}
