package com.example.app.domain.user.controller;

import com.example.app.domain.user.dto.UserDTO;
import com.example.app.domain.user.dto.UserDTO.JoinGroupResult;
import com.example.app.domain.user.service.UserService;
import com.example.app.global.response.DataResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping
    public ResponseEntity<DataResponse<JoinGroupResult>> joinGroup(@RequestBody UserDTO.JoinGroup joinGroupDTO) {
        return DataResponse.success(userService.joinGroup(joinGroupDTO));
    }
}
