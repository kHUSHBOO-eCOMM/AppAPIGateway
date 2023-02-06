package com.app.accout.management.service.service;

import com.app.accout.management.service.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public interface UserService extends UserDetailsService {

    UserDto create(UserDto userDto);
    UserDto getUserDetailByEmail(String email);
}
