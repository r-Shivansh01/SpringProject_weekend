package com.mediflow.authservice.mapper;

import com.mediflow.authservice.dto.UserResponse;
import com.mediflow.authservice.entity.User;

public final class UserMapper {

    private UserMapper() {
    }

    public static UserResponse toResponse(User user) {

        return new UserResponse(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getRole(),
                user.getCreatedAt()
        );
    }
}
