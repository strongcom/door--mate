package com.doormate.service;

import com.doormate.domain.User;
import com.doormate.dto.UserDto;

public interface UserService {

    public User createUser(UserDto userDto);

}
