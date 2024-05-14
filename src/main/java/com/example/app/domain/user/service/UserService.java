package com.example.app.domain.user.service;

import com.example.app.data.entity.GroupsEntity;
import com.example.app.data.entity.UsersEntity;
import com.example.app.data.repository.UsersRepository;
import com.example.app.domain.group.service.GroupService;
import com.example.app.domain.user.dto.UserDTO.JoinGroup;
import com.example.app.domain.user.dto.UserDTO.JoinGroupResult;
import com.example.app.domain.user.dto.UserDTO.LeaveGroup;
import com.example.app.domain.user.dto.UserDTO.LeaveGroupResult;
import com.example.app.global.error.exception.BusinessException;
import com.example.app.global.error.exception.ErrorCode;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UsersRepository userRepository;
    private final GroupService groupService;


    public UserService(UsersRepository userRepository, GroupService groupService) {
        this.userRepository = userRepository;
        this.groupService = groupService;
    }


    public UsersEntity findByUserName(String name) {
        Optional<UsersEntity> optional = userRepository.findByNickname(name);
        return optional.orElse(null);
    }

    public List<UsersEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public JoinGroupResult joinGroup(JoinGroup joinGroupDTO) {

        UsersEntity user = findByUserName(joinGroupDTO.getUserNickname());
        if (user == null) throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        GroupsEntity group = groupService.findByName(joinGroupDTO.getGroupName());
        if (group == null) throw new BusinessException(ErrorCode.GROUP_NOT_FOUND);
        user.setGroup(group);
        userRepository.save(user);

        return JoinGroupResult.builder()
            .userId(user.getId())
            .userNickname(user.getNickname())
            .groupId(group.getId())
            .groupName(group.getName())
            .build();
    }

    public LeaveGroupResult leaveGroup(LeaveGroup leaveGroupDTO) {
        UsersEntity user = findByUserName(leaveGroupDTO.getUserNickname());
        if (user == null) throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        user.setGroup(null);
        userRepository.save(user);

        return LeaveGroupResult.builder()
            .userId(user.getId())
            .userNickname(user.getNickname())
            .group(user.getGroup())
            .build();
    }
}
