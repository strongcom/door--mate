package com.doormate.service.impl;

import com.doormate.domain.Authority;
import com.doormate.domain.Reminder;
import com.doormate.domain.User;
import com.doormate.dto.UserDto;
import com.doormate.exception.NotFoundException;
import com.doormate.repository.UserRepository;
import com.doormate.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final String ERROR_NOT_FIND_USER = "회원정보가 존재하지 않습니다.";

    @Transactional
    @Override
    public User createUser(UserDto userDto) {
        if(userRepository.findOneWithAuthoritiesByUsername(userDto.getUsername()).orElse(null) != null) {
            throw new RuntimeException("이미 가입되어 있는 사용자 입니다.");
        }

        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        User user = User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .nickname(userDto.getNickname())
                .authorities(Collections.singleton(authority))
                .build();

        return userRepository.save(user);
    }

    // user's reminder list
    public List<Reminder> findUserByReminder(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(ERROR_NOT_FIND_USER));
        return user.getReminderList();
    }
}
