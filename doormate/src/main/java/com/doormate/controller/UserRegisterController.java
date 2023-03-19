package com.doormate.controller;


import com.doormate.dto.UserDto;
import com.doormate.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserRegisterController {


    private final UserService userService;


    @PostMapping("/register")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        userService.createUser(userDto);

        return new ResponseEntity<>(userDto, HttpStatus.OK);

    }

}
